package nts.uk.screen.at.ws.schedule.employeeinfo;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.screen.at.app.ksu001.sortsetting.GetSortedListEmployeeQuery;
import nts.uk.screen.at.app.ksu001.sortsetting.SortedListEmpDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hieult
 */
@Path("at/schedule/employeeinfo")
@Produces(MediaType.APPLICATION_JSON)
public class SortListEmpQueryWs extends WebService {
	
	@Inject
	private GetSortedListEmployeeQuery query;
	
	@POST
	@Path("startPage")
	public SortedListEmpDto getData(CommandKSU001S command ){
		List<String> listEmpid = command.getListEmpId().stream().map (x -> x.getId()).collect(Collectors.toList());
				
		String companyId = AppContexts.user().companyId();
		return query.getSortListEmp(companyId, GeneralDate.fromString(command.getDate(), "yyyy/MM/dd"), listEmpid, command.getSelectedEmployeeSwap());
		
		
	}
	
}
