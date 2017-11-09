package nts.uk.ctx.at.request.infra.repository.setting.company.divergencereason;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.divergencereason.KrqstAppDivergenReason;

@Stateless
public class DivergenceReasonImpl extends JpaRepository implements DivergenceReasonRepository {
	private static final String FINDER_ALL ="SELECT e FROM KrqstAppDivergenReason e";
	
	private static final String FIND_FOLLOW_COMPANYID_AND_APPTYPE;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FINDER_ALL);
		query.append(" WHERE e.krqstAppDivergenReasonPK.cid = :companyID");
		query.append(" AND e.krqstAppDivergenReasonPK.appType = :appType");
		FIND_FOLLOW_COMPANYID_AND_APPTYPE = query.toString();
	}
	@Override
	public List<DivergenceReason> getDivergenceReason(String companyID, int appType) {
		
		return this.queryProxy().query(FIND_FOLLOW_COMPANYID_AND_APPTYPE,KrqstAppDivergenReason.class)
				.setParameter("companyID", companyID)
				.setParameter("appType", appType)
				.getList(entity -> convertToDomain(entity));
	}
	private DivergenceReason convertToDomain(KrqstAppDivergenReason entity){
		return DivergenceReason.createSimpleFromJavaType(entity.getKrqstAppDivergenReasonPK().getCid(), 
				entity.getKrqstAppDivergenReasonPK().getAppType(),
				entity.getKrqstAppDivergenReasonPK().getReasonId(),
				entity.getDisporder(),
				entity.getReasonTemp(),
				entity.getDefaultFlg());
	}

}
