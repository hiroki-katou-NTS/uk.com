package nts.uk.ctx.at.function.pub.employmentinfoterminal.infoterminal;

import java.util.List;
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
	
	List<EmpInfoTerminalComStatusExport> get(String contractCode, List<String> empInfoTerCodeList);
}
