package nts.uk.ctx.sys.portal.app.find.toppage;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WidgetSettingDto {
	
	private int widgetType;
	
	private int order;

}
