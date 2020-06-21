package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
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
	 * 
	 * @param contractCd         契約コード
	 * @param employeeId         社員ID
	 * @param datetime           打刻日時
	 * @param stampNumber        打刻カード番号
	 * @param relieve            打刻する方法
	 * @param buttonType         ボタン種類
	 * @param refActualResults   実績への反映内容
	 * @param positionInfo       打刻位置情報
	 * @param stampLocationInfor 打刻位置情報
	 * @return
	 */
	
	
	public static TimeStampInputResult create(Require require, String contractCd, String employeeId,
			StampNumber stampNumber, GeneralDateTime datetime, Relieve relieve, ButtonType buttonType,
			RefectActualResult refActualResults, Optional<StampLocationInfor> stampLocationInfor) {

		// $打刻カード作成結果 = [prv-1] 打刻カード番号を取得する(require, 社員ID, 打刻カード番号, 打刻する方法.打刻手段)

		StampCardCreateResult stampNumberResult = getCardNumber(require, employeeId, stampNumber,
				relieve.getStampMeans());

		// $打刻作成するか = ボタン種類.打刻区分を取得する()

		boolean stampTypeNotEmpty = buttonType.checkStampType();

		// $表示する打刻区分 = ボタン種類.表示する打刻区分を取得する()

		String display = buttonType.getMarkingtoDisplay();

		// $打刻記録 = 打刻記録#打刻記録(契約コード, $打刻カード作成結果.打刻カード番号, 打刻日時, $表示する打刻区分, empty)

		StampRecord record = new StampRecord(new ContractCode(contractCd),
				new StampNumber(stampNumberResult.getCardNumber()), datetime, display, null);

		// if not $打刻作成するか
		if (!stampTypeNotEmpty) {
			// $予約処理結果 = 打刻データ反映処理#反映する(require, 社員ID, $打刻記録, empty)
			StampDataReflectResult stampRefResult = StampDataReflectProcessService.reflect(require,
					Optional.ofNullable(employeeId), record, null);
			// return 打刻入力結果#打刻入力結果($予約処理結果, $打刻カード作成結果.永続化処理)

			return new TimeStampInputResult(stampRefResult, stampNumberResult.getAtomTask());
		}

		// $打刻データ = 打刻#初回打刻データを作成する(契約コード, $打刻カード作成結果.打刻カード番号, 打刻日時, 打刻する方法,ボタン種類.打刻種類,
		// 実績への反映内容, 打刻場所情報)

		Stamp stampResult = new Stamp(new ContractCode(contractCd), new StampNumber(stampNumberResult.getCardNumber()),
				datetime, relieve, buttonType.getStampType().get(), refActualResults, stampLocationInfor.get());

		// $打刻反映結果 = 打刻データ反映処理#反映する(require, 社員ID, $打刻記録, $打刻データ)
		StampDataReflectResult stampRefResult = StampDataReflectProcessService.reflect(require,
				Optional.ofNullable(employeeId), record, Optional.ofNullable(stampResult));
		// return 打刻入力結果#打刻入力結果($打刻反映結果, $打刻カード作成結果.永続化処理)

		return new TimeStampInputResult(stampRefResult, stampNumberResult.getAtomTask());
	}

	/**
	 * [prv-1] 打刻カード番号を取得する
	 * 
	 * @param require
	 * @param employeeId  社員ID
	 * @param stampNumber 打刻カード番号
	 * @param StampMeans  打刻手段
	 * @return 打刻カード番号
	 */
	private static StampCardCreateResult getCardNumber(Require require, String employeeId, StampNumber stampNumber,
			StampMeans stampMeans) {

		// if 打刻する方法.打刻手段 == ICカード
		if (stampMeans.equals(stampMeans.IC_CARD)) {
			// return 打刻カード作成結果#打刻カード作成結果($打刻カード, empty)
			return new StampCardCreateResult(stampNumber.v(), Optional.empty());
		}
		// $打刻カードリスト = require.打刻カード番号を取得する(社員ID)

		List<StampCard> stampCards = require.getListStampCard(employeeId);
		// if not $打刻カードリスト.isEmpty
		if (!stampCards.isEmpty()) {
			// $打刻カード = $打刻カードリスト : map $番号 first

			StampNumber resultNumber = stampCards.stream().findFirst().get().getStampNumber();

			// return 打刻カード作成結果#打刻カード作成結果($打刻カード, empty)
			return new StampCardCreateResult(resultNumber.v(), Optional.empty());
		}
		// $打刻カード作成結果 = 打刻カード番号を自動作成する#作成する(require, 社員ID, 打刻手段)

		Optional<StampCardCreateResult> stampCardResultOpt = AutoCreateStampCardNumberService.create(require,
				employeeId, stampMeans);

		// if $打刻カード作成結果.isEmpty

		if (!stampCardResultOpt.isPresent()) {
			throw new BusinessException("Msg_433");
		}

		return stampCardResultOpt.get();

	}

	public static interface Require extends AutoCreateStampCardNumberService.Require {
		/**
		 * [R-1] 打刻カード番号を取得する - StampCardRepository
		 * 
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);
	}

	
}
