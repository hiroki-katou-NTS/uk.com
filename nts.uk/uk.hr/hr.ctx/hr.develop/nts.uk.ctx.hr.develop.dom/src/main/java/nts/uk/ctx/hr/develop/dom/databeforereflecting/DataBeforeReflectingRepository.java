/**
 * 
 */
package nts.uk.ctx.hr.develop.dom.databeforereflecting;

import java.util.List;

/**
 *
 *
 */
public interface DataBeforeReflectingRepository {
	
	void addData(List<DataBeforeReflectingPerInfo> listDomain);
	void updateData(List<DataBeforeReflectingPerInfo> listDomain);
	void deleteData(List<DataBeforeReflectingPerInfo> listDomain);
	List<DataBeforeReflectingPerInfo> getData(int workId , List<String> listSid);
}
