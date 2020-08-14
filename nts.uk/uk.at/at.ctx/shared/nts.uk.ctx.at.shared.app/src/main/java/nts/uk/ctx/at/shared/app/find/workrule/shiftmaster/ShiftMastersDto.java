package nts.uk.ctx.at.shared.app.find.workrule.shiftmaster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShiftMastersDto {

	private String companyId;

	private String shiftMasterCode;
	
	private String shiftMasterName;
	
	private String color;
	
	private String remark;
}
