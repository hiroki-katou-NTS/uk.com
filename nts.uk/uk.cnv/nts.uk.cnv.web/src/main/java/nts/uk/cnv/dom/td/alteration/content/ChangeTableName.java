package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
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
		String result = "ALTER TABLE " + tableDesign.getName().v() + " RENAME " + this.tableName;
		result = result + createRenameTableConstraintsDdl(tableDesign);
		return result;
	}

	private String createRenameTableConstraintsDdl(TableDesign tableDesign) {
		StringBuilder sb = new StringBuilder();
		String tableName = tableDesign.getName().v();

		PrimaryKey pk = tableDesign.getConstraints().getPrimaryKey();
		String pkName = tableDesign.getName().pkName();
		sb.append("ALTER TABLE " + tableName + " DROP CONSTRAINT " + pkName + ";");
		sb.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + pkName);
		sb.append(" PRIMARY KEY");
		sb.append((pk.isClustered() ? " CLUSTERED " : " NONCLUSTERED "));
		sb.append("(" + String.join(",", tableDesign.getColumnNames(pk.getColumnIds())) + ");");

		for (UniqueConstraint uqConst : tableDesign.getConstraints().getUniqueConstraints()) {
			String ukName = tableDesign.getName().ukName(uqConst.getSuffix());
			sb.append("ALTER TABLE " + tableName + " DROP CONSTRAINT " + ukName + ";");
			sb.append("ALTER TABLE " + tableName + " ADD CONSTRAINT " + ukName);
			sb.append(" UNIQUE (" + String.join(",", tableDesign.getColumnNames(uqConst.getColumnIds())) + ");");
		}

		for (TableIndex index : tableDesign.getConstraints().getIndexes()) {
			String indexName = tableDesign.getName().indexName(index.getSuffix());
			sb.append("DROP INDEX " + indexName + " ON " + tableName + ";");
			sb.append("CREATE");
			sb.append((pk.isClustered() ? " CLUSTERED " : " NONCLUSTERED "));
			sb.append("INDEX " + indexName + " ON " + tableName);
			sb.append("(" + String.join(",", tableDesign.getColumnNames(index.getColumnIds())) + ");");
		}

		return sb.toString();
	}
}
