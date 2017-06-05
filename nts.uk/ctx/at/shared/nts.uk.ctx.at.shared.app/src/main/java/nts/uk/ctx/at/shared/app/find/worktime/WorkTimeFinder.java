package nts.uk.ctx.at.shared.app.find.worktime;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

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
	private WorkTimeRepository workTimeRepository;
	
	/**
	 * find list Work Time Dto by code list 
	 * @param codeList code list 
	 * @return list Work Time Dto
	 */
	public List<WorkTimeDto> findByCodeList(List<String> codeList){
		String companyID = AppContexts.user().companyId();
		this.workTimeRepository.findByCodeList(companyID, codeList).stream()
			.map(x -> new WorkTimeDto(
					x.getWorkTimeCD().v(), 
					x.getName().v(), 
					createWorkTimeField(
						x.getWorkTimeSet().getWorkTimeDay().getA_m_UseAtr(),
						x.getWorkTimeSet().getWorkTimeDay().getA_m_StartTime(),
						x.getWorkTimeSet().getWorkTimeDay().getA_m_StartAtr(),
						x.getWorkTimeSet().getWorkTimeDay().getA_m_EndTime(),
						x.getWorkTimeSet().getWorkTimeDay().getA_m_EndAtr()
					), 
					createWorkTimeField(
						x.getWorkTimeSet().getWorkTimeDay().getP_m_UseAtr(),
						x.getWorkTimeSet().getWorkTimeDay().getP_m_StartTime(),
						x.getWorkTimeSet().getWorkTimeDay().getP_m_StartAtr(),
						x.getWorkTimeSet().getWorkTimeDay().getP_m_EndTime(),
						x.getWorkTimeSet().getWorkTimeDay().getP_m_EndAtr()
					), 
					x.getMethodAtr().name(), 
					x.getRemarks()));
		return null;
	}
	
	/**
	 * format to String form input time day
	 * @param useAtr time day use atr
	 * @param start time day start time
	 * @param startAtr time day start atr
	 * @param end time day end time
	 * @param endAtr time day end atr
	 * @return result string
	 */
	private String createWorkTimeField(UseSetting useAtr, int start, TimeDayAtr startAtr, int end, TimeDayAtr endAtr){
		if(useAtr.equals(UseSetting.UseAtr_Use)){
			return startAtr.name()+" "+(start/60)+":"+(start%60)+" ~ "+endAtr.name()+" "+(end/60)+":"+(end%60);
		} else return null;
	}
	
}
