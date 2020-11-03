package nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal;

import java.util.Optional;


public interface EmpInfoTerminalComStatusPub {

	Optional<EmpInfoTerminalComStatusExport> get(String contractCode, int empInfoTerCodeList); 
	
	void delete(EmpInfoTerminalComStatusExport empInfoTerminalComStatusExport);
}
