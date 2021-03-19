package nts.uk.cnv.dom.td.feature;

import java.util.List;

import nts.uk.cnv.dom.td.alteration.summary.AlterationSummary;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;

/**
 * Featureに属するorutaを取得する
 * @author hiroki_katou
 *
 */
public class GetFeatureAlter {
	
	/**
	 * 未発注のorutaを取得する
	 * @param require
	 * @param featureId
	 * @return
	 */
	public static List<AlterationSummary> getOfNotOrdered(Require require, String featureId) {
		return require.getOfNotOrdered(featureId, DevelopmentProgress.notOrdered());
	}
	
	public interface Require {
		List<AlterationSummary> getOfNotOrdered(String featureId, DevelopmentProgress devProgress);
	}
}
