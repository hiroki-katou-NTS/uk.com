package nts.uk.ctx.at.request.app.find.application.common;

import java.util.List;

import nts.uk.ctx.at.request.app.command.application.applicationlist.ListOfAppTypesCmd;
import nts.uk.ctx.at.request.app.find.application.common.dto.PrintContentOfEachAppDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
public class AppPrintQuery {
	
	public AppDispInfoStartupDto appDispInfoStartupOutput;
	
	public PrintContentOfEachAppDto opPrintContentOfEachApp;
	
	public List<ListOfAppTypesCmd> appNameList;
}
