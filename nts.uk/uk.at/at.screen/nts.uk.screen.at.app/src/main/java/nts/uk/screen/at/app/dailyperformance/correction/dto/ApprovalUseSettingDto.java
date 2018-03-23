package nts.uk.screen.at.app.dailyperformance.correction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ApprovalUseSettingDto {
	
	private Boolean useDayApproverConfirm;

	private Boolean useMonthApproverConfirm;

	private Integer supervisorConfirmErrorAtr;
}
