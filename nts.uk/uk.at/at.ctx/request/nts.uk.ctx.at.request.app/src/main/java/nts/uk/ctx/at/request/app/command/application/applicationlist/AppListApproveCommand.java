package nts.uk.ctx.at.request.app.command.application.applicationlist;

import java.util.List;

import lombok.Data;

@Data
public class AppListApproveCommand {
	
	private boolean isApprovalAll;
	
	private int device;
	
	private List<ListOfApplicationCmd> listOfApplicationCmds;
	
//	private List<ListOfAppTypesCmd> listOfAppTypes;
	
}
