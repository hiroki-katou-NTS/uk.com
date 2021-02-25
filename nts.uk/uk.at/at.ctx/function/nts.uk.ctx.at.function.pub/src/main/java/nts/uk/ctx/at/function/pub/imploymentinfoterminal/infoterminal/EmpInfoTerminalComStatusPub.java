package nts.uk.ctx.at.function.pub.imploymentinfoterminal.infoterminal;

import java.util.Optional;

/**
 * 
 * @author dungbn
 *
 */
public interface EmpInfoTerminalComStatusPub {

	Optional<EmpInfoTerminalComStatusExport> get(String contractCode, String empInfoTerCodeList); 
	
	// [1] 削除する
	void delete(EmpInfoTerminalComStatusExport empInfoTerminalComStatusExport);
}
