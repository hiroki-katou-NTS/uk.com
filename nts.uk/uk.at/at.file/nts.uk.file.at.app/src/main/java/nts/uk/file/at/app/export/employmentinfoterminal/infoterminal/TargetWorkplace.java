package nts.uk.file.at.app.export.employmentinfoterminal.infoterminal;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class TargetWorkplace {
	
	private String workplaceId;
	
	private String workplaceCode;
	
	private String workplaceName;
	
	public TargetWorkplace() {
		this.workplaceCode = "ー";
		this.workplaceName = "ー";
	}
}
