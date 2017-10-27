package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.math.BigDecimal;
import java.util.List;

import lombok.Value;

@Value
public class WokplaceSpecificDateDto {
	public String workPlaceId;
	public BigDecimal specificDate;
	public List<BigDecimal> specificDateItemNo;
}
