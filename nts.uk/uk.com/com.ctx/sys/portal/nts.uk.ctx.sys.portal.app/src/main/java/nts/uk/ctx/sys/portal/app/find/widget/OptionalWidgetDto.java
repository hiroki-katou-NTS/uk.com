package nts.uk.ctx.sys.portal.app.find.widget;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.sys.portal.app.command.widget.WidgetDisplayItemCommand;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;

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

	private List<WidgetDisplayItemCommand> displayItemTypes;

	public static OptionalWidgetDto fromDomain(OptionalWidget domain) {
		List<WidgetDisplayItemCommand> displayItems = domain.getWDisplayItems().stream().map(x -> {
			return new WidgetDisplayItemCommand(x.getDisplayItemType().value, x.getNotUseAtr().value);
		}).collect(Collectors.toList());

		return new OptionalWidgetDto(domain.getToppagePartID(), domain.getCode().v(), domain.getName().v(),
				domain.getSize().getWidth().v(), domain.getSize().getHeight().v(), displayItems);
	}
}
