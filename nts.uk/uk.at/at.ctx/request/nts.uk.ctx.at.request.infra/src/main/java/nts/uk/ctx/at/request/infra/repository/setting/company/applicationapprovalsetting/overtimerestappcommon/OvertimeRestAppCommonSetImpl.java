package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting.overtimerestappcommon;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon.KrqstOtRestAppComSet;

@Stateless
public class OvertimeRestAppCommonSetImpl extends JpaRepository implements OvertimeRestAppCommonSetRepository {
private static final String FINDER_ALL ="SELECT o FROM KrqstOtRestAppComSet o";
	
	private static final String FIND_FOR_COMPANYID;
	static{
		StringBuilder query = new StringBuilder();
		query.append(FINDER_ALL);
		query.append(" WHERE o.krqstOtRestAppComSetPK.cid = :companyID");
		FIND_FOR_COMPANYID = query.toString();
	}
	@Override
	public Optional<OvertimeRestAppCommonSetting> getOvertimeRestAppCommonSetting(String companyID) {
		
		return this.queryProxy().query(FIND_FOR_COMPANYID,KrqstOtRestAppComSet.class)
				.setParameter("companyID", companyID).getSingle(entity -> convertToDomain(entity));
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
