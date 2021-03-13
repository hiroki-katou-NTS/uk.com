package nts.uk.cnv.dom.td.alteration.content;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class RemoveTable extends AlterationContent {

	public RemoveTable() {
		super(AlterationType.TABLE_DROP);
	}

	public static List<AlterationContent> create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return Arrays.asList(new RemoveTable());
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return (base.isPresent() && !altered.isPresent());
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.remove();
	}
}
