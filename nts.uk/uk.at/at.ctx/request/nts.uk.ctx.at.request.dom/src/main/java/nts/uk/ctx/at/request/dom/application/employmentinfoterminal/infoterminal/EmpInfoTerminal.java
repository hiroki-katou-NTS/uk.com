package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppLateReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppStampReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppVacationReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.AppWorkHolidayReceptionData;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationCategory;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive.ApplicationReceptionData;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

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
	private  Optional<String> ipAddress;

	/**
	 * MACアドレス
	 */
	@Getter
	private  String macAddress;

	/**
	 * コード
	 */
	@Getter
	private final Integer empInfoTerCode;

	/**
	 * シリアルNO
	 */
	@Getter
	private  Optional<String> terSerialNo;

	/**
	 * 名称
	 */
	@Getter
	private  String empInfoTerName;

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
	private  Integer modelEmpInfoTer;

	/**
	 * 監視間隔時間
	 */
	@Getter
	private  Integer intervalTime;
	
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
	public <T extends ApplicationReceptionData> Object createApplication(String companyId, T recept,
			Optional<WorkType> workTypeOpt, Optional<WorkingConditionItem> workingConItemOpt, String employeeId) {

		ApplicationCategory cate = ApplicationCategory.valueStringOf(recept.getApplicationCategory());
		switch (cate) {

		// 打刻申請
		case STAMP:
			// AppStamp
			AppStampReceptionData appStampData = (AppStampReceptionData) recept;
			Application_New appStampNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appStampData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appStampData.getAppYMD())), Optional.empty(),
					appStampData.getReason());
//			AppStamp appStamp = new AppStamp(StampRequestMode.STAMP_ONLINE_RECORD, appStampNew, Collections.emptyList(),
//					Collections.emptyList(), Collections.emptyList(),
//					Optional.of(new AppStampOnlineRecord(appStampData.convertCombi(),
//							NRHelper.toMinute(appStampData.getAppTime()))));// TODO: thieu 外出区分 ->外出理由
//			return appStamp;
			return null;

		// 残業申請
		case OVERTIME:
			// AppOverTime
//			AppOverTimeReceptionData appOverTimeData = (AppOverTimeReceptionData) recept;
//			Application_New appOverNew = recept.createAplication(companyId, employeeId,
//					Integer.parseInt(appOverTimeData.getTypeBeforeAfter()),
//					NRHelper.convertAppType(recept.getApplicationCategory()),
//					Optional.of(NRHelper.createGeneralDate(appOverTimeData.getAppYMD())), Optional.empty(),
//					appOverTimeData.getReason());
//			AppOverTime appOverTime = AppOverTime.createSimpleFromJavaType(companyId, IdentifierUtil.randomUniqueId(),
//					OverTimeAtr.ALL, null, null, workClockFrom1, workClockTo1, workClockFrom2, workClockTo2,
//					divergenceReason, flexExessTime, overTimeShiftNight);
			// TODO : ko map duoc domain
			return Optional.empty();

		// 休暇申請
		case VACATION:
			// AppAbsence
			AppVacationReceptionData appAbsenceData = (AppVacationReceptionData) recept;
			Application_New appAbsenceNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appAbsenceData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getStartDate())),
					Optional.of(NRHelper.createGeneralDate(appAbsenceData.getEndDate())), appAbsenceData.getReason());
//			AppAbsence appAbsence = new AppAbsence(companyId, appAbsenceNew.getAppID(),
//					workTypeOpt.isPresent() ? NRHelper.convertHolidayType(workTypeOpt.get().getDailyWork().getOneDay()).value : null,
//					appAbsenceData.getWorkType(), null, true, false, AllDayHalfDayLeaveAtr.ALL_DAY_LEAVE.value, null,
//					null, null, null, null);
//			appAbsence.setApplication(appAbsenceNew);
//			return appAbsence;
			return null;

		// 勤務変更申請
		case WORK_CHANGE:
			// AppWorkChange
			// TODO: sua thiet ke, chua sua code
			return Optional.empty();

		// 休日出勤時間申請
		case WORK_HOLIDAY:
			// AppHolidayWork
			AppWorkHolidayReceptionData appHolidayData = (AppWorkHolidayReceptionData) recept;
			Application_New appHolidayNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appHolidayData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appHolidayData.getAppYMD())), Optional.empty(),
					appHolidayData.getReason());
			SingleDaySchedule singleDay = workingConItemOpt.isPresent() ? workingConItemOpt.get().getWorkCategory().getHolidayTime() : null;
			AppHolidayWork appHoliday = AppHolidayWork.createSimpleFromJavaType(companyId, appHolidayNew.getAppID(),
					(singleDay != null && singleDay.getWorkTypeCode().isPresent())
							? singleDay.getWorkTypeCode().get().v()
							: null,
					(singleDay != null && singleDay.getWorkTimeCode().isPresent())
							? singleDay.getWorkTimeCode().get().v()
							: null,
					null, null, null, null, 0, 0, 0, 0, "", 0);
			appHoliday.setHolidayWorkInputs(appHolidayData.holidayWorkInput(companyId, appHolidayNew.getAppID()));
			// 乖離理由 -> ""
			// 就業時間外深夜時間 -> 0
			return appHoliday;

		// 遅刻早退取消申請
		case LATE:
			// LateOrLeaveEarly
			AppLateReceptionData appLateData = (AppLateReceptionData) recept;
			Application_New appLateNew = recept.createAplication(companyId, employeeId,
					Integer.parseInt(appLateData.getTypeBeforeAfter()),
					NRHelper.convertAppType(recept.getApplicationCategory()),
					Optional.of(NRHelper.createGeneralDate(appLateData.getAppYMD())), Optional.empty(),
					appLateData.getReason());
//			LateOrLeaveEarly late = new LateOrLeaveEarly(appLateNew, 1,
//					appLateData.getReasonLeave().equals(ReasonLeaveEarly.EARLY.value) ? Select.SELECTED
//							: Select.NOTSLECTED,
//					new TimeDay(0),
//					appLateData.getReasonLeave().equals(ReasonLeaveEarly.LATE.value) ? Select.SELECTED
//							: Select.NOTSLECTED,
//					new TimeDay(0), Select.NOTSLECTED, new TimeDay(0), Select.NOTSLECTED, new TimeDay(0));
//			return late;
			return null;

		// 時間年休申請
		case ANNUAL:
			// TODO: chua co domain
			return Optional.empty();

		default:
			return Optional.empty();
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
		private Integer empInfoTerCode;

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

		public EmpInfoTerminalBuilder(Optional<String> ipAddress, String macAddress, Integer empInfoTerCode, Optional<String> terSerialNo,
				String empInfoTerName, String contractCode) {
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
