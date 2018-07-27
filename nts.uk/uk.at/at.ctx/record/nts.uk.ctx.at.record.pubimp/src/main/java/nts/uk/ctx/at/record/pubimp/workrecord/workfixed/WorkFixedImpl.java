package nts.uk.ctx.at.record.pubimp.workrecord.workfixed;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.ConfirmClsStatus;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkFixed;
import nts.uk.ctx.at.record.dom.workrecord.workfixed.WorkfixedRepository;
import nts.uk.ctx.at.record.pub.workrecord.workfixed.WorkFixedPub;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class WorkFixedImpl implements WorkFixedPub{
	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private WorkfixedRepository workfixedRepository;
	@Override
	public boolean getEmploymentFixedStatus(String companyID, GeneralDate date, String workPlaceID, int closureID) {
		// ドメインモデル「締め」を取得する
		Optional<Closure> optionalClosure = closureRepository.findById(companyID, closureID);
		if(!optionalClosure.isPresent()){
			return false;
		}
		Closure closure = optionalClosure.get();
		Optional<ClosurePeriod> optionalClosurePeriod = closure.getClosurePeriodByYmd(date);
		if(!optionalClosurePeriod.isPresent()){
			return false;
		}
		Optional<WorkFixed> workFixed = this.workfixedRepository.find(companyID, workPlaceID, closureID, optionalClosurePeriod.get().getYearMonth());
		if(!workFixed.isPresent()){
			return false;
		}
		if(workFixed.get().getConfirmClsStatus().equals(ConfirmClsStatus.Confirm)){
			return true;
		}
		return false;
	}

}
