package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnJpName extends AlterationContent {
	private final String columnName;
	private final String jpName;

	public ChangeColumnJpName(String columnName, String jpName) {
		super(AlterationType.COLUMN_JPNAME_CHANGE);
		this.columnName = columnName;
		this.jpName = jpName;
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
		return builder.columnJpName(
				this.columnName,
				this.jpName);
	}
}
