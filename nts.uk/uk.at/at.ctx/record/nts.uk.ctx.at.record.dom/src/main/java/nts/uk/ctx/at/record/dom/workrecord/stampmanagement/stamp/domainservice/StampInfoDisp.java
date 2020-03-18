package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.ReservationArt;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;

/**
 * VO : 表示する打刻情報
 * 
 * @author tutk
 *
 */
@Value
public class StampInfoDisp implements DomainValue {

	/**
	 * 打刻カード番号
	 */
	private final StampNumber stampNumber;

	/**
	 * 打刻日時
	 */
	private final GeneralDateTime stampDatetime;

	/**
	 * 打刻区分
	 */
	private final String stampAtr;

	/**
	 * 打刻
	 */
	private final Optional<Stamp> stamp;

	public StampInfoDisp(StampNumber stampNumber, GeneralDateTime stampDatetime, String stampAtr, Stamp stamp) {
		super();
		this.stampNumber = stampNumber;
		this.stampDatetime = stampDatetime;
		this.stampAtr = stampAtr;
		this.stamp = Optional.ofNullable(stamp);
	}
	
	/**
	 * 	[C-1] 打刻区分を作成する
	 * @param stampNumber
	 * @param stampDatetime
	 * @param stampRecord
	 * @param stamp
	 */
	public StampInfoDisp(StampNumber stampNumber, GeneralDateTime stampDatetime, StampRecord stampRecord, Optional<Stamp> stamp) {
		super();
		this.stampNumber = stampNumber;
		this.stampDatetime = stampDatetime;
		this.stampAtr = createStamp( stampRecord , stamp);
		this.stamp = stamp;
	}
	
	/**
	 * [prv-1] 打刻区分を作成する
	 * @param StampRecord
	 * @param stamp
	 * @return
	 */
	private String createStamp(StampRecord StampRecord, Optional<Stamp> stamp) {
		if (!stamp.isPresent()) {
			return StampRecord.getRevervationAtr().nameId;
		}
		String stampTypeDisplayed = stamp.get().getType().createStampTypeDisplay();
		boolean stampArt = StampRecord.isStampArt();
		ReservationArt revervationAtr = StampRecord.getRevervationAtr();

		if (stampArt && revervationAtr == ReservationArt.NONE) {
			return stampTypeDisplayed;
		} else if (stampArt && revervationAtr != ReservationArt.NONE) {
			return stampTypeDisplayed + "+" + revervationAtr.nameId;
		} else if (!stampArt && revervationAtr != ReservationArt.NONE) {
			return revervationAtr.nameId;
		}
		//TH này k xảy ra.
		return null;
	}

}
