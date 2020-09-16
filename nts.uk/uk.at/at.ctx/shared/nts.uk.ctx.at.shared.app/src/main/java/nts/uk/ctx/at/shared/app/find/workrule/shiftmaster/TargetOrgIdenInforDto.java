package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetOrgIdenInforDto {
	private int unit;
	
	private String workplaceId;

	private String workplaceGroupId;
}
