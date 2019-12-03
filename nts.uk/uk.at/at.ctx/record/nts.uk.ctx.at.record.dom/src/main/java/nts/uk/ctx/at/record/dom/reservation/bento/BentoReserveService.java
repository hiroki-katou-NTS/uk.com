package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuFrameNumber;

/**
 * 弁当を予約する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveService {
	
	public static AtomTask reserve(Require require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			Map<BentoMenuFrameNumber, BentoReservationCount> bentoDetails) {
		
		// 1:get(予約対象日)
		BentoMenu bentoMenu = require.getBentoMenu();
		
		// 2.1: 過去日か
		bentoMenu.receptionCheck(registerInfor, reservationDate);
		
		// 2.2: 作る(弁当メニュー枠番, 弁当予約個数)	
		List<BentoReservationDetail> bentoReservationDetails = bentoDetails.entrySet().stream()
				.map(x -> BentoReservationDetail.createNew(x.getKey(), x.getValue())).collect(Collectors.toList());
		
		// 2.3: 予約する(予約登録情報, 予約対象日, List<弁当予約明細>)
		BentoReservation bentoReservation = BentoReservation.createNew(registerInfor, reservationDate, bentoReservationDetails);
		
		// 3: persist
		return AtomTask.of(() -> require.reserve(bentoReservation));
	}
	
	public static interface Require {
		
		BentoMenu getBentoMenu();
		
		void reserve(BentoReservation bentoReservation);
	}
	
}
