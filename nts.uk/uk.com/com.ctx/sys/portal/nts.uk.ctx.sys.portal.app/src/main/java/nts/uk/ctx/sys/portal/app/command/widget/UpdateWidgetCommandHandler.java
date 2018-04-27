package nts.uk.ctx.sys.portal.app.command.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.enums.TopPagePartType;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartCode;
import nts.uk.ctx.sys.portal.dom.toppagepart.TopPagePartName;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidget;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.OptionalWidgetRepository;
import nts.uk.ctx.sys.portal.dom.toppagepart.optionalwidget.WidgetDisplayItem;
import nts.uk.ctx.sys.portal.dom.toppagepart.size.Size;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author phongtq
 *
 */
@Stateless
@Transactional
public class UpdateWidgetCommandHandler extends CommandHandler<UpdateWidgetCommand> {

	@Inject
	private OptionalWidgetRepository repository;

	@Override
	protected void handle(CommandHandlerContext<UpdateWidgetCommand> context) {
		String companyId = AppContexts.user().companyId();
		UpdateWidgetCommand command = context.getCommand();

		Optional<OptionalWidget> check = repository.findByCode(companyId, context.getCommand().getTopPagePartID());
		if (!check.isPresent())
			throw new RuntimeException("Can't find Optional Widget with ID: " + context.getCommand().getTopPagePartID());
		List<WidgetDisplayItem> wItems = new ArrayList<WidgetDisplayItem>();
		command.getDisplayItemTypes().stream().forEach(x -> {
			wItems.add(WidgetDisplayItem.createFromJavaType(x.getDisplayItemType(), x.getNotUseAtr()));
		});
		repository.update(new OptionalWidget(companyId, command.getTopPagePartID(),
				new TopPagePartCode(command.getTopPageCode()), new TopPagePartName(command.getTopPageName()),
				TopPagePartType.valueOf(1), Size.createFromJavaType(command.getWidth(), command.getHeight()), wItems));
	}
}