package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeCalArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.SetPreClockArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * @author ThanhNX
 *
 *         打刻受信データ
 */
@Value
public class StampReceptionData implements ReceptionData {

	/**
	 * ID番号
	 */
	private final String idNumber;

	/**
	 * カード区分
	 */
	private final String cardCategory;

	/**
	 * シフト
	 */
	private final String shift;

	/**
	 * 出退区分
	 */
	private final String leavingCategory;

	/**
	 * 年月日
	 */
	private final String ymd;

	/**
	 * 応援コード
	 */
	private final String supportCode;

	/**
	 * 時分
	 */
	private final String time;

	/**
	 * 残業時間
	 */
	private final String overTimeHours;

	/**
	 * 深夜時間
	 */
	private final String midnightTime;

	public StampReceptionData(StampDataBuilder builder) {
		super();
		this.idNumber = builder.idNumber;
		this.cardCategory = builder.cardCategory;
		this.shift = builder.shift;
		this.leavingCategory = builder.leavingCategory;
		this.ymd = builder.ymd;
		this.supportCode = builder.supportCode;
		this.time = builder.time;
		this.overTimeHours = builder.overTimeHours;
		this.midnightTime = builder.midnightTime;
	}

	@Override
	public GeneralDateTime getDateTime() {
		int yy = GeneralDate.today().year() / 100;
		int ymdTemp = Integer.parseInt(String.valueOf(yy) + ymd);
		return GeneralDateTime.ymdhms(ymdTemp / 10000, (ymdTemp - (ymdTemp / 10000) * 10000) / 100, ymdTemp % 100,
				Integer.parseInt(time) / 100, Integer.parseInt(time) % 100, 0);
	}

	public String getOverTimeHours() {
		if (overTimeHours.trim().isEmpty())
			return overTimeHours;
		return String.valueOf(
				(Integer.parseInt(overTimeHours.trim()) / 100) * 60 + (Integer.parseInt(overTimeHours.trim()) % 100));
	}

	public String getMidnightTime() {
		if (midnightTime.trim().isEmpty())
			return midnightTime;
		return String.valueOf(
				(Integer.parseInt(midnightTime.trim()) / 100) * 60 + (Integer.parseInt(midnightTime.trim()) % 100));
	}

	public String getSupportCode() {
		return supportCode.trim();
	}

	public String getShift() {
		return shift.trim();
	}

	// 認証方法
	public AuthcMethod convertAuthcMethod() {

		switch (CardCategory.valueStringOf(cardCategory.trim())) {

		case ID_INPUT:
			return AuthcMethod.ID_AUTHC;

		case MAGNETIC_CARD:
			return AuthcMethod.EXTERNAL_AUTHC;

		case IC_CARD:
			return AuthcMethod.IC_CARD_AUTHC;

		default:
			return AuthcMethod.VEIN_AUTHC;
		}
	}

	// 計算区分変更対象
	public ChangeCalArt convertChangeCalArt() {
		switch (LeaveCategory.valueStringOf(leavingCategory.trim())) {

		case WORK_FLEX:
		case LEAVE_FLEX:
		case WORK_FLEX_ENTRANCE:
			return ChangeCalArt.FIX;

		case LEAVE_OVERTIME:
			return ChangeCalArt.OVER_TIME;

		case EARLY:
		case EARLY_ENTRANCE:
			return ChangeCalArt.EARLY_APPEARANCE;

		case VACATION:
		case VACATION_ENTRANCE:
			return ChangeCalArt.BRARK;

		default:
			return ChangeCalArt.NONE;
		}
	}

	// 出退区分
	public ChangeClockArt convertChangeClockArt(EmpInfoTerminal empInfo) {
		switch (LeaveCategory.valueStringOf(leavingCategory.trim())) {

		case WORK:
		case WORK_HALF:
		case WORK_FLEX:
			if (empInfo.getCreateStampInfo().getConvertEmbCate().getEntranceExit() == NotUseAtr.USE)
				return ChangeClockArt.OVER_TIME;
			return ChangeClockArt.GOING_TO_WORK;

		case EARLY:
		case VACATION:
			return ChangeClockArt.GOING_TO_WORK;

		case LEAVE:
		case LEAVE_HALF:
		case LEAVE_OVERTIME:
		case LEAVE_FLEX:
			if (empInfo.getCreateStampInfo().getConvertEmbCate().getEntranceExit() == NotUseAtr.USE)
				return ChangeClockArt.OVER_TIME;
			return ChangeClockArt.WORKING_OUT;

		case GO_OUT:
			if (empInfo.getCreateStampInfo().getConvertEmbCate().getOutSupport() == NotUseAtr.USE)
				return ChangeClockArt.START_OF_SUPPORT;
			return ChangeClockArt.GO_OUT;

		case RETURN:
			if (empInfo.getCreateStampInfo().getConvertEmbCate().getOutSupport() == NotUseAtr.USE)
				return ChangeClockArt.END_OF_SUPPORT;
			return ChangeClockArt.RETURN;

		case WORK_TEMPORARY:
			return ChangeClockArt.TEMPORARY_WORK;

		case RETURN_START:
			return ChangeClockArt.START_OF_SUPPORT;

		case GO_EN:
			return ChangeClockArt.END_OF_SUPPORT;

		case WORK_ENTRANCE:
		case WORK_HALF_ENTRANCE:
		case WORK_FLEX_ENTRANCE:
			return ChangeClockArt.SUPPORT;

		case VACATION_ENTRANCE:
		case EARLY_ENTRANCE:
			return ChangeClockArt.START_OF_SUPPORT;

		case TEMPORARY_ENTRANCE:
			return ChangeClockArt.TEMPORARY_SUPPORT_WORK;

//		case RETIRED_TEMPORARY:
//			return ChangeClockArt.TEMPORARY_LEAVING;

		default:
			return ChangeClockArt.TEMPORARY_LEAVING;
		}
	}

	public StampType createStampType(EmpInfoTerminal empInfo) {
		String category = leavingCategory.trim();
		// 勤務種類を半休に変更する
		boolean changeHalfDay = false;
		if (category.equals(LeaveCategory.WORK_HALF.value) || category.equals(LeaveCategory.LEAVE_HALF.value)
				|| category.equals(LeaveCategory.WORK_HALF_ENTRANCE.value)) {
			changeHalfDay = true;
		}

		// 外出理由
		GoingOutReason goOutArt = null;
		if (empInfo.getCreateStampInfo().getWorkLocationCd().isPresent()
				&& empInfo.getCreateStampInfo().getOutPlaceConvert().getReplace() == NotUseAtr.USE
				&& empInfo.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().isPresent()) {
			goOutArt = GoingOutReason
					.valueOf(empInfo.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().value);
		} else if (category.equals(LeaveCategory.GO_OUT.value) && !getShift().isEmpty()) {
			goOutArt = GoingOutReason.valueOf(Integer.parseInt(getShift().substring(0, 1)));
		}

		return new StampType(changeHalfDay, goOutArt, SetPreClockArt.NONE, convertChangeClockArt(empInfo),
				convertChangeCalArt());
	}

	public static class StampDataBuilder {

		/**
		 * ID番号
		 */
		private String idNumber;

		/**
		 * カード区分
		 */
		private String cardCategory;

		/**
		 * シフト
		 */
		private String shift;

		/**
		 * 出退区分
		 */
		private String leavingCategory;

		/**
		 * 年月日
		 */
		private String ymd;

		/**
		 * 応援コード
		 */
		private String supportCode;

		/**
		 * 時分
		 */
		private String time;

		/**
		 * 残業時間
		 */
		private String overTimeHours;

		/**
		 * 深夜時間
		 */
		private String midnightTime;

		public StampDataBuilder(String idNumber, String cardCategory, String shift, String leavingCategory, String ymd,
				String supportCode) {
			this.idNumber = idNumber;
			this.cardCategory = cardCategory;
			this.shift = shift;
			this.leavingCategory = leavingCategory;
			this.ymd = ymd;
			this.supportCode = supportCode;
		}

		public StampDataBuilder time(String time) {
			this.time = time;
			return this;
		}

		public StampDataBuilder overTimeHours(String overTimeHours) {
			this.overTimeHours = overTimeHours;
			return this;
		}

		public StampDataBuilder midnightTime(String midnightTime) {
			this.midnightTime = midnightTime;
			return this;
		}

		public StampReceptionData build() {
			return new StampReceptionData(this);
		}
	}

}
