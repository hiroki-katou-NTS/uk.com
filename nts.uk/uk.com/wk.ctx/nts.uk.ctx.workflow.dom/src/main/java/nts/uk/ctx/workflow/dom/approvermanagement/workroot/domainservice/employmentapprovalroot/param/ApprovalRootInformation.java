package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.承認ルート情報
 */
@Value
@AllArgsConstructor
public class ApprovalRootInformation {

	/**
	 * 承認ルート区分
	 */
	private EmploymentRootAtr employmentRootAtr;
	
	/**
	 * 期間
	 */
	private DatePeriod datePeriod;
	
	/**
	 * 申請種類
	 */
	private Optional<ApplicationType> applicationType;
	
	/**
	 * 確認ルート種類
	 */
	private Optional<ConfirmationRootType> confirmationRootType;
}
