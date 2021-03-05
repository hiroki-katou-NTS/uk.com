package nts.uk.cnv.ws.table;

import static java.util.stream.Collectors.*;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.cnv.dom.td.tabledefinetype.DataType;
import nts.uk.cnv.ws.table.column.ColumnDefinitionDto;
import nts.uk.cnv.ws.table.column.ColumnTypeDefinitionDto;

@Path("td/tables")
@Produces(MediaType.APPLICATION_JSON)
public class TableWebService {

	@GET
	@Path("list")
	public List<TableInfoDto> list() {
		return Arrays.asList(
				"BCMMT_COMPANY",
				"BSYMT_JOB_HIST",
				"KFNMT_ALEX_DATA",
				"KSCDT_AVAILABILITY",
				"KSCMT_ALCHK_CONSECUTIVE_WKTM_ORG_DTL",
				"KSHMT_MON_ITEM_CONTROL",
				"SPTDT_INFO_MESSAGE_TGT"
				).stream()
				.map(name -> new TableInfoDto("", name, ""))
				.collect(toList());
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
		return new TableDefinitionDto(new TableInfoDto("", name, ""), columns);
	}
}
