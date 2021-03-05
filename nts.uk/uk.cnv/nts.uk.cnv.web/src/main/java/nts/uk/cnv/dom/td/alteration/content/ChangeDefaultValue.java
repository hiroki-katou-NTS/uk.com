package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeDefaultValue extends AlterationContent {
	private final String columnName;
	private final String defaultValue;

	public ChangeDefaultValue(String columnName, String defaultValue) {
		super(AlterationType.COLUMN_DEFAULT_VALUE_CHANGE);
		this.columnName = columnName;
		this.defaultValue = defaultValue;
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
		return builder.columnDefaultValue(
				this.columnName,
				this.defaultValue);
	}
}
