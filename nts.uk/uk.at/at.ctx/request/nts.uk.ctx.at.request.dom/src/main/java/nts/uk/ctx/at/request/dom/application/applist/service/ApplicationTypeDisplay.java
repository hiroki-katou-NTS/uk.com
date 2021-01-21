package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.CMM045_申請一覧・承認一覧.A:申請一覧画面.アルゴリズム.パラメータ.申請種類表示
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum ApplicationTypeDisplay {
	
	/**
	 * 早出残業申請
	 */
	EARLY_OVERTIME(0, "早出残業"),
	
	/**
	 * 普通残業申請
	 */
	NORMAL_OVERTIME(1, "通常残業"),
	
	/**
	 * 早出普通残業申請
	 */
	EARLY_NORMAL_OVERTIME(2, "早出残業・通常残業"),
	
	/**
	 * 打刻申請
	 */
	STAMP_ADDITIONAL(3,"打刻申請"),
	
	/**
	 * 打刻申請TR
	 */
	STAMP_ONLINE_RECORD(4,"レコーダイメージ申請");
	
	public int value;
	
	public String name;
}
