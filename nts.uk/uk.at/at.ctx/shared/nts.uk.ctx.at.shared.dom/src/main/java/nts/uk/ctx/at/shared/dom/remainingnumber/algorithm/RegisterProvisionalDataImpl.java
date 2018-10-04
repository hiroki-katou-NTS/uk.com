package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class RegisterProvisionalDataImpl implements RegisterProvisionalData {

	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Override
	public void registerProvisionalData(String companyID, List<EmpProvisionalInput> empProLst) {
		empProLst.forEach(empPro -> {
			interimRemainDataMngRegisterDateChange.registerDateChange(companyID, empPro.getEmployeeID(), empPro.getDateLst());
		});
	}

}
