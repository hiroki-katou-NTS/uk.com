package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;

/**
 * VO : 日時期間
 * 
 * @author tutk
 *
 */
public class DateAndTimePeriod implements DomainValue {

	/**
	 * 開始日時
	 */
	@Getter
	private final GeneralDateTime statDateTime;

	/**
	 * 終了日時
	 */
	@Getter
	private final GeneralDateTime endDateTime;

	public DateAndTimePeriod(GeneralDateTime statDateTime, GeneralDateTime endDateTime) {
		super();
		this.statDateTime = statDateTime;
		this.endDateTime = endDateTime;
	}

}
