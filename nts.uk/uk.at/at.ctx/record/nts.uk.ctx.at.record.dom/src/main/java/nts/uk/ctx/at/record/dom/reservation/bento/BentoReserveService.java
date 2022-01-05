package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;

/**
 * 弁当を予約する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveService {
	
	/**
	 * 	[1] 予約する
	 * @param require
	 * @param registerInfor 登録情報
	 * @param reservationDate 対象日
	 * @param dateTime 予約登録日時
	 * @param bentoDetails 明細
	 * @param frameNo 枠No
	 * @param companyID 会社ID
	 * @param workLocationCode 勤務場所コード
	 * @return
	 */
	public static AtomTask reserve(Require require, ReservationRegisterInfo registerInfor, ReservationDate reservationDate, GeneralDateTime dateTime, 
			Map<Integer, BentoReservationCount> bentoDetails, String companyID, Optional<WorkLocationCode> workLocationCode) {
		
		ReservationRecTimeZone reservationRecTimeZone = require.getReservationSetByOpDistAndFrameNo(companyID, reservationDate.getClosingTimeFrame().value, 0);
		
		if(reservationRecTimeZone==null) {
			throw new BusinessException("Msg_2285");
		}
		
		// 1: get(予約対象日,勤務場所コード)
		BentoMenuHistory bentoMenu = require.getBentoMenu(reservationDate,workLocationCode);
		
		// 2: 予約する(予約登録情報, 予約対象日, Map<弁当メニュー枠番, 弁当予約個数>)
		BentoReservation bentoReservation = bentoMenu.reserve(registerInfor, reservationDate, dateTime,workLocationCode, bentoDetails, reservationRecTimeZone);
		
		return AtomTask.of(() -> {
			// 3: persist
			require.reserve(bentoReservation);
		});
	}
	
	public static interface Require {
		
		BentoMenuHistory getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode);
		
		void reserve(BentoReservation bentoReservation);
		
		ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo, int operationDistinction);
	}
}
