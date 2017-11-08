package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;

/**
 * @author sonnlb
 *
 */
public interface EmpInfoItemDataRepository {

	List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId, String employeeId);

	List<EmpInfoItemData> getAllInfoItemByRecordId(String recordId);

	void addNewCategoryData(PerInfoCtgData perInfoCtgData);
}
