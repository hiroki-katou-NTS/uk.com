package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ErrorFlagImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.workplace.appuseset.ApprovalFunctionSet;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係あり)を取得する.申請表示情報(基準日関係あり)
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDispInfoWithDateOutput {
	
	/**
	 * 申請承認機能設定
	 */
	private ApprovalFunctionSet approvalFunctionSet;
	
	/**
	 * 事前事後区分
	 */
	@Setter
	private PrePostInitAtr prePostAtr;
	
	/**
	 * 基準日
	 */
	private GeneralDate baseDate;
	
	/**
	 * 社員所属雇用履歴を取得
	 */
	private SEmpHistImport empHistImport;
	
	/**
	 * 申請締め切り日利用区分
	 */
	private NotUseAtr appDeadlineUseCategory;
	
	/**
	 * 雇用別申請承認設定
	 */
	@Setter
	private Optional<AppEmploymentSet> opEmploymentSet;
	
	/**
	 * 承認ルート
	 */
	@Setter
	private Optional<List<ApprovalPhaseStateImport_New>> opListApprovalPhaseState;
	
	/**
	 * 承認ルートエラー情報
	 */
	@Setter
	private Optional<ErrorFlagImport> opErrorFlag;
	
	/**
	 * 表示する実績内容
	 */
	@Setter
	private Optional<List<ActualContentDisplay>> opActualContentDisplayLst;
	
	/**
	 * 表示する事前申請内容
	 */
	@Setter
	private Optional<List<PreAppContentDisplay>> opPreAppContentDisplayLst;
	
	/**
	 * 申請締め切り日
	 */
	@Setter
	private Optional<GeneralDate> opAppDeadline;
	
	/**
	 * 就業時間帯の設定
	 */
	@Setter
	private Optional<List<WorkTimeSetting>> opWorkTimeLst;
	
	public AppDispInfoWithDateOutput(
			ApprovalFunctionSet approvalFunctionSet,
			PrePostInitAtr prePostAtr,
			GeneralDate baseDate,
			SEmpHistImport empHistImport,
			NotUseAtr appDeadlineUseCategory) {
		this.approvalFunctionSet = approvalFunctionSet;
		this.prePostAtr = prePostAtr;
		this.baseDate = baseDate;
		this.empHistImport = empHistImport;
		this.appDeadlineUseCategory = appDeadlineUseCategory;
		this.opEmploymentSet = Optional.empty();
		this.opListApprovalPhaseState = Optional.empty();
		this.opErrorFlag = Optional.empty();
		this.opActualContentDisplayLst = Optional.empty();
		this.opPreAppContentDisplayLst = Optional.empty();
		this.opAppDeadline = Optional.empty();
		this.opWorkTimeLst = Optional.empty();
	}
	
}
