package nts.uk.screen.com.app.find.cmm030.a.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.ApproverSettingScreenInforDto;
import nts.uk.ctx.workflow.app.command.approvermanagement.workroot.operationsettings.SettingTypeUsedDto;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;

@Data
@AllArgsConstructor
public class ApproverOperationSettingsDto {

	/** 運用モード */
	private int operationMode;

	/** 利用するレベル */
	private int approvalLevelNo;

	/** 利用する種類設定 */
	private List<SettingTypeUsedDto> settingTypeUseds;

	/** 自分の承認者設定画面に表示情報 */
	private ApproverSettingScreenInforDto approverSettingScreenInfor;

	public static ApproverOperationSettingsDto fromDomain(ApproverOperationSettings domain) {
		return new ApproverOperationSettingsDto(domain.getOperationMode().value, domain.getApprovalLevelNo().value,
				domain.getSettingTypeUseds().stream().map(SettingTypeUsedDto::fromValueObject)
						.collect(Collectors.toList()),
				ApproverSettingScreenInforDto.fromValueObject(domain.getApproverSettingScreenInfor()));
	}
}
