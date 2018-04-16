package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import java.util.List;

import lombok.Getter;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePart;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;

/**
 * 選択ウィジェット
 * 
 * @author phongtq
 *
 */
@Getter
public class OptionalWidget extends TopPagePart {

	private List<WidgetDisplayItem> wDisplayItems;

	public OptionalWidget(String companyID, String toppagePartID, TopPagePartCode code, TopPagePartName name,
			TopPagePartType type, Size size, List<WidgetDisplayItem> wDisplayItems) {
		super(companyID, toppagePartID, code, name, type, size);
		this.wDisplayItems = wDisplayItems;
	}

}
