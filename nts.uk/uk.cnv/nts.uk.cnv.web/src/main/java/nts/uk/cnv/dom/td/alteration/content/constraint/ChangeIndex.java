package nts.uk.cnv.dom.td.alteration.content.constraint;

import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@Getter
@EqualsAndHashCode(callSuper= false)
@ToString
public class ChangeIndex extends AlterationContent {
	private final String suffix;
	private final List<String> columnIds;
	private final boolean clustred;
	private final boolean deleted;

	public ChangeIndex(String suffix, List<String> columnIds, boolean clustred, boolean deleted) {
		super(AlterationType.INDEX_CHANGE);
		this.suffix = suffix;
		this.columnIds = columnIds;
		this.clustred = clustred;
		this.deleted = deleted;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return ChangeIndexHelper.create(
				base,
				altered,
				c -> c.getIndexes(),
				(e, isDel) -> new ChangeIndex(e.getSuffix(), e.getColumnIds(), e.isClustered(), isDel));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		return ChangeIndexHelper.applicable(base, altered, c -> c.getIndexes());
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		if(this.deleted) {
			builder.delIndex(alterationId, this.suffix);
			return;
		}

		builder.index(
				alterationId,
				this.suffix, this.columnIds, this.clustred);
		return;
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		TableName tableName = tableDesign.getName();
		TableIndex index = tableDesign.getConstraints().getIndexes().stream()
				.filter(idx -> idx.getSuffix().equals(this.suffix))
				.findFirst()
				.get();
		String indexName = tableName.indexName(index.getSuffix());

		String delIndex = "DROP INDEX " + indexName + " ON " + tableName.v()+ ";\r\n";

		if(this.deleted) {
			return delIndex;
		}

		return delIndex
				+ " CREATE"
				+ (this.clustred ? " CLUSTERED" : " NONCLUSTERED")
				+ " INDEX " + tableName.indexName(this.suffix)
				+ " ON " + tableName.v()
				+ "(" + String.join(",", this.columnIds) + ");\r\n";
	}

}
