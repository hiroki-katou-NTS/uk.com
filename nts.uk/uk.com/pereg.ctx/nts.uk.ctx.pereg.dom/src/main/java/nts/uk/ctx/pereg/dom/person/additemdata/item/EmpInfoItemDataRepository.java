package nts.uk.ctx.pereg.dom.person.additemdata.item;

import java.util.List;

/**
 * @author sonnlb
 *
 */
public interface EmpInfoItemDataRepository {

	List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId, String employeeId);
	
	List<EmpInfoItemData> getAllInfoItemBySidCtgId(String ctgId, String employeeId);

	List<EmpInfoItemData> getAllInfoItemByRecordId(String recordId);

	void addItemData(EmpInfoItemData infoItemData);
	
	void updateEmpInfoItemData(EmpInfoItemData domain);
	
	void deleteEmployInfoItemData(String recordID);
}
