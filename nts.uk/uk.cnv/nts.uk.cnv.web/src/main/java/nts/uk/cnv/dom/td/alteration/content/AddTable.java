package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class AddTable extends AlterationContent {
	
	@Getter
	private final TableDesign tableDesign;

	public AddTable(TableDesign tableDesign) {
		super(AlterationType.TABLE_CREATE);
		this.tableDesign = tableDesign;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList(new AddTable(altered.get()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return (!base.isPresent() && altered.isPresent());
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.add(alterationId, tableDesign);
	}

	@Override
	public String createAlterDdl(Require require, TableDesign tableDesign, TableDefineType defineType) {
		return this.tableDesign.createFullTableSql(defineType);
	}
}
