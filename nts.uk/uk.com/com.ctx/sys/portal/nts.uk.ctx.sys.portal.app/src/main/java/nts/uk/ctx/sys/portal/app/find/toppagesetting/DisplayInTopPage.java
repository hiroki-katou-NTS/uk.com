package nts.uk.ctx.sys.portal.app.find.toppagesetting;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.portal.dom.layout.WidgetSetting;

@Data
@Builder
public class DisplayInTopPage {
	//	レイアウト枠1
	private List<FlowMenuOutputCCG008> layout1;
	//	レイアウト枠2
	private List<WidgetSetting> layout2;
	//	レイアウト枠3
	private List<WidgetSetting> layout3;
	
	private String urlLayout1;
	
	private Integer	layoutDisplayType;
}
