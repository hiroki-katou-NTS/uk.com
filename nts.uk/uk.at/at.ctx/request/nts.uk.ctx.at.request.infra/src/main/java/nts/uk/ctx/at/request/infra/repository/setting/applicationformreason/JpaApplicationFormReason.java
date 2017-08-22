package nts.uk.ctx.at.request.infra.repository.setting.applicationformreason;

import java.util.List;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.applicationformreason.ApplicationFormReason;
import nts.uk.ctx.at.request.dom.setting.applicationformreason.ApplicationFormReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.applicationformreason.KrqstAppReason;

public class JpaApplicationFormReason extends JpaRepository implements ApplicationFormReasonRepository{
	private static final String FINDBYCOMPANYID = "SELECT c FROM KrqstAppReason c WHERE c.krqstAppReasonPK.companyId = :companyId";
	
	private static final String FINDBYAPPTYPE = FINDBYCOMPANYID + " c.krqstAppReasonPK.appType = :appType";
	/**
	 * get reason by companyid
	 */
	@Override
	public List<ApplicationFormReason> getReasonByCompanyId(String companyId) {
		List<ApplicationFormReason> data = this.queryProxy()
				.query(FINDBYCOMPANYID, KrqstAppReason.class)
				.setParameter("companyId", companyId)
				.getList(c ->toDomain(c));
		return data;
	}

	private ApplicationFormReason toDomain(KrqstAppReason c) {
		
		return ApplicationFormReason.createSimpleFromJavaType(c.krqstAppReasonPK.companyId,
				c.krqstAppReasonPK.appType,
				c.krqstAppReasonPK.displayOrder, 
				c.defaultOrder);
	}

	/**
	 * get reason by application type
	 */
	@Override
	public List<ApplicationFormReason> getReasonByAppType(String companyId, int appType) {
		List<ApplicationFormReason> data = this.queryProxy()
				.query(FINDBYAPPTYPE, KrqstAppReason.class)
				.setParameter("companyId", companyId)
				.setParameter("appType", appType)
				.getList(c ->toDomain(c));
		return data;
	}

}
