package nts.uk.query.app.workflow.workroot.approvalmanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.SystemAtr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class PersonApprovalRootQuery {

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;

	/**
	 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.ワークフロー.承認者管理.就業人事申請承認ルート.App.承認者の一覧を取得する.承認者の一覧を取得する
	 */
	public List<ApprovalSettingInformation> findApproverList(String cid, List<String> sids, GeneralDate baseDate,
			SystemAtr systemAtr) {
		return this.personApprovalRootRepository.getApprovalSettingByEmployees(cid, sids, baseDate, systemAtr);
	}
}
