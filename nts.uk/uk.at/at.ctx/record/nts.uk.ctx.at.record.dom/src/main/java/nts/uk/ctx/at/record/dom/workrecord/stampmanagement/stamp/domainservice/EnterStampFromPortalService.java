package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.management.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PortalStampSettings;

/**
 * 
 * @author sonnlb
 * 
 *         ポータル打刻から打刻を入力する
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.ポータル打刻から打刻を入力する.ポータル打刻から打刻を入力する
 *
 */
public class EnterStampFromPortalService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * @param 契約コード
	 *            contractCode
	 * @param 社員ID
	 *            employeeID
	 * @param 打刻日時
	 *            datetime
	 * @param ボタン位置NO
	 *            buttonPositionNo
	 * @param 実績への反映内容
	 *            refActualResults
	 * @return 打刻入力結果
	 * 
	 *         ページNOとボタン位置NOから作成する打刻種類を判断する 社員の打刻データを作成する
	 */
	public StampDataReflectResult create(Require require, ContractCode contractCode, String employeeID,
			GeneralDateTime datetime, ButtonPositionNo buttonPositionNo, RefectActualResult refActualResults) {
		// $ポータルの打刻設定 = require.ポータルの打刻設定を取得する()

		val settingStampPotal = Optional.ofNullable(null);
		if (!settingStampPotal.isPresent()) {
			throw new BusinessException("Msg_1632");
		}
		// $ボタン詳細設定 = $ポータルの打刻設定.ボタン詳細設定を取得する(ボタン位置NO)
		val settingButton = Optional.ofNullable(null);

		if (!settingButton.isPresent()) {
			throw new BusinessException("Msg_1632");
		}

		// $打刻する方法 = 打刻する方法#打刻する方法(ID認証, ポータル打刻)
		Relieve relieve = new Relieve(null, null);
		// gọi đến service của chung

		return null;
	}

	public static interface Require {

		// [R-1] ポータルの打刻設定を取得する
		// gọi đến aggr của anh lai chưa viêt
		Optional<PortalStampSettings> getByCardNoAndContractCode(String cardNo, String contractCd);
	}

}
