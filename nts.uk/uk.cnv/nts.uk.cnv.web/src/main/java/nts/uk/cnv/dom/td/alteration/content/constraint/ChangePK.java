package nts.uk.cnv.dom.td.alteration.content.constraint;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@EqualsAndHashCode(callSuper= false)
public class ChangePK extends AlterationContent {
	private final List<String> columnIds;
	private final boolean clustred;

	public ChangePK(List<String> columnIds, boolean clustred) {
		super(AlterationType.PRIMARY_KEY_CHANGE);
		this.columnIds = columnIds;
		this.clustred = clustred;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		val pk = altered.get().getConstraints().getPrimaryKey();
		return Arrays.asList(new ChangePK(pk.getColumnIds(), pk.isClustered()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		if(!base.isPresent() || !altered.isPresent()) {
			return false;
		}

		val basePk = base.get().getConstraints().getPrimaryKey();
		val alteredPk = altered.get().getConstraints().getPrimaryKey();

		return !basePk.equals(alteredPk);
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.pk(alterationId, this.columnIds, this.clustred);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		TableName tableName = tableDesign.getName();
		return "ALTER TABLE " + tableName.v()
				+ " DROP CONSTRAINT " + tableName.pkName() + ";\r\n"
				+ "ALTER TABLE " + tableName.v()
				+ " ADD CONSTRAINT " + tableName.pkName()
				+ " PRIMARY KEY"
				+ (this.clustred ? " CLUSTERED " : " NONCLUSTERED ")
				+ "(" + String.join(",", this.columnIds) + ");\r\n";
	}
}
