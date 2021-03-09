package nts.uk.cnv.dom.td.alteration.content;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnName extends AlterationContent {
	private final String columnName;
	private final String afterName;

	public ChangeColumnName(String columnName, String afterName) {
		super(AlterationType.COLUMN_NAME_CHANGE);
		this.columnName = columnName;
		this.afterName = afterName;
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return new ArrayList<>();
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return false;
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.columnName(
				this.columnName,
				this.afterName);
	}
}
