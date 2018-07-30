package nts.uk.ctx.at.shared.app.find.specialholiday.grantinformation;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.FixGrantDate;

@Value
public class FixGrantDateDto {
	/** 周期*/
	private int interval;
	
	/** 固定付与日数 */
	private int grantDays;

	public static FixGrantDateDto fromDomain(FixGrantDate fixGrantDate) {
		if(fixGrantDate == null) {
			return null;
		}
		return new FixGrantDateDto(fixGrantDate.getInterval().v(), fixGrantDate.getGrantDays().v());
	}
}
