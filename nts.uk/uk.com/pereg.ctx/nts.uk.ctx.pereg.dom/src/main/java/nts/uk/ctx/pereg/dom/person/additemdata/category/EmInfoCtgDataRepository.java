package nts.uk.ctx.pereg.dom.person.additemdata.category;

import java.util.List;

public interface EmInfoCtgDataRepository {
	
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId);
	
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(List<String> ctgId);

	public void addCategoryData(EmpInfoCtgData empInfoCtgData);
	
	void updateEmpInfoCtgData(EmpInfoCtgData domain);
	
	void deleteEmpInfoCtgData(String recordId);
	
	
}
