package nts.uk.ctx.at.function.dom.adapter.widgetKtg;

import java.util.List;

import lombok.Getter;

@Getter
public class OptionalWidgetImport {

	private String topPagePartID;

	private String topPageCode;

	private String topPageName;
	
	private int width;
	
	private int height;
	
	private List<WidgetDisplayItemImport> WidgetDisplayItemExport;

	public OptionalWidgetImport(String topPagePartID, String topPageCode, String topPageName, int width, int height,
			List<WidgetDisplayItemImport> widgetDisplayItemExport) {
		super();
		this.topPagePartID = topPagePartID;
		this.topPageCode = topPageCode;
		this.topPageName = topPageName;
		this.width = width;
		this.height = height;
		WidgetDisplayItemExport = widgetDisplayItemExport;
	}
	
	
}
