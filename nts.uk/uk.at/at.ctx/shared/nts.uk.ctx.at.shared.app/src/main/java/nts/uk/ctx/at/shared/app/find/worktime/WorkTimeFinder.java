package nts.uk.ctx.at.shared.app.find.worktime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSet;
import nts.uk.ctx.at.shared.dom.worktimeset.WorkTimeSetRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author Doan Duy Hung
 *
 */

@Stateless
@Transactional
public class WorkTimeFinder {
	
	@Inject
	IInternationalization internationalization;
	
	@Inject
	private WorkTimeRepository workTimeRepository;
	
	@Inject
	private WorkTimeSetRepository workTimeSetRepository;
	
	/**
	 * find list Work Time Dto by code list 
	 * @param codeList code list 
	 * @return list Work Time Dto
	 */
	public List<WorkTimeDto> findByCodeList(List<String> codeList){
		String companyID = AppContexts.user().companyId();
		if(codeList.isEmpty()){
			return Collections.emptyList();
		} else {
			List<WorkTime> workTimeItems = this.workTimeRepository.findByCodeList(companyID, codeList);
			List<WorkTimeSet> workTimeSetItems = this.workTimeSetRepository.findByCodeList(companyID, codeList);
			return getWorkTimeDtos(workTimeItems, workTimeSetItems);
		}
	}
	
	public List<WorkTimeDto> findByTime(List<String> codeList, int startAtr, int startTime, int endAtr, int endTime){
		if(codeList.isEmpty()){
			return Collections.emptyList();
		} else {
			String companyID = AppContexts.user().companyId();
			List<WorkTime> workTimeItems = new ArrayList<>();
			List<WorkTimeSet> workTimeSetItems = new ArrayList<>();
			if((startTime>-1)&&(endTime>-1)) {
				if(((24*60*startAtr)+startTime)>((24*60*endAtr)+endTime)) throw new BusinessException("Msg_54");
				workTimeItems = this.workTimeRepository.findByCodeList(companyID, codeList);
				workTimeSetItems = this.workTimeSetRepository.findByStartAndEnd(companyID, codeList, startAtr, startTime, endAtr, endTime);
			} else if((startTime>-1)&&(endTime<=-1)) {
				workTimeItems = this.workTimeRepository.findByCodeList(companyID, codeList);
				workTimeSetItems = this.workTimeSetRepository.findByStart(companyID, codeList, startAtr, startTime);
			} else if((startTime<=-1)&&(endTime>-1)) {
				workTimeItems = this.workTimeRepository.findByCodeList(companyID, codeList);
				workTimeSetItems = this.workTimeSetRepository.findByEnd(companyID, codeList, endAtr, endTime);
			} else {
				throw new BusinessException("Msg_53");
			}
			return getWorkTimeDtos(workTimeItems, workTimeSetItems);
		}
	}
	
	/**
	 * get WorkTimeDto list by WorkTime list and WorkTimeSet list
	 * @param workTimeItems WorkTime list
	 * @param workTimeSetItems WorkTimeSet list
	 * @return WorkTimeDto list
	 */
	private List<WorkTimeDto> getWorkTimeDtos(List<WorkTime> workTimeItems, List<WorkTimeSet> workTimeSetItems){
		List<WorkTimeDto> workTimeDtos = new ArrayList<>();
		if(workTimeItems.isEmpty()||workTimeSetItems.isEmpty()){
			workTimeDtos = Collections.emptyList();	
		} else {
			for(WorkTimeSet item : workTimeSetItems) {
				int index = workTimeSetItems.indexOf(item);
				WorkTime currentWorkTime = workTimeItems.get(index);
				WorkTimeSet currentWorkTimeSet = workTimeSetItems.get(index);
				if(currentWorkTimeSet.getWorkTimeDay().isEmpty()) {
					continue;
				} else if(currentWorkTimeSet.getWorkTimeDay().stream().allMatch(x -> x.getUse_atr().equals(UseSetting.UseAtr_NotUse))) {
					continue;
				} else {
					workTimeDtos.add(
						new WorkTimeDto(
							currentWorkTime.getSiftCD().v(), 
							currentWorkTime.getWorkTimeDisplayName().getWorkTimeName().v(), 
							(currentWorkTimeSet.getWorkTimeDay().size()>=1)
								?createWorkTimeField(
									currentWorkTimeSet.getWorkTimeDay().get(0).getUse_atr(),
									currentWorkTimeSet.getWorkTimeDay().get(0).getA_m_StartCLock(),
									currentWorkTimeSet.getWorkTimeDay().get(0).getA_m_StartAtr(),
									currentWorkTimeSet.getWorkTimeDay().get(0).getP_m_EndClock(),
									currentWorkTimeSet.getWorkTimeDay().get(0).getP_m_EndAtr()
								):null
							, 
							(currentWorkTimeSet.getWorkTimeDay().size()>=2)
								?createWorkTimeField(
									currentWorkTimeSet.getWorkTimeDay().get(1).getUse_atr(),
									currentWorkTimeSet.getWorkTimeDay().get(1).getA_m_StartCLock(),
									currentWorkTimeSet.getWorkTimeDay().get(1).getA_m_StartAtr(),
									currentWorkTimeSet.getWorkTimeDay().get(1).getP_m_EndClock(),
									currentWorkTimeSet.getWorkTimeDay().get(1).getP_m_EndAtr()
								):null
							, 
							internationalization.getItemName(currentWorkTime.getWorkTimeDivision().getWorkTimeMethodSet().name()).get(), 
							currentWorkTime.getNote().v()
						)
					);
				}
			};
		}
		return workTimeDtos;
	}
	
	/**
	 * format to String form input time day
	 * @param useAtr time day use atr
	 * @param start time day start time
	 * @param startAtr time day start atr
	 * @param end time day end time
	 * @param endAtr time day end atr
	 * @return result string
	 * @throws ParseException 
	 */
	private String createWorkTimeField(UseSetting useAtr, int start, TimeDayAtr startAtr, int end, TimeDayAtr endAtr) {
		if(useAtr.equals(UseSetting.UseAtr_Use)){
			return internationalization.getItemName(startAtr.name()).get()+formatTime(start)+" ~ "+internationalization.getItemName(endAtr.name()).get()+formatTime(end);
		} else return null;
	}
	
	/**
	 * format int Time to string HH:mm format
	 * @param time int Time
	 * @return string HH:mm format
	 */
	private String formatTime(int time) {
		String inputTime = (time/60)+":"+(time%60);
        SimpleDateFormat curFormater = new SimpleDateFormat("H:m"); 
        Date timeObj = null;
		try {
			timeObj = curFormater.parse(inputTime);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
        SimpleDateFormat postFormater = new SimpleDateFormat("HH:mm"); 
        return postFormater.format(timeObj); 
	}
	
}
