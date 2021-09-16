package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import org.apache.commons.lang3.StringUtils;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.NRHelper;

/**
 * @author ThanhNX
 *
 *         休日出勤時間申請受信データ
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AppWorkHolidayReceptionData extends ApplicationReceptionData implements DomainValue {

	/**
	 * 休出時分１
	 */
	private final String breakTime1;

	/**
	 * 休出番号１
	 */
	private final String breakNo1;

	/**
	 * 休出時分 2
	 */
	private final String breakTime2;

	/**
	 * 休出番号 2
	 */
	private final String breakNo2;

	/**
	 * 休出時分 3
	 */
	private final String breakTime3;

	/**
	 * 休出番号3
	 */
	private final String breakNo3;

	/**
	 * 事前事後
	 */
	private final String typeBeforeAfter;

	/**
	 * 申請年月日
	 */
	private final String appYMD;

	/**
	 * 理由
	 */
	private final String reason;

	public AppWorkHolidayReceptionData(AppWorkHolidayBuilder builder) {
		super(builder.getIdNumber(), builder.getApplicationCategory(), builder.getYmd(), builder.getTime());
		this.breakTime1 = builder.breakTime1;
		this.breakNo1 = builder.breakNo1;
		this.breakTime2 = builder.breakTime2;
		this.breakNo2 = builder.breakNo2;
		this.breakTime3 = builder.breakTime3;
		this.breakNo3 = builder.breakNo3;
		this.typeBeforeAfter = builder.typeBeforeAfter;
		this.appYMD = builder.appYMD;
		this.reason = builder.reason;
	}

	public String getBreakTime1() {
		return StringUtils.isEmpty(breakTime1) ? "" : NRHelper.toMinute(breakTime1).toString();
	}

	public String getBreakTime2() {
		return StringUtils.isEmpty(breakTime2) ? "" : NRHelper.toMinute(breakTime2).toString();
	}

	public String getBreakTime3() {
		return StringUtils.isEmpty(breakTime3) ? "" : NRHelper.toMinute(breakTime3).toString();
	}

	public static class AppWorkHolidayBuilder extends ApplicationReceptionData {

		private String breakTime1;

		private String breakNo1;

		private String breakTime2;

		private String breakNo2;

		private String breakTime3;

		private String breakNo3;

		private String typeBeforeAfter;

		private String appYMD;

		private String reason;

		public AppWorkHolidayBuilder(String idNumber, String applicationCategory, String ymd, String time,
				String breakTime1, String breakNo1) {
			super(idNumber, applicationCategory, ymd, time);
			this.breakTime1 = breakTime1;
			this.breakNo1 = breakNo1;
		}

		public AppWorkHolidayBuilder breakTime2(String breakTime2) {
			this.breakTime2 = breakTime2;
			return this;
		}

		public AppWorkHolidayBuilder breakNo2(String breakNo2) {
			this.breakNo2 = breakNo2;
			return this;
		}

		public AppWorkHolidayBuilder breakTime3(String breakTime3) {
			this.breakTime3 = breakTime3;
			return this;
		}

		public AppWorkHolidayBuilder breakNo3(String breakNo3) {
			this.breakNo3 = breakNo3;
			return this;
		}

		public AppWorkHolidayBuilder typeBeforeAfter(String typeBeforeAfter) {
			this.typeBeforeAfter = typeBeforeAfter;
			return this;
		}

		public AppWorkHolidayBuilder appYMD(String appYMD) {
			this.appYMD = appYMD;
			return this;
		}

		public AppWorkHolidayBuilder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public AppWorkHolidayReceptionData build() {
			return new AppWorkHolidayReceptionData(this);
		}
	}
}
