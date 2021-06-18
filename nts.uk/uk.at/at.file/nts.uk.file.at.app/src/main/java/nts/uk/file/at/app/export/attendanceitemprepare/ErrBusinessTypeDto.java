package nts.uk.file.at.app.export.attendanceitemprepare;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.shared.dom.employeeworkway.businesstype.BusinessTypeName;
import nts.uk.ctx.at.shared.dom.workrule.businesstype.BusinessTypeCode;


@Data
@Builder
public class ErrBusinessTypeDto {

	
	private String companyId;
	
	private BusinessTypeCode businessTypeCode;
	
	private BusinessTypeName businessTypeName;
}
