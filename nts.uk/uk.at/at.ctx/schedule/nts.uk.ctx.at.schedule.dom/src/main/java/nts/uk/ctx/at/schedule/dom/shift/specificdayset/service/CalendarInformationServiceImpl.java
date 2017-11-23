package nts.uk.ctx.at.schedule.dom.shift.specificdayset.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.BasicWorkSetting;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.CompanyBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWork;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkplaceBasicWorkRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClassRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompanyRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkPlaceRepository;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.UseSet;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class CalendarInformationServiceImpl implements ICalendarInformationService {
	
	private final String DATE_FORMAT = "yyyyMMdd";
	
	@Inject
	private CalendarCompanyRepository calendarCompanyRepository;
	
	@Inject
	private CalendarWorkPlaceRepository calendarWorkPlaceRepository;
	
	@Inject
	private CalendarClassRepository calendarClassRepository;
	
	@Inject
	private CompanyBasicWorkRepository companyBasicWorkRepository;
	
	@Inject
	private WorkplaceBasicWorkRepository workplaceBasicWorkRepository;
	
	@Inject
	private ClassifiBasicWorkRepository classifiBasicWorkRepository;
	
	@Override
	public CalendarInformationOutput getCalendarInformation(String companyID, String workplaceID, String classCD, GeneralDate date) {
		// 稼働日区分を取得する
		UseSet workingDayAtr = this.getWorkingDayAtr(companyID, workplaceID, classCD, date);
		if(workingDayAtr != null){
			// 基本勤務設定を取得する
			BasicWorkSetting basicWorkSetting = this.getBasicWorkSetting(companyID, workplaceID, classCD, workingDayAtr.value);
			if(basicWorkSetting != null){
				return new CalendarInformationOutput(basicWorkSetting.getWorktypeCode().v(), basicWorkSetting.getWorkingCode().v(), date);
			}
		}
		return new CalendarInformationOutput(null, null, date);
	}
	
	/**
	 * 稼働日区分を取得する
	 * @param companyID
	 * @param workplaceID
	 * @param classCD
	 * @param date
	 * @return UseSet
	 */
	private UseSet getWorkingDayAtr(String companyID, String workplaceID, String classCD, GeneralDate date){
		BigDecimal dateID = new BigDecimal(date.toString(DATE_FORMAT));
		Optional<CalendarCompany> opCalendarCompany = calendarCompanyRepository.findCalendarCompanyByDate(companyID, dateID);
		if(opCalendarCompany.isPresent()){
			return opCalendarCompany.get().getWorkingDayAtr();
		}
		
		Optional<CalendarWorkplace> opCalendarWorkplace = calendarWorkPlaceRepository.findCalendarWorkplaceByDate(workplaceID, dateID);
		if(opCalendarWorkplace.isPresent()){
			return opCalendarWorkplace.get().getWorkingDayAtr();
		}
		
		Optional<CalendarClass> opCalendarClass = calendarClassRepository.findCalendarClassByDate(companyID, classCD, dateID);
		if(opCalendarClass.isPresent()){
			return opCalendarClass.get().getWorkingDayAtr();
		}
		
		return null;
	}
	
	/**
	 * 基本勤務を取得する
	 * @param companyID
	 * @param workplaceID
	 * @param classCD
	 * @param workingDayAtr
	 * @return List<BasicWorkSetting>
	 */
	private BasicWorkSetting getBasicWorkSetting(String companyID, String workplaceID, String classCD, Integer workingDayAtr){
		Optional<CompanyBasicWork> opCompanyBasicWork = companyBasicWorkRepository.findById(companyID, workingDayAtr);
		if(opCompanyBasicWork.isPresent()){
			CompanyBasicWork companyBasicWork = opCompanyBasicWork.get();
			if(!companyBasicWork.getBasicWorkSetting().isEmpty()){
				return companyBasicWork.getBasicWorkSetting().get(0);
			}
		} 
		
		Optional<WorkplaceBasicWork> opWorkplaceBasicWork = workplaceBasicWorkRepository.findById(workplaceID);
		if(opCompanyBasicWork.isPresent()){
			WorkplaceBasicWork workplaceBasicWork = opWorkplaceBasicWork.get();
			if(!workplaceBasicWork.getBasicWorkSetting().isEmpty()){
				return workplaceBasicWork.getBasicWorkSetting().get(0);
			}
		}
		
		Optional<ClassificationBasicWork> opClassificationBasicWork = classifiBasicWorkRepository.findById(companyID, classCD, workingDayAtr);
		if(opCompanyBasicWork.isPresent()){
			ClassificationBasicWork classificationBasicWork = opClassificationBasicWork.get();
			if(!classificationBasicWork.getBasicWorkSetting().isEmpty()){
				return classificationBasicWork.getBasicWorkSetting().get(0);
			}
		}
		
		return null;
	}
	
}
