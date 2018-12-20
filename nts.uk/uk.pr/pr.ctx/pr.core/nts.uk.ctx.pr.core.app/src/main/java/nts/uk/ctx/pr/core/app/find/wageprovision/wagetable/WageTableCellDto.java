package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WageTableCellDto {

	private String columnKey;

	private Long value;

}
