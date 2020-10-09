package nts.uk.ctx.at.request.app.find.application.gobackdirectly;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.worktime.predset.TimezoneUse;
import nts.uk.ctx.at.shared.dom.worktime.predset.UseSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 
 * @author hoangnd
 *
 */
public class TimeZoneUseDto {
	int useAtr;
	int workNo;
	int startTime;
	int endTime;

	public static TimeZoneUseDto fromDomain(TimezoneUse domain) {
		return new TimeZoneUseDto(domain.getUseAtr().value, domain.getWorkNo(), domain.getStart().v(),
				domain.getEnd().v());
	}
	
	public TimezoneUse toDomain() {
		return new TimezoneUse(
				new TimeWithDayAttr(startTime), 
				new TimeWithDayAttr(endTime), 
				EnumAdaptor.valueOf(useAtr, UseSetting.class), 
				workNo);
	}
}
