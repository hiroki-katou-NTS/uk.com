package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.schema.prospect.TableProspectBuilder;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

@EqualsAndHashCode(callSuper= false)
public class ChangeTableName extends AlterationContent {
	private final String tableName;

	public ChangeTableName(String tableName) {
		super(AlterationType.TABLE_NAME_CHANGE);
		this.tableName = tableName;
	}

	public static List<AlterationContent> create(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList(new ChangeTableName(altered.get().getName()));
	}

	public static boolean applicable(Optional<? extends TableDesign> base, Optional<TableDesign> altered) {
		return (base.isPresent() && altered.isPresent() && !base.get().getName().equals(altered.get().getName()));
	}

	@Override
	public TableProspectBuilder apply(String alterationId, TableProspectBuilder builder) {
		return builder.tableName(alterationId, tableName);

	}
}
