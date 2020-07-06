package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

import lombok.Getter;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.設定.雇用別.雇用別申請承認設定.雇用別申請承認設定
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppEmploymentSet {
	
	/**
	 * 会社ID
	 */
	private String companyID;
	
	/**
	 * 雇用区分コード
	 */
	private String epmloymentCD;
	
	/**
	 * 申請別対象勤務種類
	 */
	private List<TargetWorkTypeByApp> targetWorkTypeByAppLst;
	
}
