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
import nts.uk.cnv.dom.td.schema.tabledesign.column.DefineColumnType;

@EqualsAndHashCode(callSuper= false)
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
			if(baseCol.isPresent() && diff(baseCol.get(), alterdCol)) {
				result.add(new ChangeColumnType(
						baseCol.get().getId(),
						new DefineColumnType(
							alterdCol.getType().getDataType(),
							alterdCol.getType().getLength(),
							alterdCol.getType().getScale(),
							alterdCol.getType().isNullable(),
							alterdCol.getType().getDefaultValue(),
							alterdCol.getType().getCheckConstaint()
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
			if(baseCol.isPresent() && diff(baseCol.get(), alterdCol)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.columnType(
				alterationId,
				this.columnId,
				this.afterType.getDataType(),
				this.afterType.getLength(),
				this.afterType.getScale(),
				this.afterType.isNullable());
	}

	private static boolean diff(ColumnDesign baseCol, ColumnDesign alterdCol) {
		return !baseCol.getType().getDataType().equals(alterdCol.getType().getDataType()) ||
				baseCol.getType().getLength() != alterdCol.getType().getLength() ||
				baseCol.getType().getScale() != alterdCol.getType().getScale() ||
				baseCol.getType().isNullable() != alterdCol.getType().isNullable() ||
				!baseCol.getType().getDefaultValue().equals(alterdCol.getType().getDefaultValue()) ||
				!baseCol.getType().getCheckConstaint().equals(alterdCol.getType().getCheckConstaint());
	}
}
