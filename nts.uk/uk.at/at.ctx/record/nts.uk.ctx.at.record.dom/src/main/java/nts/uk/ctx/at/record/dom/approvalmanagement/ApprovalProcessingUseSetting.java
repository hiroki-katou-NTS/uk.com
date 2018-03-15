/**
 * 5:03:40 PM Mar 9, 2018
 */
package nts.uk.ctx.at.record.dom.approvalmanagement;

import java.util.List;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.record.dom.approvalmanagement.enums.ConfirmationOfManagerOrYouself;

/**
 * @author hungnm
 *
 */
@Getter
public class ApprovalProcessingUseSetting extends AggregateRoot {

	private String companyId;

	private Boolean useDayApproverConfirm;

	private Boolean useMonthApproverConfirm;

	private List<String> lstJobTitleNotUse;

	private ConfirmationOfManagerOrYouself supervisorConfirmErrorAtr;

	public ApprovalProcessingUseSetting(String companyId, Boolean useDayApproverConfirm,
			Boolean useMonthApproverConfirm, List<String> lstJobTitleNotUse) {
		super();
		this.companyId = companyId;
		this.useDayApproverConfirm = useDayApproverConfirm;
		this.useMonthApproverConfirm = useMonthApproverConfirm;
		this.lstJobTitleNotUse = lstJobTitleNotUse;
	}

	public ApprovalProcessingUseSetting setSupervisorConfirmErrorAtr(int supervisorConfirmErrorAtr) {
		this.supervisorConfirmErrorAtr = EnumAdaptor.valueOf(supervisorConfirmErrorAtr,
				ConfirmationOfManagerOrYouself.class);
		return this;
	}
}
