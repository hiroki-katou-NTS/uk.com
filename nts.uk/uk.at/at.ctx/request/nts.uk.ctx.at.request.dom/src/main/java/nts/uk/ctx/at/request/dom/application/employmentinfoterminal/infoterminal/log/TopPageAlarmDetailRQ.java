package nts.uk.ctx.at.request.dom.application.employmentinfoterminal.infoterminal.log;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * トップページアラーム詳細
 * 
 * @author ThanhNX
 *
 */
@Getter
public class TopPageAlarmDetailRQ {
	/** 連番 */
	private int serialNo;
	/** エラーメッセージ */
	private String errorMessage;
	/** 対象社員ID */
	private EmployeeId targerEmployee;

	public TopPageAlarmDetailRQ(int serialNo, String errorMessage, EmployeeId targerEmployee) {
		super();
		this.serialNo = serialNo;
		this.errorMessage = errorMessage;
		this.targerEmployee = targerEmployee;
	}

}
