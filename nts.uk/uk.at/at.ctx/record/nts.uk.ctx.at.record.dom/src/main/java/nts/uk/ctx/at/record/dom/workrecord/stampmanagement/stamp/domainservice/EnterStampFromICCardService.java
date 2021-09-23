package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.AuthcMethod;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.EnterStampForSharedStampService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;

/**
 * 
 * @author sonnlb
 * 
 *         ICカードから打刻を入力する
 *         UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.ICカードから打刻を入力する.ICカードから打刻を入力する
 */
public class EnterStampFromICCardService {
	/**
	 * [1] 作成する
	 * 
	 * @param require
	 * @param 契約コード
	 *            contractCode
	 * @param 会社ID
	 *            companyID
	 * @param 打刻カード番号
	 *            stampNumber
	 * @param 打刻日時
	 *            stampDatetime
	 * @param 打刻ボタン
	 *            stampButton
	 * @param 実績への反映内容
	 *            refectActualResult
	 * @return 打刻結果（社員ID込み）
	 */
	public static StampingResultEmployeeId create(Require require, String cid, ContractCode contractCode, StampNumber stampNumber,
			GeneralDateTime stampDatetime, StampButton stampButton, RefectActualResult refectActualResult) {
		
		// $打刻カード = require.打刻カードを取得する(契約コード, 打刻カード番号)
		Optional<StampCard> stampCardOpt = require.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());

		if (!stampCardOpt.isPresent()) {
			throw new BusinessException("Msg_433");
		}
		
		//	$打刻する方法 = 打刻する方法#打刻する方法(ICカード認証, ICカード打刻)	
		Relieve relieve = new Relieve(AuthcMethod.IC_CARD_AUTHC, StampMeans.IC_CARD);
		
		// $打刻入力結果 = 共有打刻から打刻を入力する#作成する(require, 契約コード, $打刻カード.社員ID, 打刻カード番号,
		// $打刻する方法, 打刻日時, 打刻ボタン, 実績への反映内容)
		
		TimeStampInputResult timeStampInputResult = EnterStampForSharedStampService.create(require,
				cid,
				contractCode.v(),
				stampCardOpt.get().getEmployeeId(),
				Optional.of(stampNumber),
				relieve,
				stampDatetime,
				stampButton,
				refectActualResult);
		
		//return 打刻結果（社員ID込み）#打刻結果（社員ID込み）($打刻入力結果,  $打刻カード.社員ID)
		return new StampingResultEmployeeId(timeStampInputResult, stampCardOpt.get().getEmployeeId());
	}

	public static interface Require extends EnterStampForSharedStampService.Require {

		// [R-1] 打刻カードを取得する
		Optional<StampCard> getByCardNoAndContractCode(String cardNo, String contractCd);
	}
}
