package nts.uk.ctx.at.record.dom.reservation.bento;

import java.util.Map;
import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDateTime;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSetting;

/**
 * 自分の弁当予約を修正する
 * @author Doan Duy Hung
 *
 */
public class BentoReserveModifyService {
	
	/**
	 * [1]予約を変更する
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
			Map<Integer, BentoReservationCount> bentoDetails, int frameNo, String companyID, Optional<WorkLocationCode> workLocationCode) {
		
		ReservationSetting reservationSetting = require.getReservationSettingByOpDist(companyID, 0);
		if(reservationSetting==null) {
			throw new BusinessException("Msg_2285");
		}
		if(frameNo==2 && !reservationSetting.isReceptionTimeZone2Use()) {
			return AtomTask.of(() -> {});
		}
		
		ReservationRecTimeZone reservationRecTimeZone = reservationSetting.getReservationRecTimeZoneLst().stream().filter(x -> x.getFrameNo().value==frameNo).findAny().orElse(null);
		if(reservationRecTimeZone==null) {
			throw new BusinessException("Msg_2285");
		}
		
		// 1: get(予約対象日)
		BentoMenuHistory bentoMenu = require.getBentoMenu(reservationDate,workLocationCode);
		
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
				BentoReservation afterBento = bentoMenu.reserve(registerInfor, reservationDate, dateTime,workLocationCode, bentoDetails, reservationRecTimeZone);
				
				// 6: persist
				require.reserve(afterBento);
			}
		});
	}
	
	public static interface Require {

		BentoMenuHistory getBentoMenu(ReservationDate reservationDate,Optional<WorkLocationCode> workLocationCode);
		
		Optional<BentoReservation> getBefore(ReservationRegisterInfo registerInfor, ReservationDate reservationDate);
		
		void reserve(BentoReservation bentoReservation);
		
		void delete(BentoReservation bentoReservation);
		
		ReservationSetting getReservationSettingByOpDist(String companyID, int operationDistinction);
	}
}
