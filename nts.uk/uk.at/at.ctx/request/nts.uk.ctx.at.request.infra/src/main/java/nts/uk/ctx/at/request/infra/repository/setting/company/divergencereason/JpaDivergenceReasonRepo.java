package nts.uk.ctx.at.request.infra.repository.setting.company.divergencereason;

//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReason;
import nts.uk.ctx.at.request.dom.setting.company.divergencereason.DivergenceReasonRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.divergencereason.KrqmtAppDivergenReason;

@Stateless
public class JpaDivergenceReasonRepo extends JpaRepository implements DivergenceReasonRepository {
	private static final String FINDER_ALL ="SELECT e FROM KrqmtAppDivergenReason e";
	private static final String PLEASE ="選択してください";
	
	private static final String FIND_FOLLOW_COMPANYID_AND_APPTYPE;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FINDER_ALL);
		query.append(" WHERE e.krqmtAppDivergenReasonPK.cid = :companyID");
		query.append(" AND e.krqmtAppDivergenReasonPK.appType = :appType");
		FIND_FOLLOW_COMPANYID_AND_APPTYPE = query.toString();
	}
	@Override
	public List<DivergenceReason> getDivergenceReason(String companyID, int appType) {
		
//		List<DivergenceReason> divergenceReasons = this.queryProxy().query(FIND_FOLLOW_COMPANYID_AND_APPTYPE,KrqmtAppDivergenReason.class)
//				.setParameter("companyID", companyID)
//				.setParameter("appType", appType)
//				.getList(entity -> convertToDomain(entity));
//		List<DivergenceReason> dataTmp = divergenceReasons.stream().filter(x -> x.getReasonTypeItem().getDefaultFlg() == DefaultFlg.DEFAULT).collect(Collectors.toList());
//		ReasonTypeItem reasonTypeItem = new ReasonTypeItem("",0,new ReasonTempPrimitive(PLEASE),DefaultFlg.NOTDEFAULT);
//		DivergenceReason firstData = new DivergenceReason(companyID, EnumAdaptor.valueOf(appType, ApplicationType.class),reasonTypeItem);
//		if(CollectionUtil.isEmpty(dataTmp)) {
//			firstData = new DivergenceReason(companyID, EnumAdaptor.valueOf(appType, ApplicationType.class),new ReasonTypeItem("",0,new ReasonTempPrimitive(PLEASE),DefaultFlg.DEFAULT));
//		}
//		divergenceReasons.sort((a,b) -> Integer.compare(a.getReasonTypeItem().getDispOrder(),b.getReasonTypeItem().getDispOrder()));
//		divergenceReasons.add(0, firstData);
//		return divergenceReasons;
		return null;
	}
	private DivergenceReason convertToDomain(KrqmtAppDivergenReason entity){
		return DivergenceReason.createSimpleFromJavaType(entity.getKrqmtAppDivergenReasonPK().getCid(), 
				entity.getKrqmtAppDivergenReasonPK().getAppType(),
				entity.getKrqmtAppDivergenReasonPK().getReasonId(),
				entity.getDisporder(),
				entity.getReasonTemp(),
				entity.getDefaultFlg());
	}

}
