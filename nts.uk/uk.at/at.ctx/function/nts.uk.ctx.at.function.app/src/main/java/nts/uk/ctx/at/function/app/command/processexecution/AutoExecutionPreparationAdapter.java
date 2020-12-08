package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationAdapter {

	void autoStoragePrepare(String patternCode, List<String> empIds);

	void autoDeletionPrepare(String patternCode);
}

