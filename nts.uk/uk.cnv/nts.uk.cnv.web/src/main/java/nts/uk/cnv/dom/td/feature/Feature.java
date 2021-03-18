package nts.uk.cnv.dom.td.feature;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.gul.text.IdentifierUtil;

/**
 * Feature
 */
@AllArgsConstructor
@Getter
public class Feature {
	
	/** Feature ID */
	String featureId;
	
	/** Feature名 */
	String name;
	
	/** 説明 */
	String description;
	
	/**
	 * 新しいFeatureを生成する
	 * @param featureName
	 * @param description
	 * @return
	 */
	public static Feature newFeature(String featureName, String description) {
		return new Feature(IdentifierUtil.randomUniqueId(), featureName, description);
	}
}
