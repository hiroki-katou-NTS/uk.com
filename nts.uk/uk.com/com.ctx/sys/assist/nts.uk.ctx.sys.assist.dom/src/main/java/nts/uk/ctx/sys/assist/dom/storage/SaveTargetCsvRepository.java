/**
 * 
 */
package nts.uk.ctx.sys.assist.dom.storage;

import java.util.List;

/**
 * @author nam.lh
 *
 */
public interface SaveTargetCsvRepository {
	List<SaveTargetCsv> getSaveTargetCsvById(String storeProcessingId);
}
