package dto;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;


@Data
public class CheckListPrintSettingDto {	
	private Boolean showCategoryInsuranceItem;
	private Boolean showDeliveryNoticeAmount;
	private Boolean showDetail;
	private Boolean showOffice;
	public CheckListPrintSettingDto(){		
	}
}
