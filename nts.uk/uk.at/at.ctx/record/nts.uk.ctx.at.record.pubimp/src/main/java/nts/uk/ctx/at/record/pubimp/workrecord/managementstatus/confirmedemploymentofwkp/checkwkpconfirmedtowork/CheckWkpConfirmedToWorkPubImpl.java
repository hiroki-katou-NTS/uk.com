package nts.uk.ctx.at.record.pubimp.workrecord.managementstatus.confirmedemploymentofwkp.checkwkpconfirmedtowork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmed;
import nts.uk.ctx.at.record.dom.workrecord.workrecord.EmploymentConfirmedRepository;
import nts.uk.ctx.at.record.pub.workrecord.managementstatus.confirmedemploymentofwkp.checkwkpconfirmedtowork.CheckWkpConfirmedToWorkPub;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;

@Stateless
public class CheckWkpConfirmedToWorkPubImpl implements CheckWkpConfirmedToWorkPub {
	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private EmploymentConfirmedRepository employmentConfirmedRepository;
	
	@Override
	public boolean checkWkpConfirmedToWork(String companyId, GeneralDate baseDate, String workplaceId, int closureId) {
		//ドメインモデル「締め」を取得する
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		if (!closure.isPresent())
			return false;

		// 指定した年月日時点の締め期間を取得する
		Optional<ClosurePeriod> closurePeriodOpt = closure.get().getClosurePeriodByYmd(baseDate);
		if (!closurePeriodOpt.isPresent())
			return false;
		
		//ドメインモデル「就業確定」を取得する
		Optional<EmploymentConfirmed> data = employmentConfirmedRepository.get(companyId, workplaceId,ClosureId.valueOf(closureId), closurePeriodOpt.get().getYearMonth());
		if(data.isPresent()) {
			return true;
		}
		return false;
	}

}
