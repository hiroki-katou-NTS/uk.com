package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.gul.location.GeoCoordinate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.AutoCreateStampCardNumberService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampType;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * DS : 社員の打刻データを作成する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.社員の打刻データを作成する
 * 
 * @author tutk
 *
 */
public class CreateStampDataForEmployeesService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * @param contractCode
	 *            契約コード
	 * @param employeeId
	 *            社員ID
	 * @param stampCardNo
	 *            打刻カード番号
	 * @param stmapDateTime
	 *            打刻日時
	 * @param relieve
	 *            打刻する方法
	 * @param buttonType
	 *            ボタン種類
	 * @param refActualResults
	 *            実績への反映内容
	 * @param positionInfo
	 *            打刻位置情報
	 * 
	 * @return TimeStampInputResult 打刻入力結果
	 */

	private static AutoCreateStampCardNumberService autoCreateStampCardNumberService = new AutoCreateStampCardNumberService();

	public static TimeStampInputResult create(Require require, ContractCode contractCode, String employeeId,
			Optional<StampNumber> stampNumber, GeneralDateTime stmapDateTime, Relieve relieve, ButtonType buttonType,
			Optional<RefectActualResult> refActualResults, Optional<GeoCoordinate> positionInfo) {

//		// $打刻カード作成結果 = [prv-1] 打刻カード番号を取得する(require, 社員ID, 打刻カード番号, 打刻する方法.打刻手段)
//		Optional<StampCardCreateResult> stampCardCreateResult = getCardNumber(require, employeeId, stampNumber,
//				relieve.getStampMeans());
//
//		// $打刻作成するか = ボタン種類.打刻区分を取得する()
//		boolean stampAtr = buttonType.checkStampType();
//
//		StampRecord stampRecord = new StampRecord(contractCode, stampNumber, stmapDateTime, buttonType.getReservationArt(),stmapDateTime,
//				empInfoTerCode);
//		if (!stampAtr) {
//			return StampDataReflectProcessService.reflect(require, Optional.of(employeeId), stampRecord,
//					Optional.empty());
//		}
//		Stamp stamp = createStampDataInfo(stampNumber, datetime, stampAtr, relieve, buttonType.getStampType().get(),
//				refActualResults.get(), positionInfo);
//
//		return StampDataReflectProcessService.reflect(require, Optional.of(employeeId), stampRecord,
//				Optional.of(stamp));
//		return new TimeStampInputResult(null, null);
		return null;

	}

	/**
	 * [prv-1] 打刻カード番号を取得する
	 * 
	 * @param require
	 * @param employeeId
	 *            社員ID
	 * @param stampNumber
	 *            打刻カード番号
	 * @param stampMeans
	 *            打刻手段
	 * @return 打刻カード作成結果
	 */
	private static StampCardCreateResult getCardNumber(Require require, String employeeId,
			Optional<StampNumber> stampNumber, StampMeans stampMeans) {

		// f 打刻する方法.打刻手段 == ICカード
		if (stampMeans == StampMeans.IC_CARD) {
			return new StampCardCreateResult(stampNumber.get().v(), null);
		}

		// $打刻カードリスト = require.打刻カード番号を取得する(社員ID)
		List<StampCard> listStampCard = require.getListStampCard(employeeId);
		if (listStampCard.isEmpty()) {
			throw new BusinessException("Msg_433");
		}

		// $打刻カード = $打刻カードリスト :
		// map $番号
		// first
		// return 打刻カード作成結果#打刻カード作成結果($打刻カード, empty)
		Optional<StampNumber> stampCard = listStampCard.stream().map(t -> t.getStampNumber()).findFirst();
		return new StampCardCreateResult(stampCard.get().v(), null);

		// $打刻カード作成結果 = 打刻カード番号を自動作成する#作成する(require, 社員ID, 打刻手段)
//		Optional<StampCardCreateResult> stampCardCreateResult = autoCreateStampCardNumberService.create(require,
//				employeeId, stampMeans);

//		if (stampCardCreateResult.isPresent()) {
//
//		}
//
//		return stampCardCreateResult.get();
	}

	/**
	 * [prv-2] 打刻データ情報を作成する
	 * 
	 * @param stampNumber
	 *            打刻カード番号
	 * @param datetime
	 *            打刻日時
	 * @param stampAtr
	 *            打刻区分
	 * @param relieve
	 *            打刻する方法
	 * @param type
	 *            打刻種類
	 * @param refActualResults
	 *            実績への反映内容
	 * @param positionInfo
	 *            打刻位置情報
	 * @return 打刻
	 */

	// private static Stamp createStampDataInfo(StampNumber stampNumber,
	// GeneralDateTime datetime,
	// boolean stampAtr,Relieve relieve, StampType type, RefectActualResult
	// refActualResults,
	// Optional<GeoCoordinate> positionInfo) {
	// StampLocationInfor stampLocationInfor = null;
	// if(positionInfo.isPresent()) {
	// stampLocationInfor = new StampLocationInfor(true, positionInfo.get()) ;
	// }
	// return new Stamp(
	// stampNumber,
	// datetime,
	// relieve,
	// type, refActualResults, stampLocationInfor);
	// }

	public static interface Require extends StampDataReflectProcessService.Require {
		/**
		 * [R-1] 打刻カード番号を取得する - StampCardRepository
		 * 
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);
	}
}
