package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Setter;
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
	private final int frameNo;
	
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
	@Setter
	private BentoReservationCount bentoCount;
	
	public BentoReservationDetail(int frameNo, GeneralDateTime dateTime, boolean autoReservation, BentoReservationCount bentoCount) {
		this.frameNo = frameNo;
		this.dateTime = dateTime;
		this.autoReservation = autoReservation;
		this.bentoCount = bentoCount;
	}
	
	/**
	 * 作る(枠番, 個数, 予約登録日時)
	 * @param frameNo
	 * @param bentoCount
	 * @param dateTime
	 * @return
	 */
	public static BentoReservationDetail createNew(int frameNo, BentoReservationCount bentoCount, GeneralDateTime dateTime) {
		return new BentoReservationDetail(
				frameNo, 
				dateTime, 
				false, 
				bentoCount);
	}
}
