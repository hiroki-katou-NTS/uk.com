package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;
import java.util.Optional;

import nts.arc.task.tran.AtomTask;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenu;

/**
 * 自分の弁当予約を修正する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveModifyService {
	
	public static AtomTask reserve(BentoReservationRequire require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate,
			Map<Integer, BentoReservationCount> bentoDetails) {
		
		// 1: get(予約対象日)
		BentoMenu bentoMenu = require.getBentoMenu(reservationDate);
		
		// 3: get(予約登録情報, 予約対象日)
		Optional<BentoReservation> opBeforeBento = require.getBefore(registerInfor, reservationDate);
		
		return AtomTask.of(() -> {
			if(opBeforeBento.isPresent()) {
				// 4: 取消可能かチェックする()
				opBeforeBento.get().isCancel();
				
				// 5: delete
				require.delete(opBeforeBento.get());
			}
			if(!CollectionUtil.isEmpty(bentoDetails.values())) {
				// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
				BentoReservation afterBento = bentoMenu.getBentoReservation(registerInfor, reservationDate, bentoDetails);
				
				// 6: persist
				require.reserve(afterBento);
			}
		});
	}
}
