package nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

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
	@Setter
	private String employmentCD;
	
	/**
	 * 申請別対象勤務種類
	 */
	private List<TargetWorkTypeByApp> targetWorkTypeByAppLst;
	
	public AppEmploymentSet(String companyID, String employmentCD, List<TargetWorkTypeByApp> targetWorkTypeByAppLst) {
		this.companyID = companyID;
		this.employmentCD = employmentCD;
		this.targetWorkTypeByAppLst = targetWorkTypeByAppLst;
	}
	
}
