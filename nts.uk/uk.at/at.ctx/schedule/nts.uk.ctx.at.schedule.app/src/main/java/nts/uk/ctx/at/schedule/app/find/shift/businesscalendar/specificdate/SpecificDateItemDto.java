package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.specificdate;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class SpecificDateItemDto {

	public String timeItemId;
	
	public int useAtr;
	
	public BigDecimal specificDateItemNo;
	
	public String specificName;

}
