package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;

import nts.arc.task.tran.AtomTask;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

/**
 * 
 * @author Doan Duy Hung
 *
 */
public class BentoReserveModifyService {
	
	public static AtomTask reserve(BentoReservationRequire require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			Map<Integer, BentoReservationCount> bentoDetails) {
		
		// 1: get(予約対象日)
		BentoMenu bentoMenu = require.getBentoMenu(reservationDate.getDate());
		
		// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
		BentoReservation afterBento = bentoMenu.getBentoReservation(registerInfor, reservationDate, bentoDetails);
		
		// 3: get(予約登録情報, 予約対象日)
		BentoReservation beforeBento = require.getBefore(registerInfor, reservationDate);
		
		// 4: 取消可能かチェックする()
		beforeBento.isCancel();
		
		// 5, 6: delete, persist
		return AtomTask.of(() -> {
			require.delete(beforeBento);
			require.reserve(afterBento);
		});
	}
}
