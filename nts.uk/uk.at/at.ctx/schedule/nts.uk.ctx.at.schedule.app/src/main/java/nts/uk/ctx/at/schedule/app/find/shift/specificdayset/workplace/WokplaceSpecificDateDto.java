package nts.uk.ctx.at.schedule.app.find.shift.specificdayset.workplace;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
@AllArgsConstructor
public class WokplaceSpecificDateDto {
	public String workPlaceId;
	public GeneralDate specificDate;
	public List<Integer> specificDateItemNo;
}
