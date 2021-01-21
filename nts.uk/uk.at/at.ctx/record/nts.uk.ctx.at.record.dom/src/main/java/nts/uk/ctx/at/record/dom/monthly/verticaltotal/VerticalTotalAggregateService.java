package nts.uk.ctx.at.record.dom.monthly.verticaltotal;

import java.util.List;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationRegisterInfo;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.Bento;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.totalfee.BentoDetailsAmountTotal;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationDetailOfMonthly;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.verticaltotal.reservation.ReservationOfMonthly;
import nts.uk.shr.com.context.AppContexts;

public class VerticalTotalAggregateService {

	/** ○予約注文 */
	public static ReservationOfMonthly aggregate(RequireM1 require, String sid, GeneralDate date) {
		
		ReservationOfMonthly result = ReservationOfMonthly.empty();
		
		/** 打刻カードを取得する */
		val stampCards = require.stampCard(sid);
		if (stampCards.isEmpty()) {
			return result; 
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
			bentous = require.bentoReservation(inforLst, date, false);
		}
		
		if (bentous.isEmpty()) {
			return result;
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
				result.addToAmount1(amountDetail.getAmount1());
				result.addToAmount2(amountDetail.getAmount1());
				
				/** 月別実績の予約明細を探して加算する */
				val order = result.getOrders().stream().filter(o -> o.getFrameNo() == detail.getFrameNo()).findFirst();
				if (order.isPresent()) {
					order.get().add(detail.getBentoCount().v());
				} else {
					result.add(ReservationDetailOfMonthly.of(detail.getFrameNo(), detail.getBentoCount().v()));
				}
			});
		});
		
		return result;
	}
	
	public static interface RequireM1 {
		
		List<StampCard> stampCard(String empId);
		
		List<BentoReservation> bentoReservation(List<ReservationRegisterInfo> inforLst, GeneralDate date, boolean ordered);
		
		Bento bento(String companyID, GeneralDate date, int frameNo);
	}
}
