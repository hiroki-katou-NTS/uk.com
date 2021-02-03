package nts.uk.ctx.at.function.dom.processexecution;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public interface ServerExternalOutputAdapter {

	Optional<ServerExternalOutputImport> findExternalOutput(String cid, String conditionCd);

	Optional<String> processAutoExecution(ProcessExecutionScope scope, String companyId, String execId,
			DatePeriod period, GeneralDate baseDate, String conditionCd);
}
