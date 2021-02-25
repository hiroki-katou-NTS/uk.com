package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

/**
 * 弁当を予約する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveService {
	
	public static AtomTask reserve(Require require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
								   GeneralDateTime dateTime, Map<Integer, BentoReservationCount> bentoDetails, Optional<WorkLocationCode> workLocationCode) {
		
		// 1: get(予約対象日,勤務場所コード)
		BentoMenu bentoMenu = require.getBentoMenu(reservationDate,workLocationCode);
		
		// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
		BentoReservation bentoReservation = bentoMenu.reserve(registerInfor, reservationDate, dateTime,workLocationCode, bentoDetails);
		
		return AtomTask.of(() -> {
			// 3: persist
			require.reserve(bentoReservation);
		});
	}
	
	public static interface Require {
		
		BentoMenu getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode);
		
		void reserve(BentoReservation bentoReservation);
	}
}
