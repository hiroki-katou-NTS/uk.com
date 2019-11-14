package nts.uk.ctx.bs.employee.app.find.employment.dto;

import lombok.Data;

@Data
public class CommonMaterItemDto {

	private String commonMasterItemID;
	
    private String commonMasterItemName;

	public CommonMaterItemDto(String commonMasterItemID, String commonMasterItemName) {
		super();
		this.commonMasterItemID = commonMasterItemID;
		this.commonMasterItemName = commonMasterItemName;
	}
    
}
