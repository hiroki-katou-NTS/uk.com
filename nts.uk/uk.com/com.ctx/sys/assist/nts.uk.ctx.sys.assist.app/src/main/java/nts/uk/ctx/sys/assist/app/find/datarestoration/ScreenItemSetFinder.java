package nts.uk.ctx.sys.assist.app.find.datarestoration;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.datarestoration.EmployeeSelection;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecovery;
import nts.uk.ctx.sys.assist.dom.datarestoration.PerformDataRecoveryRepository;
import nts.uk.ctx.sys.assist.dom.tablelist.TableList;

@Stateless
public class ScreenItemSetFinder {
	
	@Inject
	private PerformDataRecoveryRepository performDataRecoveryRepository;
	
	public EmployeeSelection getScreenItemSet(List<ItemSetDto> input) {
		//TODO: GET PERFORM DATA RECOVERY
		
		EmployeeSelection employeeSelection = new EmployeeSelection();
		
		return employeeSelection;
	}

}
