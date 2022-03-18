package io.mosip.kernel.bio.converter.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
* @author Janardhan B S
* @since 1.0.0
*/
@Data
public class ConvertRequestDto {
    @JsonProperty
	public Map<String, String> values;
    @JsonProperty
    public String sourceFormat;
    @JsonProperty
    public String targetFormat;
    @JsonProperty
    public Map<String, String> sourceParameters;
    @JsonProperty
    public Map<String, String> targetParameters;
}
