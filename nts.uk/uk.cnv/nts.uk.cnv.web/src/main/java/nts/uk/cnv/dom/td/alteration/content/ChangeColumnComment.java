package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeColumnComment extends AlterationContent {
	private final String columnName;
	private final String comment;

	public ChangeColumnComment(String columnName, String comment) {
		super(AlterationType.COLUMN_COMMENT_CHANGE);
		this.columnName = columnName;
		this.comment = comment;
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
		return builder.columnComment(
				this.columnName,
				this.comment);
	}
}
