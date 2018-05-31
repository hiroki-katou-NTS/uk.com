package nts.uk.ctx.at.request.app.find.application.applicationlist;

import lombok.Getter;

@Getter
public class AppListParamFilter {

	private AppListExtractConditionDto condition;
	private boolean spr;
	private int extractCondition;
}
