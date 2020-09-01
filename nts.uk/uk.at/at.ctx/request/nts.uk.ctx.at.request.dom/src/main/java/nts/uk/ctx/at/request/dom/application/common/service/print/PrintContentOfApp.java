package nts.uk.ctx.at.request.dom.application.common.service.print;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTrip;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripInfo;
import nts.uk.ctx.at.request.dom.application.businesstrip.BusinessTripPrintContent;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.InforGoBackCommonDirectOutput;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;
import nts.uk.ctx.at.request.dom.application.stamp.output.AppStampOutput;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppReasonStandard;

/**
 * refactor 4
 * UKDesign.UniversalK.就業.KAF_申請.共通アルゴリズム.印刷内容を取得する.申請の印刷内容
 * @author Doan Duy Hung
 *
 */
@Setter
@Getter
public class PrintContentOfApp {
	
	/**
	 * 会社名
	 */
	private String companyName;
	
	/**
	 * 事前事後区分
	 */
	private PrePostAtr prePostAtr;
	
	/**
	 * 承認者欄の内容
	 */
	private ApproverColumnContents approverColumnContents;
	
	/**
	 * 申請開始日
	 */
	private GeneralDate appStartDate;
	
	/**
	 * 申請者の社員情報
	 */
	private List<EmployeeInfoImport> employeeInfoLst;
	
	/**
	 * 申請者の職場表示名
	 */
	private String workPlaceName;
	
	/**
	 * 申請終了日
	 */
	private GeneralDate appEndDate;
	
	/**
	 * 申請対象日
	 */
	private GeneralDate appDate;
	
	/**
	 * 申請定型理由
	 */
	private AppReasonStandard appReasonStandard;
	
	/**
	 * 申請名
	 */
	private String applicationName;
	
	/**
	 * 申請理由
	 */
	private AppReason opAppReason;
	
	/**
	 * 複数回勤務の管理
	 */
	private boolean managementMultipleWorkCycles;
	
	/**
	 * 休暇申請の印刷内容
	 */
	
	/**
	 * 休日出勤の印刷内容
	 */
	
	/**
	 * 勤務変更申請の印刷内容
	 */
	private Optional<PrintContentOfWorkChange> opPrintContentOfWorkChange;
	
	/**
	 * 時間休暇申請の印刷内容
	 */
	
	/**
	 * 出張申請の印刷内容
	 */
	private Optional<BusinessTripPrintContent> opBusinessTripPrintContent;
	
	/**
	 * 振休振出申請の印刷内容
	 */
	
	/**
	 * 打刻申請の印刷内容
	 */
	private Optional<AppStampOutput> opAppStampOutput;
	
	/**
	 * 遅刻早退取消申請の印刷内容
	 */
	private Optional<ArrivedLateLeaveEarlyInfoOutput> opArrivedLateLeaveEarlyInfo;
	
	/**
	 * 直行直帰申請の印刷内容
	 */
	private Optional<InforGoBackCommonDirectOutput> opInforGoBackCommonDirectOutput;

	
	/**
	 * 任意項目申請の印刷内容
	 */
	
	public PrintContentOfApp() {
		this.employeeInfoLst = Collections.emptyList();
		this.opPrintContentOfWorkChange = Optional.empty();
		this.opArrivedLateLeaveEarlyInfo = Optional.empty();
		this.opAppStampOutput = Optional.empty();
		this.opBusinessTripPrintContent = Optional.empty();
	}
}
