package nts.uk.ctx.sys.portal.ws.widget;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.portal.app.command.widget.AddWidgetCommandHandler;
import nts.uk.ctx.sys.portal.app.command.widget.DeleteWidgetCommandHandler;
import nts.uk.ctx.sys.portal.app.command.widget.UpdateWidgetCommandHandler;

@Path("sys/portal/flowmenu")
@Produces("application/json")
public class WidgetWebService extends WebService {

	@Inject 
	private AddWidgetCommandHandler addWidgetCommandHandler;
	
	@Inject
	private UpdateWidgetCommandHandler updateWidgetCommandHandler;
	
	@Inject
	private DeleteWidgetCommandHandler deleteWidgetCommandHandler;
	
}
