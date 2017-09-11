package nts.uk.ctx.at.request.infra.repository.setting.applicationformreason;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReason;
import nts.uk.ctx.at.request.dom.setting.applicationreason.ApplicationReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.applicationformreason.KrqstAppReason;

@Stateless
public class JpaApplicationFormReason extends JpaRepository implements ApplicationReasonRepository{
	private static final String FINDBYCOMPANYID = "SELECT c FROM KrqstAppReason c WHERE c.krqstAppReasonPK.companyId = :companyId";
	
	private static final String FINDBYAPPTYPE = FINDBYCOMPANYID + " c.krqstAppReasonPK.appType = :appType";
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
				c.krqstAppReasonPK.displayOrder, 
				c.defaultOrder);
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

}
