package nts.uk.cnv.ws.table;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.table.AlterTableCommand;
import nts.uk.cnv.app.td.command.table.AlterTableCommandHandler;
import nts.uk.cnv.app.td.command.table.DropTableCommandHandler;
import nts.uk.cnv.app.td.schema.prospect.TableListProspectQuery;
import nts.uk.cnv.app.td.schema.prospect.TableProspectDto;
import nts.uk.cnv.app.td.schema.prospect.TableProspectQuery;
import nts.uk.cnv.dom.td.schema.prospect.list.TableListProspect;

@Path("td/tables")
@Produces(MediaType.APPLICATION_JSON)
public class TableWebService {
	
	@Inject
	TableListProspectQuery tableListProspect;
	
	@Inject
	TableProspectQuery tableProspect;

	@Inject
	AlterTableCommandHandler alterTable;

	@Inject
	DropTableCommandHandler dropTable;

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
	@Path("alter")
	public void alter(AlterTableCommand command) {
		alterTable.handle(command);
	}

	@POST
	@Path("drop")
	public void drop(AlterTableCommand command) {
		dropTable.handle(command);
	}
}
