package nts.uk.cnv.ws.table;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.app.td.command.TableDefinitionRegistCommand;
import nts.uk.cnv.app.td.command.TableDefinitionRegistCommandHandler;
import nts.uk.cnv.app.td.schema.prospect.TableListProspectQuery;
import nts.uk.cnv.app.td.schema.prospect.TableProspectQuery;
import nts.uk.cnv.app.td.service.TableDesignerService;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspect;
import nts.uk.cnv.dom.td.schema.prospect.list.TableListProspect;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.ws.table.column.ColumnDefinitionDto;
import nts.uk.cnv.ws.table.column.ColumnTypeDefinitionDto;

@Path("td/tables")
@Produces(MediaType.APPLICATION_JSON)
public class TableWebService {

	@Inject
	TableDesignerService tdService;

	@Inject
	TableDefinitionRegistCommandHandler handler;
	
	@Inject
	TableListProspectQuery tableListProspect;
	
	@Inject
	TableProspectQuery tableProspect;

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
