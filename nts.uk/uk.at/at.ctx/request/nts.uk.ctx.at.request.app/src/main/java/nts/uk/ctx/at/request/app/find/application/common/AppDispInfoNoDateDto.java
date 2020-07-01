package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.AppReasonStandardDto;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.ReasonTypeItemDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@Getter
public class AppDispInfoNoDateDto {
	/**
	 * メールサーバ設定済区分
	 */
	private boolean mailServerSet;
	
	/**
	 * 事前申請の受付制限
	 */
	private int advanceAppAcceptanceLimit;
	
	/**
	 * 社員情報リスト
	 */
	private List<EmployeeInfoImport> employeeInfoLst;
	
	/**
	 * 申請設定
	 */
	private ApplicationSettingDto applicationSetting;
	
	/**
	 * 申請定型理由リスト
	 */
	private List<AppReasonStandardDto> appReasonStandardLst;
	
	/**
	 * 申請理由の表示区分
	 */
	private int displayAppReason;
	
	/**
	 * 定型理由の表示区分
	 */
	private int displayStandardReason;
	
	/**
	 * 定型理由項目<List> 
	 */
	private List<ReasonTypeItemDto> reasonTypeItemLst;
	
	/**
	 * 複数回勤務の管理
	 */
	private boolean managementMultipleWorkCycles;
	
	/**
	 * 事前受付時分
	 */
	private Integer opAdvanceReceptionHours;
	
	/**
	 * 事前受付日
	 */
	private String opAdvanceReceptionDate;
	
	/**
	 * 入力者社員情報
	 */
	private EmployeeInfoImport opEmployeeInfo;
}
