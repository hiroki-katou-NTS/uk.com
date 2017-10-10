package nts.uk.ctx.sys.env.ws.mailserver;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.sys.env.app.command.mailserver.MailServerSaveCommand;
import nts.uk.ctx.sys.env.app.command.mailserver.MailServerSaveCommandHandler;
import nts.uk.ctx.sys.env.app.find.mailserver.MailServerDto;
import nts.uk.ctx.sys.env.app.find.mailserver.MailServerFinder;


@Path("sys/env/mailserver")
@Produces(MediaType.APPLICATION_JSON)
public class MailServerWs extends WebService{
	
	@Inject
	MailServerSaveCommandHandler saveHandler;
	
	@Inject
	MailServerFinder findHandler;
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("find")
	@POST
	public MailServerDto find() {
		return this.findHandler.find();
	}
	
	/**
	 * Save.
	 *
	 * @param command the command
	 */
	@Path("save")
	@POST
	public void save(MailServerSaveCommand command) {
		this.saveHandler.handle(command);
	}
}
