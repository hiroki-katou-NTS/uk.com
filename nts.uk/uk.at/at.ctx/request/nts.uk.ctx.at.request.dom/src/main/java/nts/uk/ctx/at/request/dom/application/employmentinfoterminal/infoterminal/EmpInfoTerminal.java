package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
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
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.AppTimeType;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
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
	public <T extends ApplicationReceptionData> Application createApplication(String companyId, T recept,
			Optional<WorkType> workTypeOpt, Optional<WorkingConditionItem> workingConItemOpt, String employeeId) {

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
			return appImg;

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
					appOverTimeData.getOvertimeHour1(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> applicationTimeDetail.add(x));
			createOvertimeSetting(appOverTimeData.getOvertimeNo2(),
					appOverTimeData.getOvertimeHour2(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> applicationTimeDetail.add(x));
			createOvertimeSetting(appOverTimeData.getOvertimeNo3(),
					appOverTimeData.getOvertimeHour3(), AttendanceType_Update.NORMALOVERTIME).ifPresent(x -> applicationTimeDetail.add(x));

			ApplicationTime applicationTime = new ApplicationTime(applicationTimeDetail, Optional.empty(),
					Optional.empty(), Optional.empty(), Optional.empty());
			AppOverTime appOverTime = new AppOverTime(OvertimeAppAtr.EARLY_NORMAL_OVERTIME, applicationTime,
					Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
			appOverTime.setApplication(appOverNew);
			return appOverTime;

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
			return appAbsence;

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
			return appChange;

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
					appHolidayData.getBreakTime1(), AttendanceType_Update.BREAKTIME).ifPresent(x -> applicationTimeHolDetail.add(x));
			createOvertimeSetting(appHolidayData.getBreakNo2(),
					appHolidayData.getBreakTime2(), AttendanceType_Update.BREAKTIME).ifPresent(x -> applicationTimeHolDetail.add(x));
			createOvertimeSetting(appHolidayData.getBreakNo3(),
					appHolidayData.getBreakTime3(), AttendanceType_Update.BREAKTIME).ifPresent(x -> applicationTimeHolDetail.add(x));

			ApplicationTime applicationTimeHol = new ApplicationTime(applicationTimeHolDetail, Optional.empty(),
					Optional.empty(), Optional.empty(), Optional.empty());
			List<TimeZoneWithWorkNo> lstTimeZone = workingConItemOpt.get().getWorkCategory().getHolidayWork().getWorkingHours(
					).stream().map(x -> new TimeZoneWithWorkNo(x.getCnt(), x.getStart().v(), x.getEnd().v())).collect(Collectors.toList());
			AppHolidayWork appHoliday = new AppHolidayWork(
					new WorkInformation(
							workingConItemOpt.get().getWorkCategory().getHolidayWork().getWorkTypeCode().orElse(null),
							workingConItemOpt.get().getWorkCategory().getHolidayWork().getWorkTimeCode().orElse(null)),
					applicationTimeHol, nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE,
					nts.uk.ctx.at.shared.dom.workdayoff.frame.NotUseAtr.NOT_USE, Optional.empty(), Optional.of(lstTimeZone),
					Optional.empty());
			appHoliday.setApplication(appHolidayNew);
			return appHoliday;

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
			List<LateCancelation> lateCancelation = new ArrayList<>();
			lateCancelation.add(new LateCancelation(1,
					appLateData.getReasonLeave().equals(ReasonLeaveEarly.EARLY.value) ? LateOrEarlyAtr.EARLY
							: LateOrEarlyAtr.LATE));
			appLate.setLateCancelation(lateCancelation);
			return appLate;

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
											? new AttendanceTime(Integer.parseInt(appAnnual.getAnnualHolidayTime()))
											: AttendanceTime.ZERO,
									AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO, AttendanceTime.ZERO,
									!check60h(appAnnual.getAnnualHolidayType())
											? new AttendanceTime(Integer.parseInt(appAnnual.getAnnualHolidayTime()))
											: AttendanceTime.ZERO,
									Optional.empty())));
			return new TimeLeaveApplication(appAnnualHol, leaveApplicationDetails);

		default:
			return null;
		}
	}

	private Optional<OvertimeApplicationSetting> createOvertimeSetting(String no, String time,
			AttendanceType_Update type) {
		return StringUtils.isEmpty(no) ? Optional.empty()
				: Optional.of(new OvertimeApplicationSetting(Integer.parseInt(no) + 1, type,
						StringUtils.isEmpty(time) ? 0 : Integer.parseInt(time)));
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
}
