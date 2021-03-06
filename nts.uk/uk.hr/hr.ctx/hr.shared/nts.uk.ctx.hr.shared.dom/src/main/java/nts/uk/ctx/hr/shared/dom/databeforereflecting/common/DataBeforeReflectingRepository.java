/**
 * 
 */
package nts.uk.ctx.hr.shared.dom.databeforereflecting.common;

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

	List<DataBeforeReflectingPerInfo> getData(String cid, Integer workId, List<String> listSid,List<String> listPid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType);
	
	List<Integer> getWorkId(String cId, String employeeId);
	
	List<DataBeforeReflectingPerInfo> getDataByApproveSid(String cid, Integer workId, String sid,
			Optional<Boolean> includReflected, Optional<String> sortByColumnName, Optional<String> orderType);
	
	Optional<DataBeforeReflectingPerInfo> getByHistId(String histId);
	
	boolean checkExitByWorkIdCidSid(String cId, String sid);
	
}
