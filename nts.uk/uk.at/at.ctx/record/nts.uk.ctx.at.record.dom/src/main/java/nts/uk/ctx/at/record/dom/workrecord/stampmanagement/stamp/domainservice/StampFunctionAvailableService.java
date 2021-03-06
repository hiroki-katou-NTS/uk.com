package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.AutoCreateStampCardNumberService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardCreateResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;

/**
 * DS : 打刻機能が利用できるか
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.打刻管理.打刻.打刻機能が利用できるか
 * 
 * @author tutk
 *
 */
@Stateless
public class StampFunctionAvailableService {

	/**
	 * [1] 判断する
	 * 
	 * @param require
	 * @param 社員ID
	 *            employeeId
	 * @param 打刻手段
	 *            stampMeans
	 * @return 打刻機能の利用判断結果 MakeUseJudgmentResults
	 */

	public static MakeUseJudgmentResults decide(Require require, String employeeId, StampMeans stampMeans) {
		// $利用設定 = require.利用設定を取得する()
		Optional<SettingsUsingEmbossing> cardCreate = require.get();
		
		if (!cardCreate.isPresent()) {
			return new MakeUseJudgmentResults(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED, Optional.empty());
		}
		
		// $利用設定.打刻利用できるか(打刻手段)
		if (!cardCreate.isPresent() || !cardCreate.get().canUsedStamping(stampMeans)) {
			return new MakeUseJudgmentResults(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED, Optional.empty());
		}
		
		// if not (打刻手段 = 個人打刻 OR 打刻手段 = ポータル打刻 OR 打刻手段 = スマホ打刻)
		List<StampMeans> items = Arrays.asList(StampMeans.INDIVITION, StampMeans.PORTAL, StampMeans.SMART_PHONE);

		if (items.indexOf(stampMeans) == -1) {
			// 打刻利用判断結果#利用できる()
			return MakeUseJudgmentResults.get();
		}

		// $打刻カードリスト = require.打刻カード番号を取得する(社員ID)
		List<StampCard> data = require.getListStampCard(employeeId);
		if (!data.isEmpty()) {
			// 打刻利用判断結果#利用できる()
			return MakeUseJudgmentResults.get();
		}
		

		// $打刻カード作成結果 = 打刻カード番号を自動作成する#作成する(require, 社員ID, 打刻手段)
		Optional<StampCardCreateResult> optCardCreate = AutoCreateStampCardNumberService.create(require,
				employeeId, stampMeans);
		
		// if not $打刻カード作成結果.isEmpty
		if (optCardCreate.isPresent()) {
			return new MakeUseJudgmentResults(CanEngravingUsed.AVAILABLE, optCardCreate);
		}

		return new MakeUseJudgmentResults(CanEngravingUsed.UNREGISTERED_STAMP_CARD, Optional.empty());
	}

	public static interface Require extends AutoCreateStampCardNumberService.Require {
		/**
		 * [R-1] 打刻カード番号を取得する StampCardRepository
		 * 
		 * @param sid
		 * @return
		 */
		List<StampCard> getListStampCard(String sid);

		/**
		 * [R-1] 利用設定を取得する
		 * 
		 * @return
		 */

		Optional<SettingsUsingEmbossing> get();

	}
}
