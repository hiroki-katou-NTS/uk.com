package nts.uk.ctx.at.request.dom.application.applist.service.detail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.i18n.I18NText;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsenceRepository;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeave_Old;
import nts.uk.ctx.at.request.dom.application.appabsence.appforspecleave.AppForSpecLeaveRepository;
import nts.uk.ctx.at.request.dom.application.applist.service.AppCompltLeaveSync;
import nts.uk.ctx.at.request.dom.application.applist.service.OverTimeFrame;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AppCompltLeaveSyncOutput;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectlyRepository_Old;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.GoBackDirectly_Old;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.HolidayWorkInput;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeInput;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.TimeItemTypeAtr;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange_Old;
import nts.uk.ctx.at.request.dom.application.workchange.IAppWorkChangeRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.relationship.Relationship;
import nts.uk.ctx.at.shared.dom.relationship.repository.RelationshipRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * get info detail application
 * 
 * @author hoatt
 *
 */
@Stateless
public class AppDetailInfoImpl implements AppDetailInfoRepository {

	@Inject
	private GoBackDirectlyRepository_Old repoGoBack;
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
	private ApplicationRepository repoApp;
	@Inject
	private CompltLeaveSimMngRepository compLeaveRepo;

	/**
	 * 残業申請 get Application Over Time Info appType = 0;
	 * 
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppOverTimeInfoFull getAppOverTimeInfo(String companyId, String appId) {
		Map<String, AppOverTime> appOtOp = repoOverTime.getListAppOvertimeFrame(companyId, Arrays.asList(appId));
		AppOverTime appOt = appOtOp.get(appId);
		List<OverTimeInput> lstOverTimeInput = appOt.getOverTimeInput();
		List<OverTimeFrame> lstFrame = new ArrayList<>();
		for (OverTimeInput overTime : lstOverTimeInput) {
			List<Integer> lstFrameNo = new ArrayList<>();
			if (overTime.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)) {
				lstFrameNo.add(overTime.getFrameNo());
				if (overTime.getTimeItemTypeAtr().equals(TimeItemTypeAtr.NORMAL_TYPE)) {
					List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId,
							lstFrameNo);
					String nameNr = !lstFramBonus.isEmpty() ? lstFramBonus.get(0).getTimeItemName().v()
							: overTime.getFrameNo() + "マスタ未登録";
					lstFrame.add(new OverTimeFrame(3, overTime.getFrameNo(), nameNr, TimeItemTypeAtr.NORMAL_TYPE.value,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				} else {
					List<BonusPayTimeItem> specs = repoBonusTime.getListSpecialBonusPayTimeItemName(companyId,
							lstFrameNo);
					String nameBn = !specs.isEmpty() ? specs.get(0).getTimeItemName().v()
							: overTime.getFrameNo() + "マスタ未登録";
					lstFrame.add(new OverTimeFrame(4, overTime.getFrameNo(), nameBn, TimeItemTypeAtr.SPECIAL_TYPE.value,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				}
			}
			if (overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)) {
				lstFrameNo.add(overTime.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId, lstFrameNo);
				String nameBr = !lstFramWork.isEmpty() ? lstFramWork.get(0).getWorkdayoffFrName().v()
						: overTime.getFrameNo() + "マスタ未登録";
				lstFrame.add(new OverTimeFrame(2, overTime.getFrameNo(), nameBr, null,
						overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}
			if (overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)) {
				String name = "";
				if (overTime.getFrameNo() == 11) {
					name = I18NText.getText("CMM045_270");
				} else if (overTime.getFrameNo() == 12) {
					name = I18NText.getText("CMM045_271");
				} else {
					lstFrameNo.add(overTime.getFrameNo());
					List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId,
							lstFrameNo);
					name = !lstFramOt.isEmpty() ? lstFramOt.get(0).getOvertimeWorkFrName().v()
							: overTime.getFrameNo() + "マスタ未登録";
				}
				lstFrame.add(new OverTimeFrame(1, overTime.getFrameNo(), name, null,
						overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
						overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
						overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
			}
		}
		AppOvertimeDetail timeDetail = appOt.getAppOvertimeDetail().isPresent() ? appOt.getAppOvertimeDetail().get()
				: null;
		TimeNo417 timeNo417 = timeDetail == null ? null
				: new TimeNo417(
						timeDetail.getTime36Agree().getAgreeMonth().getActualTime().v()
								+ timeDetail.getTime36Agree().getApplicationTime().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream().map(c -> c.v())
								.collect(Collectors.toList()));
		return new AppOverTimeInfoFull(appId, this.convertTime(appOt.getWorkClockFrom1()),
				this.convertTime(appOt.getWorkClockTo1()), this.convertTime(appOt.getWorkClockFrom2()),
				this.convertTime(appOt.getWorkClockTo2()), lstFrame, this.convertTime(appOt.getOverTimeShiftNight()),
				this.convertTime(appOt.getFlexExessTime()), timeNo417,
				new WkTypeWkTime(appOt.getWorkTypeCode() != null ? appOt.getWorkTypeCode().v() : null,
						appOt.getSiftCode() != null ? appOt.getSiftCode().v() : null));
	}

	/**
	 * 直行直帰申請 get Application Go Back Info appType = 4;
	 * 
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppGoBackInfoFull getAppGoBackInfo(String companyID, String appId) {
		Optional<GoBackDirectly_Old> appGoBackOp = repoGoBack.findByApplicationID(companyID, appId);
		GoBackDirectly_Old appGoBack = appGoBackOp.get();
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
	 * 休日出勤時間申請 get Application Holiday Work Info appType = 6;
	 * 
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppHolidayWorkFull getAppHolidayWorkInfo(String companyId, String appId, List<WorkType> lstWkType,
			List<WorkTimeSetting> lstWkTime) {
		Map<String, AppHolidayWork> appHdWork = repoHolidayWork.getListAppHdWorkFrame(companyId, Arrays.asList(appId));
		AppHolidayWork hdWork = appHdWork.get(appId);
		List<HolidayWorkInput> lstHdWkInput = hdWork.getHolidayWorkInputs();
		List<OverTimeFrame> lstFrame = new ArrayList<>();
		for (HolidayWorkInput hdwk : lstHdWkInput) {
			List<Integer> lstFrameNo = new ArrayList<>();
			if (hdwk.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)) {
				lstFrameNo.add(hdwk.getFrameNo());
				List<BonusPayTimeItem> lstFramBonus = repoBonusTime.getListBonusPayTimeItemName(companyId, lstFrameNo);
				String nameBn = !lstFramBonus.isEmpty() ? lstFramBonus.get(0).getTimeItemName().v()
						: hdwk.getFrameNo() + "マスタ未登録";
				lstFrame.add(new OverTimeFrame(3, hdwk.getFrameNo(), nameBn, AttendanceType.BONUSPAYTIME.value,
						hdwk.getApplicationTime().v(), hdwk.getStartTime() == null ? null : hdwk.getStartTime().v(),
						hdwk.getEndTime() == null ? null : hdwk.getEndTime().v()));
			}
			if (hdwk.getAttendanceType().equals(AttendanceType.BREAKTIME)) {
				lstFrameNo.add(hdwk.getFrameNo());
				List<WorkdayoffFrame> lstFramWork = repoWork.getWorkdayoffFrameBy(companyId, lstFrameNo);
				String nameBr = !lstFramWork.isEmpty() ? lstFramWork.get(0).getWorkdayoffFrName().v()
						: hdwk.getFrameNo() + "マスタ未登録";
				lstFrame.add(new OverTimeFrame(2, hdwk.getFrameNo(), nameBr, null, hdwk.getApplicationTime().v(),
						hdwk.getStartTime() == null ? null : hdwk.getStartTime().v(),
						hdwk.getEndTime() == null ? null : hdwk.getEndTime().v()));
			}
			if (hdwk.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)) {
				String name = "";
				if (hdwk.getFrameNo() == 11 || hdwk.getFrameNo() == 12) {
					continue;
				}
				lstFrameNo.add(hdwk.getFrameNo());
				List<OvertimeWorkFrame> lstFramOt = repoOverTimeFr.getOvertimeWorkFrameByFrameNos(companyId,
						lstFrameNo);
				name = !lstFramOt.isEmpty() ? lstFramOt.get(0).getOvertimeWorkFrName().v()
						: hdwk.getFrameNo() + "マスタ未登録";
				lstFrame.add(new OverTimeFrame(1, hdwk.getFrameNo(), name, null, hdwk.getApplicationTime().v(),
						hdwk.getStartTime() == null ? null : hdwk.getStartTime().v(),
						hdwk.getEndTime() == null ? null : hdwk.getEndTime().v()));
			}

		}
		String workTypeName = "";
		String wkTypeCd = null;
		if (hdWork.getWorkTypeCode() != null && !Strings.isBlank(hdWork.getWorkTypeCode().v())) {
			// 勤務就業名称を作成 - WorkType
			wkTypeCd = hdWork.getWorkTypeCode().v();
			workTypeName = this.findWorkTypeName(lstWkType, wkTypeCd);
		}
		String workTimeName = "";
		String wkTimeCd = null;
		if (hdWork.getWorkTimeCode() != null && !hdWork.getWorkTimeCode().v().equals("000")) {
			// 勤務就業名称を作成 - WorkTime
			wkTimeCd = hdWork.getWorkTimeCode().v();
			workTimeName = this.findWorkTimeName(lstWkTime, wkTimeCd);
		}
		AppOvertimeDetail timeDetail = hdWork.getAppOvertimeDetail().isPresent() ? hdWork.getAppOvertimeDetail().get()
				: null;
		TimeNo417 timeNo417 = timeDetail == null ? null
				: new TimeNo417(
						timeDetail.getTime36Agree().getAgreeMonth().getActualTime().v()
								+ timeDetail.getTime36Agree().getApplicationTime().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
						timeDetail.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream().map(c -> c.v())
								.collect(Collectors.toList()));
		return new AppHolidayWorkFull(appId, workTypeName, workTimeName,
				hdWork.getWorkClock1().getStartTime() == null ? ""
						: this.convertTime(hdWork.getWorkClock1().getStartTime().v()),
				hdWork.getWorkClock1().getEndTime() == null ? ""
						: this.convertTime(hdWork.getWorkClock1().getEndTime().v()),
				hdWork.getWorkClock2().getStartTime() == null ? ""
						: this.convertTime(hdWork.getWorkClock2().getStartTime().v()),
				hdWork.getWorkClock2().getEndTime() == null ? ""
						: this.convertTime(hdWork.getWorkClock2().getEndTime().v()),
				lstFrame, timeNo417, new WkTypeWkTime(wkTypeCd, wkTimeCd));
	}

	/**
	 * 勤務変更申請 get Application Work Change Info appType = 2;
	 * 
	 * @param companyID
	 * @param appId
	 * @return
	 */
	@Override
	public AppWorkChangeFull getAppWorkChangeInfo(String companyID, String appId, List<WorkType> lstWkType,
			List<WorkTimeSetting> lstWkTime) {
		Optional<AppWorkChange_Old> workChange = repoworkChange.getAppworkChangeById(companyID, appId);
		AppWorkChange_Old appWkChange = workChange.get();
		String workTypeName = "";
		if (appWkChange.getWorkTypeCd() != null && !Strings.isBlank(appWkChange.getWorkTypeCd())) {
			// 勤務就業名称を作成 - WorkType
			workTypeName = this.findWorkTypeName(lstWkType, appWkChange.getWorkTypeCd());
		}
		String workTimeName = "";
		if (appWkChange.getWorkTimeCd() != null && !appWkChange.getWorkTimeCd().equals("000")) {
			// 勤務就業名称を作成 - WorkTime
			workTimeName = this.findWorkTimeName(lstWkTime, appWkChange.getWorkTimeCd());
		}
		return new AppWorkChangeFull(appId, workTypeName, workTimeName, appWkChange.getGoWorkAtr1(),
				this.convertTime(appWkChange.getWorkTimeStart1()), appWkChange.getBackHomeAtr1(),
				this.convertTime(appWkChange.getWorkTimeEnd1()), appWkChange.getGoWorkAtr2(),
				this.convertTime(appWkChange.getWorkTimeStart2()), appWkChange.getBackHomeAtr2(),
				this.convertTime(appWkChange.getWorkTimeEnd2()), this.convertTime(appWkChange.getBreakTimeStart1()),
				this.convertTime(appWkChange.getBreakTimeEnd1()));
	}

	/**
	 * 休暇申請 get Application Absence Info appType = 1;
	 * 
	 * @param companyID
	 * @param appId
	 * @param day
	 * @return
	 */
	@Override
	public AppAbsenceFull getAppAbsenceInfo(String companyId, String appId, Integer day, List<WorkType> lstWkType,
			List<WorkTimeSetting> lstWkTime) {
		// get 休暇申請
		Optional<AppAbsence> absence = repoAbsence.getAbsenceById(companyId, appId);
		AppAbsence appAbsence = absence.get();
		// get 特別休暇申請
		Optional<AppForSpecLeave_Old> appSpec = repoAppLeaveSpec.getAppForSpecLeaveById(companyId, appId);
		if (appSpec.isPresent()) {
			appAbsence.setAppForSpecLeave(appSpec.get());
		}
		String workTimeName = "";
		if (appAbsence.getWorkTimeCode() != null) {
			// 勤務就業名称を作成 - WorkTime
			workTimeName = this.findWorkTimeName(lstWkTime, appAbsence.getWorkTimeCode().v());
		}
		String startTime1 = appAbsence.getStartTime1() == null ? ""
				: appAbsence.getStartTime1().getDayDivision().description
						+ appAbsence.getStartTime1().getInDayTimeWithFormat();
		String endTime1 = appAbsence.getEndTime1() == null ? ""
				: appAbsence.getEndTime1().getDayDivision().description
						+ appAbsence.getEndTime1().getInDayTimeWithFormat();
		String startTime2 = appAbsence.getStartTime2() == null ? ""
				: appAbsence.getStartTime2().getDayDivision().description
						+ appAbsence.getStartTime2().getInDayTimeWithFormat();
		String endTime2 = appAbsence.getEndTime2() == null ? ""
				: appAbsence.getEndTime2().getDayDivision().description
						+ appAbsence.getEndTime2().getInDayTimeWithFormat();
		AppForSpecLeave_Old appForSpec = appAbsence.getAppForSpecLeave();
		String relaCode = appForSpec == null ? ""
				: appForSpec.getRelationshipCD() == null ? "" : appForSpec.getRelationshipCD().v();
		String relaName = "";
		if (!relaCode.equals("")) {
			Optional<Relationship> rela = repoRelationship.findByCode(companyId, relaCode);
			relaName = rela.isPresent() ? rela.get().getRelationshipName().v() : relaCode + "マスタ未登録";
		}
		// ver39
		String workTypeName = "";
		if (appAbsence.getWorkTypeCode() != null && !Strings.isBlank(appAbsence.getWorkTypeCode().v())) {
			// 勤務就業名称を作成 - WorkType
			workTypeName = this.findWorkTypeName(lstWkType, appAbsence.getWorkTypeCode().v());
		}
		return new AppAbsenceFull(appId,
				appAbsence.getHolidayAppType() == null ? null : appAbsence.getHolidayAppType().value, day, workTimeName,
				appAbsence.getAllDayHalfDayLeaveAtr().value, startTime1, endTime1, startTime2, endTime2, relaCode,
				relaName, appForSpec == null ? false : appForSpec.isMournerFlag(), workTypeName);
	}

	/**
	 * 振休振出申請 get Application Complt Leave Info appType = 10;
	 * 
	 * @param companyID
	 * @param appId
	 * @param type
	 * @return
	 */
	@Override
	public AppCompltLeaveFull getAppCompltLeaveInfo(String companyID, String appId, int type,
			List<WorkType> lstWkType) {
		if (type == 0) {// xin nghi
			AbsenceLeaveApp abs = absRepo.findByAppId(appId).get();
			return new AppCompltLeaveFull(abs.getAppID(), type,
					this.findWorkTypeName(lstWkType, abs.getWorkTypeCD().v()), // 勤務就業名称を作成
																				// -
																				// WorkType
					abs.getWorkTime1() == null ? null : this.convertTime(abs.getWorkTime1().getStartTime().v()),
					abs.getWorkTime1() == null ? null : this.convertTime(abs.getWorkTime1().getEndTime().v()));
		}
		// di lam
		RecruitmentApp rec = recRepo.findByAppId(appId).get();
		return new AppCompltLeaveFull(rec.getAppID(), type, this.findWorkTypeName(lstWkType, rec.getWorkTypeCD().v()), // 勤務就業名称を作成
																														// -
																														// WorkType
				this.convertTime(rec.getWorkTime1().getStartTime().v()),
				this.convertTime(rec.getWorkTime1().getEndTime().v()));
	}

	/**
	 * convert time from integer to Time_Short_HM
	 * 
	 * @param time
	 * @return
	 */
	@Override
	public String convertTime(Integer time) {
		if (time == null) {
			return "";
		}
		TimeWithDayAttr timeConvert = new TimeWithDayAttr(time);
		return timeConvert.getDayDivision().description + timeConvert.getInDayTimeWithFormat();
	}

	/**
	 * 残業申請 get list Application Over Time Info appType = 0;
	 * 
	 * @param companyID
	 * @param appId
	 * @return list AppOverTimeInfoFull
	 */
	@Override
	public List<AppOverTimeInfoFull> getListAppOverTimeInfo(String companyId, List<String> lstAppId) {
		List<AppOverTimeInfoFull> lstAppFull = new ArrayList<>();
		// 1.加給時間 - BONUSPAYTIME
		// 1.A NORMAL_TYPE
		List<BonusPayTimeItem> lstBonus = repoBonusTime.getListBonusPayTimeItem(companyId);
		// 1.B SPECIAL_TYPE
		List<BonusPayTimeItem> lstSpBonus = repoBonusTime.getListSpecialBonusPayTimeItem(companyId);
		// 2.休出時間 - BREAKTIME
		List<WorkdayoffFrame> lstWork = repoWork.getAllWorkdayoffFrame(companyId);
		// 3.残業時間 - NORMALOVERTIME
		List<OvertimeWorkFrame> lstOtWork = repoOverTimeFr.getAllOvertimeWorkFrame(companyId);
		// get list appOverTime detail
		Map<String, AppOverTime> mapOvFrame = repoOverTime.getListAppOvertimeFrame(companyId, lstAppId);
		for (String appId : lstAppId) {
			AppOverTime appOt = mapOvFrame.get(appId);
			List<OverTimeInput> lstOverTimeInput = appOt.getOverTimeInput();
			List<OverTimeFrame> lstFrame = new ArrayList<>();
			for (OverTimeInput overTime : lstOverTimeInput) {
				int frameNo = overTime.getFrameNo();
				if (overTime.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)) {// 加給時間
					if (overTime.getTimeItemTypeAtr().equals(TimeItemTypeAtr.NORMAL_TYPE)) {// NORMAL_TYPE
						String frameName = this.findBonusItem(lstBonus, frameNo);
						lstFrame.add(new OverTimeFrame(3, frameNo, frameName, TimeItemTypeAtr.NORMAL_TYPE.value,
								overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
								overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
								overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
					} else {// SPECIAL_TYPE
						String frameName = this.findBonusItem(lstSpBonus, frameNo);
						lstFrame.add(new OverTimeFrame(4, frameNo, frameName, TimeItemTypeAtr.SPECIAL_TYPE.value,
								overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
								overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
								overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
					}
				}
				if (overTime.getAttendanceType().equals(AttendanceType.BREAKTIME)) {// 休出時間
					String frameName = this.findWorkItem(lstWork, frameNo);
					lstFrame.add(new OverTimeFrame(2, frameNo, frameName, null,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				}
				if (overTime.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)) {// 残業時間
					String name = "";
					if (overTime.getFrameNo() == 11) {
						name = "時間外深夜時間";
					} else if (overTime.getFrameNo() == 12) {
						name = "ﾌﾚｯｸｽ超過";
					} else {
						name = this.findOtWorkItem(lstOtWork, frameNo);
					}
					lstFrame.add(new OverTimeFrame(1, frameNo, name, null,
							overTime.getApplicationTime() == null ? null : overTime.getApplicationTime().v(),
							overTime.getStartTime() == null ? null : overTime.getStartTime().v(),
							overTime.getEndTime() == null ? null : overTime.getEndTime().v()));
				}
			}
			AppOvertimeDetail timeDetail = appOt.getAppOvertimeDetail().isPresent() ? appOt.getAppOvertimeDetail().get()
					: null;
			TimeNo417 timeNo417 = timeDetail == null ? null
					: new TimeNo417(
							timeDetail.getTime36Agree().getAgreeMonth().getActualTime().v()
									+ timeDetail.getTime36Agree().getApplicationTime().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream().map(c -> c.v())
									.collect(Collectors.toList()));
			lstAppFull.add(new AppOverTimeInfoFull(appId, this.convertTime(appOt.getWorkClockFrom1()),
					this.convertTime(appOt.getWorkClockTo1()), this.convertTime(appOt.getWorkClockFrom2()),
					this.convertTime(appOt.getWorkClockTo2()), lstFrame,
					this.convertTime(appOt.getOverTimeShiftNight()), this.convertTime(appOt.getFlexExessTime()),
					timeNo417, new WkTypeWkTime(appOt.getWorkTypeCode() != null ? appOt.getWorkTypeCode().v() : null,
							appOt.getSiftCode() != null ? appOt.getSiftCode().v() : null)));
		}
		return lstAppFull;
	}

	/**
	 * 休日出勤申請一覧 get list Application Holiday Work Info appType = 6;
	 * 
	 * @param companyID
	 * @param appId
	 * @return list AppHolidayWorkFull
	 */
	@Override
	public List<AppHolidayWorkFull> getListAppHdWorkInfo(String companyId, List<String> lstAppId,
			List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime) {
		List<AppHolidayWorkFull> lstAppFull = new ArrayList<>();
		// 1.加給時間 - BONUSPAYTIME
		// 1.A NORMAL_TYPE
		List<BonusPayTimeItem> lstBonus = repoBonusTime.getListBonusPayTimeItem(companyId);
		// 2.休出時間 - BREAKTIME
		List<WorkdayoffFrame> lstWork = repoWork.getAllWorkdayoffFrame(companyId);
		// 3.残業時間 - NORMALOVERTIME
		List<OvertimeWorkFrame> lstOtWork = repoOverTimeFr.getAllOvertimeWorkFrame(companyId);
		// get list appHoliday detail
		Map<String, AppHolidayWork> mapHdFrame = repoHolidayWork.getListAppHdWorkFrame(companyId, lstAppId);
		if (mapHdFrame.isEmpty()) {
			return new ArrayList<>();
		}
		Map<String, String> mapWorkTimeName = new HashMap<>();
		for (String appId : lstAppId) {
			AppHolidayWork hdWork = mapHdFrame.get(appId);
			List<HolidayWorkInput> lstHdInput = hdWork.getHolidayWorkInputs();
			List<OverTimeFrame> lstFrame = new ArrayList<>();
			for (HolidayWorkInput hd : lstHdInput) {
				int frameNo = hd.getFrameNo();
				if (hd.getAttendanceType().equals(AttendanceType.BONUSPAYTIME)) {// 加給時間
					String frameName = this.findBonusItem(lstBonus, frameNo);
					lstFrame.add(new OverTimeFrame(3, frameNo, frameName, TimeItemTypeAtr.NORMAL_TYPE.value,
							hd.getApplicationTime() == null ? null : hd.getApplicationTime().v(),
							hd.getStartTime() == null ? null : hd.getStartTime().v(),
							hd.getEndTime() == null ? null : hd.getEndTime().v()));
				}
				if (hd.getAttendanceType().equals(AttendanceType.BREAKTIME)) {// 休出時間
					String frameName = this.findWorkItem(lstWork, frameNo);
					lstFrame.add(new OverTimeFrame(2, frameNo, frameName, null,
							hd.getApplicationTime() == null ? null : hd.getApplicationTime().v(),
							hd.getStartTime() == null ? null : hd.getStartTime().v(),
							hd.getEndTime() == null ? null : hd.getEndTime().v()));
				}
				if (hd.getAttendanceType().equals(AttendanceType.NORMALOVERTIME)) {// 残業時間
					if (frameNo == 11 || frameNo == 12) {// appHd khong co loai
															// 11,12
						continue;
					}
					String name = this.findOtWorkItem(lstOtWork, frameNo);
					lstFrame.add(new OverTimeFrame(1, frameNo, name, null,
							hd.getApplicationTime() == null ? null : hd.getApplicationTime().v(),
							hd.getStartTime() == null ? null : hd.getStartTime().v(),
							hd.getEndTime() == null ? null : hd.getEndTime().v()));
				}
			}
			String workTypeName = "";
			String wkTypeCd = null;
			if (hdWork.getWorkTypeCode() != null && !Strings.isBlank(hdWork.getWorkTypeCode().v())) {
				// 勤務就業名称を作成 - WorkType
				wkTypeCd = hdWork.getWorkTypeCode().v();
				workTypeName = this.findWorkTypeName(lstWkType, wkTypeCd);
			}
			String workTimeName = "";
			String wkTimeCD = null;
			if (hdWork.getWorkTimeCode() != null && !hdWork.getWorkTimeCode().v().equals("000")) {
				wkTimeCD = hdWork.getWorkTimeCode().v();
				if (mapWorkTimeName.containsKey(wkTimeCD)) {
					workTimeName = mapWorkTimeName.get(wkTimeCD);
				} else {
					// 勤務就業名称を作成 - WorkTime
					workTimeName = this.findWorkTimeName(lstWkTime, wkTimeCD);
					mapWorkTimeName.put(wkTimeCD, workTimeName);

				}
			}
			AppOvertimeDetail timeDetail = hdWork.getAppOvertimeDetail().isPresent()
					? hdWork.getAppOvertimeDetail().get() : null;
			TimeNo417 timeNo417 = timeDetail == null ? null
					: new TimeNo417(
							timeDetail.getTime36Agree().getAgreeMonth().getActualTime().v()
									+ timeDetail.getTime36Agree().getApplicationTime().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getLimitErrorTime().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getNumOfYear36Over().v(),
							timeDetail.getTime36Agree().getAgreeMonth().getYear36OverMonth().stream().map(c -> c.v())
									.collect(Collectors.toList()));
			lstAppFull.add(new AppHolidayWorkFull(appId, workTypeName, workTimeName,
					hdWork.getWorkClock1().getStartTime() == null ? ""
							: this.convertTime(hdWork.getWorkClock1().getStartTime().v()),
					hdWork.getWorkClock1().getEndTime() == null ? ""
							: this.convertTime(hdWork.getWorkClock1().getEndTime().v()),
					hdWork.getWorkClock2().getStartTime() == null ? ""
							: this.convertTime(hdWork.getWorkClock2().getStartTime().v()),
					hdWork.getWorkClock2().getEndTime() == null ? ""
							: this.convertTime(hdWork.getWorkClock2().getEndTime().v()),
					lstFrame, timeNo417, new WkTypeWkTime(wkTypeCd, wkTimeCD)));
		}
		return lstAppFull;
	}

	/**
	 * 加給時間 - BONUSPAYTIME find bonus item by frame No
	 * 
	 * @param lstBonus
	 * @param frameNo
	 * @return frame Name
	 */
	private String findBonusItem(List<BonusPayTimeItem> lstBonus, int frameNo) {
		for (BonusPayTimeItem bonus : lstBonus) {
			if (bonus.getId() == frameNo) {
				return bonus.getTimeItemName().v();
			}
		}
		return frameNo + "マスタ未登録";
	}

	/**
	 * 2.休出時間 - BREAKTIME find work item by frame No
	 * 
	 * @param lstWork
	 * @param frameNo
	 * @return frame Name
	 */
	private String findWorkItem(List<WorkdayoffFrame> lstWork, int frameNo) {
		for (WorkdayoffFrame work : lstWork) {
			if (work.getWorkdayoffFrNo().v().equals(new BigDecimal(frameNo))) {
				return work.getWorkdayoffFrName().v();
			}
		}
		return frameNo + "マスタ未登録";
	}

	/**
	 * 残業時間 - NORMALOVERTIME find overtime work item by frame No
	 * 
	 * @param lstOtWork
	 * @param frameNo
	 * @return frame Name
	 */
	private String findOtWorkItem(List<OvertimeWorkFrame> lstOtWork, int frameNo) {
		for (OvertimeWorkFrame otWork : lstOtWork) {
			if (otWork.getOvertimeWorkFrNo().v().equals(new BigDecimal(frameNo))) {
				return otWork.getOvertimeWorkFrName().v();
			}
		}
		return frameNo + "マスタ未登録";
	}

	/**
	 * 勤務変更申請一覧 get list Application Work Change Info
	 * 
	 * @param companyID
	 * @param lstAppId
	 * @return
	 */
	@Override
	public List<AppWorkChangeFull> getListAppWorkChangeInfo(String companyID, List<String> lstAppId,
			List<WorkType> lstWkType, List<WorkTimeSetting> lstWkTime) {
		List<AppWorkChangeFull> lstAppFull = new ArrayList<>();
		// get list app work change by lstId
		List<AppWorkChange_Old> lstWorkChange = repoworkChange.getListAppWorkChangeByID(companyID, lstAppId);
		Map<String, String> mapWorkTypeName = new HashMap<>();
		Map<String, String> mapWorkTimeName = new HashMap<>();
		for (AppWorkChange_Old appWkChange : lstWorkChange) {
			// find work type name
			String wkTypeCD = appWkChange.getWorkTypeCd();
			String workTypeName = "";
			if (wkTypeCD != null && !Strings.isBlank(wkTypeCD)) {
				if (mapWorkTypeName.containsKey(wkTypeCD)) {
					workTypeName = mapWorkTypeName.get(wkTypeCD);
				} else {
					// 勤務就業名称を作成 - WorkType
					workTypeName = this.findWorkTypeName(lstWkType, wkTypeCD);
					mapWorkTypeName.put(wkTypeCD, workTypeName);
				}
			}
			// find work time name
			String wkTimeCD = appWkChange.getWorkTimeCd();
			String workTimeName = "";
			if (wkTimeCD != null && !wkTimeCD.equals("000")) {
				if (mapWorkTimeName.containsKey(wkTimeCD)) {
					workTimeName = mapWorkTimeName.get(wkTimeCD);
				} else {
					// 勤務就業名称を作成 - WorkTime
					workTimeName = this.findWorkTimeName(lstWkTime, wkTimeCD);
					mapWorkTimeName.put(wkTimeCD, workTimeName);
				}
			}
			lstAppFull.add(new AppWorkChangeFull(appWkChange.getAppId(), workTypeName, workTimeName,
					appWkChange.getGoWorkAtr1(), this.convertTime(appWkChange.getWorkTimeStart1()),
					appWkChange.getBackHomeAtr1(), this.convertTime(appWkChange.getWorkTimeEnd1()),
					appWkChange.getGoWorkAtr2(), this.convertTime(appWkChange.getWorkTimeStart2()),
					appWkChange.getBackHomeAtr2(), this.convertTime(appWkChange.getWorkTimeEnd2()),
					this.convertTime(appWkChange.getBreakTimeStart1()),
					this.convertTime(appWkChange.getBreakTimeEnd1())));
		}
		return lstAppFull;
	}

	/**
	 * 直行直帰申請一覧 get list Application Go Back Info
	 * 
	 * @param companyID
	 * @param lstAppId
	 * @return
	 */
	@Override
	public List<AppGoBackInfoFull> getListAppGoBackInfo(String companyID, List<String> lstAppId) {
		List<AppGoBackInfoFull> lstAppFull = new ArrayList<>();
		List<GoBackDirectly_Old> lstAppGoBack = repoGoBack.getListAppGoBack(companyID, lstAppId);
		for (GoBackDirectly_Old appGoBack : lstAppGoBack) {
			lstAppFull.add(new AppGoBackInfoFull(appGoBack.getAppID(), appGoBack.getGoWorkAtr1().value,
					this.convertTime(appGoBack.getWorkTimeStart1().map(x -> x.v()).orElse(null)),
					appGoBack.getBackHomeAtr1().value,
					this.convertTime(appGoBack.getWorkTimeEnd1().map(x -> x.v()).orElse(null)),
					appGoBack.getGoWorkAtr2().map(x -> x.value).orElse(null),
					this.convertTime(appGoBack.getWorkTimeStart2().map(x -> x.v()).orElse(null)),
					appGoBack.getBackHomeAtr2().map(x -> x.value).orElse(null),
					this.convertTime(appGoBack.getWorkTimeEnd2().map(x -> x.v()).orElse(null))));
		}
		return lstAppFull;
	}

	@Override
	public AppCompltLeaveSync getCompltLeaveSync(String companyID, String appId, List<WorkType> lstWkType) {
		AppCompltLeaveFull appMain = null;
		AppCompltLeaveFull appSub = null;
		String appDateSub = null;
		String appInputSub = null;
		// アルゴリズム「申請一覧リスト取得振休振出」を実行する-(get List App Complement Leave): 6 -
		// 申請一覧リスト取得振休振出
		AppCompltLeaveSyncOutput sync = this.getAppComplementLeaveSync(companyID, appId);
		if (!sync.isSync()) {// TH k co don lien ket
			// lay thong tin chi tiet
			appMain = this.getAppCompltLeaveInfo(companyID, appId, sync.getType(), lstWkType);
		} else {// TH co don lien ket
				// lay thong tin chi tiet A
			appMain = this.getAppCompltLeaveInfo(companyID, appId, sync.getType(), lstWkType);
			// check B co trong list don xin k?
			String appIdSync = sync.getType() == 0 ? sync.getRecId() : sync.getAbsId();
			// lay thong tin chung
			Application sub = repoApp.findByID(companyID, appIdSync).get();
			appDateSub = sub.getAppDate().getApplicationDate().toString("yyyy/MM/dd");
			appInputSub = sub.getInputDate().toString("yyyy/MM/dd HH:mm");
			appSub = this.getAppCompltLeaveInfo(companyID, appIdSync, sync.getType() == 0 ? 1 : 0, lstWkType);
		}
		return new AppCompltLeaveSync(sync.getType(), sync.isSync(), appMain, appSub, appDateSub, appInputSub);
	}

	@Override
	public AppCompltLeaveSyncOutput getAppComplementLeaveSync(String companyId, String appId) {
		Optional<AbsenceLeaveApp> abs = absRepo.findByAppId(appId);
		Optional<CompltLeaveSimMng> sync = null;
		String absId = "";
		String recId = "";
		boolean synced = false;
		int type = 0;
		if (abs.isPresent()) {// don xin nghi
			absId = appId;
			// tim lien ket theo abs
			sync = compLeaveRepo.findByAbsID(appId);
			if (sync.isPresent() && sync.get().getSyncing().equals(SyncState.SYNCHRONIZING)) {
				recId = sync.get().getRecAppID();
				synced = true;
			}
		} else {// don lam bu
			type = 1;
			recId = appId;
			sync = compLeaveRepo.findByRecID(appId);
			if (sync.isPresent() && sync.get().getSyncing().equals(SyncState.SYNCHRONIZING)) {
				absId = sync.get().getAbsenceLeaveAppID();
				synced = true;
			}
		}
		return new AppCompltLeaveSyncOutput(absId, recId, synced, type);
	}

	/**
	 * 勤務就業名称を作成 - WorkType
	 * 
	 * @param lstWkType
	 * @param wkTypeCd
	 * @return
	 */
	@Override
	public String findWorkTypeName(List<WorkType> lstWkType, String wkTypeCd) {
		if (lstWkType.isEmpty()) {
			Optional<WorkType> wt = repoWorkType.findByPK(AppContexts.user().companyId(), wkTypeCd);
			return wt.isPresent() ? wt.get().getName().v() : wkTypeCd + "マスタ未登録";
		}
		for (WorkType workType : lstWkType) {
			if (workType.getWorkTypeCode().v().equals(wkTypeCd)) {
				return workType.getName().v();
			}
		}
		return wkTypeCd + "マスタ未登録";
	}

	/**
	 * 勤務就業名称を作成 - WorkTime
	 * 
	 * @param lstWkTime
	 * @param wkTimeCd
	 * @return
	 */
	@Override
	public String findWorkTimeName(List<WorkTimeSetting> lstWkTime, String wkTimeCd) {
		if (lstWkTime.isEmpty()) {
			Optional<WorkTimeSetting> workTime = repoworkTime.findByCode(AppContexts.user().companyId(), wkTimeCd);
			return workTime.isPresent() ? workTime.get().getWorkTimeDisplayName().getWorkTimeName().v()
					: wkTimeCd + "マスタ未登録";
		}
		for (WorkTimeSetting workTime : lstWkTime) {
			if (workTime.getWorktimeCode().v().equals(wkTimeCd)) {
				return workTime.getWorkTimeDisplayName().getWorkTimeName().v();
			}
		}
		return wkTimeCd + "マスタ未登録";
	}
}
