package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import lombok.Getter;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;

//選択ウィジェット
@Getter
public class OptionalWidget extends TopPagePart {

	private WidgetDisplayItem widgetDisplayItem;
	
	public OptionalWidget(String companyID, String toppagePartID, TopPagePartCode code, TopPagePartName name,
			TopPagePartType type, Size size, WidgetDisplayItem widgetDisplayItem) {
		super(companyID, toppagePartID, code, name, type, size);
		this.widgetDisplayItem = widgetDisplayItem;
	}

}
