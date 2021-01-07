package nts.uk.query.pubimp.exo.condset;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.query.app.exo.condset.AutoExecutionServerExternalOutputQuery;
import nts.uk.query.app.exo.condset.OutputPeriodSettingQuery;
import nts.uk.query.app.exo.condset.dto.ServerExternalOutputDto;
import nts.uk.query.pub.exo.condset.ServerExternalOutputExport;
import nts.uk.query.pub.exo.condset.ServerExternalOutputPub;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class ServerExternalOutputPubImpl implements ServerExternalOutputPub {

	@Inject
	private OutputPeriodSettingQuery outputPeriodSettingQuery;

	@Inject
	private AutoExecutionServerExternalOutputQuery autoExecutionServerExternalOutputQuery;

	@Override
	public ServerExternalOutputExport findExternalOutput(String cid, String conditionCd) {
		ServerExternalOutputDto dto = this.outputPeriodSettingQuery.processServerExternalOutput(cid, conditionCd);
		if (dto == null) {
			return null;
		}
		return new ServerExternalOutputExport(dto.isExecutionResult(), dto.getErrorMessage(), dto.getPeriod(),
				dto.getBaseDate());
	}

	@Override
	public void processAutoExecution(String conditionCd, DatePeriod period, GeneralDate baseDate, Integer categoryId,
			String execId) {
		this.autoExecutionServerExternalOutputQuery.processAutoExecution(conditionCd, period, baseDate, categoryId,
				execId);
	}
}
