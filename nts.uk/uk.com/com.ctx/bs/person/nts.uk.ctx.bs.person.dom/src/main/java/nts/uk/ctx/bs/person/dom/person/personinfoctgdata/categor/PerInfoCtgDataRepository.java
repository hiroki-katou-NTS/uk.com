/**
 * 
 */
package nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor;

import java.util.List;
import java.util.Optional;

/**
 * @author danpv
 *
 */
public interface PerInfoCtgDataRepository {
	
	public Optional<PerInfoCtgData> getByRecordId(String recordId);
	
	public List<PerInfoCtgData> getByPerIdAndCtgId(String perId, String ctgId);

}
