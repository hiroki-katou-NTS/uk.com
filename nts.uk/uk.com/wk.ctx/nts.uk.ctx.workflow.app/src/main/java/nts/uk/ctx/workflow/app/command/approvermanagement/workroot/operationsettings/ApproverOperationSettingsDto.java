package nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverSettingScreenInfor;

/**
 * 自分の承認者の運用設定DTO
 */
@Data
@Builder
public class ApproverOperationSettingsDto {
	
	/** 運用モード */
	private Integer operationMode;
	
	/** 利用するレベル */
	private Integer approvalLevelNo;
	
	/** 利用する種類設定 */
	private List<SettingTypeUsedDto> settingTypeUseds;
	
	/** 自分の承認者設定画面に表示情報 */
	private ApproverSettingScreenInforDto approverSettingScreenInfor;
	
	public static ApproverOperationSettingsDto fromDomain(ApproverOperationSettings domain) {
		Integer operationMode = domain.getOperationMode().value;
		Integer approvalLevelNo = domain.getApprovalLevelNo().value;
		List<SettingTypeUsedDto> settingTypeUseds = domain.getSettingTypeUseds().stream()
				.map(x -> {
					return SettingTypeUsedDto.builder()
							.employmentRootAtr(x.getEmploymentRootAtr().value)
							.applicationType(x.getApplicationType().map(appType -> appType.value).orElse(null))
							.confirmRootType(x.getConfirmRootType().map(confirm -> confirm.value).orElse(null))
							.notUseAtr(x.getNotUseAtr().value)
							.build();
				})
				.collect(Collectors.toList());
		ApproverSettingScreenInfor screenInfor = domain.getApproverSettingScreenInfor();
		ApproverSettingScreenInforDto approverSettingScreenInfor = ApproverSettingScreenInforDto.builder()
				.firstItemName(screenInfor.getFirstItemName().v())
				.secondItemName(screenInfor.getSecondItemName().map(name -> name.v()).orElse(null))
				.thirdItemName(screenInfor.getThirdItemName().map(name -> name.v()).orElse(null))
				.fourthItemName(screenInfor.getFourthItemName().map(name -> name.v()).orElse(null))
				.fifthItemName(screenInfor.getFifthItemName().map(name -> name.v()).orElse(null))
				.processMemo(screenInfor.getProcessMemo().map(name -> name.v()).orElse(null))
				.attentionMemo(screenInfor.getProcessMemo().map(name -> name.v()).orElse(null))
				.build();
		
		return ApproverOperationSettingsDto.builder()
				.operationMode(operationMode)
				.approvalLevelNo(approvalLevelNo)
				.settingTypeUseds(settingTypeUseds)
				.approverSettingScreenInfor(approverSettingScreenInfor)
				.build();
	}
}
