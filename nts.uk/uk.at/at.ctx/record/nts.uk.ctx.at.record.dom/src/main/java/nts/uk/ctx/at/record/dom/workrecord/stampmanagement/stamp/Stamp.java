package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.objecttype.DomainAggregate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.stamp.management.StampType;

/**
 * AR : 打刻
 * @author tutk
 *
 */

public class Stamp implements DomainAggregate {
	
	/**
	 * 打刻カード番号
	 */
	@Getter
	private final StampNumber cardNumber;

	/**
	 * 打刻日時
	 */
	@Getter
	private final GeneralDateTime stampDateTime;
	
	/**
	 * 打刻する方法
	 */
	@Getter
	private final Relieve relieve;
	
	/**
	 * 打刻種類
	 */
	@Getter
	private final StampType type; 
	
	
	/**
	 * 実績への反映内容
	 */
	@Getter
	private final RefectActualResult refActualResults; 
	
	/**
	 * 反映済み区分
	 */
	@Getter
	private  boolean reflectedCategory;
	
	/**
	 * 打刻場所情報
	 */
	@Getter
	private final Optional<StampLocationInfor> locationInfor;

	public Stamp(StampNumber cardNumber, GeneralDateTime stampDateTime, Relieve relieve, StampType type,
			RefectActualResult refActualResults, boolean reflectedCategory,
			StampLocationInfor locationInfor) {
		super();
		this.cardNumber = cardNumber;
		this.stampDateTime = stampDateTime;
		this.relieve = relieve;
		this.type = type;
		this.refActualResults = refActualResults;
		this.reflectedCategory = reflectedCategory;
		this.locationInfor = Optional.of(locationInfor);
	} 
}
