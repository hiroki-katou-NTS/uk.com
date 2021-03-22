package nts.uk.cnv.dom.td.alteration.content.column;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class AddColumn extends AlterationContent {
	private final String columnId;
	private final ColumnDesign column;

	public AddColumn(String columnId, ColumnDesign column) {
		super(AlterationType.COLUMN_ADD);
		this.columnId = columnId;
		this.column = column;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(!baseCol.isPresent()) {
				result.add(new AddColumn(alterdCol.getId(), alterdCol));
			}
		}
		return result;
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(!baseCol.isPresent()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.addColumn(alterationId, this.columnId, this.column);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return "ALTER TABLE " + tableDesign.getName().v()
				+ " ADD " + this.column.getName() + " "
				+ defineType.dataType(
						column.getType().getDataType(),
						column.getType().getLength(),
						column.getType().getScale())
				+ (column.getType().isNullable() ? " NULL" : " NOT NULL")
				+ (column.getType().getDefaultValue().isEmpty() ? "" : " DEFAULT " + column.getType().getDefaultValue())
				+ ";"
				+ (column.getComment().isEmpty()
						? ""
						: "\r\n" + defineType.columnCommentDdl(tableDesign.getName().v(), this.column.getName(), this.column.getJpName()));
	}
}
