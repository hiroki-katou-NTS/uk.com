package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         打刻受信データExport
 */
@Value
public class StampReceptionDataExport {

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

	public StampReceptionDataExport(StampDataExportBuilder builder) {
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

	public static class StampDataExportBuilder {

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

		public StampDataExportBuilder(String idNumber, String cardCategory, String shift, String leavingCategory,
				String ymd, String supportCode) {
			this.idNumber = idNumber;
			this.cardCategory = cardCategory;
			this.shift = shift;
			this.leavingCategory = leavingCategory;
			this.ymd = ymd;
			this.supportCode = supportCode;
		}

		public StampDataExportBuilder time(String time) {
			this.time = time;
			return this;
		}

		public StampDataExportBuilder overTimeHours(String overTimeHours) {
			this.overTimeHours = overTimeHours;
			return this;
		}

		public StampDataExportBuilder midnightTime(String midnightTime) {
			this.midnightTime = midnightTime;
			return this;
		}

		public StampReceptionDataExport build() {
			return new StampReceptionDataExport(this);
		}
	}
}
