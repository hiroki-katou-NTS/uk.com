package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.util.List;

/**
 * @author sonnlb
 *
 */
public interface EmpInfoItemDataRepository {

	List<EmpInfoItemData> getAllInfoItem(String categoryCd, String companyId);

	List<EmpInfoItemData> getAllInfoItemByRecordId(String recordId);
}
