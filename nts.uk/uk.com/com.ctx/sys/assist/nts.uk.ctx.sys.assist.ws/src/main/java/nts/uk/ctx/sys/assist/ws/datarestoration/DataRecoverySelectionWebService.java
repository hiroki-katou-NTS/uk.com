package nts.uk.ctx.sys.assist.ws.datarestoration;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.sys.assist.app.find.datarestoration.DataRecoverySelectionDto;
import nts.uk.ctx.sys.assist.app.find.datarestoration.DataRecoverySelectionFinder;
import nts.uk.ctx.sys.assist.app.find.datarestoration.SearchSelectedDataRecovery;

@Path("ctx/sys/assist/datarestoration")
@Produces("application/json")
public class DataRecoverySelectionWebService {
	@Inject
	private DataRecoverySelectionFinder dataRecoverySelectionFinder;
	@POST
	@Path("findDataRecoverySelection")
	public List<DataRecoverySelectionDto> findDataRecoverySelection(SearchSelectedDataRecovery paramSearch) {
		return dataRecoverySelectionFinder.getTargetById(paramSearch.getStartDate(), paramSearch.getEndDate());
	}
}
