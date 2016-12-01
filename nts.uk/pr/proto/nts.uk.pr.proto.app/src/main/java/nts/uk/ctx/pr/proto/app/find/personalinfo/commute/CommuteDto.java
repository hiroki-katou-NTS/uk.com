package nts.uk.ctx.pr.proto.app.find.personalinfo.commute;

import lombok.Value;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;

@Value
public class CommuteDto {
	Double commuNotaxLimitPubNo;
	
	public static CommuteDto fromDomain(PersonalCommuteFee domain){
		return new CommuteDto(domain.getCommuteNoTaxLimitPublishNo().doubleValue());
	}
}
