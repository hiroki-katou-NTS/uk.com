package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class AddColumn extends AlterationContent {
	private final String columnName;
	private final ColumnDesign column;

	public AddColumn(String columnName, ColumnDesign column) {
		super(AlterationType.COLUMN_ADD);
		this.columnName = columnName;
		this.column = column;
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
		return builder.addColumn(
				this.columnName,
				this.column);
	}
}
