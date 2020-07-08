package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting;

import lombok.AllArgsConstructor;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.会社別.申請承認設定.申請設定.申請種類別設定.申請受付制限日数
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
public enum AppAcceptLimitDay {
	
	/**
	 * 当日 
	 */
	THATDAY(0, "当日"),
	
	/**
	 * 1日前
	 */
	ONEDAYAGO(1, "1日前"),
	
	/**
	 * 2日前
	 */
	TWODAYAGO(2, "2日前"),
	
	/**
	 * 3日前
	 */
	THREEDAYAGO(3, "3日前"),
	
	/**
	 * 4日前
	 */
	FOURDAYAGO(4, "4日前"),
	
	/**
	 * 5日前
	 */
	FIVEDAYAGO(5, "5日前"),
	
	/**
	 * 6日前
	 */
	SIXDAYAGO(6, "6日前"),
	
	/**
	 * 7日前
	 */
	SEVENDAYAGO(7, "7日前");

	public final int value;
	
	public final String name;
	
}
