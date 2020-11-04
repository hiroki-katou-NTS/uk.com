package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import nts.arc.time.GeneralDateTime;

public class EmpInfoTerminalComStatusHelper {

	public static EmpInfoTerminalComStatus createEmpInfoTerminalComStatus() {
		return new EmpInfoTerminalComStatus(new ContractCode("000000000000"),
										new EmpInfoTerminalCode(1111),
										GeneralDateTime.ymdhms(2020, 12, 31, 0, 0, 0));
	}
}
