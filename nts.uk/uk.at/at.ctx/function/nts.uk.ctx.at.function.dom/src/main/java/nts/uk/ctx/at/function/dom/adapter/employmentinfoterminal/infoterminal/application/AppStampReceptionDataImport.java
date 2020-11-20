package nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.application;

import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * @author ThanhNX
 *
 *         打刻申請受信データPub
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AppStampReceptionDataImport extends ApplicationReceptionDataImport {

	/**
	 * 外出区分
	 */
	private final String goOutCategory;

	/**
	 * 事前事後
	 */
	private final String typeBeforeAfter;

	/**
	 * 申請時分
	 */
	private final String appTime;

	/**
	 * 申請年月日
	 */
	private final String appYMD;

	/**
	 * 打刻区分
	 */
	private final String stampType;

	/**
	 * 理由
	 */
	private final String reason;

	public AppStampReceptionDataImport(AppStampBuilder builder) {
		super(builder.getIdNumber(), builder.getApplicationCategory(), builder.getYmd(), builder.getTime());
		this.goOutCategory = builder.goOutCategory;
		this.typeBeforeAfter = builder.typeBeforeAfter;
		this.appTime = builder.appTime;
		this.appYMD = builder.appYMD;
		this.stampType = builder.stampType;
		this.reason = builder.reason;
	}

	public AppStampReceptionDataImport(ApplicationReceptionDataImport app, String goOutCategory, String typeBeforeAfter,
			String appTime, String appYMD, String stampType, String reason) {
		super(app.getIdNumber(), app.getApplicationCategory(), app.getYmd(), app.getTime());
		this.goOutCategory = goOutCategory;
		this.typeBeforeAfter = typeBeforeAfter;
		this.appTime = appTime;
		this.appYMD = appYMD;
		this.stampType = stampType;
		this.reason = reason;
	}

	public static class AppStampBuilder extends ApplicationReceptionDataImport {

		/**
		 * 外出区分
		 */
		private String goOutCategory;

		/**
		 * 事前事後
		 */
		private String typeBeforeAfter;

		/**
		 * 申請時分
		 */
		private String appTime;

		/**
		 * 申請年月日
		 */
		private String appYMD;

		/**
		 * 打刻区分
		 */
		private String stampType;

		/**
		 * 理由
		 */
		private String reason;

		public AppStampBuilder(String idNumber, String applicationCategory, String ymd, String time,
				String goOutCategory, String typeBeforeAfter) {
			super(idNumber, applicationCategory, ymd, time);
			this.goOutCategory = goOutCategory;
			this.typeBeforeAfter = typeBeforeAfter;
		}

		public AppStampBuilder appTime(String appTime) {
			this.appTime = appTime;
			return this;
		}

		public AppStampBuilder appYMD(String appYMD) {
			this.appYMD = appYMD;
			return this;
		}

		public AppStampBuilder stampType(String stampType) {
			this.stampType = stampType;
			return this;
		}

		public AppStampBuilder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public AppStampReceptionDataImport build() {
			return new AppStampReceptionDataImport(this);
		}

	}
}
