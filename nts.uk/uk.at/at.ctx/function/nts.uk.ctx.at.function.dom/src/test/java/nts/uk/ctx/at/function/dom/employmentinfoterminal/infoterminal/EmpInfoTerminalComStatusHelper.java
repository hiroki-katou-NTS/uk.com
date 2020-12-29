package nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal;

import nts.arc.time.GeneralDateTime;
/**
 * 
 * @author dungbn
 *
 */
public class EmpInfoTerminalComStatusHelper {

	public static EmpInfoTerminalComStatus createEmpInfoTerminalComStatus() {
		return new EmpInfoTerminalComStatus(new ContractCode("000000000000"),
										new EmpInfoTerminalCode("1111"),
										GeneralDateTime.now().addHours(60));
	}
	
	public static EmpInfoTerminalComStatus createEmpInfoTerminalComStatus2() {
		return new EmpInfoTerminalComStatus(new ContractCode("000000000000"),
										new EmpInfoTerminalCode("1111"),
										GeneralDateTime.now().addHours(-60));
	}
}
