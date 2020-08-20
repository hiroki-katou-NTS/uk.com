package nts.uk.ctx.at.shared.dom.monthly.verticaltotal.worktime.reservation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;
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
	
	/** ○予約注文 */
	public void aggregate(RequireM1 require, String sid, GeneralDate date) {
		
		/** 打刻カードを取得する */
		val stampCards = require.stampCard(sid);
		if (stampCards.isEmpty()) {
			return;
		}
		
		/** TODO: Tin - stop at here */
		/** 弁当予約設定を取得する */
		
		/** 月別実績の集計　＝　注文済みのみ */
		boolean monthlyAggrMethod = true;
		
		List<ReservationRegisterInfo> inforLst = stampCards.stream()
				.map(c -> new ReservationRegisterInfo(c.getStampNumber().v()))
				.collect(Collectors.toList());
		
		List<BentoReservation> bentous;
		if (monthlyAggrMethod) {
			bentous = require.bentoReservation(inforLst, date, true);
		} else {
			bentous = require.bentoReservation(inforLst, date, true);
		}
		
		if (bentous.isEmpty()) {
			return;
		}
		
		String cid = AppContexts.user().companyId();
		bentous.stream().forEach(bentou -> {
			
			bentou.getBentoReservationDetails().stream().forEach(detail -> {
				/** 取得した弁当予約明細から弁当を取得する */
				val bentouMenu = require.bento(cid, date, detail.getFrameNo());
				
				/** 弁当合計金額明細を作成する */
				val amountDetail = BentoDetailsAmountTotal.calculate(detail.getFrameNo(), 
						detail.getBentoCount().v(), bentouMenu.getAmount1().v(), 
						bentouMenu.getAmount2().v());
				
				/** 月別実績の予約に加算する */
				this.sum(amountDetail);
				
				/** 月別実績の予約明細を探して加算する */
				val order = this.orders.stream().filter(o -> o.getFrameNo() == detail.getFrameNo()).findFirst();
				if (order.isPresent()) {
					order.get().add(detail.getBentoCount().v());
				} else {
					this.orders.add(ReservationDetailOfMonthly.of(detail.getFrameNo(), detail.getBentoCount().v()));
				}
			});
		});
	}
	
	public void sum(BentoDetailsAmountTotal bentoDetail) {
		this.amount1 = this.amount1.add(bentoDetail.getAmount1());
		this.amount2 = this.amount2.add(bentoDetail.getAmount1());
	}
	
	public static interface RequireM1 {
		
		List<StampCard> stampCard(String empId);
		
		List<BentoReservation> bentoReservation(List<ReservationRegisterInfo> inforLst, GeneralDate date, boolean ordered);
		
		Bento bento(String companyID, GeneralDate date, int frameNo);
	}
}
