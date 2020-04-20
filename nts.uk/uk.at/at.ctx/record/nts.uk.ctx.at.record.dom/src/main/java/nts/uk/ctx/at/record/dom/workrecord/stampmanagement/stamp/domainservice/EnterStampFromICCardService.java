package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;

/**
 * 
 * @author sonnlb
 * 
 *         ICカードから打刻を入力す
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.ICカードから打刻を入力する.ICカードから打刻を入力する
 */
public class EnterStampFromICCardService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * @param 契約コード
	 *            contractCode
	 * @param 打刻カード番号
	 *            stampNumber
	 * @param 打刻日時
	 *            stampDatetime
	 * @param 打刻ボタン
	 *            stampButton
	 * @param 実績への反映内容
	 *            refectActualResult
	 * @return
	 */
	public StampDataReflectResult create(Require require, ContractCode contractCode, StampNumber stampNumber,
			GeneralDateTime stampDatetime, String stampButton, RefectActualResult refectActualResult) {
		// $打刻カード = require.打刻カードを取得する(契約コード, 打刻カード番号)
		Optional<StampCard> stampCardOpt = require.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());

		if (!stampCardOpt.isPresent()) {
			throw new BusinessException("Msg_433");
		}
		// gọi đến ds của chung
		// $打刻入力結果 = 共有打刻から打刻を入力する#作成する(require, 契約コード, $打刻カード.社員ID, 打刻カード番号,
		// $打刻する方法, 打刻日時, 打刻ボタン, 実績への反映内容)
		return null;
	}

	public static interface Require {

		// [R-1] 打刻カードを取得する
		Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd);
	}
}
