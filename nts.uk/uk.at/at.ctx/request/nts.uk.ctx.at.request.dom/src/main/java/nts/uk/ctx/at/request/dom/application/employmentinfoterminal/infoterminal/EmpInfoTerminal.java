package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppLateReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkChangeReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ReasonLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateleaveearly.ArrivedLateLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateCancelation;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrEarlyAtr;
import nts.uk.ctx.at.request.dom.application.stamp.AppRecordImage;
import nts.uk.ctx.at.request.dom.application.workchange.AppWorkChange;
import nts.uk.ctx.at.shared.dom.common.TimeZoneWithWorkNo;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.AttendanceClock;

/**
 * @author ThanhNX
 *
 *         就業情報端末
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
	 * 打刻情報の作成
	 */
//	@Getter
//	private final CreateStampInfo createStampInfo;

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
					Optional.of(NRHelper.createGeneralDate(appStampData.getAppYMD())), Optional.empty(),
					appStampData.getReason(), true);
			AppRecordImage appImg = new AppRecordImage(appStampData.convertCombi(),
					new AttendanceClock(NRHelper.toMinute(appStampData.getAppTime())),
					GoingOutReason.corvert(Integer.parseInt(appStampData.getGoOutCategory())), appStampNew);
			return appImg;

		// 残業申請
		case OVERTIME:
			// AppOverTime
//			AppOverTimeReceptionData appOverTimeData = (AppOverTimeReceptionData) recept;
//			Application appOverNew = recept.createAplication(companyId, employeeId,
//					Integer.parseInt(appOverTimeData.getTypeBeforeAfter()),
//					NRHelper.convertAppType(recept.getApplicationCategory()),
//					Optional.of(NRHelper.createGeneralDate(appOverTimeData.getAppYMD())), Optional.empty(),
//					appOverTimeData.getReason());
//			AppOverTime appOverTime = AppOverTime.createSimpleFromJavaType(companyId, IdentifierUtil.randomUniqueId(),
//					OverTimeAtr.ALL, null, null, workClockFrom1, workClockTo1, workClockFrom2, workClockTo2,
//					divergenceReason, flexExessTime, overTimeShiftNight);
			// TODO : ko map duoc domain
			return null;

		// 休暇申請
		case VACATION:
			// AppAbsence
//			AppVacationReceptionData appAbsenceData = (AppVacationReceptionData) recept;
//			Application appAbsenceNew = recept.createAplication(companyId, employeeId,
//					Integer.parseInt(appAbsenceData.getTypeBeforeAfter()),
//					NRHelper.convertAppType(recept.getApplicationCategory()),
//					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getStartDate())),
//					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getEndDate())), appAbsenceData.getReason());
//			AppAbsence appAbsence = new AppAbsence(companyId, appAbsenceNew.getAppID(),
//					workTypeOpt.isPresent() ? NRHelper.convertHolidayType(workTypeOpt.get().getDailyWork().getOneDay()).value : null,
//					appAbsenceData.getWorkType(), null, true, false, AllDayHalfDayLeaveAtr.ALL_DAY_LEAVE.value, null,
//					null, null, null, null);
//			appAbsence.setApplication(appAbsenceNew);
//			return appAbsence;
			return null;

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
//			AppWorkHolidayReceptionData appHolidayData = (AppWorkHolidayReceptionData) recept;
//			Application appHolidayNew = recept.createAplication(companyId, employeeId,
//					Integer.parseInt(appHolidayData.getTypeBeforeAfter()),
//					NRHelper.convertAppType(recept.getApplicationCategory()),
//					Optional.of(NRHelper.createGeneralDate(appHolidayData.getAppYMD())), Optional.empty(),
//					appHolidayData.getReason());
//			SingleDaySchedule singleDay = workingConItemOpt.isPresent()
//					? workingConItemOpt.get().getWorkCategory().getHolidayTime()
//					: null;
//			AppHolidayWork appHoliday = AppHolidayWork.createSimpleFromJavaType(companyId, appHolidayNew.getAppID(),
//					(singleDay != null && singleDay.getWorkTypeCode().isPresent())
//							? singleDay.getWorkTypeCode().get().v()
//							: null,
//					(singleDay != null && singleDay.getWorkTimeCode().isPresent())
//							? singleDay.getWorkTimeCode().get().v()
//							: null,
//					null, null, null, null, 0, 0, 0, 0, "", 0);
//			appHoliday.setHolidayWorkInputs(appHolidayData.holidayWorkInput(companyId, appHolidayNew.getAppID()));
//			// 乖離理由 -> ""
//			// 就業時間外深夜時間 -> 0
			return null;

		// 遅刻早退取消申請
		case LATE:
			// LateOrLeaveEarly
			AppLateReceptionData appLateData = (AppLateReceptionData) recept;
			Application appLateNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appLateData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appLateData.getAppYMD())), Optional.empty(),
					appLateData.getReason());
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
			// TODO: chua co domain
			return null;

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

		public EmpInfoTerminal build() {
			return new EmpInfoTerminal(this);
		}
	}
}
