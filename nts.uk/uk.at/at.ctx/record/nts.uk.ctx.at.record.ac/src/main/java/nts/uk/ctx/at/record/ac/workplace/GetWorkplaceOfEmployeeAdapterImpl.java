package nts.uk.ctx.at.record.ac.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.adapter.workplace.GetWorkplaceOfEmployeeAdapter;
import nts.uk.ctx.at.record.dom.adapter.workplace.ReferenceableWorkplaceImport;
import nts.uk.ctx.sys.auth.pub.workplace.ReferenceableWorkplaceExport;
import nts.uk.ctx.sys.auth.pub.workplace.WorkplaceListPub;

/**
 * 
 * @author sonnlb
 *
 */
@Stateless
public class GetWorkplaceOfEmployeeAdapterImpl implements GetWorkplaceOfEmployeeAdapter {
	
	@Inject
	private WorkplaceListPub workplaceListPub;

	@Override
	public ReferenceableWorkplaceImport get(String userID, String employeeID, GeneralDate date) {
		
		
		//$参照可能職場 = 参照可能社員の所属職場を取得するPublish.取得する(require,ユーザID,社員ID,基準日)
		ReferenceableWorkplaceExport wkp =  this.workplaceListPub.getWorkPlace(userID, employeeID, date);
		
		//return 参照可能職場#参照可能職場($参照可能職場.職場リスト,$参照可能職場.所属情報)	
		
		return new ReferenceableWorkplaceImport(wkp.getWorkplaceList(), wkp.getAffiliationInformation()); 
	}

}
