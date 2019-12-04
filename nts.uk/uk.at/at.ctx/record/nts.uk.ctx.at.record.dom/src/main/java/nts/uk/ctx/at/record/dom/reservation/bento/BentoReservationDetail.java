package nts.uk.ctx.at.record.dom.reservation.bento;

import org.eclipse.persistence.internal.xr.ValueObject;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * 弁当予約明細
 * @author Doan Duy Hung
 *
 */
public class BentoReservationDetail extends ValueObject {
	
	/**
	 * 弁当メニュー枠番
	 */
	@Getter
	private final Integer frameNo;
	
	/**
	 * 予約登録日時
	 */
	@Getter
	private final GeneralDateTime dateTime;
	
	/**
	 * 自動予約か
	 */
	@Getter
	private final boolean autoReservation;
	
	/**
	 * 個数
	 */
	@Getter
	private final BentoReservationCount bentoCount;
	
	public BentoReservationDetail(Integer frameNo, GeneralDateTime dateTime, boolean autoReservation, BentoReservationCount bentoCount) {
		this.frameNo = frameNo;
		this.dateTime = dateTime;
		this.autoReservation = autoReservation;
		this.bentoCount = bentoCount;
	}
	
	/**
	 * 作る(枠番, 個数)
	 * @param frameNo
	 * @param bentoCount
	 * @return
	 */
	public static BentoReservationDetail createNew(Integer frameNo, BentoReservationCount bentoCount) {
		return new BentoReservationDetail(
				frameNo, 
				GeneralDateTime.now(), 
				false, 
				bentoCount);
	}
}
