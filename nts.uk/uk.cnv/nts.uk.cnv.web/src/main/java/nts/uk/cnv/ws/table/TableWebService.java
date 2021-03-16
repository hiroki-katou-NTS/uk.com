package nts.uk.cnv.ws.table;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.table.TableDefinitionRegistCommand;
import nts.uk.cnv.app.td.command.table.TableDefinitionRegistCommandHandler;
import nts.uk.cnv.app.td.schema.prospect.TableListProspectQuery;
import nts.uk.cnv.app.td.schema.prospect.TableProspectDto;
import nts.uk.cnv.app.td.schema.prospect.TableProspectQuery;
import nts.uk.cnv.app.td.service.TableDesignerService;
import nts.uk.cnv.dom.td.schema.prospect.list.TableListProspect;

@Path("td/tables")
@Produces(MediaType.APPLICATION_JSON)
public class TableWebService {
	
	@Inject
	TableListProspectQuery tableListProspect;
	
	@Inject
	TableProspectQuery tableProspect;

	@Inject
	TableDefinitionRegistCommandHandler handler;

	@GET
	@Path("list")
	public TableListProspect list() {
		return tableListProspect.get();
	}

	@GET
	@Path("id/{tableId}")
	public TableProspectDto definition(@PathParam("tableId") String tableId) {
		return new TableProspectDto(tableProspect.get(tableId).get());
	}

	@POST
	@Path("regist")
	public void regist(TableDefinitionRegistCommand command) {
		handler.handle(command);
	}
}
