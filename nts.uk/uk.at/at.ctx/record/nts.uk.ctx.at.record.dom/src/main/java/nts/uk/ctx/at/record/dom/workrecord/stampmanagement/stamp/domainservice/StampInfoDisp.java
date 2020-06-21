package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ReservationArt;

/**
 * VO : 表示する打刻情報
 * 
 * @author tutk
 *
 */
public class StampInfoDisp implements DomainValue {

	/**
	 * 打刻カード番号
	 */
	@Getter
	private final StampNumber stampNumber;

	/**
	 * 打刻日時
	 */
	@Getter
	private final GeneralDateTime stampDatetime;

	/**
	 * 打刻区分
	 */
	@Getter
	private final String stampAtr;

	/**
	 * 打刻
	 */
	@Getter
	private final List<Stamp> stamp;

	public StampInfoDisp(StampNumber stampNumber, GeneralDateTime stampDatetime, String stampAtr, List<Stamp> stamp) {
		super();
		this.stampNumber = stampNumber;
		this.stampDatetime = stampDatetime;
		this.stampAtr = stampAtr;
		this.stamp = stamp;
	}

}
