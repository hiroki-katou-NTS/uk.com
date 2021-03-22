package nts.uk.ctx.at.function.app.find.attendancerecord.item;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApprovalProcessingUseSettingDto {
	String companyId;

	Boolean useDayApproverConfirm;

	Boolean useMonthApproverConfirm;

	List<String> lstJobTitleNotUse;

	int supervisorConfirmErrorAtr;
}
