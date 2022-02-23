package nts.uk.ctx.workflow.app.command.approvermanagement.workroot;

import java.util.Optional;

import lombok.Value;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalRootInformation;

@Value
public class ApprovalRootInformationCommand {

	/**
	 * 承認ルート区分
	 */
	private int employmentRootAtr;

	/**
	 * 期間
	 */
	private GeneralDate startDate;
	private GeneralDate endDate;

	/**
	 * 申請種類
	 */
	private Integer applicationType;

	/**
	 * 確認ルート種類
	 */
	private Integer confirmationRootType;

	public ApprovalRootInformation toDomain() {
		return new ApprovalRootInformation(EnumAdaptor.valueOf(employmentRootAtr, EmploymentRootAtr.class),
				new DatePeriod(startDate, endDate),
				Optional.ofNullable(applicationType).map(data -> EnumAdaptor.valueOf(data, ApplicationType.class)),
				Optional.ofNullable(confirmationRootType)
						.map(data -> EnumAdaptor.valueOf(data, ConfirmationRootType.class)));
	}
}
