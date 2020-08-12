package nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal;

import lombok.Value;

/**
 * @author ThanhNX
 *
 *         タイムレコードのﾘｸｴｽﾄ設定Export
 */
@Value
public class SendTimeRecordSettingExport {

	/**
	 * 全打刻情報受信フラグ
	 */
	private final boolean request1;

	/**
	 * 時刻同期要求フラグ
	 */
	private final boolean request2;

	/**
	 * 全予約情報受信フラグ
	 */
	private final boolean request3;

	/**
	 * 全申請情報受信フラグ
	 */
	private final boolean request4;

	/**
	 * 個人情報送信要求フラグ
	 */
	private final boolean request6;

	/**
	 * 勤務種類名称送信要求フラグ
	 */
	private final boolean request7;

	/**
	 * 就業時間帯名称送信要求フラグ
	 */
	private final boolean request8;

	/**
	 * 残業名称／休日出勤名称送信要求フラグ
	 */
	private final boolean request9;

	/**
	 * 予約メニュー送信要求フラグ
	 */
	private final boolean request10;

	/**
	 * 申請理由名称送信要求フラグ
	 */
	private final boolean request11;

	public SendTimeRecordSettingExport(SettingExportBuilder builder) {
		this.request1 = builder.request1;
		this.request2 = builder.request2;
		this.request3 = builder.request3;
		this.request4 = builder.request4;
		this.request6 = builder.request6;
		this.request7 = builder.request7;
		this.request8 = builder.request8;
		this.request9 = builder.request9;
		this.request10 = builder.request10;
		this.request11 = builder.request11;
	}

	public static class SettingExportBuilder {

		/**
		 * 全打刻情報受信フラグ
		 */
		private boolean request1;

		/**
		 * 時刻同期要求フラグ
		 */
		private boolean request2;

		/**
		 * 全予約情報受信フラグ
		 */
		private boolean request3;

		/**
		 * 全申請情報受信フラグ
		 */
		private boolean request4;

		/**
		 * 個人情報送信要求フラグ
		 */
		private boolean request6;

		/**
		 * 勤務種類名称送信要求フラグ
		 */
		private boolean request7;

		/**
		 * 就業時間帯名称送信要求フラグ
		 */
		private boolean request8;

		/**
		 * 残業名称／休日出勤名称送信要求フラグ
		 */
		private boolean request9;

		/**
		 * 予約メニュー送信要求フラグ
		 */
		private boolean request10;

		/**
		 * 申請理由名称送信要求フラグ
		 */
		private boolean request11;

		public SettingExportBuilder(boolean request1, boolean request2, boolean request3, boolean request4,
				boolean request6) {
			this.request1 = request1;

			this.request2 = request2;

			this.request3 = request3;

			this.request4 = request4;

			this.request6 = request6;
		}

		public SettingExportBuilder createReq7(boolean request7) {
			this.request7 = request7;
			return this;
		}

		public SettingExportBuilder createReq8(boolean request8) {
			this.request8 = request8;
			return this;
		}

		public SettingExportBuilder createReq9(boolean request9) {
			this.request9 = request9;
			return this;
		}

		public SettingExportBuilder createReq10(boolean request10) {
			this.request10 = request10;
			return this;
		}

		public SettingExportBuilder createReq11(boolean request11) {
			this.request11 = request11;
			return this;
		}

		public SendTimeRecordSettingExport build() {
			return new SendTimeRecordSettingExport(this);
		}

	}

}
