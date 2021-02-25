package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationsetting.ApplicationSettingDto;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.AppReasonStandardDto;
import nts.uk.ctx.at.request.app.find.setting.company.appreasonstandard.ReasonTypeItemDto;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.EmployeeInfoImport;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoNoDateOutput;
import nts.uk.ctx.at.request.dom.setting.DisplayAtr;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
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
	
	public static AppDispInfoNoDateDto fromDomain(AppDispInfoNoDateOutput appDispInfoNoDateOutput) {
		return new AppDispInfoNoDateDto(
				appDispInfoNoDateOutput.isMailServerSet(), 
				appDispInfoNoDateOutput.getAdvanceAppAcceptanceLimit().value, 
				appDispInfoNoDateOutput.getEmployeeInfoLst(), 
				ApplicationSettingDto.fromDomain(appDispInfoNoDateOutput.getApplicationSetting()), 
				appDispInfoNoDateOutput.getAppReasonStandardLst().stream().map(x -> AppReasonStandardDto.fromDomain(x)).collect(Collectors.toList()), 
				appDispInfoNoDateOutput.getDisplayAppReason().value, 
				appDispInfoNoDateOutput.getDisplayStandardReason().value, 
				appDispInfoNoDateOutput.getReasonTypeItemLst().stream().map(x -> ReasonTypeItemDto.fromDomain(x)).collect(Collectors.toList()), 
				appDispInfoNoDateOutput.isManagementMultipleWorkCycles(), 
				appDispInfoNoDateOutput.getOpAdvanceReceptionHours().map(x -> x.v()).orElse(null), 
				appDispInfoNoDateOutput.getOpAdvanceReceptionDate().map(x -> x.toString()).orElse(null), 
				appDispInfoNoDateOutput.getOpEmployeeInfo().orElse(null));
	}
	
	public AppDispInfoNoDateOutput toDomain() {
		AppDispInfoNoDateOutput appDispInfoNoDateOutput = new AppDispInfoNoDateOutput(
				mailServerSet, 
				EnumAdaptor.valueOf(advanceAppAcceptanceLimit, NotUseAtr.class), 
				employeeInfoLst, 
				applicationSetting.toDomain(), 
				appReasonStandardLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				EnumAdaptor.valueOf(displayAppReason, DisplayAtr.class), 
				EnumAdaptor.valueOf(displayStandardReason, DisplayAtr.class), 
				reasonTypeItemLst.stream().map(x -> x.toDomain()).collect(Collectors.toList()), 
				managementMultipleWorkCycles);
		if(opAdvanceReceptionHours != null) {
			appDispInfoNoDateOutput.setOpAdvanceReceptionHours(Optional.of(new AttendanceClock(opAdvanceReceptionHours)));
		}
		if(opAdvanceReceptionDate != null) {
			appDispInfoNoDateOutput.setOpAdvanceReceptionDate(Optional.of(GeneralDate.fromString(opAdvanceReceptionDate, "yyyy/MM/dd")));
		}
		if(opEmployeeInfo != null) {
			appDispInfoNoDateOutput.setOpEmployeeInfo(Optional.of(opEmployeeInfo));
		}
		return appDispInfoNoDateOutput;
	}
}
