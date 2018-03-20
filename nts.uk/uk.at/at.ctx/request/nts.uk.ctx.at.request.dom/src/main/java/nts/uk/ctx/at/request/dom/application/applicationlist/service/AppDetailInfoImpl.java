package nts.uk.ctx.at.request.dom.application.applicationlist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
/**
 * get info detail application
 * @author hoatt
 *
 */
@Stateless
public class AppDetailInfoImpl implements AppDetailInfoRepository{

	@Inject
	private GoBackDirectlyRepository repoGoBack;
	@Inject
	private OvertimeRepository repoOverTime;
	@Inject
	private BPTimeItemRepository repoBonusTime;
	@Inject
	private WorkdayoffFrameRepository repoWork;
	@Inject
	private OvertimeWorkFrameRepository repoOverTimeFr;
	@Inject
	private AppHolidayWorkRepository repoHolidayWork;
	@Inject
	private WorkTypeRepository repoWorkType;
	@Inject
	private WorkTimeSettingRepository repoworkTime;
	@Inject
	private IAppWorkChangeRepository repoworkChange;
	/**
	 * get Application Over Time Info
	 * appType = 0;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppOverTimeInfoFull getAppOverTimeInfo(String companyId, String appId) {
		Optional<AppOverTime> appOtOp = repoOverTime.getAppOvertimeFrame(companyId, appId);
		AppOverTime appOt = appOtOp.get();
		List<OverTimeInput> lstOverTimeInput = appOt.getOverTimeInput();
		List<OverTimeFrame> lstFrame = new ArrayList<>();
		for (OverTimeInput overTime : lstOverTimeInput) {
			List<Integer> lstFrameNo = new ArrayList<>();
			if(overTime.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstFrameNo);
				lstFrame.add(new OverTimeFrame(3, lstFramBonus.get(0).getId(),lstFramBonus.get(0).getTimeItemName().v(), 
						lstFramBonus.get(0).getTimeItemTypeAtr().value, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstFrameNo);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(), 
						lstFramWork.get(0).getWorkdayoffFrName().v(), null, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)){
				String name = "";
				if(overTime.getFrameNo() == 11){
					name = "時間外深夜時間";
				}else if(overTime.getFrameNo() == 12){
					name = "ﾌﾚｯｸｽ超過";
				}else{
				lstFrameNo.add(overTime.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstFrameNo);
				name = lstFramOt.get(0).getOvertimeWorkFrName().v();
				}
				lstFrame.add(new OverTimeFrame(1, overTime.getFrameNo(), 
						name, null, overTime.getApplicationTime().v()));
			}
		}
		return new AppOverTimeInfoFull(appId, 
				this.convertTime(appOt.getWorkClockFrom1()),
				this.convertTime(appOt.getWorkClockTo1()),
				this.convertTime(appOt.getWorkClockFrom2()),
				this.convertTime(appOt.getWorkClockTo2()),
				0, lstFrame, appOt.getOverTimeShiftNight(),
				appOt.getFlexExessTime());
	}

	/**
	 * get Application Go Back Info
	 * appType = 4;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId) {
		Optional<GoBackDirectly> appGoBackOp = repoGoBack.findByApplicationID(companyID, appId);
		GoBackDirectly appGoBack = appGoBackOp.get();
		return new AppGoBackInfoFull(appId, appGoBack.getGoWorkAtr1().value,
				this.convertTime(appGoBack.getWorkTimeStart1().v()),
				appGoBack.getBackHomeAtr1().value, 
				this.convertTime(appGoBack.getWorkTimeEnd1().v()),
				appGoBack.getGoWorkAtr2().value,
				this.convertTime(appGoBack.getWorkTimeStart2().v()),
				appGoBack.getBackHomeAtr2().value,
				this.convertTime(appGoBack.getWorkTimeEnd2().v()));
	}
	/**
	 * get Application Holiday Work Info
	 * appType = 6;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppHolidayWorkFull getAppHolidayWorkInfo(String companyId, String appId) {
		// TODO Auto-generated method stub
		Optional<AppHolidayWork> appHdWork = repoHolidayWork.getAppHolidayWorkFrame(companyId, appId);
		AppHolidayWork hdWork = appHdWork.get();
		List<HolidayWorkInput> lstOverTimeInput = hdWork.getHolidayWorkInputs();
		List<OverTimeFrame> lstFrame = new ArrayList<>();
		for (HolidayWorkInput overTime : lstOverTimeInput) {
			List<Integer> lstFrameNo = new ArrayList<>();
			if(overTime.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstFrameNo);
				lstFrame.add(new OverTimeFrame(3, lstFramBonus.get(0).getId(),lstFramBonus.get(0).getTimeItemName().v(), 
						lstFramBonus.get(0).getTimeItemTypeAtr().value, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstFrameNo);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(), 
						lstFramWork.get(0).getWorkdayoffFrName().v(), null, overTime.getApplicationTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)){
				String name = "";
				if(overTime.getFrameNo() == 11){
					name = "時間外深夜時間";
				}else if(overTime.getFrameNo() == 12){
					name = "ﾌﾚｯｸｽ超過";
				}else{
				lstFrameNo.add(overTime.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstFrameNo);
				name = lstFramOt.get(0).getOvertimeWorkFrName().v();
				}
				lstFrame.add(new OverTimeFrame(1, overTime.getFrameNo(), 
						name, null, overTime.getApplicationTime().v()));
			}
			
		}
		String workTypeName = hdWork.getWorkTypeCode() == null ||  Strings.isBlank(hdWork.getWorkTypeCode().v()) ? "" : 
						repoWorkType.findByPK(companyId, hdWork.getWorkTypeCode().v()).get().getName().v();
		String workTimeName = hdWork.getWorkTimeCode().v().equals("000") ? "" :
			repoworkTime.findByCode(companyId,hdWork.getWorkTimeCode().v()).get().getWorkTimeDisplayName().getWorkTimeName().v();
		
//		     .orElseGet(()->{
//		      return workTimeRepository.findByCompanyId(companyID).get(0);
//		     });
//		workTime.getWorkTimeDisplayName().getWorkTimeName().toString()
		return new AppHolidayWorkFull(appId, workTypeName,workTimeName,
				hdWork.getWorkClock1().getStartTime() == null ? "" : this.convertTime(hdWork.getWorkClock1().getStartTime().v()),
				hdWork.getWorkClock1().getEndTime() == null ? "" : this.convertTime(hdWork.getWorkClock1().getEndTime().v()),
				hdWork.getWorkClock2().getStartTime() == null ? "" : this.convertTime(hdWork.getWorkClock2().getStartTime().v()),
				hdWork.getWorkClock2().getEndTime() == null ? "" : this.convertTime(hdWork.getWorkClock2().getEndTime().v()),
				lstFrame);
	}
	/**
	 * get Application Work Change Info
	 * appType = 6;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppWorkChangeFull getAppWorkChangeInfo(String companyID, String appId) {
		Optional<AppWorkChange> workChange = repoworkChange.getAppworkChangeById(companyID, appId);
		AppWorkChange appWkChange = workChange.get();
		return new AppWorkChangeFull(appId, appWkChange.getWorkTypeName() == null ? "" : appWkChange.getWorkTypeName(),
				appWkChange.getWorkTimeName() == null ? "" : appWkChange.getWorkTimeName(),
				appWkChange.getGoWorkAtr1(),
				this.convertTime(appWkChange.getWorkTimeStart1()),
				appWkChange.getBackHomeAtr1(),
				this.convertTime(appWkChange.getWorkTimeEnd1()),
				appWkChange.getGoWorkAtr2(),
				this.convertTime(appWkChange.getWorkTimeStart2()),
				appWkChange.getBackHomeAtr2(),
				this.convertTime(appWkChange.getWorkTimeEnd2()),
				this.convertTime(appWkChange.getBreakTimeStart1()),
				this.convertTime(appWkChange.getBreakTimeEnd1()));
	}
	/**
	 * convert time from integer to Time_Short_HM
	 * @param time
	 * @return
	 */
	private String convertTime(Integer time){
		if(time == null){
			return "";
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(time);
		return timeConvert.getDayDivision().description + timeConvert.getInDayTimeWithFormat();
	}
}
