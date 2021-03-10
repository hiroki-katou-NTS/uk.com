package nts.uk.cnv.dom.td.alteration.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.DefineColumnType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnType extends AlterationContent {
	private final String columnId;
	private final DefineColumnType afterType;

	public ChangeColumnType(String columnId, DefineColumnType afterType) {
		super(AlterationType.COLUMN_TYPE_CHANGE);
		this.columnId = columnId;
		this.afterType = afterType;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
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
							alterdCol.getType(),
							alterdCol.getMaxLength(),
							alterdCol.getScale(),
							alterdCol.isNullable(),
							alterdCol.getDefaultValue(),
							alterdCol.getCheck()
						)));
			}
		}
		return result;
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
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
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.columnType(
				this.columnId,
				this.afterType.getType(),
				this.afterType.getLength(),
				this.afterType.getScale(),
				this.afterType.isNullable());
	}

	private static boolean diff(ColumnDesign baseCol, ColumnDesign alterdCol) {
		return !baseCol.getType().equals(alterdCol.getType()) ||
				baseCol.getMaxLength() != alterdCol.getMaxLength() ||
				baseCol.getScale() != alterdCol.getScale() ||
				baseCol.isNullable() != alterdCol.isNullable() ||
				!baseCol.getDefaultValue().equals(alterdCol.getDefaultValue()) ||
				!baseCol.getCheck().equals(alterdCol.getCheck());
	}
}
