package nts.uk.screen.at.app.ktgwidget.find;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.WidgetDisplayItemImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.ktgwidget.find.dto.DatePeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetDisplay;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetInfoDTO;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class OptionalWidgetKtgFinder {
	
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepo;
	
	@Inject
	private ClosureRepository closureRepo;
	
	@Inject
	private ClosureService closureService;
	
	@Inject
	private OptionalWidgetAdapter optionalWidgetAdapter; 
	
	

	public DatePeriodDto getCurrentMonth() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		
		Optional<BsEmploymentHistoryImport> EmploymentHistoryImport = shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, GeneralDate.today());
		if(!EmploymentHistoryImport.isPresent())
			return null;
		String employmentCode = EmploymentHistoryImport.get().getEmploymentCode();
		
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepo.findByEmploymentCD(companyId, employmentCode);
		if(!closureEmployment.isPresent())
			return null;
		
		Optional<Closure> closure = closureRepo.findById(companyId, closureEmployment.get().getClosureId());
		if(!closure.isPresent())
			return null;
		
		YearMonth currentMonth = closure.get().getClosureMonth().getProcessingYm();
		
		DatePeriod datePeriodOfcurrentMonth = closureService.getClosurePeriod(closureEmployment.get().getClosureId(),currentMonth);
		
		YearMonth nextMonth = currentMonth.addMonths(1);
		
		DatePeriod datePeriodOfNextMonth = closureService.getClosurePeriod(closureEmployment.get().getClosureId(), nextMonth);
		
		DatePeriodDto dto = new DatePeriodDto(datePeriodOfcurrentMonth.start(), datePeriodOfcurrentMonth.end(), datePeriodOfNextMonth.start(), datePeriodOfNextMonth.end());
		
		return dto;
	}
	
	public OptionalWidgetImport findOptionalWidgetByCode(String topPagePartCode) {
		String companyId = AppContexts.user().companyId(); 
		Optional<OptionalWidgetImport> dto = optionalWidgetAdapter.getSelectedWidget(companyId, topPagePartCode);
		if(!dto.isPresent())
			return null;
		return optionalWidgetAdapter.getSelectedWidget(companyId, topPagePartCode).get();
	}
	
	public OptionalWidgetDisplay getOptionalWidgetDisplay(String topPagePartCode) {
		DatePeriodDto datePeriodDto = getCurrentMonth();
		OptionalWidgetImport optionalWidgetImport = findOptionalWidgetByCode(topPagePartCode);
		return new OptionalWidgetDisplay(datePeriodDto, optionalWidgetImport);
	}
	
	public OptionalWidgetInfoDTO getDataByDateperiord(String code, GeneralDate startDate, GeneralDate endDate) {
		
		List<WidgetDisplayItemImport> widgetDisplayItem = findOptionalWidgetByCode(code).getWidgetDisplayItemExport();
		for (WidgetDisplayItemImport item : widgetDisplayItem) {
			if(item.getNotUseAtr()==1) {
				if(item.getDisplayItemType() == 0) {
					
				}else if(item.getDisplayItemType() == 1) {
					
				}else if(item.getDisplayItemType() == 2) {
					
				}else if(item.getDisplayItemType() == 3) {
					
				}else if(item.getDisplayItemType() == 4) {
					
				}else if(item.getDisplayItemType() == 5) {
					
				}else if(item.getDisplayItemType() == 6) {
					
				}else if(item.getDisplayItemType() == 7) {
					
				}else if(item.getDisplayItemType() == 8) {
					
				}else if(item.getDisplayItemType() == 9) {
					
				}else if(item.getDisplayItemType() == 10) {
					
				}else if(item.getDisplayItemType() == 11) {
					
				}else if(item.getDisplayItemType() == 12) {
					
				}else if(item.getDisplayItemType() == 13) {
					
				}else if(item.getDisplayItemType() == 14) {
					
				}else if(item.getDisplayItemType() == 15) {
					
				}else if(item.getDisplayItemType() == 16) {
					
				}else if(item.getDisplayItemType() == 17) {
					
				}else if(item.getDisplayItemType() == 18) {
					
				}else if(item.getDisplayItemType() == 19) {
					
				}else if(item.getDisplayItemType() == 20) {
					
				}else if(item.getDisplayItemType() == 21) {
					
				}else if(item.getDisplayItemType() == 22) {
					
				}else if(item.getDisplayItemType() == 23) {
					
				}else if(item.getDisplayItemType() == 24) {
					
				}else if(item.getDisplayItemType() == 25) {
					
				}
			}
		}
		return null;
	}
	
}
