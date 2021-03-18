package nts.uk.cnv.app.td.command.feature;

import lombok.Value;

/**
 * Featureを登録する
 */
@Value
public class FeatureCommand {
	private String featureName;
	private String description;

}
