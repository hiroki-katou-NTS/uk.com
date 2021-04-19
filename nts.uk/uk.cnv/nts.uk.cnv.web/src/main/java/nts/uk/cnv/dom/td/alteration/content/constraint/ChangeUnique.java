package nts.uk.cnv.dom.td.alteration.content.constraint;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.schema.prospect.definition.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@Getter
@EqualsAndHashCode(callSuper= false)
@ToString
public class ChangeUnique extends AlterationContent {
	private final String suffix;
	private final List<String> columnIds;
	private final boolean clustred;
	private final boolean deleted;

	public ChangeUnique(String suffix, List<String> columnIds, boolean clustred, boolean deleted) {
		super(AlterationType.UNIQUE_KEY_CHANGE);
		this.suffix = suffix;
		this.columnIds = columnIds;
		this.clustred = clustred;
		this.deleted = deleted;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		return ChangeIndexHelper.create(
				base,
				altered,
				c -> c.getUniqueConstraints(),
				(e, isDel) -> new ChangeUnique(e.getSuffix(), e.getColumnIds(), e.isClustered(), isDel));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {

		return ChangeIndexHelper.applicable(base, altered, c -> c.getUniqueConstraints());
	}

	@Override
	public void apply(String alterationId, TableProspectBuilder builder) {
		builder.unique(alterationId, this.suffix, this.columnIds, this.clustred);
	}

	@Override
	public String createAlterDdl(TableDesign tableDesign, TableDefineType defineType) {
		UniqueConstraint uqConst = tableDesign.getConstraints().getUniqueConstraints().stream()
			.filter(uk -> uk.getSuffix().equals(this.suffix))
			.findFirst()
			.get();
		val columnNames = columnIds.stream()
				.map(colId -> tableDesign.getColumns().stream()
						.filter(cd -> cd.getId().equals(colId))
						.findFirst()
						.get()
						.getName())
				.collect(Collectors.toList());

		String delUk = "ALTER TABLE " + tableDesign.getName().v()
				+ " DROP CONSTRAINT " + tableDesign.getName().ukName(uqConst.getSuffix()) + ";\r\n";

		if(this.deleted) {
			return delUk;
		}

		return delUk
				+ "ALTER TABLE " + tableDesign.getName().v()
				+ " ADD CONSTRAINT " + tableDesign.getName().ukName(this.suffix)
				+ " UNIQUE (" + String.join(",", columnNames) + ");\r\n";
	}
}
