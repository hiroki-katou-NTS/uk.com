package nts.uk.ctx.sys.portal.app.command.widget;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service.WidgetService;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
@Transactional
public class AddWidgetCommandHandler extends CommandHandler<AddWidgetCommand> {

	@Inject
	private WidgetService widgetService;
	
	@Override
	protected void handle(CommandHandlerContext<AddWidgetCommand> context) {
		String topPagePartId = IdentifierUtil.randomUniqueId();
		AddWidgetCommand command = context.getCommand();
		
		widgetService.createWidget(command.toDomain(topPagePartId));
		
	}

}
