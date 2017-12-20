package nts.uk.ctx.at.request.infra.repository.setting.company.applapproval.otrestappcommon;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon.KrqstOtRestAppComSet;

@Stateless
public class JpaOTRestAppCommonSetRep extends JpaRepository implements OvertimeRestAppCommonSetRepository {
private static final String FINDER_ALL ="SELECT o FROM KrqstOtRestAppComSet o";
	
	private static final String FIND_FOR_COMPANYID_APPTYPE;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FINDER_ALL);
		query.append(" WHERE o.krqstOtRestAppComSetPK.cid = :companyID");
		query.append(" AND o.krqstOtRestAppComSetPK.appType = :appType");
		FIND_FOR_COMPANYID_APPTYPE = query.toString();
	}
	@Override
	public Optional<OvertimeRestAppCommonSetting> getOvertimeRestAppCommonSetting(String companyID,int appType) {
		
		return this.queryProxy().query(FIND_FOR_COMPANYID_APPTYPE,KrqstOtRestAppComSet.class)
				.setParameter("companyID", companyID).setParameter("appType", appType).getSingle(entity -> convertToDomain(entity));
	}
	private OvertimeRestAppCommonSetting convertToDomain(KrqstOtRestAppComSet entity){
		return OvertimeRestAppCommonSetting.createSimpleFromJavaType(entity.getKrqstOtRestAppComSetPK().getCid(),
				entity.getKrqstOtRestAppComSetPK().getAppType(),
				entity.getDivergenceReasonInputAtr(),
				entity.getDivergenceReasonFormAtr(), 
				entity.getDivergenceReasonRequired(),
				entity.getPreDisplayAtr(),
				entity.getPreExcessDisplaySetting(),
				entity.getBonusTimeDisplayAtr(), 
				entity.getOutingSettingAtr(),
				entity.getPerformanceDisplayAtr(),
				entity.getPerformanceExcessAtr(),
				entity.getIntructDisplayAtr(),
				entity.getExtratimeDisplayAtr(),
				entity.getExtratimeExcessAtr(),
				entity.getAppDateContradictionAtr(),
				entity.getCalculationOvertimeDisplayAtr());
				
	}
}
