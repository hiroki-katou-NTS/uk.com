package nts.uk.ctx.at.request.infra.repository.setting.applicationreason;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.applicationapproval.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.applicationformreason.KrqstAppReason;

@Stateless
public class JpaApplicationReason extends JpaRepository implements ApplicationReasonRepository{
	private static final String FINDBYCOMPANYID = "SELECT c FROM KrqstAppReason c "
			+ "WHERE c.krqstAppReasonPK.companyId = :companyId";
	
	private static final String FINDBYAPPTYPE = FINDBYCOMPANYID + " AND c.krqstAppReasonPK.appType = :appType";

	
	private static final String FINDBYREASONID = FINDBYCOMPANYID + " AND c.krqstAppReasonPK.reasonID = :reasonID";

	/**
	 * get reason by companyid
	 */
	@Override
	public List<ApplicationReason> getReasonByCompanyId(String companyId) {
		List<ApplicationReason> data = this.queryProxy()
				.query(FINDBYCOMPANYID, KrqstAppReason.class)
				.setParameter("companyId", companyId)
				.getList(c ->toDomain(c));
		return data;
	}

	private ApplicationReason toDomain(KrqstAppReason c) {
		
		return ApplicationReason.createSimpleFromJavaType(c.krqstAppReasonPK.companyId,
				c.krqstAppReasonPK.appType,
				c.krqstAppReasonPK.reasonID,
				Integer.valueOf(c.dispOrder).intValue(),
				c.reasonTemp,
				c.defaultFlg
				);
	}

	/**
	 * get reason by application type
	 */
	@Override
	public List<ApplicationReason> getReasonByAppType(String companyId, int appType) {
		List<ApplicationReason> data = this.queryProxy()
				.query(FINDBYAPPTYPE, KrqstAppReason.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getList(c ->toDomain(c));
		return data;
	}

	/**
	 * get reason by reasonID
	 */
	@Override
	public Optional<ApplicationReason> getReasonById(String companyId, String reasonID) {
		return this.queryProxy()
				.query(FINDBYREASONID,KrqstAppReason.class)
				.setParameter("companyId", companyId)
				.setParameter("reasonID", reasonID)
				.getSingle(c->toDomain(c));
	}

}
