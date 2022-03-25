package nts.uk.ctx.at.record.dom.reservation.bentomenu;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationCount;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationDetail;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.BentoItemByClosingTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.closingtime.ReservationClosingTimeFrame;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoDetailsAmountTotal;

import java.util.Optional;

/**
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.勤務実績.支給賞与額履歴.予約.弁当メニュー.弁当
 * @author Doan Duy Hung
 *
 */
public class Bento {
	
	/**
	 * 枠番
	 */
	@Getter
	private final int frameNo;
	
	/**
	 * 弁当名
	 */
	@Getter
	private BentoName name;
	
	/**
	 * 金額１
	 */
	@Getter
	private BentoAmount amount1;
	
	/**
	 * 金額２
	 */
	@Getter
	private BentoAmount amount2;
	
	/**
	 * 単位
	 */
	@Getter
	private BentoReservationUnitName unit;
	
	/**
	 * 受付時間帯NO
	 */
	@Getter
	private final ReservationClosingTimeFrame receptionTimezoneNo;
	
	/**
	 * 勤務場所コード
	 */
	@Getter
	private Optional<WorkLocationCode> workLocationCode;

	public Bento(int frameNo, BentoName name, BentoAmount amount1, BentoAmount amount2,
				 BentoReservationUnitName unit, ReservationClosingTimeFrame receptionTimezoneNo, Optional<WorkLocationCode> workLocationCode) {
		this.frameNo = frameNo; 
		this.name = name; 
		this.amount1 = amount1; 
		this.amount2 = amount2;
		this.unit = unit;
		this.receptionTimezoneNo = receptionTimezoneNo;
		this.workLocationCode = workLocationCode;
	}
	
	/**
	 * 予約する
	 * @param reservationDate
	 * @param bentoCount
	 * @param dateTime
	 * @return
	 */
	public BentoReservationDetail reserve(ReservationDate reservationDate, BentoReservationCount bentoCount, GeneralDateTime dateTime) {
		if(reservationDate.getClosingTimeFrame()!=this.receptionTimezoneNo) {
			throw new RuntimeException("System Error");
		}
		return BentoReservationDetail.createNew(frameNo, bentoCount, dateTime);
	}
	
	/**
	 * 締め時刻別のメニュー項目
	 * @return
	 */
	public BentoItemByClosingTime itemByClosingTime() {
		return new BentoItemByClosingTime(frameNo, name, amount1, amount2, unit);
	}
	
	/**
	 * 金額を計算する
	 * @param quantity
	 * @return
	 */
	public BentoDetailsAmountTotal calculateAmount(int quantity) {
		return BentoDetailsAmountTotal.calculate(frameNo, quantity, amount1.v(), amount2.v());
	}
}
