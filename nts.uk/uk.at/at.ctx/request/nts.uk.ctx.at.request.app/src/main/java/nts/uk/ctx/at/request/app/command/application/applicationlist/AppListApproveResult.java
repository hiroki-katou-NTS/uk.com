package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ListOfApplicationDto;

@AllArgsConstructor
@Getter
@Setter
public class AppListApproveResult {
	
	private List<ListOfApplicationDto> successLst;
	
	private List<ListOfApplicationDto> failLst;
}
