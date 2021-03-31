package nts.uk.cnv.dom.td.alteration.content.column;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
@Getter
@ToString
public class ChangeColumnType extends AlterationContent {
	private final String columnId;
	private final DefineColumnType afterType;

	public ChangeColumnType(String columnId, DefineColumnType afterType) {
		super(AlterationType.COLUMN_TYPE_CHANGE);
		this.columnId = columnId;
		this.afterType = afterType;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		List<AlterationContent> result = new ArrayList<>();
		for(int i=0; i<altered.get().getColumns().size(); i++) {
			ColumnDesign alterdCol = altered.get().getColumns().get(i);
			Optional<ColumnDesign> baseCol = base.get().getColumns().stream()
					.filter(col -> col.getId().equals(alterdCol.getId()))
					.findFirst();
			if(baseCol.isPresent() && !baseCol.get().sameDesign(alterdCol)) {
				result.add(new ChangeColumnType(
						baseCol.get().getId(),
						new DefineColumnType(
							alterdCol.getType().getDataType(),
							alterdCol.getType().getLength(),
							alterdCol.getType().getScale(),
							alterdCol.getType().isNullable(),
							alterdCol.getType().getDefaultValue(),
							alterdCol.getType().getCheckConstraint()
						)));
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
			if(baseCol.isPresent() && !baseCol.get().getType().equals(alterdCol.getType())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.columnType(
				alterationId,
				this.columnId,
				this.afterType.getDataType(),
				this.afterType.getLength(),
				this.afterType.getScale(),
				this.afterType.isNullable(),
				this.afterType.getDefaultValue(),
				this.afterType.getCheckConstraint());
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		return "ALTER TABLE " + tableDesign.getName().v()
				+ " ALTER COLUMN " + tableDesign.getColumnName(this.columnId) + " "
				+ defineType.dataType(
						afterType.getDataType(),
						afterType.getLength(),
						afterType.getScale())
				+ (afterType.isNullable() ? " NULL " : " NOT NULL ")
				+ (afterType.getDefaultValue().isEmpty() ? "" : " DEFAULT " + afterType.getDefaultValue())
				+ ";";
	}
}
