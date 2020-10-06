package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
/**
 * 月別実績の予約
 */
@Getter
public class ReservationOfMonthly implements Serializable{

	/***/
	private static final long serialVersionUID = 1L;

	/** 注文数 */
	private List<ReservationDetailOfMonthly> orders;
	
	/** 金額1注文金額 */
	private OrderAmountMonthly amount1;

	/** 金額2注文金額 */
	private OrderAmountMonthly amount2;
	
	private ReservationOfMonthly(OrderAmountMonthly amount1, 
			OrderAmountMonthly amount2, 
			List<ReservationDetailOfMonthly> order) {
		this.orders = order;
		this.amount1 = amount1;
		this.amount2 = amount2;
	}
	
	public static ReservationOfMonthly empty() {
		return new ReservationOfMonthly(new OrderAmountMonthly(0), new OrderAmountMonthly(0), new ArrayList<>());
	}
	
	public static ReservationOfMonthly of(
			OrderAmountMonthly amount1, 
			OrderAmountMonthly amount2, 
			List<ReservationDetailOfMonthly> order) {
		
		return new ReservationOfMonthly(amount1, amount2, order);
	}
	
	public void sum(ReservationOfMonthly target) {
		target.orders.stream().forEach(o -> {
			val current = orders.stream().filter(co -> co.getFrameNo() == o.getFrameNo()).findFirst();
			if (current.isPresent()) {
				current.get().add(o.getOrder().v());
			} else {
				orders.add(o);
			}
		});
		
		this.amount1 = this.amount1.add(target.amount1);
		this.amount2 = this.amount2.add(target.amount2);
	}
	
	public void add(ReservationDetailOfMonthly detail) {
		this.orders.add(detail);
	}
	
	/** ○予約注文 */
	public void aggregate(RequireM1 require, String sid, GeneralDate date) {
		
		/** VerticalTotalAggregateService参照 */
		val reservation = require.reservation(sid, date);
		
		this.sum(reservation);
	}
	
	public void addToAmount1(int amount) {
		this.amount1 = this.amount1.add(amount);
	}
	
	public void addToAmount2(int amount) {
		this.amount2 = this.amount2.add(amount);
	}
	
	public static interface RequireM1 {
		
		ReservationOfMonthly reservation(String sid, GeneralDate date);
	}
}
