package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal;

import java.util.List;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * @author ThanhNX
 *
 *         タイムレコードのﾘｸｴｽﾄ設定
 */
public class TimeRecordReqSetting implements DomainAggregate {

	/**
	 * コード
	 */
	@Getter
	private final Integer terminalCode;

	/**
	 * 会社ID
	 */
	@Getter
	private final CompanyId companyId;

	/**
	 * 会社コード
	 */
	@Getter
	private final String companyCode;

	/**
	 * 社員ID
	 */
	@Getter
	private final List<EmployeeId> employeeIds;

	/**
	 * 弁当メニュー枠番
	 */
	@Getter
	private final List<Integer> bentoMenuFrameNumbers;

	/**
	 * 勤務種類コード
	 */
	@Getter
	private final List<WorkTypeCode> workTypeCodes;

	/**
	 * 就業時間帯コード
	 */
	@Getter
	private final List<WorkTimeCode> workTimeCodes;

	/**
	 * 残業・休日出勤送信
	 */
	@Getter
	private final boolean overTimeHoliday;

	/**
	 * 申請理由送信
	 */
	@Getter
	private final boolean applicationReason;

	/**
	 * 全ての打刻データ
	 */
	@Getter
	private final boolean stampReceive;

	/**
	 * 全ての予約データ
	 */
	@Getter
	private final boolean reservationReceive;

	/**
	 * 全ての申請データ
	 */
	@Getter
	private final boolean applicationReceive;

	/**
	 * 時刻セット
	 */
	@Getter
	private final boolean timeSetting;

	public TimeRecordReqSetting(ReqSettingBuilder builder) {
		super();
		this.terminalCode = builder.terminalCode;
		this.companyId = builder.companyId;
		this.companyCode = builder.companyCode;
		this.employeeIds = builder.employeeIds;
		this.bentoMenuFrameNumbers = builder.bentoMenuFrameNumbers;
		this.workTypeCodes = builder.workTypeCodes;
		this.workTimeCodes = builder.workTimeCodes;
		this.overTimeHoliday = builder.overTimeHoliday;
		this.applicationReason = builder.applicationReason;
		this.stampReceive = builder.stampReceive;
		this.reservationReceive = builder.reservationReceive;
		this.applicationReceive = builder.applicationReceive;
		this.timeSetting = builder.timeSetting;
	}

	public static class ReqSettingBuilder {
		/**
		 * コード
		 */
		private Integer terminalCode;

		/**
		 * 会社ID
		 */
		private CompanyId companyId;

		/**
		 * 会社コード
		 */
		private String companyCode;

		/**
		 * 社員ID
		 */
		private List<EmployeeId> employeeIds;

		/**
		 * 弁当メニュー枠番
		 */
		private List<Integer> bentoMenuFrameNumbers;

		/**
		 * 勤務種類コード
		 */
		private List<WorkTypeCode> workTypeCodes;

		/**
		 * 就業時間帯コード
		 */
		private List<WorkTimeCode> workTimeCodes;

		/**
		 * 残業・休日出勤送信
		 */
		private boolean overTimeHoliday;

		/**
		 * 申請理由送信
		 */
		private boolean applicationReason;

		/**
		 * 全ての打刻データ
		 */
		private boolean stampReceive;

		/**
		 * 全ての予約データ
		 */
		private boolean reservationReceive;

		/**
		 * 全ての申請データ
		 */
		private boolean applicationReceive;

		/**
		 * 時刻セット
		 */
		private boolean timeSetting;

		public ReqSettingBuilder(Integer terminalCode, CompanyId companyId, String companyCode,
				List<EmployeeId> employeeIds, List<Integer> bentoMenuFrameNumbers, List<WorkTypeCode> workTypeCodes) {
			this.terminalCode = terminalCode;
			this.companyId = companyId;
			this.companyCode = companyCode;
			this.employeeIds = employeeIds;
			this.bentoMenuFrameNumbers = bentoMenuFrameNumbers;
			this.workTypeCodes = workTypeCodes;
		}

		public ReqSettingBuilder workTime(List<WorkTimeCode> workTimeCodes) {
			this.workTimeCodes = workTimeCodes;
			return this;
		}

		public ReqSettingBuilder overTimeHoliday(boolean overTimeHoliday) {
			this.overTimeHoliday = overTimeHoliday;
			return this;
		}

		public ReqSettingBuilder applicationReason(boolean applicationReason) {
			this.applicationReason = applicationReason;
			return this;
		}

		public ReqSettingBuilder stampReceive(boolean stampReceive) {
			this.stampReceive = stampReceive;
			return this;
		}

		public ReqSettingBuilder reservationReceive(boolean reservationReceive) {
			this.reservationReceive = reservationReceive;
			return this;
		}

		public ReqSettingBuilder applicationReceive(boolean applicationReceive) {
			this.applicationReceive = applicationReceive;
			return this;
		}

		public ReqSettingBuilder timeSetting(boolean timeSetting) {
			this.timeSetting = timeSetting;
			return this;
		}

		public TimeRecordReqSetting build() {
			return new TimeRecordReqSetting(this);
		}
	}

}
