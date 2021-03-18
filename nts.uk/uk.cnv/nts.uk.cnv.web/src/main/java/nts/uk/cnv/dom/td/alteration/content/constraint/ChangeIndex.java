package nts.uk.cnv.dom.td.alteration.content.constraint;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangeIndex extends AlterationContent {
	private final String suffix;
	private final List<String> columnIds;
	private final boolean clustred;

	public ChangeIndex(String suffix, List<String> columnIds, boolean clustred) {
		super(AlterationType.INDEX_CHANGE);
		this.suffix = suffix;
		this.columnIds = columnIds;
		this.clustred = clustred;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		return ChangeIndexHelper.create(
				base,
				altered,
				c -> c.getIndexes(),
				e -> new ChangeIndex(e.getSuffix(), e.getColumnIds(), e.isClustered()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		return ChangeIndexHelper.applicable(base, altered, c -> c.getIndexes());
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.index(
				alterationId,
				this.suffix, this.columnIds, this.clustred);
	}

	@Override
	public String createAlterDdl(Require require, TableDesign tableDesign, TableDefineType defineType) {
		TableName tableName = tableDesign.getName();
		TableIndex index = tableDesign.getConstraints().getIndexes().stream()
				.filter(idx -> idx.getSuffix().equals(this.suffix))
				.findFirst()
				.get();
		String indexName = tableName.indexName(index.getSuffix());
		return "DROP INDEX " + indexName + " ON " + tableName.v() + ";\r\n"
				+ " CREATE"
				+ (this.clustred ? " CLUSTERED" : " NONCLUSTERED")
				+ " INDEX " + tableName.indexName(this.suffix)
				+ " ON " + tableName.v()
				+ "(" + String.join(",", this.columnIds) + ");\r\n";
	}

}
