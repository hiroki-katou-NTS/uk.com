package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.ScheRecAtr;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

/** 対象の年月期間の管理期間の36協定時間を取得する */
public class AgreementTimeGetService {

	/**
	 * 対象の年月期間の管理期間の36協定時間を取得する
	 * @param sid 社員ID
	 * @param ymPeriod　年月期間
	 * @param baseDate　基準日
	 * @param preData 36協定時間
	 * @param scheRecAtr　予実区分
	 */
	public static void prepareData(RequireM2 require, String sid, YearMonthPeriod ymPeriod, GeneralDate baseDate,
			Map<YearMonth, AgreementTimeOfManagePeriod> preData, ScheRecAtr scheRecAtr) {
		
		/** 年月期間をループする */
		YearMonth currentYm = ymPeriod.start();
		while(currentYm.lessThanOrEqualTo(ymPeriod.end())) {
			
			/** パラメータ。36協定時間に処理中の年月のデータが存在するかを確認する */
			if (!preData.containsKey(currentYm)) {
				
				/** 指定月が締め処理済みかどうか判断 */
				if (isClosured(require, sid, currentYm)) {
					
					/** 「管理期間の36協定時間」を取得する */
					val agreementTime = require.agreementTimeOfManagePeriod(sid, currentYm);
					if (agreementTime.isPresent()) {
						
						/** パラメータ。36協定時間に入れる */
						preData.put(currentYm, agreementTime.get());
					}
					
				} else {
					
					/** 【NO.333】36協定時間の取得 */
					val agreementTime = GetAgreementTime.get(require, sid, currentYm, new ArrayList<>(), baseDate, scheRecAtr);
					
					/** パラメータ。36協定時間に入れる */
					preData.put(currentYm, agreementTime);
				}
			}
			
			currentYm = currentYm.addMonths(1);
		}
	}
	
	/** 指定月が締め処理済みかどうか判断 */
	public static boolean isClosured(RequireM1 require, String sid, YearMonth ym) {
		
		/** 「36協定運用設定」を取得する */
		val agreementSetting = require.agreementOperationSetting(AppContexts.user().companyId());
		if (!agreementSetting.isPresent()) {
			return false;
		}
		
		/** 年月から集計期間を取得 */
		val getPeriod = agreementSetting.map(as -> as.getAggregatePeriodByYearMonth(ym)).get(); 
		
		/** 最新の締め状態管理を取得 */
		val closureStatus = require.latestClosureStatusManagement(sid);
		
		/** 取得した締め状態管理。期間。終了日と取得した期間．終了日を比較して判断 */
		return closureStatus.map(cs -> getPeriod.end().beforeOrEquals(cs.getPeriod().end())).orElse(false);
	}
	
	public static interface RequireM1 {
		
		Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId);
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
	}
	
	public static interface RequireM2 extends RequireM1, GetAgreementTime.RequireM6 {
		
		Optional<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(String sid, YearMonth ym);
	}
}
