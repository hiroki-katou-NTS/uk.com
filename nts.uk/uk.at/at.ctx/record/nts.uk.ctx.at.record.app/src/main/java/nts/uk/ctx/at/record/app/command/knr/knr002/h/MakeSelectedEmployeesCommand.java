package nts.uk.ctx.at.record.app.command.knr.knr002.h;

import java.util.List;

import lombok.Value;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;

/**
 * 
 * @author xuannt
 *
 */
@Value
public class MakeSelectedEmployeesCommand {
	//	就業情報端末コード
	private EmpInfoTerminalCode terminalCode;
	//	ログイン会社の社員ID(List)
	private List<EmployeeId> loginCompanyEmployeesID;
	//	選択した社員ID<List>
	private List<EmployeeId> selectedEmployeesID;
}
