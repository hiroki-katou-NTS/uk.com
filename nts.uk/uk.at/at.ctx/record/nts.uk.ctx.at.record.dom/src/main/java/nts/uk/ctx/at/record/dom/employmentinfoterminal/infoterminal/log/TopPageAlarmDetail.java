package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * トップページアラーム詳細
 * 
 * @author ThanhNX
 *
 */
@Getter
public class TopPageAlarmDetail {
	/** 連番 */
	private int serialNo;
	/** エラーメッセージ */
	private String errorMessage;
	/** 対象社員ID */
	private EmployeeId targerEmployee;

	public TopPageAlarmDetail(int serialNo, String errorMessage, EmployeeId targerEmployee) {
		super();
		this.serialNo = serialNo;
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}

}
