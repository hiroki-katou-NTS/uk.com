package nts.uk.ctx.bs.employee.dom.regpersoninfo.personinfoadditemdata.item;

import java.util.List;

import nts.uk.ctx.bs.person.dom.person.personinfoctgdata.categor.PerInfoCtgData;

/**
 * @author sonnlb
 *
 */
public interface EmpInfoItemDataRepository {

	List<PerInfoCtgData> getAllInfoItem(String categoryCd);

}
