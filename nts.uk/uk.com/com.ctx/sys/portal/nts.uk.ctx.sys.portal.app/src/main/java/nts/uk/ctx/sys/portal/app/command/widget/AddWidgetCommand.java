package nts.uk.ctx.sys.portal.app.command.widget;

import lombok.Getter;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.shr.com.context.AppContexts;

@Getter
public class AddWidgetCommand {

	private String topPagePartID;

	private String topPageCode;
	
	private String topPageName;
	//width size
	private int width;
	//height size
	private int height;


	public OptionalWidget toDomain(String topPagePartID) {
		return OptionalWidget.createFromJavaType(AppContexts.user().companyId(), topPagePartID,
				topPageCode, topPageName,
				TopPagePartType.FlowMenu.value, width, height);
	}
}
