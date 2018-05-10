package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.basic.LeaveType;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursCareLevRemainDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.nursingcareleavemanagement.data.NursingCareLeaveRemainingData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.nursingcareleave.KrcmtChildCareHDData;

@Stateless
public class JpaNursCareLevRemainDataRepo extends JpaRepository implements NursCareLevRemainDataRepository{

	

	@Override
	public void add(NursingCareLeaveRemainingData obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			KrcmtChildCareHDData childCare = new KrcmtChildCareHDData(obj.getSId(), cId, obj.getNumOfUsedDay().v());
			this.commandProxy().insert(childCare);
		}else{
			KrcmtCareHDData childCare = new KrcmtCareHDData(obj.getSId(), cId, obj.getNumOfUsedDay().v());
			this.commandProxy().insert(childCare);
		}	
	}

	@Override
	public void update(NursingCareLeaveRemainingData obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			Optional<KrcmtChildCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtChildCareHDData.class);
			if(entityOpt.isPresent()){
				KrcmtChildCareHDData entity = entityOpt.get();
				entity.setCId(cId);
				entity.setUserDay(obj.getNumOfUsedDay().v());
				this.commandProxy().update(entity);
			}
		}else{
			Optional<KrcmtCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDData.class);
			if(entityOpt.isPresent()){
				KrcmtCareHDData entity = entityOpt.get();
				entity.setCId(cId);
				entity.setUserDay(obj.getNumOfUsedDay().v());
				this.commandProxy().update(entity);
			}
		}	
	}

	@Override
	public void remove(NursingCareLeaveRemainingData obj, String cId) {
		if(obj.getLeaveType() == LeaveType.CHILD_CARE_LEAVE){
			Optional<KrcmtChildCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtChildCareHDData.class);
			if(entityOpt.isPresent()){
				KrcmtChildCareHDData entity = entityOpt.get();
				this.commandProxy().remove(entity);
			}
		}else{
			Optional<KrcmtCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDData.class);
			if(entityOpt.isPresent()){
				KrcmtCareHDData entity = entityOpt.get();
				this.commandProxy().remove(entity);
			}
		}	
		
	}

	@Override
	public Optional<NursingCareLeaveRemainingData> getChildCareByEmpId(String empId) {
		Optional<KrcmtChildCareHDData> entity = this.queryProxy().find(empId, KrcmtChildCareHDData.class);
		if(entity.isPresent()){
			return Optional.of(NursingCareLeaveRemainingData.getChildCareHDRemaining(entity.get().getSId(), entity.get().getUserDay()));
		}else{
			return Optional.empty();
		}
	}

	@Override
	public Optional<NursingCareLeaveRemainingData> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDData> entity = this.queryProxy().find(empId, KrcmtCareHDData.class);
		if(entity.isPresent()){
			return Optional.of(NursingCareLeaveRemainingData.getCareHDRemaining(entity.get().getSId(), entity.get().getUserDay()));
		}else{
			return Optional.empty();
		}
	}

}
