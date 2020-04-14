package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.workplace.ApprovalFunctionSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;

/**
 * 申請表示情報(基準日関係あり)
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AppDispInfoWithDateOutput {
	
	/**
	 * 申請承認機能設定
	 */
	private ApprovalFunctionSetting approvalFunctionSet;
	
	/**
	 * 雇用別申請承認設定
	 */
	private List<AppEmploymentSetting> employmentSet;
	
	/**
	 * 就業時間帯の設定
	 */
	private List<WorkTimeSetting> workTimeLst;
	
	/**
	 * 承認ルート
	 */
	private ApprovalRootStateImport_New approvalRootState;
	
	/**
	 * 承認ルートエラー情報
	 */
	private ErrorFlagImport errorFlag;
	
	/**
	 * 事前事後区分
	 */
	private PrePostAtr prePostAtr;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 表示する実績内容
	 */
	private List<AchievementOutput> achievementOutputLst;
	
	/**
	 * 表示する事前申請内容
	 */
	private List<AppDetailContent> appDetailContentLst;
	
}	
