package nts.uk.ctx.at.function.ac.autosetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.uk.ctx.at.function.app.command.processexecution.AutoExecutionPreparationAdapter;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class AutoExecutionPreparationAdapterImpl implements AutoExecutionPreparationAdapter {
	
	@Override
	public void autoStoragePrepare(String patternCode, List<String> empIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void autoDeletionPrepare(String patternCode) {
		// TODO Auto-generated method stub
		
	}

}
