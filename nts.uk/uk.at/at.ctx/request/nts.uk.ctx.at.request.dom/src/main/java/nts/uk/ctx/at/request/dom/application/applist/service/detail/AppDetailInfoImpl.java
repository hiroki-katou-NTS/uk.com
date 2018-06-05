package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.TimeItemTypeAtr;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
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
	@Inject
	private AppAbsenceRepository repoAbsence;
	@Inject
	private RelationshipRepository repoRelationship;
	@Inject
	private AppForSpecLeaveRepository repoAppLeaveSpec;
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	@Inject
	private RecruitmentAppRepository recRepo;
	@Inject
	private BPTimeItemRepository bPTimeItemRepo;
	/**
	 * 残業申請
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
				if(overTime.getTimeItemTypeAtr().equals(TimeItemTypeAtr.NORMAL_TYPE)){
					List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstFrameNo);
					lstFrame.add(new OverTimeFrame(3, lstFramBonus.get(0).getId(),lstFramBonus.get(0).getTimeItemName().v(),
							lstFramBonus.get(0).getTimeItemTypeAtr().value,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				}else{
					List<BonusPayTimeItem> specs = bPTimeItemRepo
							.getListSpecialBonusPayTimeItemName(companyId, lstFrameNo);
					lstFrame.add(new OverTimeFrame(4, specs.get(0).getId(),specs.get(0).getTimeItemName().v(),
							specs.get(0).getTimeItemTypeAtr().value,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				}
			}
			if(overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstFrameNo);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(),
						lstFramWork.get(0).getWorkdayoffFrName().v(), null,
						overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
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
						name, null,
						overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}
		}
		return new AppOverTimeInfoFull(appId,
				this.convertTime(appOt.getWorkClockFrom1()),
				this.convertTime(appOt.getWorkClockTo1()),
				this.convertTime(appOt.getWorkClockFrom2()),
				this.convertTime(appOt.getWorkClockTo2()),
				lstFrame, this.convertTime(appOt.getOverTimeShiftNight()),
				this.convertTime(appOt.getFlexExessTime()));
	}

	/**
	 * 直行直帰申請
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
				this.convertTime(appGoBack.getWorkTimeStart1().map(x -> x.v()).orElse(null)),
				appGoBack.getBackHomeAtr1().value,
				this.convertTime(appGoBack.getWorkTimeEnd1().map(x -> x.v()).orElse(null)),
				appGoBack.getGoWorkAtr2().map(x -> x.value).orElse(null),
				this.convertTime(appGoBack.getWorkTimeStart2().map(x -> x.v()).orElse(null)),
				appGoBack.getBackHomeAtr2().map(x -> x.value).orElse(null),
				this.convertTime(appGoBack.getWorkTimeEnd2().map(x -> x.v()).orElse(null)));
	}
	/**
	 * 勤務変更申請
	 * get Application Holiday Work Info
	 * appType = 6;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppHolidayWorkFull getAppHolidayWorkInfo(String companyId, String appId) {
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
						lstFramBonus.get(0).getTimeItemTypeAtr().value, overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)){
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId,lstFrameNo);
				lstFrame.add(new OverTimeFrame(2, lstFramWork.get(0).getWorkdayoffFrNo().v().intValue(),
						lstFramWork.get(0).getWorkdayoffFrName().v(), null, overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}
			if(overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)){
				String name = "";
				if(overTime.getFrameNo() == 11 || overTime.getFrameNo() == 12){
					continue;
				}
				lstFrameNo.add(overTime.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId, lstFrameNo);
				name = lstFramOt.get(0).getOvertimeWorkFrName().v();
				lstFrame.add(new OverTimeFrame(1, overTime.getFrameNo(),
						name, null, overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}

		}
		String workTypeName = hdWork.getWorkTypeCode() == null ||  Strings.isBlank(hdWork.getWorkTypeCode().v()) ? "" :
						repoWorkType.findByPK(companyId, hdWork.getWorkTypeCode().v()).get().getName().v();
		String workTimeName = "";
		if(hdWork.getWorkTimeCode() != null && !hdWork.getWorkTimeCode().v().equals("000")){
			Optional<WorkTimeSetting> workTime =  repoworkTime.findByCode(companyId,hdWork.getWorkTimeCode().v());
			if(workTime.isPresent()){
				workTimeName = workTime.get().getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}
		return new AppHolidayWorkFull(appId, workTypeName,workTimeName,
				hdWork.getWorkClock1().getStartTime() == null ? "" : this.convertTime(hdWork.getWorkClock1().getStartTime().v()),
				hdWork.getWorkClock1().getEndTime() == null ? "" : this.convertTime(hdWork.getWorkClock1().getEndTime().v()),
				hdWork.getWorkClock2().getStartTime() == null ? "" : this.convertTime(hdWork.getWorkClock2().getStartTime().v()),
				hdWork.getWorkClock2().getEndTime() == null ? "" : this.convertTime(hdWork.getWorkClock2().getEndTime().v()),
				lstFrame);
	}
	/**
	 * 勤務変更申請
	 * get Application Work Change Info
	 * appType = 2;
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppWorkChangeFull getAppWorkChangeInfo(String companyID, String appId) {
		Optional<AppWorkChange> workChange = repoworkChange.getAppworkChangeById(companyID, appId);
		AppWorkChange appWkChange = workChange.get();
		String workTypeName = appWkChange.getWorkTypeCd() == null ||  Strings.isBlank(appWkChange.getWorkTypeCd()) ? "" :
					repoWorkType.findByPK(companyID, appWkChange.getWorkTypeCd()).get().getName().v();
		String workTimeName = "";
		if(appWkChange.getWorkTimeCd() != null && !appWkChange.getWorkTimeCd().equals("000")){
			Optional<WorkTimeSetting> workTime =  repoworkTime.findByCode(companyID,appWkChange.getWorkTimeCd());
			if(workTime.isPresent()){
				workTimeName = workTime.get().getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}
		return new AppWorkChangeFull(appId, workTypeName,
				workTimeName,
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
	 * 休暇申請
	 * get Application Absence Info
	 * appType = 1;
	 * @param companyID
	 * @param appId
	 * @param day
	 * @return
	 */
	@Override
	public AppAbsenceFull getAppAbsenceInfo(String companyId, String appId, Integer day) {
		//get 休暇申請
		Optional<AppAbsence> absence = repoAbsence.getAbsenceById(companyId, appId);
		AppAbsence appAbsence = absence.get();
		//get 特別休暇申請
		Optional<AppForSpecLeave> appSpec = repoAppLeaveSpec.getAppForSpecLeaveById(companyId, appId);
		if(appSpec.isPresent()){
			appAbsence.setAppForSpecLeave(appSpec.get());
		}
		String workTimeName = "";
		if(appAbsence.getWorkTimeCode() != null){
			Optional<WorkTimeSetting> workTime =  repoworkTime.findByCode(companyId,appAbsence.getWorkTimeCode().v());
			if(workTime.isPresent()){
				workTimeName = workTime.get().getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}
//		String workTimeName = appAbsence.getWorkTimeCode() == null ? "" :
//			repoworkTime.findByCode(companyId,appAbsence.getWorkTimeCode().v()).get().getWorkTimeDisplayName().getWorkTimeName().v();
		String startTime1 = appAbsence.getStartTime1() == null ? "" : appAbsence.getStartTime1().getDayDivision().description
				+ appAbsence.getStartTime1().getInDayTimeWithFormat();
		String endTime1 = appAbsence.getEndTime1() == null ? "" : appAbsence.getEndTime1().getDayDivision().description
				+ appAbsence.getEndTime1().getInDayTimeWithFormat();
		String startTime2 = appAbsence.getStartTime2() == null ? "" : appAbsence.getStartTime2().getDayDivision().description
				+ appAbsence.getStartTime2().getInDayTimeWithFormat();
		String endTime2 = appAbsence.getEndTime2() == null ? "" : appAbsence.getEndTime2().getDayDivision().description
				+ appAbsence.getEndTime2().getInDayTimeWithFormat();
		AppForSpecLeave appForSpec = appAbsence.getAppForSpecLeave();
		String relaCode = appForSpec == null ? "" : appForSpec.getRelationshipCD() == null ? "" : appForSpec.getRelationshipCD().v();
		String relaName = relaCode.equals("") ? "" : repoRelationship.findByCode(companyId, relaCode).get().getRelationshipName().v();
		return new AppAbsenceFull(appId, appAbsence.getHolidayAppType() == null ? null : appAbsence.getHolidayAppType().value, day,
				workTimeName, appAbsence.getAllDayHalfDayLeaveAtr().value, startTime1, endTime1,startTime2, endTime2,
				relaCode, relaName, appForSpec == null ? false : appForSpec.isMournerFlag());
	}
	/**
	 * 振休振出申請
	 * get Application Complt Leave Info
	 * appType = 10;
	 * @param companyID
	 * @param appId
	 * @param type
	 * @return
	 */
	@Override
	public AppCompltLeaveFull getAppCompltLeaveInfo(String companyID, String appId, int type) {
		if(type == 0){//xin nghi
			AbsenceLeaveApp abs = absRepo.findByAppId(appId).get();
			return new AppCompltLeaveFull(abs.getAppID(), type,
					repoWorkType.findByPK(companyID, abs.getWorkTypeCD().v()).get().getName().v(),
					abs.getWorkTime1() == null ? null : this.convertTime(abs.getWorkTime1().getStartTime().v()),
					abs.getWorkTime1() == null ? null : this.convertTime(abs.getWorkTime1().getEndTime().v()));
		}
		//di lam
		RecruitmentApp rec = recRepo.findByAppId(appId).get();
		return new AppCompltLeaveFull(rec.getAppID(), type,
				repoWorkType.findByPK(companyID, rec.getWorkTypeCD().v()).get().getName().v(),
				this.convertTime(rec.getWorkTime1().getStartTime().v()),
				this.convertTime(rec.getWorkTime1().getEndTime().v()));
	}
	/**
	 * convert time from integer to Time_Short_HM
	 * @param time
	 * @return
	 */
	@Override
	public String convertTime(Integer time){
		if(time == null){
			return "";
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(time);
		return timeConvert.getDayDivision().description + timeConvert.getInDayTimeWithFormat();
	}



}
