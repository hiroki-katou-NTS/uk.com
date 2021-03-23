package nts.uk.cnv.dom.td.alteration.content.column;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
@Getter
public class RemoveColumn extends AlterationContent {
	private final String columnId;

	public RemoveColumn(String columnId) {
		super(AlterationType.COLUMN_DELETE);
		this.columnId = columnId;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> retult = new ArrayList<>();
		for(int i=0; i<base.get().getColumns().size(); i++) {
			ColumnDesign baseCol = base.get().getColumns().get(i);
			Optional<ColumnDesign> alteredCol = altered.get().getColumns().stream()
					.filter(col -> col.getId().equals(baseCol.getId()))
					.findFirst();
			if(!alteredCol.isPresent()) {
				retult.add(new RemoveColumn(baseCol.getId()));
			}
		}
		return retult;
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		for(int i=0; i<base.get().getColumns().size(); i++) {
			ColumnDesign baseCol = base.get().getColumns().get(i);
			Optional<ColumnDesign> alteredCol = altered.get().getColumns().stream()
					.filter(col -> col.getId().equals(baseCol.getId()))
					.findFirst();
			if(!alteredCol.isPresent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.removeColumn(alterationId, this.columnId);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return "ALTER TABLE " + tableDesign.getName().v()
				+ " DROP COLUMN " + tableDesign.getColumnName(this.columnId) + ";\r\n";
	}
}
