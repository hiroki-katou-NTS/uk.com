package nts.uk.ctx.at.request.ac.record.remainingnumber.reserveleavemanage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.GetRsvLeaNumCriteriaDate;
import nts.uk.ctx.at.record.pub.remainnumber.reserveleave.RsvLeaNumByCriteriaDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaGrantRemainingImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaveInfoImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.TmpRsvLeaveMngImport;

@Stateless
public class ReserveLeaveManagerApdaterImpl implements ReserveLeaveManagerApdater {

	@Inject
	private GetRsvLeaNumCriteriaDate rsvLeaNumCriteriaDatePub;
	
	@Override
	public Optional<RsvLeaManagerImport> getRsvLeaveManager(String employeeID, GeneralDate referDate) {
		Optional<RsvLeaNumByCriteriaDate> rsvDateOp = rsvLeaNumCriteriaDatePub.algorithm(employeeID, referDate);
		if(!rsvDateOp.isPresent()){
			return Optional.empty();
		}
		RsvLeaNumByCriteriaDate rsvDate = rsvDateOp.get();
		////付与日
		Double grantDay = 0.0;
		if(rsvDate.getReserveLeaveInfo().getGrantInfo().isPresent()){
			grantDay = rsvDate.getReserveLeaveInfo().getGrantInfo().get().getGrantDays().v();
		}
		////付与前残数
		Double befRemainDay = rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus()
								.getRemainingNumberBeforeGrant().getTotalRemainingDays().v();
		//付与後残数
		Double aftRemainDay = 0.0;
		if(rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumberAfterGrant().isPresent()){
			aftRemainDay = rsvDate.getReserveLeaveInfo().getRemainingNumber().getReserveLeaveWithMinus().getRemainingNumberAfterGrant().get().getTotalRemainingDays().v();
		}
		RsvLeaveInfoImport rsvInfo = new RsvLeaveInfoImport(befRemainDay, aftRemainDay, grantDay);
		List<RsvLeaGrantRemainingImport> lstGrantRemain = rsvDate.getGrantRemainingList().stream()
				 .map(c -> new RsvLeaGrantRemainingImport(c.getGrantDate(), c.getDeadline(),
					c.getGrantNumber().v(), c.getUsedNumber().getDays().v(), c.getRemainingNumber().v()))
				 .collect(Collectors.toList());
		List<TmpRsvLeaveMngImport> lstTmp = rsvDate.getTmpManageList().stream().map(c -> new TmpRsvLeaveMngImport(c.getYmd(), c.getCreatorAtr().value,
					c.getUseDays().v())).collect(Collectors.toList());
		RsvLeaManagerImport rsvLeaManager = new RsvLeaManagerImport(rsvInfo, lstGrantRemain, lstTmp);
		return Optional.of(rsvLeaManager);
	}

}
