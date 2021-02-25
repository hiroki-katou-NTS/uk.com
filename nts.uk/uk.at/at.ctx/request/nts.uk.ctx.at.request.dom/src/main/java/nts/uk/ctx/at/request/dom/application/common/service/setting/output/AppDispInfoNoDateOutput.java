package nts.uk.ctx.at.request.dom.application.common.service.setting.output;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.ReasonTypeItem;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.申請表示情報(基準日関係なし)を取得する.申請表示情報(基準日関係なし)
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDispInfoNoDateOutput {
	
	/**
	 * メールサーバ設定済区分
	 */
	private boolean mailServerSet;
	
	/**
	 * 事前申請の受付制限
	 */
	private NotUseAtr advanceAppAcceptanceLimit;
	
	/**
	 * 社員情報リスト
	 */
	private List<EmployeeInfoImport> employeeInfoLst;
	
	/**
	 * 申請設定
	 */
	private ApplicationSetting applicationSetting;
	
	/**
	 * 申請定型理由リスト
	 */
	private List<AppReasonStandard> appReasonStandardLst;
	
	/**
	 * 申請理由の表示区分
	 */
	@Setter
	private DisplayAtr displayAppReason;
	
	/**
	 * 定型理由の表示区分
	 */
	@Setter
	private DisplayAtr displayStandardReason;
	
	/**
	 * 定型理由項目一覧
	 */
	@Setter
	private List<ReasonTypeItem> reasonTypeItemLst;
	
	/**
	 * 複数回勤務の管理
	 */
	private boolean managementMultipleWorkCycles;
	
	/**
	 * 事前受付時分
	 */
	@Setter
	private Optional<AttendanceClock> opAdvanceReceptionHours;
	
	/**
	 * 事前受付日
	 */
	@Setter
	private Optional<GeneralDate> opAdvanceReceptionDate;
	
	/**
	 * 入力者社員情報
	 */
	@Setter
	private Optional<EmployeeInfoImport> opEmployeeInfo;
	
	public AppDispInfoNoDateOutput(
			boolean mailServerSet,
			NotUseAtr advanceAppAcceptanceLimit,
			List<EmployeeInfoImport> employeeInfoLst,
			ApplicationSetting applicationSetting,
			List<AppReasonStandard> appReasonStandardLst,
			DisplayAtr displayAppReason,
			DisplayAtr displayStandardReason,
			List<ReasonTypeItem> reasonTypeItemLst,
			boolean managementMultipleWorkCycles) {
		this.mailServerSet = mailServerSet;
		this.advanceAppAcceptanceLimit = advanceAppAcceptanceLimit;
		this.employeeInfoLst = employeeInfoLst;
		this.applicationSetting = applicationSetting;
		this.appReasonStandardLst = appReasonStandardLst;
		this.displayAppReason = displayAppReason;
		this.displayStandardReason = displayStandardReason;
		this.reasonTypeItemLst = reasonTypeItemLst;
		this.managementMultipleWorkCycles = managementMultipleWorkCycles;
		this.opAdvanceReceptionHours = Optional.empty();
		this.opAdvanceReceptionDate = Optional.empty();
		this.opEmployeeInfo = Optional.empty();
	}
	
}
