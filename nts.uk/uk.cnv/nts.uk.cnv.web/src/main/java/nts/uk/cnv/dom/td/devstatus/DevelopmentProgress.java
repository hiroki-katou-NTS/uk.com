package nts.uk.cnv.dom.td.devstatus;

import lombok.Value;

/**
 * 開発状況を進捗（到達地点を指定）で表したクラス
 */
@Value
public class DevelopmentProgress {

	/**
	 * 基準となる開発状況
	 */
	DevelopmentStatus baseline;
	
	/**
	 * 基準となる開発状況が達成済みならtrue
	 */
	boolean isAchieved;
	
	/**
	 * 未検収すべて
	 * @return
	 */
	public static DevelopmentProgress notAccepted() {
		// 「検収済み」を「達成していない」
		return new DevelopmentProgress(DevelopmentStatus.ACCEPTED, false);
	}
}
