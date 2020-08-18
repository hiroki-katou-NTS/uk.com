package nts.uk.cnv.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.cnv.app.command.TableDesignImportCommand;
import nts.uk.cnv.app.command.TableDesignImportCommandHandler;

@Path("cnv/tabledesign")
@Produces("application/json")
public class TableDesignImportWebService {

	@Inject
	private TableDesignImportCommandHandler handler;

	@POST
	@Path("import")
	public void importTable(TableDesignImportCommand command) {
		handler.handle(command);
	}
}
