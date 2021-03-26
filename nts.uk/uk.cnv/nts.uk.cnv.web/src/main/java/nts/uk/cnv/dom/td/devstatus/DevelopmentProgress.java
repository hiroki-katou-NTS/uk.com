package nts.uk.cnv.dom.td.devstatus;

import lombok.Value;

/**
 * 開発進捗状況
 * 	※開発状況を進捗（到達地点を指定）で表したクラス
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
	 * 指定した開発状況に到達していない
	 * @return
	 */
	public static DevelopmentProgress not(DevelopmentStatus status) {
		return new DevelopmentProgress(status, false);
	}
	
	/**
	 * 未発注すべて
	 * @return
	 */
	public static DevelopmentProgress notOrdered() {
		// 「発注済み」を「達成していない」
		return new DevelopmentProgress(DevelopmentStatus.ORDERED, false);
	}
	
	/**
	 * 発注済みすべて
	 * @return
	 */
	public static DevelopmentProgress ordered() {
		// 「発注済み」を「達成している」
		return new DevelopmentProgress(DevelopmentStatus.ORDERED, true);
	}
	
	/**
	 * 未納品すべて
	 * @return
	 */
	public static DevelopmentProgress notDeliveled() {
		// 「納品済み」を「達成していない」
		return new DevelopmentProgress(DevelopmentStatus.DELIVERED, false);
	}
	
	/**
	 * 納品済みすべて
	 * @return
	 */
	public static DevelopmentProgress deliveled() {
		// 「納品済み」を「達成している」
		return new DevelopmentProgress(DevelopmentStatus.DELIVERED, true);
	}
	
	/**
	 * 未検収すべて
	 * @return
	 */
	public static DevelopmentProgress notAccepted() {
		// 「検収済み」を「達成していない」
		return new DevelopmentProgress(DevelopmentStatus.ACCEPTED, false);
	}
	
	/**
	 * 検収済みすべて
	 * @return
	 */
	public static DevelopmentProgress accepted() {
		// 「検収済み」を「達成している」
		return new DevelopmentProgress(DevelopmentStatus.ACCEPTED, true);
	}
}
