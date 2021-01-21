package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

/**
 * 自分の弁当予約を修正する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveModifyService {
	
	public static AtomTask reserve(Require require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			GeneralDateTime dateTime, Map<Integer, BentoReservationCount> bentoDetails,Optional<WorkLocationCode> workLocationCode) {
		
		// 1: get(予約対象日)
		BentoMenu bentoMenu = require.getBentoMenu(reservationDate,workLocationCode);
		
		// 3: get(予約登録情報, 予約対象日)
		Optional<BentoReservation> opBeforeBento = require.getBefore(registerInfor, reservationDate);
		
		return AtomTask.of(() -> {
			if(opBeforeBento.isPresent()) {
				// 4: 取消可能かチェックする()
				opBeforeBento.get().checkCancelPossible();
				
				// 5: delete
				require.delete(opBeforeBento.get());
			}
			if(!CollectionUtil.isEmpty(bentoDetails.values())) {
				// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
				BentoReservation afterBento = bentoMenu.reserve(registerInfor, reservationDate, dateTime,workLocationCode, bentoDetails);
				
				// 6: persist
				require.reserve(afterBento);
			}
		});
	}
	
	public static interface Require {

		BentoMenu getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode);
		
		Optional<BentoReservation> getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
		
		void reserve(BentoReservation bentoReservation);
		
		void delete(BentoReservation bentoReservation);
	}
}
