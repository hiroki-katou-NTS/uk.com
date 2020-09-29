package nts.uk.ctx.at.request.app.find.application.applicationlist;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.app.command.application.applicationlist.AppListExtractConditionCmd;
import nts.uk.ctx.at.request.app.command.application.applicationlist.ListOfAppTypesCmd;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StartMobileParam {
	private List<Integer> listAppType;
	private List<ListOfAppTypesCmd> listOfAppTypes;
	private AppListExtractConditionCmd appListExtractCondition;
}
