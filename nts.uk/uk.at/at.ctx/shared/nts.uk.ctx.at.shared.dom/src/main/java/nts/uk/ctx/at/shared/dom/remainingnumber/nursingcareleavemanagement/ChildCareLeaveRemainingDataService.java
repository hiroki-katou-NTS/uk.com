package nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;

@Stateless
@Transactional
public class ChildCareLeaveRemainingDataService {
	@Inject
	private CareLeaveRemainingInfoRepository careInfoRepo;/*介護　基本情報*/

	@Inject
	private ChildCareLeaveRemInfoRepository childCareInfoRepo;/*看護　基本情報*/

	@Inject
	private CareUsedNumberRepository careDataRepo;

	@Inject
	private ChildCareUsedNumberRepository childCareDataRepo;

	public void addData(String cid, List<ChildCareUsedNumberData> childCareDataInsert,
			List<CareUsedNumberData> leaveCareDataInsert, List<ChildCareLeaveRemainingInfo> childCareLeaveInfoInsert,
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

	public void updateData(String cid,
			List<ChildCareUsedNumberData> childCareDataUpdate,
			List<CareUsedNumberData> leaveCareDataUpdate,
			List<ChildCareLeaveRemainingInfo> childCareLeaveInfoUpdate,
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
