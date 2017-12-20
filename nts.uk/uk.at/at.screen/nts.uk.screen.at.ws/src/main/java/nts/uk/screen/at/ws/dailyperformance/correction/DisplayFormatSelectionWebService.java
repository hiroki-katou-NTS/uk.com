package nts.uk.screen.at.ws.dailyperformance.correction;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.screen.at.app.dailyperformance.correction.DisplayFormatSelectionProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceFormatDto;

@Path("screen/at/dailyperformance/correction/dailyPerfFormat")
@Produces("application/json")
public class DisplayFormatSelectionWebService {

	@Inject
	private DisplayFormatSelectionProcessor processor;

	@POST
	@Path("getFormatList")
	public List<DailyPerformanceFormatDto> getAll() {
		return this.processor.getDailyPerformanceFormatList();
	}
}