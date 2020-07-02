package nts.uk.ctx.at.record.app.find.stamp.management.personalengraving;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeCard;

/**
 * @author anhdt
 * 打刻入力のタイムカードを表示する
 */
@Stateless
public class TimeCardFinder {
	
	@Inject
	private StampSettingsEmbossFinder stampSettingFinder;
	
	public TimeCard getTimeCard(String employeeId, GeneralDate date) {
		return stampSettingFinder.getTimeCard(employeeId, date);
	}
}
