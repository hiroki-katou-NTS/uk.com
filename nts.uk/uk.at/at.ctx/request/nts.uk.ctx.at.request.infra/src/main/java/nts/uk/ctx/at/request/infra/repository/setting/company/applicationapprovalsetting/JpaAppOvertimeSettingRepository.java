package nts.uk.ctx.at.request.infra.repository.setting.company.applicationapprovalsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.appovertime.AppOvertimeSettingRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationapprovalsetting.appovertime.KrqstAppOvertimeSet;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class JpaAppOvertimeSettingRepository extends JpaRepository implements AppOvertimeSettingRepository{
	/**
	 * convert from domain to entiy
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstAppOvertimeSet toEntity(AppOvertimeSetting domain){
		val entity = new KrqstAppOvertimeSet();
		entity.setCid(domain.getCompanyID());
		entity.setAttendanceId(domain.getOtHour().getAttendanceId().value);
		entity.setCalendarDispAtr(domain.getCalendarDispAtr().value);
		entity.setEarlyOverTimeUseAtr(domain.getEarlyOvertimeUseAtr().value);
		entity.setFlexExcessUseSetAtr(domain.getFlexJExcessUseSetAtr().value);
		entity.setInstructExcessOtAtr(domain.getInstructExcessOTAtr().value);
		entity.setNormalOvertimeUseAtr(domain.getNormalOvertimeUseAtr().value);
		entity.setPostBreakReflectFlg(domain.getPostBreakReflectFlg().value);
		entity.setPostTypesiftReflectFlg(domain.getPostTypeSiftReflectFlg().value);
		entity.setPostWorktimeReflectFlg(domain.getPostWorktimeReflectFlg().value);
		entity.setPreOvertimeReflectFlg(domain.getPreOvertimeReflectFlg().value);
		entity.setPreTypeSiftReflectFlg(domain.getPreTypeSiftReflectFlg().value);
		entity.setPriorityStampSetAtr(domain.getPriorityStampSetAtr().value);
		entity.setUnitAssignmentOvertime(domain.getUnitAssignmentOvertime().value);
		entity.setUseOt(domain.getOtHour().getUseOt().value);
		entity.setRestAtr(domain.getRestAtr().value);
		entity.setWorkTypeChangeFlag(domain.getWorkTypeChangeFlag().value);
		return entity;
	}
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private static AppOvertimeSetting toDomain(KrqstAppOvertimeSet entity){
		AppOvertimeSetting domain = AppOvertimeSetting.createFromJavaType(entity.getCid(), 
				entity.getFlexExcessUseSetAtr(), entity.getPreTypeSiftReflectFlg(), 
				entity.getPreOvertimeReflectFlg(), entity.getPostTypesiftReflectFlg(), 
				entity.getPostBreakReflectFlg(), entity.getPostWorktimeReflectFlg(), 
				entity.getCalendarDispAtr(), entity.getEarlyOverTimeUseAtr(), entity.getInstructExcessOtAtr(), 
				entity.getPriorityStampSetAtr(), entity.getUnitAssignmentOvertime(), 
				entity.getNormalOvertimeUseAtr(), entity.getAttendanceId(), entity.getUseOt(), 
				entity.getRestAtr(), entity.getWorkTypeChangeFlag());
		return domain;
	}
	/**
	 * find app over time set by company id
	 * @author yennth
	 */
	@Override
	public Optional<AppOvertimeSetting> getAppOver() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstAppOvertimeSet.class).map(x -> toDomain(x));
	}
	/**
	 * update app over time set by company id
	 * @author yennth
	 */
	@Override
	public void update(AppOvertimeSetting appOverTime) {
		Optional<KrqstAppOvertimeSet> oldEntity = this.queryProxy().find(appOverTime.getCompanyID(), KrqstAppOvertimeSet.class);
		if(oldEntity.isPresent()){
			KrqstAppOvertimeSet entityUpdate = oldEntity.get();
			entityUpdate.setWorkTypeChangeFlag(appOverTime.getWorkTypeChangeFlag().value);
			entityUpdate.setFlexExcessUseSetAtr(appOverTime.getFlexJExcessUseSetAtr().value);
			entityUpdate.setPriorityStampSetAtr(appOverTime.getPriorityStampSetAtr().value);
			entityUpdate.setPreTypeSiftReflectFlg(appOverTime.getPreTypeSiftReflectFlg().value);
			entityUpdate.setPreOvertimeReflectFlg(appOverTime.getPreOvertimeReflectFlg().value);
			entityUpdate.setPostTypesiftReflectFlg(appOverTime.getPostTypeSiftReflectFlg().value);
			entityUpdate.setPostBreakReflectFlg(appOverTime.getPostBreakReflectFlg().value);
			entityUpdate.setPostWorktimeReflectFlg(appOverTime.getPostWorktimeReflectFlg().value);
			entityUpdate.setPriorityStampSetAtr(appOverTime.getPriorityStampSetAtr().value);
			entityUpdate.setRestAtr(appOverTime.getRestAtr().value);
			this.commandProxy().update(entityUpdate);
		}else{
			KrqstAppOvertimeSet entity = toEntity(appOverTime);
			this.commandProxy().insert(entity);
		}
		
	}
	/**
	 * insert app over time setting
	 * @author yennth
	 */
	@Override
	public void insert(AppOvertimeSetting appOverTime) {
		KrqstAppOvertimeSet entity = toEntity(appOverTime);
		this.commandProxy().insert(entity);
	}

}
