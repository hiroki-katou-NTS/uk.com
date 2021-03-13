package nts.uk.cnv.dom.td.alteration.content;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.tabledesign.TableDesignBuilder;

@Getter
@AllArgsConstructor
public abstract class AlterationContent {
	AlterationType type;

	public abstract TableDesignBuilder apply(TableDesignBuilder builder);
}
