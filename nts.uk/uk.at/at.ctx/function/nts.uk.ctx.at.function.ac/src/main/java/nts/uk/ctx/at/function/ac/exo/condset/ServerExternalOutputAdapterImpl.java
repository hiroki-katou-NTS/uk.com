package nts.uk.ctx.at.function.ac.exo.condset;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
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
	public ServerExternalOutputImport findExternalOutput(String cid, String conditionCd) throws Exception {
		ServerExternalOutputExport export = pub.findExternalOutput(cid, conditionCd);
		return new ServerExternalOutputImport(export.isExecutionResult(), export.getErrorMessage(), export.getPeriod(),
				export.getBaseDate());
	}

	@Override
	public void processAutoExecution(String conditionCd, DatePeriod period, GeneralDate baseDate, String execId) {
		this.pub.processAutoExecution(conditionCd, period, baseDate, null, execId);
	}

}
