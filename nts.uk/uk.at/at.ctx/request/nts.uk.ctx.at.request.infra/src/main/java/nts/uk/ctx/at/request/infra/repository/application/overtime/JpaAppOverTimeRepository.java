package nts.uk.ctx.at.request.infra.repository.application.overtime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.AppReason;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.PrePostAtr;
import nts.uk.ctx.at.request.dom.application.ReasonForReversion;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTimeRepository;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.stamp.StampRequestMode;
import nts.uk.ctx.at.request.dom.setting.company.appreasonstandard.AppStandardReasonCode;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTimeOfExistMinus;

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

	@Override
	public void add() {
		// TODO Auto-generated method stub
		
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
		// 112610
//		if (divergenceNo != null) {
//			ReasonForReversion reasonForReversion = new ReasonForReversion(rawValue)
//		} else {
//			appOverTime.setOpReversionReason(Optional.empty());
//		}
		ApplicationTime applicationTime = new ApplicationTime();
		applicationTime.setFlexOverTime(Optional.ofNullable(flexExcessTime != null ? new AttendanceTimeOfExistMinus(flexExcessTime) : null));
		
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
