package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemaiDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.ChildCareLeaveRemainingData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.LeaveForCareDataRepo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;

@Stateless
@Transactional
public class ChildCareLeaveRemainingDataService {
	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;

	@Inject
	private ChildCareLeaveRemaiDataRepo childCareDataRepo;
	
	@Inject
	private LeaveForCareDataRepo careDataRepo;
	
	public void addData(String cid, List<ChildCareLeaveRemainingData> childCareDataInsert,
			List<LeaveForCareData> leaveCareDataInsert, List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert,
			List<CareLeaveRemainingInfo> leaveCareInfoInsert) {
		// data
		if (!childCareDataInsert.isEmpty()) {
			childCareDataRepo.addAll(cid, childCareDataInsert);
		}

		if (!leaveCareDataInsert.isEmpty()) {
			careDataRepo.addAll(cid, leaveCareDataInsert);
		}

		// info
		if (!childCareLeaveInfoInsert.isEmpty()) {
			childCareInfoRepo.addAll(cid, childCareLeaveInfoInsert);
		}

		if (!leaveCareInfoInsert.isEmpty()) {
			careInfoRepo.addAll(cid, leaveCareInfoInsert);
		}
	}

	
	
	public void updateData(String cid, List<ChildCareLeaveRemainingData> childCareDataUpdate,
			List<LeaveForCareData> leaveCareDataUpdate, List<ChildCareLeaveRemainingInfo> childCareLeaveInfoUpdate,
			List<CareLeaveRemainingInfo> leaveCareInfoUpdate) {
		// data
		if (!childCareDataUpdate.isEmpty()) {
			childCareDataRepo.updateAll(cid, childCareDataUpdate);
		}
		if (!leaveCareDataUpdate.isEmpty()) {
			careDataRepo.updateAll(cid, leaveCareDataUpdate);
		}
		// info
		if (!childCareLeaveInfoUpdate.isEmpty()) {
			childCareInfoRepo.updateAll(cid, childCareLeaveInfoUpdate);
		}
		if (!leaveCareInfoUpdate.isEmpty()) {
			careInfoRepo.updateAll(cid, leaveCareInfoUpdate);
		}
	}
}
