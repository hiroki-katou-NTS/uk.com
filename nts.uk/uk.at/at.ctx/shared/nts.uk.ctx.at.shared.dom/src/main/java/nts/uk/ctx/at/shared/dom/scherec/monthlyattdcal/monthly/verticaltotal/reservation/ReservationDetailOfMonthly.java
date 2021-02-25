package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation;

import java.io.Serializable;

import lombok.Getter;
/**
 * 月別実績の予約明細
 */
@Getter
public class ReservationDetailOfMonthly implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;

	/**弁当メニュー枠番 */
	private final int frameNo;
	
	/** 注文数 */
	private OrderNumberMonthly order;
	
	private ReservationDetailOfMonthly (int frameNo, OrderNumberMonthly order) {
		this.frameNo = frameNo;
		this.order = order;
	}
	
	public static ReservationDetailOfMonthly of (int frameNo) {
		
		return new ReservationDetailOfMonthly(frameNo, new OrderNumberMonthly(0));
	}
	
	public static ReservationDetailOfMonthly of (int frameNo, int order) {
		
		return new ReservationDetailOfMonthly(frameNo, new OrderNumberMonthly(order));
	}
	
	public void add(int order) {
		this.order = this.order.add(order);
	}
}
