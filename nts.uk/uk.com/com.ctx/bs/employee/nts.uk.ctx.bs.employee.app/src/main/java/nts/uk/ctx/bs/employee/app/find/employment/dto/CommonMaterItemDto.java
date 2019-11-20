package nts.uk.ctx.bs.employee.app.find.employment.dto;

import lombok.Data;

@Data
public class CommonMaterItemDto {

	private String commonMasterItemID;
	
    private String commonMasterItemName;
    
    private String commonMasterItemCD;

	public CommonMaterItemDto(String commonMasterItemID, String commonMasterItemName , String commonMasterItemCD) {
		super();
		this.commonMasterItemID = commonMasterItemID;
		this.commonMasterItemName = commonMasterItemName;
		this.commonMasterItemCD = commonMasterItemCD ;
	}
    
}
