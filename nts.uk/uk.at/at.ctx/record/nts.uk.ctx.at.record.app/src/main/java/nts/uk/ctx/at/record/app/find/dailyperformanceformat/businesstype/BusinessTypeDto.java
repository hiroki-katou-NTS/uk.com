package nts.uk.ctx.at.record.app.find.dailyperformanceformat.businesstype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessTypeDto extends PeregDomainDto{

	// 開始日
	@PeregItem("IS00255")
	private GeneralDate startDate;
	
	//終了日
	@PeregItem("IS00256")
	private GeneralDate endDate;
	
	//勤務種別CD
	@PeregItem("IS00257")
	private String businessTypeCode;
	
}
