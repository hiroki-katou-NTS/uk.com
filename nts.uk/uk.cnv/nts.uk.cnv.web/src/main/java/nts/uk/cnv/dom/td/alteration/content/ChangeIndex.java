package nts.uk.cnv.dom.td.alteration.content;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class ChangeIndex extends AlterationContent {
	private final String indexName;
	private final List<String> columnNames;
	private final boolean clustred;
	private final boolean unique;

	public ChangeIndex(String indexName, List<String> columnNames, boolean unique) {
		super(AlterationType.INDEX_CHANGE);
		this.indexName = indexName;
		this.columnNames = columnNames;
		this.clustred = true;
		this.unique = unique;
	}

	public ChangeIndex(String indexName, List<String> columnNames, boolean clustred, boolean unique) {
		super(AlterationType.INDEX_CHANGE);
		this.indexName = indexName;
		this.columnNames = columnNames;
		this.clustred = clustred;
		this.unique = unique;
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
		return builder.index(this.indexName, this.columnNames, this.clustred, this.unique);
	}
}
