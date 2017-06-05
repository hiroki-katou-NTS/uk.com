package nts.uk.ctx.at.shared.app.find.worktime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.i18n.custom.IInternationalization;
import nts.uk.ctx.at.shared.app.find.worktime.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.worktime.TimeDayAtr;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
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
	
	/**
	 * find list Work Time Dto by code list 
	 * @param codeList code list 
	 * @return list Work Time Dto
	 */
	public List<WorkTimeDto> findByCodeList(List<String> codeList){
		String companyID = AppContexts.user().companyId();
		return this.workTimeRepository.findByCodeList(companyID, codeList).stream()
			.map(x -> new WorkTimeDto(
					x.getWorkTimeCD().v(), 
					x.getName().v(), 
					(x.getWorkTimeSet().getWorkTimeDay().size()==1)
						?createWorkTimeField(
							x.getWorkTimeSet().getWorkTimeDay().get(0).getA_m_UseAtr(),
							x.getWorkTimeSet().getWorkTimeDay().get(0).getA_m_StartTime(),
							x.getWorkTimeSet().getWorkTimeDay().get(0).getA_m_StartAtr(),
							x.getWorkTimeSet().getWorkTimeDay().get(0).getP_m_EndTime(),
							x.getWorkTimeSet().getWorkTimeDay().get(0).getP_m_EndAtr()
						):null
					, 
					(x.getWorkTimeSet().getWorkTimeDay().size()==2)
						?createWorkTimeField(
							x.getWorkTimeSet().getWorkTimeDay().get(1).getP_m_UseAtr(),
							x.getWorkTimeSet().getWorkTimeDay().get(1).getP_m_StartTime(),
							x.getWorkTimeSet().getWorkTimeDay().get(1).getP_m_StartAtr(),
							x.getWorkTimeSet().getWorkTimeDay().get(1).getP_m_EndTime(),
							x.getWorkTimeSet().getWorkTimeDay().get(1).getP_m_EndAtr()
						):null
					, 
					internationalization.getItemName(x.getMethodAtr().name()).get(), 
					x.getRemarks()))
			.collect(Collectors.toList());
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
