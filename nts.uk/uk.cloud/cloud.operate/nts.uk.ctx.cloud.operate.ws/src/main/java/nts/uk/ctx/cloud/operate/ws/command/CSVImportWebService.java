package nts.uk.ctx.cloud.operate.ws.command;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import lombok.val;
import nts.uk.ctx.cloud.operate.app.command.CSVImportCommand;
import nts.uk.ctx.cloud.operate.app.command.CSVImportCommandHandler;

@Path("ctx/cld/operate/tenant")
public class CSVImportWebService {

	@Inject
	private CSVImportCommandHandler handler;

	@POST
	@Path("csvimport")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public void csvImport(@MultipartForm MultipartFormDataInput input) {
		val command = new CSVImportCommand(input.getFormDataMap());
		this.handler.handle(command);
	}
}