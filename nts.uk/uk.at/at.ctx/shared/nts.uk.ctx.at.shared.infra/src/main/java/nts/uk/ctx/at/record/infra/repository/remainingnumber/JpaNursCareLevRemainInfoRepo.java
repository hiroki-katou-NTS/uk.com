package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursCareLevRemainInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDInfo;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.nursingcareleave.KrcmtChildCareHDInfo;

@Stateless
public class JpaNursCareLevRemainInfoRepo extends JpaRepository implements NursCareLevRemainInfoRepository{

	@Override
	public void add(NursingCareLeaveRemainingInfo obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			KrcmtChildCareHDInfo entity = new KrcmtChildCareHDInfo(obj.getSId(), cId, obj.isUseClassification()? 1 : 0, 
					obj.getUpperlimitSetting().value, obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null, 
							obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			this.commandProxy().insert(entity);
		}else{
			KrcmtCareHDInfo entity = new KrcmtCareHDInfo(obj.getSId(), cId, obj.isUseClassification()? 1 : 0, 
					obj.getUpperlimitSetting().value, obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null, 
							obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
			this.commandProxy().insert(entity);
		}
		
	}

	@Override
	public void update(NursingCareLeaveRemainingInfo obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			Optional<KrcmtChildCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtChildCareHDInfo.class);
			if(entityOpt.isPresent()){
				KrcmtChildCareHDInfo entity = entityOpt.get();
				entity.setCId(cId);
				entity.setUseAtr(obj.isUseClassification()? 1 : 0);
				entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
				entity.setMaxDayNextFiscalYear(obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
				entity.setMaxDayThisFiscalYear(obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
				this.commandProxy().update(entity);
			}
		}else{
			Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDInfo.class);
			if(entityOpt.isPresent()){
				KrcmtCareHDInfo entity = entityOpt.get();
				entity.setCId(cId);
				entity.setUseAtr(obj.isUseClassification()? 1 : 0);
				entity.setUpperLimSetAtr(obj.getUpperlimitSetting().value);
				entity.setMaxDayNextFiscalYear(obj.getMaxDayForNextFiscalYear().isPresent() ? obj.getMaxDayForNextFiscalYear().get().v() : null);
				entity.setMaxDayThisFiscalYear(obj.getMaxDayForThisFiscalYear().isPresent() ? obj.getMaxDayForThisFiscalYear().get().v() : null);
				this.commandProxy().update(entity);
			}
		}		
	}

	@Override
	public void remove(NursingCareLeaveRemainingInfo obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			Optional<KrcmtChildCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtChildCareHDInfo.class);
			if(entityOpt.isPresent()){
				KrcmtChildCareHDInfo entity = entityOpt.get();
				this.commandProxy().remove(entity);
			}
		}else{
			Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDInfo.class);
			if(entityOpt.isPresent()){
				KrcmtCareHDInfo entity = entityOpt.get();
				this.commandProxy().remove(entity);
			}
		}
	}

	@Override
	public Optional<NursingCareLeaveRemainingInfo> getChildCareByEmpId(String empId) {
		Optional<KrcmtChildCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtChildCareHDInfo.class);
		if(entityOpt.isPresent()){
			KrcmtChildCareHDInfo entity = entityOpt.get();
			return Optional.of(NursingCareLeaveRemainingInfo.createChildCareLeaveInfo(entity.getSId(), entity.getUseAtr(), 
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

	@Override
	public Optional<NursingCareLeaveRemainingInfo> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDInfo> entityOpt = this.queryProxy().find(empId, KrcmtCareHDInfo.class);
		if(entityOpt.isPresent()){
			KrcmtCareHDInfo entity = entityOpt.get();
			return Optional.of(NursingCareLeaveRemainingInfo.createChildCareLeaveInfo(entity.getSId(), entity.getUseAtr(), 
					entity.getUpperLimSetAtr(), entity.getMaxDayThisFiscalYear(), entity.getMaxDayNextFiscalYear()));
		}
		return Optional.empty();
	}

}
