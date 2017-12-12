package nts.uk.ctx.pereg.dom.person.additemdata.category;

import java.util.List;
import java.util.Optional;

public interface EmInfoCtgDataRepository {
	
	public Optional<EmpInfoCtgData> getEmpInfoCtgDataBySIdAndCtgId(String sId, String ctgId);
	
	public List<EmpInfoCtgData> getByEmpIdAndCtgId(String employeeId, String categoryId);

	public void addCategoryData(EmpInfoCtgData empInfoCtgData);
	
	void updateEmpInfoCtgData(EmpInfoCtgData domain);
	
	void deleteEmpInfoCtgData(String recordId);
	
	
}
