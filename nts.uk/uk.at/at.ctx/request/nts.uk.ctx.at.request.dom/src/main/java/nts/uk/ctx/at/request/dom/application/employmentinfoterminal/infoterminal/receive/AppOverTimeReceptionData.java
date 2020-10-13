package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.receive;

import lombok.EqualsAndHashCode;
import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;

/**
 * @author ThanhNX
 *
 *         残業申請受信データ
 */
@Value
@EqualsAndHashCode(callSuper = true)
public class AppOverTimeReceptionData extends ApplicationReceptionData implements DomainValue {

	/**
	 * 残業時分１
	 */
	private final String overtimeHour1;

	/**
	 * 残業番号１
	 */
	private final String overtimeNo1;

	/**
	 * 残業時分2
	 */
	private final String overtimeHour2;

	/**
	 * 残業番号2
	 */
	private final String overtimeNo2;

	/**
	 * 残業時分3
	 */
	private final String overtimeHour3;

	/**
	 * 残業番号3
	 */
	private final String overtimeNo3;

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

	public AppOverTimeReceptionData(AppOverTimBuilder builder) {
		super(builder.getIdNumber(), builder.getApplicationCategory(), builder.getYmd(), builder.getTime());
		this.overtimeHour1 = builder.overtimeHour1;
		this.overtimeNo1 = builder.overtimeNo1;
		this.overtimeHour2 = builder.overtimeHour2;
		this.overtimeNo2 = builder.overtimeNo2;
		this.overtimeHour3 = builder.overtimeHour3;
		this.overtimeNo3 = builder.overtimeNo3;
		this.typeBeforeAfter = builder.typeBeforeAfter;
		this.appYMD = builder.appYMD;
		this.reason = builder.reason;
	}

	public static class AppOverTimBuilder extends ApplicationReceptionData {

		/**
		 * 残業時分１
		 */
		private String overtimeHour1;

		/**
		 * 残業番号１
		 */
		private String overtimeNo1;

		/**
		 * 残業時分2
		 */
		private String overtimeHour2;

		/**
		 * 残業番号2
		 */
		private String overtimeNo2;

		/**
		 * 残業時分3
		 */
		private String overtimeHour3;

		/**
		 * 残業番号3
		 */
		private String overtimeNo3;

		/**
		 * 事前事後
		 */
		private String typeBeforeAfter;

		/**
		 * 申請年月日
		 */
		private String appYMD;

		/**
		 * 理由
		 */
		private String reason;

		public AppOverTimBuilder(String idNumber, String applicationCategory, String ymd, String time,
				String overtimeHour1, String overtimeNo1) {
			super(idNumber, applicationCategory, ymd, time);
			this.overtimeHour1 = overtimeHour1;
			this.overtimeNo1 = overtimeNo1;
		}

		public AppOverTimBuilder overtimeHour2(String overtimeHour2) {
			this.overtimeHour2 = overtimeHour2;
			return this;
		}

		public AppOverTimBuilder overtimeNo2(String overtimeNo2) {
			this.overtimeNo2 = overtimeNo2;
			return this;
		}

		public AppOverTimBuilder overtimeHour3(String overtimeHour3) {
			this.overtimeHour3 = overtimeHour3;
			return this;
		}

		public AppOverTimBuilder overtimeNo3(String overtimeNo3) {
			this.overtimeNo3 = overtimeNo3;
			return this;
		}

		public AppOverTimBuilder typeBeforeAfter(String typeBeforeAfter) {
			this.typeBeforeAfter = typeBeforeAfter;
			return this;
		}

		public AppOverTimBuilder appYMD(String appYMD) {
			this.appYMD = appYMD;
			return this;
		}

		public AppOverTimBuilder reason(String reason) {
			this.reason = reason;
			return this;
		}

		public AppOverTimeReceptionData build() {
			return new AppOverTimeReceptionData(this);
		}
	}

}
