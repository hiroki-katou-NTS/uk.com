package nts.uk.cnv.ws.feature;

import lombok.Value;

/**
 * Featureの情報
 */
@Value
public class FeatureInfoDto {
	String id;
	String name;
	String description;
}
