package nts.uk.cnv.dom.td.alteration.content;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangePK extends AlterationContent {
	private final List<String> columnNames;
	private final boolean clustred;

	public ChangePK(List<String> columnNames) {
		super(AlterationType.PRIMARY_KEY_CHANGE);
		this.columnNames = columnNames;
		this.clustred = true;
	}

	public ChangePK(List<String> columnNames, boolean clustred) {
		super(AlterationType.PRIMARY_KEY_CHANGE);
		this.columnNames = columnNames;
		this.clustred = clustred;
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
		return builder.pk(this.columnNames, this.clustred);
	}
}
