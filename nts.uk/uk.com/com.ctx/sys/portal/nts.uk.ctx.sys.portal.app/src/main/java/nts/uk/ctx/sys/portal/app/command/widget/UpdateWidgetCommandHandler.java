package nts.uk.ctx.sys.portal.app.command.widget;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.service.WidgetService;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author phongtq
 *
 */
@Stateless
@Transactional
public class UpdateWidgetCommandHandler extends CommandHandler<AddWidgetCommand> {
	
	@Inject
	private OptionalWidgetRepository repository;

	@Inject
	private WidgetService service;
	
	@Override
	protected void handle(CommandHandlerContext<AddWidgetCommand> context) {
		String companyId = AppContexts.user().companyId();
		AddWidgetCommand command = context.getCommand();
		
		Optional<OptionalWidget> check = repository.findByCode(companyId, context.getCommand().getTopPagePartID());
		if (!check.isPresent())
			throw new RuntimeException("Can't find FlowMenu with ID: " + context.getCommand().getTopPagePartID());
		
		OptionalWidget widget = check.get();
		widget.setName(command.getTopPageName());
		widget.setSize(command.getWidth(), command.getHeight());
		
		service.updateWidget(widget);
	}
}