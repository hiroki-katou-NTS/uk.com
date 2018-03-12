package nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.shr.com.enumcommon.NotUseAtr;

//ウィジェット表示項目
@Value
@AllArgsConstructor
public class WidgetDisplayItem {

	private WidgetDisplayItemType displayItemType;
	
	private NotUseAtr notUseAtr;
	
}
