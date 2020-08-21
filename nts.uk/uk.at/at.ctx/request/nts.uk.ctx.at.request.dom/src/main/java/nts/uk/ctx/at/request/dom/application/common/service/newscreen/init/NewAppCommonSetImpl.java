package nts.uk.ctx.at.request.dom.application.common.service.newscreen.init;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.init.output.AppCommonSetOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class NewAppCommonSetImpl implements NewAppCommonSetService {

	@Override
	public AppCommonSetOutput getNewAppCommonSet(String companyID, String employeeID, EmploymentRootAtr rootAtr,
			ApplicationType appType, GeneralDate appDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
