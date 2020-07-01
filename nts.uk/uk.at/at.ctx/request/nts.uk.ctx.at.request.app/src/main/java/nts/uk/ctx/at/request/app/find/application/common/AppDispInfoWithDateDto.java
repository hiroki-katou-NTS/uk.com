package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.request.app.find.application.common.dto.AchievementDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.AppEmploymentSettingDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.ApprovalPhaseStateForAppDto;
import nts.uk.ctx.at.request.app.find.application.common.dto.SEmpHistImportDto;
import nts.uk.ctx.at.request.app.find.setting.workplace.ApprovalFunctionSettingDto;
import nts.uk.ctx.at.request.dom.application.common.service.other.AppDetailContent;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDispInfoWithDateDto {
	/**
	 * 申請承認機能設定
	 */
	private ApprovalFunctionSettingDto approvalFunctionSet;
	
	/**
	 * 事前事後区分
	 */
	private int prePostAtr;
	
	/**
	 * 基準日
	 */
	private String baseDate;
	
	/**
	 * 社員所属雇用履歴を取得
	 */
	private SEmpHistImportDto empHistImport;
	
	/**
	 * 申請締め切り日利用区分
	 */
	private int appDeadlineUseCategory;
	
	/**
	 * 雇用別申請承認設定
	 */
	private AppEmploymentSettingDto opEmploymentSet;
	
	/**
	 * 承認ルート
	 */
	private List<ApprovalPhaseStateForAppDto> opListApprovalPhaseState;
	
	/**
	 * 承認ルートエラー情報
	 */
	private Integer opErrorFlag;
	
	/**
	 * 表示する実績内容
	 */
	private List<AchievementDto> opAchievementOutputLst;
	
	/**
	 * 表示する事前申請内容
	 */
	private List<AppDetailContent> opAppDetailContentLst;
	
	/**
	 * 申請締め切り日
	 */
	private String opAppDeadline;
	
	/**
	 * 就業時間帯の設定
	 */
	private List<WorkTimeSettingDto> opWorkTimeLst;
}
