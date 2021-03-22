package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangeTableName extends AlterationContent {

	@Getter
	private final String tableName;

	public ChangeTableName(String tableName) {
		super(AlterationType.TABLE_NAME_CHANGE);
		this.tableName = tableName;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList(new ChangeTableName(altered.get().getName().v()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return (base.isPresent() && altered.isPresent() && !base.get().getName().equals(altered.get().getName()));
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.tableName(alterationId, tableName);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		String result = ""
				+ "ALTER TABLE " + tableDesign.getName().v() + " RENAME " + this.tableName + ";\r\n"
				+ createRenameTableConstraintsDdl(tableDesign, new TableName(this.tableName));
		return result;
	}

	private String createRenameTableConstraintsDdl(TableDesign tableDesign, TableName newTableName) {
		StringBuilder sb = new StringBuilder();
		String tableName = newTableName.v();

		PrimaryKey pk = tableDesign.getConstraints().getPrimaryKey();
		String pkName = newTableName.pkName();
		sb.append("ALTER TABLE " + tableName + " DROP CONSTRAINT " + pkName + ";\r\n");
		sb.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + pkName);
		sb.append(" PRIMARY KEY");
		sb.append((pk.isClustered() ? " CLUSTERED " : " NONCLUSTERED "));
		sb.append("(" + String.join(",", tableDesign.getColumnNames(pk.getColumnIds())) + ");\r\n");

		for (UniqueConstraint uqConst : tableDesign.getConstraints().getUniqueConstraints()) {
			String ukName = newTableName.ukName(uqConst.getSuffix());
			sb.append("ALTER TABLE " + tableName + " DROP CONSTRAINT " + ukName + ";\r\n");
			sb.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + ukName);
			sb.append(" UNIQUE (" + String.join(",", tableDesign.getColumnNames(uqConst.getColumnIds())) + ");");
		}

		for (TableIndex index : tableDesign.getConstraints().getIndexes()) {
			String indexName = newTableName.indexName(index.getSuffix());
			sb.append("DROP INDEX " + indexName + " ON " + tableName + ";\\r\\n");
			sb.append("CREATE");
			sb.append((pk.isClustered() ? " CLUSTERED " : " NONCLUSTERED "));
			sb.append("INDEX " + indexName + " ON " + tableName);
			sb.append("(" + String.join(",", tableDesign.getColumnNames(index.getColumnIds())) + ");");
		}

		return sb.toString();
	}
}
