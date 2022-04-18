package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.ReflectFreeTimeApp;
import nts.uk.ctx.at.request.dom.application.appabsence.SupplementInfoVacation;
import nts.uk.ctx.at.request.dom.application.appabsence.VacationRequestInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.apptimedigest.TimeDigestApplication;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AnnualHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AnnualHolidayType;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppLateReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppOverTimeReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkChangeReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ReasonLeaveEarly;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.overtime.AppOverTime;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.AttendanceType_Update;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeAppAtr;
import nts.uk.ctx.at.request.dom.application.overtime.OvertimeApplicationSetting;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplication;
import nts.uk.ctx.at.request.dom.application.timeleaveapplication.TimeLeaveApplicationDetail;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.CancelAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyCancelAppSet;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.scherec.workinfo.GetWorkInfoTimeZoneFromRcSc;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author ThanhNX
 *
 *         就業情報端末Import
 */
public class EmpInfoTerminal implements DomainAggregate {

	/**
	 * IPアドレス
	 */
	@Getter
	private Optional<String> ipAddress;

	/**
	 * MACアドレス
	 */
	@Getter
	private String macAddress;

	/**
	 * コード
	 */
	@Getter
	private final String empInfoTerCode;

	/**
	 * シリアルNO
	 */
	@Getter
	private Optional<String> terSerialNo;

	/**
	 * 名称
	 */
	@Getter
	private String empInfoTerName;

	/**
	 * 契約コード
	 */
	@Getter
	private final String contractCode;

	/**
	 * 外出理由
	 */
    @Getter
	private final Optional<GoingOutReason> goOutReason;

	/**
	 * 機種
	 */
	@Getter
	private Integer modelEmpInfoTer;

	/**
	 * 監視間隔時間
	 */
	@Getter
	private Integer intervalTime;

	/**
	 * 就業情報端末のメモ
	 */
	@Getter
	private Optional<String> empInfoTerMemo;

	public EmpInfoTerminal(EmpInfoTerminalBuilder builder) {
		super();
		this.ipAddress = builder.ipAddress;
		this.macAddress = builder.macAddress;
		this.empInfoTerCode = builder.empInfoTerCode;
		this.terSerialNo = builder.terSerialNo;
		this.empInfoTerName = builder.empInfoTerName;
		this.contractCode = builder.contractCode;
		this.modelEmpInfoTer = builder.modelEmpInfoTer;
		this.intervalTime = builder.intervalTime;
		this.empInfoTerMemo = builder.empInfoTerMemo;
		this.goOutReason = builder.goOutReason;
	}

	// [３] 申請
	public <T extends ApplicationReceptionData> Optional<Application> createApplication(Require require, String companyId, T recept,
			Optional<WorkType> workTypeOpt, String employeeId) {

		ApplicationCategory cate = ApplicationCategory.valueStringOf(recept.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
			// AppStamp
			AppStampReceptionData appStampData = (AppStampReceptionData) recept;
			Application appStampNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appStampData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appStampData.getAppYMD())),
					Optional.of(NRHelper.createGeneralDate(appStampData.getAppYMD())), appStampData.getReason(), true);
			AppRecordImage appImg = new AppRecordImage(appStampData.convertCombi(),
					new AttendanceClock(NRHelper.toMinute(appStampData.getAppTime())),
					StringUtils.isEmpty(appStampData.getGoOutCategory()) ? this.goOutReason
							: GoingOutReason.corvert(Integer.parseInt(appStampData.getGoOutCategory())),
					appStampNew);
			return Optional.of(appImg);

		// 残業申請
		case OVERTIME:
			// AppOverTime
			AppOverTimeReceptionData appOverTimeData = (AppOverTimeReceptionData) recept;
			Application appOverNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appOverTimeData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appOverTimeData.getAppYMD())),
					Optional.of(NRHelper.createGeneralDate(appOverTimeData.getAppYMD())),	appOverTimeData.getReason());
			List<OvertimeApplicationSetting> applicationTimeDetail = new ArrayList<OvertimeApplicationSetting>();
			createOvertimeSetting(appOverTimeData.getOvertimeNo1(),
					appOverTimeData.getOvertimeHour1(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> addOvertimeAppSetting(applicationTimeDetail, x));
			createOvertimeSetting(appOverTimeData.getOvertimeNo2(),
					appOverTimeData.getOvertimeHour2(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> addOvertimeAppSetting(applicationTimeDetail, x));
			createOvertimeSetting(appOverTimeData.getOvertimeNo3(),
					appOverTimeData.getOvertimeHour3(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> addOvertimeAppSetting(applicationTimeDetail, x));

			ApplicationTime applicationTime = new ApplicationTime(applicationTimeDetail, Optional.empty(),
					Optional.empty(), Optional.empty(), Optional.empty());
			AppOverTime appOverTime = new AppOverTime(OvertimeAppAtr.EARLY_NORMAL_OVERTIME, applicationTime,
					Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
			appOverTime.setApplication(appOverNew);
			return Optional.of(appOverTime);

		// 休暇申請
		case VACATION:
			// AppAbsence
			AppVacationReceptionData appAbsenceData = (AppVacationReceptionData) recept;
			Application appAbsenceNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appAbsenceData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getStartDate())),
					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getEndDate())), appAbsenceData.getReason());
			ReflectFreeTimeApp reflectFreeTimeApp = new ReflectFreeTimeApp(Optional.empty(), Optional.empty(),
					new WorkInformation(workTypeOpt.map(x -> x.getWorkTypeCode().v()).orElse(null), null),
					NotUseAtr.NOT_USE);
			VacationRequestInfo vacationInfo = new VacationRequestInfo(workTypeOpt.isPresent()
					? HolidayAppType.covertToHoldayType(workTypeOpt.get().getDailyWork().getOneDay())
					: HolidayAppType.HOLIDAY, new SupplementInfoVacation(Optional.empty(), Optional.empty()));
			ApplyForLeave appAbsence = new ApplyForLeave(reflectFreeTimeApp, vacationInfo);
			appAbsence.setApplication(appAbsenceNew);
			return Optional.of(appAbsence);

		// 勤務変更申請
		case WORK_CHANGE:

			AppWorkChangeReceptionData appWorkData = (AppWorkChangeReceptionData) recept;

			Application appWorkNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appWorkData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appWorkData.getStartDate())),
					Optional.of(NRHelper.createGeneralDate(appWorkData.getEndDate())), appWorkData.getReason());

			AppWorkChange appChange = new AppWorkChange(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, Optional.empty(),
					Optional.of(new WorkTimeCode(appWorkData.getWorkTime())), new ArrayList<TimeZoneWithWorkNo>(),
					appWorkNew);
			return Optional.of(appChange);

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			// AppHolidayWork
			AppWorkHolidayReceptionData appHolidayData = (AppWorkHolidayReceptionData) recept;
			Application appHolidayNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appHolidayData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appHolidayData.getAppYMD())),
					Optional.of(NRHelper.createGeneralDate(appHolidayData.getAppYMD())),	appHolidayData.getReason());

			List<OvertimeApplicationSetting> applicationTimeHolDetail = new ArrayList<OvertimeApplicationSetting>();
			createOvertimeSetting(appHolidayData.getBreakNo1(),
					appHolidayData.getBreakTime1(), AttendanceType_Update.BREAKTIME).ifPresent(x ->  addOvertimeAppSetting(applicationTimeHolDetail, x));
			createOvertimeSetting(appHolidayData.getBreakNo2(),
					appHolidayData.getBreakTime2(), AttendanceType_Update.BREAKTIME).ifPresent(x ->  addOvertimeAppSetting(applicationTimeHolDetail, x));
			createOvertimeSetting(appHolidayData.getBreakNo3(),
					appHolidayData.getBreakTime3(), AttendanceType_Update.BREAKTIME).ifPresent(x ->  addOvertimeAppSetting(applicationTimeHolDetail, x));

			val workInfoAndTimeZone = GetWorkInfoTimeZoneFromRcSc.getInfo(require, companyId, employeeId,
					appHolidayNew.getAppDate().getApplicationDate(), Optional.empty());
			if(!workInfoAndTimeZone.isPresent()) {
				return Optional.empty();
			}
			ApplicationTime applicationTimeHol = new ApplicationTime(applicationTimeHolDetail, Optional.empty(),
					Optional.empty(), Optional.empty(), Optional.empty());
			List<TimeZoneWithWorkNo> lstTimeZone = new ArrayList<>();
			for (int i = 0; i < workInfoAndTimeZone.get().getTimeZones().size(); i++) {
				lstTimeZone.add(
						new TimeZoneWithWorkNo(i + 1, workInfoAndTimeZone.get().getTimeZones().get(i).getStart().v(),
								workInfoAndTimeZone.get().getTimeZones().get(i).getEnd().v()));
			}
			AppHolidayWork appHoliday = new AppHolidayWork(
					new WorkInformation(
							workInfoAndTimeZone.get().getWorkType().getWorkTypeCode(),
							workInfoAndTimeZone.get().getWorkTime().map(x -> x.getWorktimeCode()).orElse(null)),
					applicationTimeHol, nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE,
					nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE, Optional.empty(), Optional.of(lstTimeZone)
					);
			appHoliday.setApplication(appHolidayNew);
			return Optional.of(appHoliday);

		// 遅刻早退取消申請
		case LATE:
			// LateOrLeaveEarly
			AppLateReceptionData appLateData = (AppLateReceptionData) recept;
			Application appLateNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appLateData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appLateData.getAppYMD())),
					Optional.of(NRHelper.createGeneralDate(appLateData.getAppYMD())), appLateData.getReason());
			ArrivedLateLeaveEarly appLate = new ArrivedLateLeaveEarly(appLateNew);
			appLate.setLateOrLeaveEarlies(new ArrayList<>());
			val setting = require.getLateEarlyCancelAppSetByCId(companyId);
			List<LateCancelation> lateCancelation = new ArrayList<>();
			if (setting.map(x -> x.getCancelAtr() != CancelAtr.NOT_USE).orElse(false)) {
				lateCancelation.add(new LateCancelation(1,
						appLateData.getReasonLeave().equals(ReasonLeaveEarly.EARLY.value) ? LateOrEarlyAtr.EARLY
								: LateOrEarlyAtr.LATE));
			}
			appLate.setLateCancelation(lateCancelation);
			return Optional.of(appLate);

		// 時間年休申請
		case ANNUAL:
			AnnualHolidayReceptionData appAnnual = (AnnualHolidayReceptionData) recept;
			Application appAnnualHol = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appAnnual.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appAnnual.getAppYMD())),
					Optional.of(NRHelper.createGeneralDate(appAnnual.getAppYMD())), appAnnual.getReason());
			List<TimeLeaveApplicationDetail> leaveApplicationDetails = new ArrayList<>();
			leaveApplicationDetails.add(
					new TimeLeaveApplicationDetail(toAppTimeType(appAnnual.getAnnualHolidayType()), new ArrayList<>(),
							new TimeDigestApplication(
									check60h(appAnnual.getAnnualHolidayType())
											? appAnnual.getAnnualHolidayTime().isEmpty() ? AttendanceTime.ZERO
													: new AttendanceTime(
															NRHelper.toMinute(appAnnual.getAnnualHolidayTime()))
											: AttendanceTime.ZERO,
									AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO,
									!check60h(appAnnual.getAnnualHolidayType())
									? appAnnual.getAnnualHolidayTime().isEmpty() ? AttendanceTime.ZERO
											: new AttendanceTime(
													NRHelper.toMinute(appAnnual.getAnnualHolidayTime()))
											: AttendanceTime.ZERO,
									Optional.empty())));
			return Optional.of(new TimeLeaveApplication(appAnnualHol, leaveApplicationDetails));

		default:
			return Optional.empty();
		}
	}

	private Optional<OvertimeApplicationSetting> createOvertimeSetting(String no, String time,
			AttendanceType_Update type) {
		return StringUtils.isEmpty(no) ? Optional.empty()
				: Optional.of(new OvertimeApplicationSetting(Integer.parseInt(no) + 1, type,
						StringUtils.isEmpty(time) ? 0 : Integer.parseInt(time)));
	}

	private void addOvertimeAppSetting(List<OvertimeApplicationSetting> lstOverHd, OvertimeApplicationSetting value) {
		val valueCheck = lstOverHd.stream().filter(x -> x.getAttendanceType().value == value.getAttendanceType().value
				&& x.getFrameNo().v() == value.getFrameNo().v()).findFirst();
		if(valueCheck.isPresent()) {
			valueCheck.get().setApplicationTime(new AttendanceTime(valueCheck.get().getApplicationTime().v() + value.getApplicationTime().v()));
		}else {
			lstOverHd.add(value);
		}
	}
	
	private boolean check60h(String annualHolidayType) {
		switch (AnnualHolidayType.valueStringOf(annualHolidayType)) {
		case LATE1:
		case LATE2:
		case EARLY1:
		case EARLY2:
		case OUT1:
		case OUT2:
			return false;
		case VERY_LATE1:
		case VERY_LATE2:
		case LEAVE_EARLY1:
		case LEAVE_EARLY2:
		case HOLIDAY_PRIV:
		case HOLIDAY_GOOUT:
			return true;
		default:
			return false;
		}
	}

	private AppTimeType toAppTimeType(String annualHolidayType) {
		switch (AnnualHolidayType.valueStringOf(annualHolidayType)) {
		case LATE1:
		case VERY_LATE1:
			return AppTimeType.ATWORK;
		case LATE2:
		case VERY_LATE2:
			return AppTimeType.ATWORK2;
		case EARLY1:
		case LEAVE_EARLY1:
			return AppTimeType.OFFWORK;
		case EARLY2:
		case LEAVE_EARLY2:
			return AppTimeType.OFFWORK2;
		case OUT1:
		case HOLIDAY_PRIV:
			return AppTimeType.PRIVATE;
		case OUT2:
		case HOLIDAY_GOOUT:
			return AppTimeType.UNION;
		default:
			return null;
		}
	}

	public static class EmpInfoTerminalBuilder {
		/**
		 * IPアドレス
		 */
		private Optional<String> ipAddress;

		/**
		 * MACアドレス
		 */
		private String macAddress;

		/**
		 * コード
		 */
		private String empInfoTerCode;

		/**
		 * シリアルNO
		 */
		private Optional<String> terSerialNo;

		/**
		 * 名称
		 */
		private String empInfoTerName;

		/**
		 * 契約コード
		 */
		private String contractCode;
		
		/**
		 * 外出理由
		 */
	    @Getter
		private Optional<GoingOutReason> goOutReason;

		/**
		 * 機種
		 */
		private Integer modelEmpInfoTer;

		/**
		 * 監視間隔時間
		 */
		private Integer intervalTime;

		/**
		 * 就業情報端末のメモ
		 */
		@Getter
		private Optional<String> empInfoTerMemo;

		public EmpInfoTerminalBuilder(Optional<String> ipAddress, String macAddress, String empInfoTerCode,
				Optional<String> terSerialNo, String empInfoTerName, String contractCode) {
			this.ipAddress = ipAddress;
			this.macAddress = macAddress;
			this.empInfoTerCode = empInfoTerCode;
			this.terSerialNo = terSerialNo;
			this.empInfoTerName = empInfoTerName;
			this.contractCode = contractCode;
		}

		public EmpInfoTerminalBuilder modelEmpInfoTer(Integer modelEmpInfoTer) {
			this.modelEmpInfoTer = modelEmpInfoTer;
			return this;
		}

		public EmpInfoTerminalBuilder intervalTime(Integer intervalTime) {
			this.intervalTime = intervalTime;
			return this;
		}

		public EmpInfoTerminalBuilder empInfoTerMemo(Optional<String> empInfoTerMemo) {
			this.empInfoTerMemo = empInfoTerMemo;
			return this;
		}

		public EmpInfoTerminalBuilder goOutReason(Optional<GoingOutReason> goOutReason) {
			this.goOutReason = goOutReason;
			return this;
		}
		
		public EmpInfoTerminal build() {
			return new EmpInfoTerminal(this);
		}
	}
	
	public static interface Require extends GetWorkInfoTimeZoneFromRcSc.Require{
		
		//LateEarlyCancelAppSetRepository
		Optional<LateEarlyCancelAppSet> getLateEarlyCancelAppSetByCId(String companyId);
	}
}
