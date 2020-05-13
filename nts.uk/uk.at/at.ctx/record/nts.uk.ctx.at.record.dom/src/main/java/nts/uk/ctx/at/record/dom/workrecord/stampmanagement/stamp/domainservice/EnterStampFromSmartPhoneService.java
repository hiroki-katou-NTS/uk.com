package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;

/**
 * @author sonnlb
 *
 *         スマホから打刻を入力する
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.スマホから打刻を入力する.スマホから打刻を入力する
 */
public class EnterStampFromSmartPhoneService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * 
	 * @param 契約コード
	 *            contractCode
	 * @param 社員ID
	 *            employeeID
	 * @param 打刻日時
	 *            stampDatetime
	 * @param 打刻ボタン
	 *            stampButton
	 * @param 地理座標
	 *            locationInfor
	 * @param 実績への反映内容
	 *            refActualResults
	 * 
	 *            ページNOとボタン位置NOから作成する打刻種類を判断する 社員の打刻データを作成する
	 */

	public StampDataReflectResult create(Require require, ContractCode contractCode, String employeeID, GeneralDateTime stampDatetime,
			String stampButton, Optional<StampLocationInfor> locationInfor, RefectActualResult refActualResults) {
		// $打刻場所情報 = empty
		StampLocationInfor stampLocalInfo = null;

		if (locationInfor.isPresent()) {
			stampLocalInfo = locationInfor.get();
		}

		// $スマホ打刻の打刻設定 = require.スマホ打刻の打刻設定を取得する()
		val settingSmartPhoneStampo = Optional.ofNullable(null);

		if (!settingSmartPhoneStampo.isPresent()) {

			throw new BusinessException("Msg_1632");
		}

		// $ボタン詳細設定 = $スマホ打刻の打刻設定.ボタン詳細設定を取得する(打刻ボタン)
		val settingButton = Optional.ofNullable(null);

		if (!settingButton.isPresent()) {
			throw new BusinessException("Msg_1632");
		}

		// $打刻する方法 = 打刻する方法#打刻する方法(ID認証, スマホ打刻)

		Relieve relieve = new Relieve(null, null);
		// gọi đến service của chung
		return null;

	}

	public static interface Require {

		// [R-1] スマホ打刻の打刻設定を取得する
		// domain này do anh lai làm
		Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd);
	}

}
