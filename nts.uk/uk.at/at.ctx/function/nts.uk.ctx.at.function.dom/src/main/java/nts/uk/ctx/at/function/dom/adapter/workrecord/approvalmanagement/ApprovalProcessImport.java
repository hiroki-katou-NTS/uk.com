package nts.uk.ctx.at.function.dom.adapter.workrecord.approvalmanagement;

import java.util.List;

import lombok.Value;


/**
 * 
 * @author thuongtv
 *
 */
@Value
public class ApprovalProcessImport {
	
	private String companyId;

	private Boolean useDayApproverConfirm;

	private Boolean useMonthApproverConfirm;

	private List<String> lstJobTitleNotUse;

	private ConfirmationOfManagerOrYouselfImport supervisorConfirmErrorAtr;

}
