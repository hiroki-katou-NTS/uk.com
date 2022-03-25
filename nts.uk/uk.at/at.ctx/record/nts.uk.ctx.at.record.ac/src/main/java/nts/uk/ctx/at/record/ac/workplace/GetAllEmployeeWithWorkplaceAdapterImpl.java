package nts.uk.ctx.at.record.ac.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.GetAllEmployeeWithWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.ReferenceableWorkplaceImport;
import nts.uk.ctx.sys.auth.pub.workplace.ReferenceableWorkplaceExport;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;

/**
 *
 * @author sonnlb
 *
 */
@Stateless
public class GetAllEmployeeWithWorkplaceAdapterImpl implements GetAllEmployeeWithWorkplaceAdapter {

	@Inject
	private WorkplaceListPub workplaceListPub;

	@Override
	public ReferenceableWorkplaceImport get(String employeeId, GeneralDate baseDate) {
		
		//	$参照可能職場 = 全社員の参照範囲で職場・社員を取得する.取得する(require,社員ID,基準日)
		ReferenceableWorkplaceExport wkp = this.workplaceListPub.getWorkPlaceByReference(employeeId,baseDate);
		//	return 参照可能職場#参照可能職場($参照可能職場.職場リスト,$参照可能職場.所属情報)	
		return new ReferenceableWorkplaceImport(wkp.getWorkplaceList(), wkp.getAffiliationInformation());
	}
	
	
	

}
