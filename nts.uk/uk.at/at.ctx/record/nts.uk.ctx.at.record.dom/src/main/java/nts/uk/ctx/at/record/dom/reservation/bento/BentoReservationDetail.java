package nts.uk.ctx.at.record.dom.reservation.bento;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuFrameNumber;

/**
 * 弁当予約明細
 * @author Doan Duy Hung
 *
 */
public class BentoReservationDetail {
	
	/**
	 * 弁当メニュー枠番
	 */
	@Getter
	private final BentoMenuFrameNumber frameNo;
	
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
	
	public BentoReservationDetail(BentoMenuFrameNumber frameNo, GeneralDateTime dateTime, boolean autoReservation, BentoReservationCount bentoCount) {
		this.frameNo = frameNo;
		this.dateTime = dateTime;
		this.autoReservation = autoReservation;
		this.bentoCount = bentoCount;
	}
	
	public static BentoReservationDetail createNew(BentoMenuFrameNumber frameNo, BentoReservationCount bentoCount) {
		return new BentoReservationDetail(
				frameNo, 
				GeneralDateTime.now(), 
				false, 
				bentoCount);
	}
}
