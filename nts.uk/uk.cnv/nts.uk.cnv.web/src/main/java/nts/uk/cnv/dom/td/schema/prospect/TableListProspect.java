package nts.uk.cnv.dom.td.schema.prospect;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TableListProspect {
	String alterationId;
	List<TableListItem> tableList;

}
