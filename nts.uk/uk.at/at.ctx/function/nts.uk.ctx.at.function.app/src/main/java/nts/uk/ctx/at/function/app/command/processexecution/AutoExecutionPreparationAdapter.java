package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.uk.ctx.at.function.dom.processexecution.UpdateProcessAutoExecution;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface AutoExecutionPreparationAdapter {

	Optional<String> autoStoragePrepare(UpdateProcessAutoExecution domain);

	Optional<String> autoDeletionPrepare(UpdateProcessAutoExecution domain);
}

