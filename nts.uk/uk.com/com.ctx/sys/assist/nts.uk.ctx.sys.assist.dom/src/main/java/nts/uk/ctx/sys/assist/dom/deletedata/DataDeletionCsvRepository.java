/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.deletedata;

import java.util.List;

/**
 * @author nam.lh
 *
 */
public interface DataDeletionCsvRepository {
	List<DataDeletionCsv> getDataDeletionCsvById(String delId);
}
