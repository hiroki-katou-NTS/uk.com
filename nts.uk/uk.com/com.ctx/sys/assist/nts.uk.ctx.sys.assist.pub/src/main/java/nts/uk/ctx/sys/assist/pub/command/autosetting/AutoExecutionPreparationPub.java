package nts.uk.ctx.sys.assist.pub.command.autosetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationPub {
	
	AutoPrepareDataExport autoStoragePrepare(String patternCode);
	
	boolean updateTargetEmployee(String storeProcessingId, String patternCode, List<String> empIds);
	
	AutoPrepareDataExport autoDeletionPrepare(String patternCode);
	
	boolean updateEmployeeDeletion(String delId, List<String> empIds);
}
