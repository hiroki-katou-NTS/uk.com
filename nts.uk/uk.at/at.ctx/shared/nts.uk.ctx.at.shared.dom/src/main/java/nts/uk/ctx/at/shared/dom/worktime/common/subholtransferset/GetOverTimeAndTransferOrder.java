package nts.uk.ctx.at.shared.dom.worktime.common.subholtransferset;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.GetSubHolOccurrenceSetting;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSetAtr;

/**
 * 残業・振替の処理順序を取得する
 * @author shuichu_ishida
 */
public class GetOverTimeAndTransferOrder {

	/**
	 * 残業・振替の処理順序を取得する
	 * @param companyId 会社ID
	 * @param workTimeCode 就業時間帯コード
	 * @param reverse 逆時系列にする
	 * @return 残業振替区分リスト　（処理順）
	 */
	public static List<OverTimeAndTransferAtr> get(RequireM1 require, String companyId, String workTimeCode, boolean reverse) {
		
		// 代休振替設定を取得する
		val subHolTimeSets = GetSubHolOccurrenceSetting.process(require, 
				companyId, Optional.of(workTimeCode), CompensatoryOccurrenceDivision.FromOverTime);
		
		return subHolTimeSets.map(sht -> {
				
			// 代休振替設定．使用区分を取得する
			if (sht.isUseDivision() || sht.getSubHolTransferSetAtr() == SubHolTransferSetAtr.CERTAIN_TIME_EXC_SUB_HOL) {
				// 一定時間を超えたら代休とする時
				return getOrderForCertainTime(reverse);
			}
			
			return getOrderForSpecifiedTime(reverse);
		}).orElseGet(() -> {
			
			return getOrderForSpecifiedTime(reverse);
		});
	}

	// 一定時間を超えたら代休とする時
	private static List<OverTimeAndTransferAtr> getOrderForCertainTime(boolean reverse) {
		/**　逆時系列にするか確認する */
		if (reverse){
			return Arrays.asList(OverTimeAndTransferAtr.TRANSFER, OverTimeAndTransferAtr.OVER_TIME);
		} else {
			return Arrays.asList(OverTimeAndTransferAtr.OVER_TIME, OverTimeAndTransferAtr.TRANSFER);
		}
	}

	// 指定した時間を代休とする時
	private static List<OverTimeAndTransferAtr> getOrderForSpecifiedTime(boolean reverse) {
		/**　逆時系列にするか確認する */
		if (reverse){
			return Arrays.asList(OverTimeAndTransferAtr.OVER_TIME, OverTimeAndTransferAtr.TRANSFER);
		} else {
			return Arrays.asList(OverTimeAndTransferAtr.TRANSFER, OverTimeAndTransferAtr.OVER_TIME);
		}
	}

	public static interface RequireM1 extends GetSubHolOccurrenceSetting.Require {
		
	}
}
