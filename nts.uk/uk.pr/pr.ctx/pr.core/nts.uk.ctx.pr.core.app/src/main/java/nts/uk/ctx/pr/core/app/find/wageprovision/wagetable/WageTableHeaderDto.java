package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class WageTableHeaderDto {

	private String headerText;

	private String key;
	
	private String dataType;

	private String width;

	private boolean hidden;
	
	private String ntsControl;

}
