package nts.uk.ctx.at.function.ac.exo.condset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.processexecution.ProcessExecutionScope;
import nts.uk.ctx.at.function.dom.processexecution.ServerExternalOutputAdapter;
import nts.uk.ctx.at.function.dom.processexecution.ServerExternalOutputImport;
import nts.uk.query.pub.exo.condset.ServerExternalOutputExport;
import nts.uk.query.pub.exo.condset.ServerExternalOutputPub;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ServerExternalOutputAdapterImpl implements ServerExternalOutputAdapter {

	@Inject
	private ServerExternalOutputPub pub;

	@Override
	public Optional<ServerExternalOutputImport> findExternalOutput(String cid, String conditionCd) {
		try {
			ServerExternalOutputExport export = pub.findExternalOutput(cid, conditionCd);
			return Optional.ofNullable(export).map(exp -> new ServerExternalOutputImport(exp.isExecutionResult(),
					exp.getErrorMessage(), exp.getPeriod(), exp.getBaseDate()));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	@Override
	public Optional<String> processAutoExecution(ProcessExecutionScope scope, String companyId, String execId,
			DatePeriod period, GeneralDate baseDate, String conditionCd) {
		return this.pub.processAutoExecution(companyId, conditionCd, period, baseDate, null, execId);
	}

}
