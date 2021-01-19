package nts.uk.ctx.sys.assist.pub.command.autosetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationPub {
	
	AutoPrepareDataExport autoStoragePrepare(String patternCode);
	
	Optional<String> updateTargetEmployee(String storeProcessingId, String patternCode, List<String> empIds);
	
	AutoPrepareDataExport autoDeletionPrepare(String patternCode);
	
	Optional<String> updateEmployeeDeletion(String delId, List<String> empIds);
}
