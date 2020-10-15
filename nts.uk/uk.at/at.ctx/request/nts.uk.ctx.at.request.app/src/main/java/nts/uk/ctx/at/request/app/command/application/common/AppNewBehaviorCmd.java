package nts.uk.ctx.at.request.app.command.application.common;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ListOfAppTypesCmd;
import nts.uk.ctx.at.request.app.find.application.ApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;

/**
 * refactor 4
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AppNewBehaviorCmd {
	
	private ApplicationDto application;
	
	private AppDispInfoStartupDto appDispInfoStartupOutput;
	
	private List<ListOfAppTypesCmd> listOfAppTypes;
}
