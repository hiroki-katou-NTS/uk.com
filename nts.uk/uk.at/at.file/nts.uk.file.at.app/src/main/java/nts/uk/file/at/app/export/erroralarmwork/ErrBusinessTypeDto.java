package nts.uk.file.at.app.export.erroralarmwork;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeName;


@Data
@Builder
public class ErrBusinessTypeDto {

	
	private String companyId;
	
	private BusinessTypeCode businessTypeCode;
	
	private BusinessTypeName businessTypeName;
}
