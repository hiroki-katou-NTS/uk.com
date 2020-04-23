package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

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
	private final EmpInfoTerminalCode terminalCode;

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
	private final List<String> bentoMenuFrameNumbers;

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

	public TimeRecordReqSetting(EmpInfoTerminalCode terminalCode, CompanyId companyId, String companyCode,
			List<EmployeeId> employeeIds, List<String> bentoMenuFrameNumbers, List<WorkTypeCode> workTypeCodes,
			List<WorkTimeCode> workTimeCodes, boolean overTimeHoliday, boolean applicationReason, boolean stampReceive,
			boolean reservationReceive, boolean applicationReceive, boolean timeSetting) {
		super();
		this.terminalCode = terminalCode;
		this.companyId = companyId;
		this.companyCode = companyCode;
		this.employeeIds = employeeIds;
		this.bentoMenuFrameNumbers = bentoMenuFrameNumbers;
		this.workTypeCodes = workTypeCodes;
		this.workTimeCodes = workTimeCodes;
		this.overTimeHoliday = overTimeHoliday;
		this.applicationReason = applicationReason;
		this.stampReceive = stampReceive;
		this.reservationReceive = reservationReceive;
		this.applicationReceive = applicationReceive;
		this.timeSetting = timeSetting;
	}

}
