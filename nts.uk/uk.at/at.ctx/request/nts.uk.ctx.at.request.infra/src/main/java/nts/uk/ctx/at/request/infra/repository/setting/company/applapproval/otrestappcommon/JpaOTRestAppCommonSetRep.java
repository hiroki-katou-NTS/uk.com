package nts.uk.ctx.at.request.infra.repository.setting.company.applapproval.otrestappcommon;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetting;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon.KrqstOtRestAppComSet;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.overtimerestappcommon.KrqstOtRestAppComSetPK;

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
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private KrqstOtRestAppComSet toEntity(OvertimeRestAppCommonSetting domain){
		val entity = new KrqstOtRestAppComSet();
		entity.setAppDateContradictionAtr(domain.getAppDateContradictionAtr().value);
		entity.setBonusTimeDisplayAtr(domain.getBonusTimeDisplayAtr().value);
		entity.setCalculationOvertimeDisplayAtr(domain.getCalculationOvertimeDisplayAtr().value);
		entity.setDivergenceReasonFormAtr(domain.getDivergenceReasonFormAtr().value);
		entity.setDivergenceReasonInputAtr(domain.getDivergenceReasonInputAtr().value);
		entity.setDivergenceReasonRequired(domain.getDivergenceReasonRequired().value);
		entity.setExtratimeDisplayAtr(domain.getExtratimeDisplayAtr().value);
		entity.setExtratimeExcessAtr(domain.getExtratimeExcessAtr().value);
		entity.setOutingSettingAtr(domain.getOutingSettingAtr().value);
		entity.setPerformanceDisplayAtr(domain.getPerformanceDisplayAtr().value);
		entity.setPerformanceExcessAtr(domain.getPerformanceExcessAtr().value);
		entity.setPreDisplayAtr(domain.getPreDisplayAtr().value);
		entity.setPreExcessDisplaySetting(domain.getPreExcessDisplaySetting().value);
		entity.setIntructDisplayAtr(domain.getIntructDisplayAtr().value);
		entity.setKrqstOtRestAppComSetPK(new KrqstOtRestAppComSetPK(domain.getCompanyID(), domain.getAppType().value));
		return entity;
	}
	
	/**
	 * update a item
	 * @author yennth
	 */
	@Override
	public void update(OvertimeRestAppCommonSetting otRestAppCommonSet) {
		Optional<KrqstOtRestAppComSet> findDB = this.queryProxy().query(FIND_FOR_COMPANYID_APPTYPE,KrqstOtRestAppComSet.class)
												.setParameter("companyID", otRestAppCommonSet.getCompanyID())
												.setParameter("appType", otRestAppCommonSet.getAppType().value).getSingle();
		if(findDB.isPresent()){
			KrqstOtRestAppComSet oldEntity = findDB.get();
			oldEntity.setBonusTimeDisplayAtr(otRestAppCommonSet.getBonusTimeDisplayAtr().value);
			oldEntity.setDivergenceReasonFormAtr(otRestAppCommonSet.getDivergenceReasonFormAtr().value);
			oldEntity.setDivergenceReasonInputAtr(otRestAppCommonSet.getDivergenceReasonInputAtr().value);
			oldEntity.setPerformanceDisplayAtr(otRestAppCommonSet.getPerformanceDisplayAtr().value);
			oldEntity.setPreDisplayAtr(otRestAppCommonSet.getPreDisplayAtr().value);
			oldEntity.setCalculationOvertimeDisplayAtr(otRestAppCommonSet.getCalculationOvertimeDisplayAtr().value);
			oldEntity.setExtratimeDisplayAtr(otRestAppCommonSet.getExtratimeDisplayAtr().value);
			oldEntity.setPreExcessDisplaySetting(otRestAppCommonSet.getPreExcessDisplaySetting().value);
			oldEntity.setPerformanceExcessAtr(otRestAppCommonSet.getPerformanceExcessAtr().value);
			oldEntity.setExtratimeExcessAtr(otRestAppCommonSet.getExtratimeExcessAtr().value);
			oldEntity.setAppDateContradictionAtr(otRestAppCommonSet.getAppDateContradictionAtr().value);
			this.commandProxy().update(oldEntity);
		}else{
			KrqstOtRestAppComSet entity = toEntity(otRestAppCommonSet);
			this.commandProxy().insert(entity);
		}
	}
	
	/**
	 * insert a item
	 * @author yennth
	 */
	@Override
	public void insert(OvertimeRestAppCommonSetting domain) {
		KrqstOtRestAppComSet entity = toEntity(domain);
		this.commandProxy().insert(entity);
	}
}
