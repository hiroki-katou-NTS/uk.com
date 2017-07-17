package nts.uk.ctx.at.schedule.app.command.shift.specificdayset.company;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Value;
import nts.uk.ctx.at.schedule.dom.shift.specificdayset.company.CompanySpecificDateItem;
import nts.uk.shr.com.context.AppContexts;

@Value
public class CompanySpecificDateCommand {

	private BigDecimal specificDate;

	private BigDecimal specificDateItemNo;
	
	
//	public CompanySpecificDateItem toDomain() {
//		return CompanySpecificDateItem.createFromJavaType(AppContexts.user().companyId(), this.specificDate,this.specificDateItemNo,"");
//	}

}
