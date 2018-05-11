package nts.uk.ctx.at.record.pubimp.remainnumber.annualleave;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoDomService;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.CalcNextAnnualLeaveGrantDate;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.empinfo.basicinfo.valueobject.AnnLeaRemNumValueObject;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.GetAnnLeaRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.TempAnnualLeaveMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.param.AggrResultOfAnnualLeave;
import nts.uk.ctx.at.record.dom.workrecord.actualsituation.CheckShortageFlex;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveOfThisMonth;
import nts.uk.ctx.at.record.pub.remainnumber.annualleave.AnnLeaveRemainNumberPub;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.export.NextAnnualLeaveGrant;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class AnnLeaveRemainNumberPubImpl implements AnnLeaveRemainNumberPub {

	@Inject
	private AnnLeaEmpBasicInfoDomService annLeaService;

	@Inject
	private GetClosureStartForEmployee closureStartService;

	@Inject
	private CheckShortageFlex checkShortageFlex;

	@Inject
	private GetAnnLeaRemNumWithinPeriod getAnnLeaRemNumWithinPeriod;

	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaBasicInfoRepo;
	
	@Inject
	private CalcNextAnnualLeaveGrantDate calcNextAnnualLeaveGrantDate;
	
	@Override
	public AnnLeaveOfThisMonth getAnnLeaveOfThisMonth(String employeeId) {
		String companyId = AppContexts.user().companyId();
		AnnLeaRemNumValueObject remainNumber = annLeaService.getAnnLeaveNumber(companyId, employeeId);

		GeneralDate startDate = closureStartService.algorithm(employeeId).get();

		DatePeriod datePeriod = checkShortageFlex.findClosurePeriod(employeeId, startDate);

		Optional<AggrResultOfAnnualLeave> aggrResult = getAnnLeaRemNumWithinPeriod.algorithm(companyId, employeeId,
				datePeriod, TempAnnualLeaveMngMode.OTHER, datePeriod.end(), false, false, Optional.empty(),
				Optional.empty(), Optional.empty());
		AggrResultOfAnnualLeave annualLeave = new AggrResultOfAnnualLeave();
		Optional<AnnualLeaveEmpBasicInfo> basicInfo = annLeaBasicInfoRepo.get(employeeId);
		
		List<NextAnnualLeaveGrant> annualLeaveGrant = calcNextAnnualLeaveGrantDate.algorithm(companyId, employeeId, Optional.empty());
		
		AnnLeaveOfThisMonth result = new AnnLeaveOfThisMonth(   annualLeaveGrant.get(0).getGrantDate(),
																annualLeaveGrant.get(0).getGrantDays(), 
																remainNumber.getDays(),
																remainNumber.getMinutes(),
																annualLeave.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedDays().getUsedDays(),
																annualLeave.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getUsedNumber().getUsedTime(),
																annualLeave.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingDays(),
																annualLeave.getAsOfPeriodEnd().getRemainingNumber().getAnnualLeaveWithMinus().getRemainingNumber().getTotalRemainingTime()
				);
		return result;
	}

}
