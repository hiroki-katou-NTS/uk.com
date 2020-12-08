package nts.uk.ctx.sys.assist.pub.command.autosetting;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationPub {
	
	void autoStoragePrepare(String patternCode, List<String> empIds);
	
	void autoDeletionPrepare(String patternCode);
}
