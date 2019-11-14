package nts.uk.ctx.bs.employee.app.find.employment.dto;

import lombok.Data;

@Data
public class CommonMaterItemDto {

	private String commonMasterItemCode;
	
    private String commonMasterItemName;

	public CommonMaterItemDto(String commonMasterItemCode, String commonMasterItemName) {
		super();
		this.commonMasterItemCode = commonMasterItemCode;
		this.commonMasterItemName = commonMasterItemName;
	}
    
}
