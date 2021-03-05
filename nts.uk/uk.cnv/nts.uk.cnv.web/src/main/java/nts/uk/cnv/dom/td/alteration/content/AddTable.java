package nts.uk.cnv.dom.td.alteration.content;

import java.util.Optional;

import lombok.EqualsAndHashCode;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@EqualsAndHashCode(callSuper= false)
public class AddTable extends AlterationContent {
	private final TableDesign tableDesign;

	public AddTable(TableDesign tableDesign) {
		super(AlterationType.TABLE_CREATE);
		this.tableDesign = tableDesign;
	}

	public static AlterationContent create(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return new AddTable(altered.get());
	}

	public static boolean applicable(Optional<TableDesign> base, Optional<TableDesign> altered) {
		return (!base.isPresent() && altered.isPresent());
	}

	@Override
	public TableDesignBuilder apply(TableDesignBuilder builder) {
		return builder.add(tableDesign);
	}
}
