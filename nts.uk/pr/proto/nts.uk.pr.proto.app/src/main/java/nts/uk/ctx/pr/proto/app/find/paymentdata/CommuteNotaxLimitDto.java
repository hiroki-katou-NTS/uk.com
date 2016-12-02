package nts.uk.ctx.pr.proto.app.find.paymentdata;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.paymentdata.CommuNotaxLimit;

@Value
public class CommuteNotaxLimitDto {
	
	private String commuNotaxLimitCode;
	
	
	private String commuNotaxLimitName;
	
	
	private int commuNotaxLimitValue;
	
	public static CommuteNotaxLimitDto fromDomain(CommuNotaxLimit domain){
		return new CommuteNotaxLimitDto(domain.getCommuNotaxLimitCode().v(), 
										domain.getCommuNotaxLimitName().v(), 
										domain.getCommuNotaxLimitValue().v());
	}
}
