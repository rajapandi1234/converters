package io.mosip.kernel.bio.converter.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import io.mosip.kernel.bio.converter.constant.ConverterErrorCode;
import io.mosip.kernel.bio.converter.constant.SourceFormatCode;
import io.mosip.kernel.bio.converter.constant.TargetFormatCode;
import io.mosip.kernel.bio.converter.dto.ConvertRequestDto;
import io.mosip.kernel.bio.converter.exception.ConversionException;
import io.mosip.kernel.bio.converter.service.IConverterApi;
import io.mosip.kernel.bio.converter.service.impl.ConverterServiceImpl;
import io.mosip.kernel.core.http.RequestWrapper;
import io.mosip.kernel.core.http.ResponseFilter;
import io.mosip.kernel.core.http.ResponseWrapper;

/**
* Convert Handler Controller
*
*
* @author Janardhan B S
* @since 1.0.0
*/

@RestController
public class ConvertController {
	/**
	* Service instance {@link ConverterServiceImpl}
	*/
	@Autowired
	private IConverterApi converterService;
	
	/**
	* Verifies mapping of input public key with any machine.
	* if valid returns corresponding keyIndex.
	*
	* @param convertRequestDto
	* @return
	*/
	@ResponseFilter
	@PostMapping(value = "/convert", produces = "application/json")
	public ResponseWrapper<Map<String, String>> convert(
		@RequestBody @Valid RequestWrapper<ConvertRequestDto> convertRequestDto) throws Exception {
		
		ConverterErrorCode errorCode = null;
		ResponseWrapper<Map<String, String>> responseDto = new ResponseWrapper<Map<String, String>>();

		if (convertRequestDto != null && convertRequestDto.getRequest() != null
			&& convertRequestDto.getRequest().getValues () != null
			&& convertRequestDto.getRequest().getSourceFormat () != null
			&& convertRequestDto.getRequest().getTargetFormat () != null)
		{
			try
			{
				if (convertRequestDto.getRequest().getValues() == null || convertRequestDto.getRequest().getValues().isEmpty())
				{
					errorCode = ConverterErrorCode.INVALID_REQUEST_EXCEPTION;
					throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
				}

				String sourceFormat = convertRequestDto.getRequest().getSourceFormat();
				if (sourceFormat == null || sourceFormat.trim().length() == 0 || !SourceFormatCode.validCode(sourceFormat))
				{
					errorCode = ConverterErrorCode.INVALID_SOURCE_EXCEPTION;
					throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
				}

				String targetFormat = convertRequestDto.getRequest().getTargetFormat();
				if (targetFormat == null || targetFormat.trim().length() == 0 || !TargetFormatCode.validCode(targetFormat))
				{
					errorCode = ConverterErrorCode.INVALID_TARGET_EXCEPTION;
					throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
				}

				if (SourceFormatCode.fromCode(sourceFormat).getCode ().equalsIgnoreCase (SourceFormatCode.ISO19794_6_2011.getCode ()) &&
					TargetFormatCode.fromCode(targetFormat).getCode ().equalsIgnoreCase (TargetFormatCode.ISO19794_6_2011_JPEG.getCode ()))
				{
					errorCode = ConverterErrorCode.TARGET_FORMAT_EXCEPTION;
					throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
				}

				responseDto.setResponse (converterService.convert(convertRequestDto.getRequest().getValues(),
					convertRequestDto.getRequest().getSourceFormat(),
					convertRequestDto.getRequest().getTargetFormat(),
					convertRequestDto.getRequest().getSourceParameters(),
					convertRequestDto.getRequest().getTargetParameters()));
			}
			catch (ConversionException ex)
			{
				responseDto.setResponse(null);
				errorCode = ConverterErrorCode.fromErrorCode(ex.getErrorCode());
				throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
			}
		}
		else
		{
			errorCode = ConverterErrorCode.INPUT_SOURCE_EXCEPTION;
			throw new ConversionException (errorCode.getErrorCode(), errorCode.getErrorMessage());
		}
		return responseDto;
	}
}
