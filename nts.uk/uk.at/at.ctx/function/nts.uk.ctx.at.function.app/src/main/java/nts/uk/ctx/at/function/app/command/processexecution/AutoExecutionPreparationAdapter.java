package nts.uk.ctx.at.function.app.command.processexecution;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationAdapter {

	void autoStoragePrepare(UpdateProcessAutoExecution domain);

	void autoDeletionPrepare(UpdateProcessAutoExecution domain);
}

