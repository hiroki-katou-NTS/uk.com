package nts.uk.screen.at.app.ktgwidget.find;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.function.dom.adapter.application.ApplicationAdapter;
import nts.uk.ctx.at.function.dom.adapter.application.importclass.ApplicationDeadlineImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetAdapter;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.OptionalWidgetImport;
import nts.uk.ctx.at.function.dom.adapter.widgetKtg.WidgetDisplayItemImport;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.holidayinstruction.HolidayInstructRepository;
import nts.uk.ctx.at.request.dom.overtimeinstruct.OvertimeInstructRepository;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.ktgwidget.find.dto.DatePeriodDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.DeadlineOfRequest;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetDisplay;
import nts.uk.screen.at.app.ktgwidget.find.dto.OptionalWidgetInfoDto;
import nts.uk.screen.at.app.ktgwidget.find.dto.WidgetDisplayItemTypeImport;
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

	@Inject
	private OvertimeInstructRepository overtimeInstructRepo;
	
	@Inject
	private HolidayInstructRepository holidayInstructRepo;
	
	@Inject
	private ApplicationRepository_New applicationRepo_New;
	
	@Inject
	private ApplicationAdapter applicationAdapter;

	public DatePeriodDto getCurrentMonth() {
		String companyId = AppContexts.user().companyId();
		
		ClosureEmployment closureEmployment = getClosureEmployment();
		
		Optional<Closure> closure = closureRepo.findById(companyId, getClosureEmployment().getClosureId());
		if(!closure.isPresent())
			return null;
		
		YearMonth currentMonth = closure.get().getClosureMonth().getProcessingYm();
		
		DatePeriod datePeriodOfcurrentMonth = closureService.getClosurePeriod(closureEmployment.getClosureId(),currentMonth);
		
		YearMonth nextMonth = currentMonth.addMonths(1);
		
		DatePeriod datePeriodOfNextMonth = closureService.getClosurePeriod(closureEmployment.getClosureId(), nextMonth);
		
		DatePeriodDto dto = new DatePeriodDto(datePeriodOfcurrentMonth.start(), datePeriodOfcurrentMonth.end(), datePeriodOfNextMonth.start(), datePeriodOfNextMonth.end());
		
		return dto;
	}
	
	private ClosureEmployment getClosureEmployment() {
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		
		Optional<BsEmploymentHistoryImport> EmploymentHistoryImport = shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, GeneralDate.today());
		if(!EmploymentHistoryImport.isPresent())
			throw new RuntimeException("Not found EmploymentHistory by closureID");
		String employmentCode = EmploymentHistoryImport.get().getEmploymentCode();
		
		Optional<ClosureEmployment> closureEmployment = closureEmploymentRepo.findByEmploymentCD(companyId, employmentCode);
		if(!closureEmployment.isPresent()) {
			throw new RuntimeException("Not found ClosureEmployment");
		}
		return closureEmployment.get();
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
	
	public OptionalWidgetInfoDto getDataRecord(String code, GeneralDate startDate, GeneralDate endDate) {
		String sId = AppContexts.user().employeeId();
		ClosureEmployment employment = getClosureEmployment();
		OptionalWidgetInfoDto dto = new OptionalWidgetInfoDto();
		List<WidgetDisplayItemImport> widgetDisplayItem = findOptionalWidgetByCode(code).getWidgetDisplayItemExport();
		for (WidgetDisplayItemImport item : widgetDisplayItem) {
			if(item.getNotUseAtr()==1) {
				if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.OVERTIME_WORK_NO.value) {
					dto.setOverTime(overtimeInstructRepo.getAllOverTimeInstructBySId(sId, startDate, endDate).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.INSTRUCTION_HD_NO.value) {
					dto.setHolidayInstruction(holidayInstructRepo.getAllHolidayInstructBySId(sId, startDate, endDate).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.APPROVED_NO.value) {
					//・反映状態　　＝　「反映済み」または「反映待ち」(「反映済み」 OR 「反映待ち」)
					List<Integer> reflected = new ArrayList<>();
					reflected.add(ReflectedState_New.REFLECTED.value);
					reflected.add(ReflectedState_New.WAITREFLECTION.value);
					dto.setApproved(applicationRepo_New.getByListRefStatus(sId, startDate, endDate, reflected).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.UNAPPROVED_NO.value) {
					//・反映状態　　＝　「未承認」または「差戻し」(「未承認」OR 「差戻し」)
					List<Integer> reflected = new ArrayList<>();
					reflected.add(ReflectedState_New.NOTREFLECTED.value);
					reflected.add(ReflectedState_New.REMAND.value);
					dto.setUnApproved(applicationRepo_New.getByListRefStatus(sId, startDate, endDate, reflected).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.DENIED_NO.value) {
					//・反映状態　　＝　「否認」
					List<Integer> reflected = new ArrayList<>();
					reflected.add(ReflectedState_New.DENIAL.value);
					dto.setDeniedNo(applicationRepo_New.getByListRefStatus(sId, startDate, endDate, reflected).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.REMAND_NO.value) {
					//・反映状態　　＝　「差戻し」
					List<Integer> reflected = new ArrayList<>();
					reflected.add(ReflectedState_New.REMAND.value);
					dto.setDeniedNo(applicationRepo_New.getByListRefStatus(sId, startDate, endDate, reflected).size());
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.APP_DEADLINE_MONTH.value) {
					ApplicationDeadlineImport deadlineImport = applicationAdapter.getApplicationDeadline(employment.getCompanyId(), employment.getClosureId());
					dto.setAppDeadlineMonth(new DeadlineOfRequest(deadlineImport.isUseApplicationDeadline(), deadlineImport.getDateDeadline()));
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.PRESENCE_DAILY_PER.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.REFER_WORK_RECORD.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.OVERTIME_HOURS.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.FLEX_TIME.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.REST_TIME.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.NIGHT_WORK_HOURS.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.LATE_OR_EARLY_RETREAT.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.YEARLY_HD.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.HAFT_DAY_OFF.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.HOURS_OF_HOLIDAY_UPPER_LIMIT.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.RESERVED_YEARS_REMAIN_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.PLANNED_YEAR_HOLIDAY.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.REMAIN_ALTERNATION_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.REMAINS_LEFT.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.PUBLIC_HD_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.HD_REMAIN_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.CARE_LEAVE_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.SPHD_RAMAIN_NO.value) {
					
				}else if(item.getDisplayItemType() == WidgetDisplayItemTypeImport.SIXTYH_EXTRA_REST.value) {
					
				}
			}
		}
		return dto;
	}
	
}
