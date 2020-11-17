package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.HolidayMidNightTime;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeShiftNight;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.overtime.ReasonDivergence;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReason;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36Agree;
import nts.uk.ctx.at.request.dom.application.overtime.time36.Time36AgreeUpperLimit;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOverTime;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetail;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtAppOvertimeDetailPk;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInput;
import nts.uk.ctx.at.request.infra.entity.application.overtime.KrqdtOvertimeInputPK;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.deviationtime.DiverdenceReasonCode;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.holidaywork.StaturoryAtrOfHolidayWork;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Stateless
public class JpaAppOverTimeRepository extends JpaRepository implements AppOverTimeRepository{
	public static final String FIND_BY_ID = "SELECT *  "
			+ "FROM KRQDT_APP_OVERTIME as a INNER JOIN KRQDT_APPLICATION as b ON a.APP_ID = b.APP_ID"
			+ " WHERE a.APP_ID = @appID AND a.CID = @companyId";
	@Override
	public Optional<AppOverTime> find(String companyId, String appId) {
		return new NtsStatement(FIND_BY_ID, this.jdbcProxy())
				.paramString("appID", appId)
				.paramString("companyId", companyId)
				.getSingle(res -> toDomain(res));
	}
	// KrqdtAppOverTime
	@Override
	public void add(AppOverTime appOverTime) {
		this.commandProxy().insert(toEntity(appOverTime));
	}
	private KrqdtAppOverTime toEntity(AppOverTime appOverTime) {
		KrqdtAppOverTime krqdtAppOverTime = new KrqdtAppOverTime();
		krqdtAppOverTime.krqdtAppOvertimePK.appId = appOverTime.getAppID();
		krqdtAppOverTime.overtimeAtr = appOverTime.getOverTimeClf().value;
		krqdtAppOverTime.workTypeCode = appOverTime.getWorkInfoOp().map(x -> x.getWorkTypeCode().v()).orElse(null);
		krqdtAppOverTime.workTimeCode = appOverTime.getWorkInfoOp().map(x -> x.getWorkTimeCode().v()).orElse(null);
		List<TimeZoneWithWorkNo> workHours = appOverTime.getWorkHoursOp().orElse(Collections.emptyList());
		workHours.stream().forEach(item -> {
			if (item.getWorkNo().v() == 1) {
				krqdtAppOverTime.workTimeStart1 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.workTimeEnd1 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 2) {
				krqdtAppOverTime.workTimeStart2 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.workTimeEnd2 = item.getTimeZone().getEndTime().v();
			}
		});
		// 
		
		// 
		krqdtAppOverTime.flexExcessTime = appOverTime.getApplicationTime().getFlexOverTime().map(x -> x.v()).orElse(null);
		krqdtAppOverTime.overTimeNight = appOverTime.getApplicationTime().getOverTimeShiftNight().map(x -> x.getOverTimeMidNight().v()).orElse(null);
		krqdtAppOverTime.totalNight = appOverTime.getApplicationTime().getOverTimeShiftNight().map(x -> x.getMidNightOutSide().v()).orElse(null);
		
		List<TimeZoneWithWorkNo> breakTimes = appOverTime.getBreakTimeOp().orElse(Collections.emptyList());
		breakTimes.stream().forEach(item -> {
			if (item.getWorkNo().v() == 1) {
				krqdtAppOverTime.breakTimeStart1 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd1 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 2) {
				krqdtAppOverTime.breakTimeStart2 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd2 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 3) {
				krqdtAppOverTime.breakTimeStart3 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd3 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 4) {
				krqdtAppOverTime.breakTimeStart4 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd4 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 5) {
				krqdtAppOverTime.breakTimeStart5 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd5 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 6) {
				krqdtAppOverTime.breakTimeStart6 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd6 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 7) {
				krqdtAppOverTime.breakTimeStart7 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd7 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 8) {
				krqdtAppOverTime.breakTimeStart8 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd8 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 9) {
				krqdtAppOverTime.breakTimeStart9 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd9 = item.getTimeZone().getEndTime().v();
			} else if (item.getWorkNo().v() == 10) {
				krqdtAppOverTime.breakTimeStart10 = item.getTimeZone().getStartTime().v();
				krqdtAppOverTime.breakTimeEnd10 = item.getTimeZone().getEndTime().v();
			}
		});
		krqdtAppOverTime.overtimeInputs = new ArrayList<KrqdtOvertimeInput>();
		
		List<OvertimeApplicationSetting> overtimeApplicationSettings = appOverTime.getApplicationTime().getApplicationTime();
		if (CollectionUtil.isEmpty(overtimeApplicationSettings)) {
			krqdtAppOverTime.overtimeInputs = overtimeApplicationSettings
				.stream()
				.map(x -> new KrqdtOvertimeInput(
										new KrqdtOvertimeInputPK(
												AppContexts.user().companyId(),
												appOverTime.getAppID(),
												x.getAttendanceType().value,
												x.getFrameNo().v()),
										x.getApplicationTime().v(),
										krqdtAppOverTime))
				.collect(Collectors.toList());
				
									
		}
		AppOvertimeDetail appOvertimeDetail = appOverTime.getDetailOverTimeOp().orElse(null);
		if (appOvertimeDetail != null) {
			Time36Agree time36Agree = appOvertimeDetail.getTime36Agree();
			Time36AgreeUpperLimit time36AgreeUpperLimit = appOvertimeDetail.getTime36AgreeUpperLimit();
			KrqdtAppOvertimeDetail krqdtAppOvertimeDetail = new KrqdtAppOvertimeDetail(
					new KrqdtAppOvertimeDetailPk(
							AppContexts.user().companyId(),
							appOverTime.getAppID()),
					time36Agree.getApplicationTime().v(),
					appOvertimeDetail.getYearMonth().v(),
					time36Agree.getAgreeMonth().getActualTime().v(),
					time36Agree.getAgreeMonth().getLimitErrorTime().v(),
					time36Agree.getAgreeMonth().getLimitAlarmTime().v(),
					time36Agree.getAgreeMonth().getExceptionLimitErrorTime().map(x -> x.v()).orElse(null),
					time36Agree.getAgreeMonth().getExceptionLimitAlarmTime().map(x -> x.v()).orElse(null),
					time36Agree.getAgreeMonth().getNumOfYear36Over().v(),
					time36Agree.getAgreeAnnual().getActualTime().v(),
					time36Agree.getAgreeAnnual().getLimitTime().v(),
					time36AgreeUpperLimit.getApplicationTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitMonth().getOverTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitMonth().getUpperLimitTime().v(),
					time36AgreeUpperLimit.getAgreeUpperLimitAverage().getUpperLimitTime().v(),
					krqdtAppOverTime
					);
			krqdtAppOverTime.appOvertimeDetail = krqdtAppOvertimeDetail;
		}
		
		
		return krqdtAppOverTime;
	}
	@Override
	public void update(AppOverTime appOverTime) {
		KrqdtAppOverTime krqdtAppOverTime = toEntity(appOverTime);
		Optional<KrqdtAppOverTime> updateAppOverTime = this.queryProxy().find(krqdtAppOverTime.krqdtAppOvertimePK, KrqdtAppOverTime.class);
		if (!updateAppOverTime.isPresent()) return;
		updateAppOverTime.get().overtimeAtr = krqdtAppOverTime.overtimeAtr;
		updateAppOverTime.get().workTypeCode = krqdtAppOverTime.workTypeCode;
		updateAppOverTime.get().workTimeCode = krqdtAppOverTime.workTimeCode;
		updateAppOverTime.get().workTimeStart1 = krqdtAppOverTime.workTimeStart1;
		updateAppOverTime.get().workTimeEnd1 = krqdtAppOverTime.workTimeEnd1;
		updateAppOverTime.get().workTimeStart2 = krqdtAppOverTime.workTimeStart2;
		updateAppOverTime.get().workTimeEnd2 = krqdtAppOverTime.workTimeEnd2;
		
		
		updateAppOverTime.get().flexExcessTime = krqdtAppOverTime.flexExcessTime;
		updateAppOverTime.get().overTimeNight = krqdtAppOverTime.overTimeNight;
		updateAppOverTime.get().totalNight = krqdtAppOverTime.totalNight;
		
		
		updateAppOverTime.get().breakTimeStart1 = krqdtAppOverTime.breakTimeStart1;
		updateAppOverTime.get().breakTimeEnd1 = krqdtAppOverTime.breakTimeEnd1;
		updateAppOverTime.get().breakTimeStart2 = krqdtAppOverTime.breakTimeStart2;
		updateAppOverTime.get().breakTimeEnd2 = krqdtAppOverTime.breakTimeEnd2;
		updateAppOverTime.get().breakTimeStart3 = krqdtAppOverTime.breakTimeStart3;
		updateAppOverTime.get().breakTimeEnd3 = krqdtAppOverTime.breakTimeEnd3;
		updateAppOverTime.get().breakTimeStart4 = krqdtAppOverTime.breakTimeStart4;
		updateAppOverTime.get().breakTimeEnd4 = krqdtAppOverTime.breakTimeEnd4;
		updateAppOverTime.get().breakTimeStart5 = krqdtAppOverTime.breakTimeStart5;
		updateAppOverTime.get().breakTimeEnd5 = krqdtAppOverTime.breakTimeEnd5;
		updateAppOverTime.get().breakTimeStart6 = krqdtAppOverTime.breakTimeStart6;
		updateAppOverTime.get().breakTimeEnd6 = krqdtAppOverTime.breakTimeEnd6;
		updateAppOverTime.get().breakTimeStart7 = krqdtAppOverTime.breakTimeStart7;
		updateAppOverTime.get().breakTimeEnd7 = krqdtAppOverTime.breakTimeEnd7;
		updateAppOverTime.get().breakTimeStart8 = krqdtAppOverTime.breakTimeStart8;
		updateAppOverTime.get().breakTimeEnd8 = krqdtAppOverTime.breakTimeEnd8;
		updateAppOverTime.get().breakTimeStart9 = krqdtAppOverTime.breakTimeStart9;
		updateAppOverTime.get().breakTimeEnd9 = krqdtAppOverTime.breakTimeEnd9;
		updateAppOverTime.get().breakTimeStart10 = krqdtAppOverTime.breakTimeStart10;
		updateAppOverTime.get().breakTimeEnd10 = krqdtAppOverTime.breakTimeEnd10;
		
		updateAppOverTime.get().overtimeInputs = krqdtAppOverTime.overtimeInputs;
		updateAppOverTime.get().appOvertimeDetail = krqdtAppOverTime.appOvertimeDetail;
		
		this.commandProxy().update(updateAppOverTime.get());
	}
	public AppOverTime toDomain(NtsResultRecord res) {
		String pattern = "yyyy/MM/dd HH:mm:ss";
		String pattern2 = "yyyy/MM/dd";
		DateFormat df = new SimpleDateFormat(pattern);
		DateFormat df2 = new SimpleDateFormat(pattern2);
		String appID = res.getString("APP_ID");
		Integer version = res.getInt("EXCLUS_VER");
		PrePostAtr prePostAtr = EnumAdaptor.valueOf(res.getInt("PRE_POST_ATR"), PrePostAtr.class);
		String enteredPerson = res.getString("ENTERED_PERSON_SID");
		GeneralDateTime inputDate = GeneralDateTime.fromString(df.format(res.getDate("INPUT_DATE")), pattern);
		ApplicationDate appDate = new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2));
		ApplicationType appType = EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class);
		String employeeID = res.getString("APPLICANTS_SID");
		Application application = new Application(version, appID, prePostAtr, employeeID, appType, appDate, enteredPerson, inputDate, null);
		if (res.getString("REASON_REVERSION") == null) {
			application.setOpReversionReason(Optional.ofNullable(null));
		}else {
			application.setOpReversionReason(Optional.ofNullable(new ReasonForReversion(res.getString("REASON_REVERSION"))));			
		}
//		application.setAppDate(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_DATE")), pattern2)));
		if (res.getInt("FIXED_REASON") == null) {
			application.setOpAppStandardReasonCD(Optional.ofNullable(null));
		}else {
			application.setOpAppStandardReasonCD(Optional.ofNullable(new AppStandardReasonCode(res.getInt("FIXED_REASON"))));			
		}
		if (res.getString("APP_REASON") == null) {
			application.setOpAppReason(Optional.ofNullable(null));
		}else {
			application.setOpAppReason(Optional.ofNullable(new AppReason(res.getString("APP_REASON"))));			
		}
//		application.setAppType(EnumAdaptor.valueOf(res.getInt("APP_TYPE"), ApplicationType.class));
//		application.setEmployeeID(res.getString("APPLICANTS_SID"));
		if (res.getDate("APP_START_DATE") == null) {
			application.setOpAppStartDate(Optional.ofNullable(null));
		}else {
			application.setOpAppStartDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_START_DATE")), pattern2))));
		}
		if (res.getDate("APP_END_DATE") == null) {
			application.setOpAppEndDate(Optional.ofNullable(null));
		} else {
			application.setOpAppEndDate(Optional.of(new ApplicationDate(GeneralDate.fromString(df2.format(res.getDate("APP_END_DATE")), pattern2))));
		}
		
		if (res.getInt("STAMP_OPTION_ATR") == null) {
			application.setOpStampRequestMode(Optional.ofNullable(null));
		}else {
			application.setOpStampRequestMode(Optional.of(EnumAdaptor.valueOf(res.getInt("STAMP_OPTION_ATR"), StampRequestMode.class)));
		}
		
		AppOverTime appOverTime = new AppOverTime(application);
		
		Integer overtimeAtr = res.getInt("OVERTIME_ATR");
		
		String workTypeCode = res.getString("WORK_TYPE_CD");
		
		String workTimeCode = res.getString("WORK_TIME_CD");
		
		Integer workTimeStart1 = res.getInt("WORK_TIME_START1");
		
		Integer workTimeEnd1 = res.getInt("WORK_TIME_END1");
		
		Integer workTimeStart2 = res.getInt("WORK_TIME_START2");
		
		Integer workTimeEnd2 = res.getInt("WORK_TIME_END2");
		
		Integer divergenceNo = res.getInt("DIVERGENCE_NO");
		
		String divergenceCD = res.getString("DIVERGENCE_CD");
		
		String divergenceReason = res.getString("DIVERGENCE_REASON");
		
		Integer flexExcessTime = res.getInt("FLEX_EXCESS_TIME");
		
		Integer overTimeNight = res.getInt("OVERTIME_NIGHT");
		
		Integer totalNight = res.getInt("TOTAL_NIGHT");
		
		Integer legalHdNight = res.getInt("LEGAL_HD_NIGHT");
		
		Integer nonLegalHdNight = res.getInt("NON_LEGAL_HD_NIGHT");
		
		Integer nonLegalPublicHdNight = res.getInt("NON_LEGAL_PUBLIC_HD_NIGHT");
		
		Integer breakTimeStart1 = res.getInt("BREAK_TIME_START1");
		
		Integer breakTimeEnd1 = res.getInt("BREAK_TIME_END1");
		
		Integer breakTimeStart2 = res.getInt("BREAK_TIME_START2");
		
		Integer breakTimeEnd2 = res.getInt("BREAK_TIME_END2");
		
		Integer breakTimeStart3 = res.getInt("BREAK_TIME_START3");
		
		Integer breakTimeEnd3 = res.getInt("BREAK_TIME_END3");
		
		Integer breakTimeStart4 = res.getInt("BREAK_TIME_START4");
		
		Integer breakTimeEnd4 = res.getInt("BREAK_TIME_END4");
		
		Integer breakTimeStart5 = res.getInt("BREAK_TIME_START5");
		
		Integer breakTimeEnd5 = res.getInt("BREAK_TIME_END5");
		
		Integer breakTimeStart6 = res.getInt("BREAK_TIME_START6");
		
		Integer breakTimeEnd6 = res.getInt("BREAK_TIME_END6");
		
		Integer breakTimeStart7 = res.getInt("BREAK_TIME_START7");
		
		Integer breakTimeEnd7 = res.getInt("BREAK_TIME_END7");
		
		Integer breakTimeStart8 = res.getInt("BREAK_TIME_START8");
		
		Integer breakTimeEnd8 = res.getInt("BREAK_TIME_END8");
		
		Integer breakTimeStart9 = res.getInt("BREAK_TIME_START9");
		
		Integer breakTimeEnd9 = res.getInt("BREAK_TIME_END9");
		
		Integer breakTimeStart10 = res.getInt("BREAK_TIME_START10");
		
		Integer breakTimeEnd10 = res.getInt("BREAK_TIME_END10");
		
		
		appOverTime.setOverTimeClf(EnumAdaptor.valueOf(overtimeAtr, OvertimeAppAtr.class));
		if (StringUtils.isNotBlank(workTimeCode) || StringUtils.isNotBlank(workTimeCode)) {
			WorkInformation workInformation = new WorkInformation("", "");
			appOverTime.setWorkInfoOp(Optional.of(workInformation));
			if (StringUtils.isNotBlank(workTypeCode)) {
				workInformation.setWorkTypeCode(workTypeCode);
			}
			if (StringUtils.isNotBlank(workTimeCode)) {
				workInformation.setWorkTimeCode(workTimeCode);
			}
		}
		List<TimeZoneWithWorkNo> workHoursOp = new ArrayList<TimeZoneWithWorkNo>();
		List<TimeZoneWithWorkNo> breakTimeOp = new ArrayList<TimeZoneWithWorkNo>();
		if (workTimeStart1 != null && workTimeEnd1 != null) {
			
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, workTimeStart1, workTimeEnd1);
			workHoursOp.add(timeZoneWithWorkNo);
			appOverTime.setWorkHoursOp(Optional.of(workHoursOp));
		}
		if (workTimeStart2 != null && workTimeEnd2 != null) {
			
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(2, workTimeStart2, workTimeEnd2);
			workHoursOp.add(timeZoneWithWorkNo);		
		}
		appOverTime.setOpReversionReason(Optional.empty());
		ReasonDivergence reasonDivergence = new ReasonDivergence();
		
		ApplicationTime applicationTime = new ApplicationTime();
		applicationTime.setFlexOverTime(Optional.ofNullable(flexExcessTime != null ? new AttendanceTimeOfExistMinus(flexExcessTime) : null));
		// 112610
		if (divergenceNo != null) {
			reasonDivergence.setDiviationTime(divergenceNo);
		}
		if (divergenceCD != null) {
			DiverdenceReasonCode reasonCode = new DiverdenceReasonCode(divergenceCD);
			reasonDivergence.setReasonCode(reasonCode);
		}
		if (divergenceReason != null) {
			DivergenceReason diReason = new DivergenceReason(divergenceReason);
			reasonDivergence.setReason(diReason);
		}
		List<ReasonDivergence> reasonDissociation;
		if (reasonDivergence.isNullProp()) {
			applicationTime.setReasonDissociation(Optional.empty());
		} else {
			reasonDissociation = new ArrayList<ReasonDivergence>();
			reasonDissociation.add(reasonDivergence);
			applicationTime.setReasonDissociation(Optional.of(reasonDissociation));
		}

		OverTimeShiftNight overTimeShiftNight = new OverTimeShiftNight();
		if (overTimeNight == null 
				&& totalNight == null
				&& legalHdNight == null 
				&& nonLegalHdNight == null 
				&& nonLegalPublicHdNight == null) {
			applicationTime.setOverTimeShiftNight(Optional.empty());
			
		} else {
			if (overTimeNight != null) {
				overTimeShiftNight.setOverTimeMidNight(new TimeWithDayAttr(overTimeNight));
			}
			if (totalNight != null) {
				overTimeShiftNight.setMidNightOutSide(new TimeWithDayAttr(totalNight));
			}
			List<HolidayMidNightTime> midNightHolidayTimes = new ArrayList<HolidayMidNightTime>();
			if (legalHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(legalHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.WithinPrescribedHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			
			if (nonLegalHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(nonLegalHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.ExcessOfStatutoryHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			
			
			if (nonLegalPublicHdNight != null) {
				HolidayMidNightTime holidayMidNightTime = new HolidayMidNightTime();
				holidayMidNightTime.setAttendanceTime(new AttendanceTime(nonLegalPublicHdNight));
				holidayMidNightTime.setLegalClf(StaturoryAtrOfHolidayWork.PublicHolidayWork);
				midNightHolidayTimes.add(holidayMidNightTime);
			}
			applicationTime.setOverTimeShiftNight(Optional.of(overTimeShiftNight));
			
		}
		
		if (breakTimeStart1 != null && breakTimeEnd1 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart1, breakTimeEnd1);
			breakTimeOp.add(timeZoneWithWorkNo);
			appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));
		}
		
		if (breakTimeStart2 != null && breakTimeEnd2 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart2, breakTimeEnd2);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		if (breakTimeStart3 != null && breakTimeEnd3 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart3, breakTimeEnd3);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		if (breakTimeStart4 != null && breakTimeEnd4 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart4, breakTimeEnd4);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart5 != null && breakTimeEnd5 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart5, breakTimeEnd5);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart6 != null && breakTimeEnd6 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart6, breakTimeEnd6);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart7 != null && breakTimeEnd7 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart7, breakTimeEnd7);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart8 != null && breakTimeEnd8 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart8, breakTimeEnd8);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart9 != null && breakTimeEnd9 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart9, breakTimeEnd9);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		if (breakTimeStart10 != null && breakTimeEnd10 != null) {
			TimeZoneWithWorkNo timeZoneWithWorkNo = new TimeZoneWithWorkNo(1, breakTimeStart10, breakTimeEnd10);
			breakTimeOp.add(timeZoneWithWorkNo);
			if (!appOverTime.getBreakTimeOp().isPresent()) {
				appOverTime.setBreakTimeOp(Optional.of(breakTimeOp));				
			}
		}
		
		return appOverTime;
	}

}
