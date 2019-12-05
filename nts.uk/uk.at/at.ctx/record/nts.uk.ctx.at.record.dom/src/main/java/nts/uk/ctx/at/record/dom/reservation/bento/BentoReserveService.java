package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

/**
 * 弁当を予約する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveService {
	
	public static AtomTask reserve(BentoReservationRequire require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			Map<Integer, BentoReservationCount> bentoDetails) {
		
		// 1: get(予約対象日)
		BentoMenu bentoMenu = require.getBentoMenu(reservationDate);
		
		// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
		BentoReservation bentoReservation = bentoMenu.getBentoReservation(registerInfor, reservationDate, bentoDetails);
		
		// 3: persist
		return AtomTask.of(() -> require.reserve(bentoReservation));
	}
	
}
