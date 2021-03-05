package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class RemoveTable extends AlterationContent {
	private final String tableName;

	public RemoveTable(String tableName) {
		super(AlterationType.TABLE_DROP);
		this.tableName = tableName;
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
		return builder.remove(this.tableName);
	}
}
