package nts.uk.cnv.ws.td.table;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.alteration.command.AlterTableCommand;
import nts.uk.cnv.app.td.alteration.command.AlterTableCommandHandler;
import nts.uk.cnv.app.td.alteration.command.DropTableCommandHandler;
import nts.uk.cnv.app.td.schema.prospect.TableListProspectQuery;
import nts.uk.cnv.app.td.schema.prospect.TableProspectDto;
import nts.uk.cnv.app.td.schema.prospect.TableProspectQuery;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
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
	
	private static final Map<String, DevelopmentProgress> PROGRESS_MAP;
	static {
		PROGRESS_MAP = new HashMap<>();
		PROGRESS_MAP.put("not-accepted", DevelopmentProgress.notAccepted());
		PROGRESS_MAP.put("ordered", DevelopmentProgress.ordered());
		PROGRESS_MAP.put("delivered", DevelopmentProgress.deliveled());
	}

	@GET
	@Path("list/{progress}")
	public TableListProspect list(@PathParam("progress") String progress) {
		return tableListProspect.get(PROGRESS_MAP.get(progress));
	}

	@GET
	@Path("id/{tableId}/{progress}")
	public TableProspectDto definition(
			@PathParam("tableId") String tableId,
			@PathParam("progress") String progress) {
		return new TableProspectDto(
				tableProspect.get(tableId, PROGRESS_MAP.get(progress)).get());
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
