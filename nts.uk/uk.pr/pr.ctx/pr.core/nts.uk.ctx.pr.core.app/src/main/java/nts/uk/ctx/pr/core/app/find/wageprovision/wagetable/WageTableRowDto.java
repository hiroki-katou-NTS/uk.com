package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WageTableRowDto {

	private String rowId;
	
	private String element;

	private Set<WageTableCellDto> cellData;

}
