package nts.uk.ctx.at.shared.infra.repository.remainingnumber.nursingcareleavemanagement.data;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.nursingcareleave.KrcmtCareHDData;

@Stateless
public class JpaLeaveForCareDataRepo extends JpaRepository implements LeaveForCareDataRepo {

	@Override
	public Optional<LeaveForCareData> getCareByEmpId(String empId) {
		Optional<KrcmtCareHDData> entity = this.queryProxy().find(empId, KrcmtCareHDData.class);
		if (entity.isPresent()) {
			return Optional.of(LeaveForCareData.getCareHDRemaining(entity.get().getSId(), entity.get().getUserDay()));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public void add(LeaveForCareData obj, String cId) {
		KrcmtCareHDData childCare = new KrcmtCareHDData(obj.getSId(), cId, obj.getNumOfUsedDay().v());
		this.commandProxy().insert(childCare);
	}

	@Override
	public void update(LeaveForCareData obj, String cId) {
		Optional<KrcmtCareHDData> entityOpt = this.queryProxy().find(obj.getSId(), KrcmtCareHDData.class);
		if (entityOpt.isPresent()) {
			KrcmtCareHDData entity = entityOpt.get();
			entity.setCId(cId);
			entity.setUserDay(obj.getNumOfUsedDay().v());
			this.commandProxy().update(entity);
		}
	}

}
