package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.ExtractConditionSelectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorAlarmWorkRecordDto;

@Path("screen/at/dailyperformance/correction/extraction")
@Produces("application/json")
public class ExtractConditionSelectionWebService {

	@Inject
	private ExtractConditionSelectionProcessor processor;

	@POST
	@Path("getErrorList")
	public List<ErrorAlarmWorkRecordDto> getAll() {
		return this.processor.getErrorAlarmWorkRecordList();
	}
}