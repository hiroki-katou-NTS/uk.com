package nts.uk.screen.at.app.ktgwidget.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.PublicHolidaySettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosurePeriod;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OptionalWidgetKtgFinder {
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ClosureRepository closureRepository;
	
	@Inject
	private OptionalWidgetAdapter optionalWidgetAdapter; 

	public ClosurePeriod getCurrentMonth(String employmentCode) {
		String companyId = AppContexts.user().companyId();
		
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepo.findByEmploymentCD(companyId, employmentCode);
		if(!closureEmployment.isPresent())
			return null;
		Integer closureId = closureEmployment.get().getClosureId();
		
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		if(!closure.isPresent())
			return null;
		
		YearMonth yearMonth = closure.get().getClosureMonth().getProcessingYm().addMonths(1);
		
		GeneralDate ymd = GeneralDate.ymd(yearMonth.year(), yearMonth.month(), 0);
		Optional<ClosurePeriod> closurePeriod = closure.get().getClosurePeriodByYmd(ymd);
				
		if(!closurePeriod.isPresent())
			return null;
		
		return closurePeriod.get();
	}
	
}
