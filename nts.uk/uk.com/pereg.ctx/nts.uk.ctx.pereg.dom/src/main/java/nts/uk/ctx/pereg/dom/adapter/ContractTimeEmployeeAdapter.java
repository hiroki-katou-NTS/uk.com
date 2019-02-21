/**
 * 
 */
package nts.uk.ctx.pereg.dom.adapter;

import java.util.List;

import nts.arc.time.GeneralDate;

/**
 * @author hieult
 *
 */
public interface ContractTimeEmployeeAdapter {
	
	List<ContractTimeEmployeeImport> getData(List<String> listEmpID, GeneralDate baseDate);


}
