/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting;

import java.util.List;
import java.util.Optional;

/**
 *
 *
 */
public interface DataBeforeReflectingRepository {

	void addData(List<DataBeforeReflectingPerInfo> listDomain);
	
	void addDataNoCheckSid(List<DataBeforeReflectingPerInfo> listDomain);

	void updateData(List<DataBeforeReflectingPerInfo> listDomain);

	void deleteData(List<DataBeforeReflectingPerInfo> listDomain);

	void deleteDataByHistId(String histId);

	List<DataBeforeReflectingPerInfo> getData(int workId, List<String> listSid);

	List<DataBeforeReflectingPerInfo> getData(String cid, Integer workId, List<String> listSid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType);
}
