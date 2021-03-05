package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledefinetype.DefineColumnType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnType extends AlterationContent {
	private final String columnName;
	private final DefineColumnType afterType;

	public ChangeColumnType(String columnName, DefineColumnType afterType) {
		super(AlterationType.COLUMN_TYPE_CHANGE);
		this.columnName = columnName;
		this.afterType = afterType;
	}

	public static AlterationContent create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		// TODO:
		return null;
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		// TODO:
		return false;
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.columnType(
				this.columnName,
				this.afterType.getType(),
				this.afterType.getLength(),
				this.afterType.getScale(),
				this.afterType.isNullable());
	}
}
