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
import nts.uk.cnv.app.td.service.TableDesignerService;
import nts.uk.cnv.dom.td.schema.prospect.TableListProspect;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;
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

	@GET
	@Path("list")
	public TableListProspect list() {
		return tableListProspect.get();
	}

	@GET
	@Path("{name}")
	public TableDefinitionDto definition(@PathParam("name") String name) {
		List<ColumnDefinitionDto> columns = Arrays.asList(
				new ColumnDefinitionDto(
						"",
						"CONTRACT_CD",
						"契約コード",
						new ColumnTypeDefinitionDto(false, DataType.CHAR, 12, 0, "", ""),
						"けいやく\nコード\nだよ"),
				new ColumnDefinitionDto(
						"",
						"CID",
						"会社コード",
						new ColumnTypeDefinitionDto(true, DataType.VARCHAR, 17, 0, "", ""),
						"")
				);
		return new TableDefinitionDto(new TableInfoDto("", name), columns);
	}

	@POST
	@Path("regist")
	public void regist(TableDefinitionRegistCommand command) {
		handler.handle(command);
	}
}
