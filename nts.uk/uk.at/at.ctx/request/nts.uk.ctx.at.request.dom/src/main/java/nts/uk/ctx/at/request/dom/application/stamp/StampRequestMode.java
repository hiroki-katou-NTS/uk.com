package nts.uk.ctx.at.request.dom.application.stamp;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.打刻申請.打刻申請モード
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum StampRequestMode {
	
	/**
	 * 打刻申請
	 */
	STAMP_ADDITIONAL(0,"打刻申請"),
	
	/**
	 * レコーダイメージ申請
	 */
	STAMP_ONLINE_RECORD(1,"レコーダイメージ申請");
	
	public final int value;
	
	public final String name;
}
