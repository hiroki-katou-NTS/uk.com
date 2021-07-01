package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.send;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         ﾘｸｴｽﾄ設定
 */
@Value
public class SendTimeRecordSetting implements DomainValue {

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
	
	/**
	 * リモート設定送信要求フラグ
	 */
	private final boolean request12;
	
	/**
	 * 切替日時送信要求フラグ
	 */
	private final boolean request13;
	
	/**
	 * Request-14
	 */
	private final boolean request14;
	
	/**
	 * Request-15
	 */
	private final boolean request15;
	
	/**
	 * Request-16
	 */
	private final boolean request16;
	
	/**
	 * 再起動を行う送信要求フラグ
	 */
	private final boolean request17;

	public SendTimeRecordSetting(SettingBuilder builder) {
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
		this.request12 = builder.request12;
		this.request13 = builder.request13;
		this.request14 = builder.request14;
		this.request15 = builder.request15;
		this.request16 = builder.request16;
		this.request17 = builder.request17;
	}

	public static class SettingBuilder {

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
		
		/**
		 * リモート設定送信要求フラグ
		 */
		private boolean request12;
		
		/**
		 * 切替日時送信要求フラグ
		 */
		private boolean request13;
		
		/**
		 * Request-14
		 */
		private boolean request14;
		
		/**
		 * Request-15
		 */
		private boolean request15;
		
		/**
		 * Request-16
		 */
		private boolean request16;
		
		/**
		 * 再起動を行う送信要求フラグ
		 */
		private boolean request17;

		public SettingBuilder(boolean request1, boolean request2, boolean request3, boolean request4,
				boolean request6) {
			this.request1 = request1;

			this.request2 = request2;

			this.request3 = request3;

			this.request4 = request4;

			this.request6 = request6;
		}

		public SettingBuilder createReq7(boolean request7) {
			this.request7 = request7;
			return this;
		}

		public SettingBuilder createReq8(boolean request8) {
			this.request8 = request8;
			return this;
		}

		public SettingBuilder createReq9(boolean request9) {
			this.request9 = request9;
			return this;
		}

		public SettingBuilder createReq10(boolean request10) {
			this.request10 = request10;
			return this;
		}

		public SettingBuilder createReq11(boolean request11) {
			this.request11 = request11;
			return this;
		}

		public SettingBuilder createReq12(boolean request12) {
			this.request12 = request12;
			return this;
		}
		
		public SettingBuilder createReq13(boolean request13) {
			this.request13 = request13;
			return this;
		}
		
		public SettingBuilder createReq14(boolean request14) {
			this.request14 = request14;
			return this;
		}

		public SettingBuilder createReq15(boolean request15) {
			this.request15 = request15;
			return this;
		}

		public SettingBuilder createReq16(boolean request16) {
			this.request16 = request16;
			return this;
		}
		
		public SettingBuilder createReq17(boolean request17) {
			this.request17 = request17;
			return this;
		}
		
		public SendTimeRecordSetting build() {
			return new SendTimeRecordSetting(this);
		}

	}

}
