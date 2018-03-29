package nts.uk.screen.at.app.ktgwidget.find;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OptionalWidgetDto {

	private String topPagePartID;

	private String topPageCode;

	private String topPageName;
	// width size
	private int width;
	// height size
	private int height;
	
	private List<WidgetDisplayItem> widgetDisplayItem;
	
}
