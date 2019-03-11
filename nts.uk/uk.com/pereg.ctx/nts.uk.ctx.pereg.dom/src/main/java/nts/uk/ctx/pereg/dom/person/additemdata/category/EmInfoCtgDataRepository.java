package nts.uk.ctx.pereg.dom.person.additemdata.category;

import java.util.List;

public interface EmInfoCtgDataRepository {
	
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId);
	
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(List<String> ctgId);

	public void addCategoryData(EmpInfoCtgData empInfoCtgData);
	
	void updateEmpInfoCtgData(EmpInfoCtgData domain);
	
	void deleteEmpInfoCtgData(String recordId);
	/**
	 * @author lanlt
	 * get thông tin data của employee by sids, ctgId
	 * getBySidsAndCtgId
	 * @param sids
	 * @param ctgId
	 * @return
	 */
	public List<EmpInfoCtgData> getBySidsAndCtgId(List<String> sids, String ctgId);
	
	/**
	 * @author lanlt
	 * addAll EmpInfoCtgData
	 * @param domains
	 */
	public void addAll(List<EmpInfoCtgData> domains);
	
	/**
	 * @author lanlt
	 * updateAll EmpInfoCtgData
	 * @param domains
	 */
	void updateAll(List<EmpInfoCtgData> domains);
	
	
}
