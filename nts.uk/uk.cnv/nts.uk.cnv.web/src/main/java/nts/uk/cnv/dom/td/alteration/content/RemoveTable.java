package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@Getter
@EqualsAndHashCode(callSuper= false)
@ToString
public class RemoveTable extends AlterationContent {

	public RemoveTable() {
		super(AlterationType.TABLE_DROP);
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList(new RemoveTable());
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return (base.isPresent() && !altered.isPresent());
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.remove(alterationId);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return "DROP TABLE "+ tableDesign.getName().v() + ";";
	}
}
