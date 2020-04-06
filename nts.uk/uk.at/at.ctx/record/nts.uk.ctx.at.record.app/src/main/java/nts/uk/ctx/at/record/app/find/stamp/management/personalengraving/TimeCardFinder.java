package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.TimeCardDto;

/**
 * @author anhdt
 * 打刻入力のタイムカードを表示する
 */
public class TimeCardFinder {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingFinder;
	
	public TimeCardDto getTimeCard(String employeeId, GeneralDate date) {
		return new TimeCardDto(stampSettingFinder.getTimeCard(employeeId, date));
	}
}
