package nts.uk.ctx.at.request.infra.repository.application.approvalstatus;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTempRepository;
import nts.uk.ctx.at.request.infra.entity.application.approvalstatus.KshstApprovalStatusMailTemp;

@Stateless
public class JpaApprovalStatusMailTempRepository extends JpaRepository implements ApprovalStatusMailTempRepository {

	private static final String SELECT_ALL_QUERY_STRING = "SELECT f FROM KshstApprovalStatusMailTemp f";
	private static final String SELECT_BY_KEY_STRING = SELECT_ALL_QUERY_STRING
			+ " WHERE  f.approvalStatusMailTempPk.cid =:cid AND  f.approvalStatusMailTempPk.type =:type ";

	@Override
	public Optional<ApprovalStatusMailTemp> getApprovalStatusMailTempById(String cid, int type) {
		return this.queryProxy().query(SELECT_BY_KEY_STRING, KshstApprovalStatusMailTemp.class).setParameter("cid", cid)
				.setParameter("type", type).getSingle(c -> c.toDomain());
	}

	@Override
	public void add(ApprovalStatusMailTemp domain) {
		this.commandProxy().insert(KshstApprovalStatusMailTemp.toEntity(domain));
	}

	@Override
	public void update(ApprovalStatusMailTemp domain) {
		KshstApprovalStatusMailTemp newApprovalMailTemp = KshstApprovalStatusMailTemp.toEntity(domain);
		KshstApprovalStatusMailTemp updateApprovalMailTemp = this.queryProxy()
				.find(newApprovalMailTemp.approvalStatusMailTempPk, KshstApprovalStatusMailTemp.class).get();
		if (null == updateApprovalMailTemp) {
			return;
		}
		updateApprovalMailTemp.urlApprovalEmbed = newApprovalMailTemp.urlApprovalEmbed;
		updateApprovalMailTemp.urlDayEmbed = newApprovalMailTemp.urlDayEmbed;
		updateApprovalMailTemp.urlMonthEmbed = newApprovalMailTemp.urlMonthEmbed;
		updateApprovalMailTemp.subject = newApprovalMailTemp.subject;
		updateApprovalMailTemp.text = newApprovalMailTemp.text;
		this.commandProxy().update(updateApprovalMailTemp);
	}
}
