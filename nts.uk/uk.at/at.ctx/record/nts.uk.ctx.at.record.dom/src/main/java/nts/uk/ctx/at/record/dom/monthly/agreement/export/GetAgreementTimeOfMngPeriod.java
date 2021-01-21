package nts.uk.ctx.at.record.dom.monthly.agreement.export;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.Year;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.AgreementTimeOfManagePeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.agreement.management.setting.AgreementOperationSetting;
import nts.uk.shr.com.context.AppContexts;

/**
 * 管理期間の36協定時間を取得
 * @author shuichi_ishida
 */
public class GetAgreementTimeOfMngPeriod {

	/**
	 * [NO676]管理期間の36協定時間を取得
	 * 管理期間の36協定時間を取得
	 * @param sid 社員ID
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	public static List<AgreementTimeOfManagePeriod> get(RequireM1 require, String sid, Year year) {
		
		val datas = get(require, Arrays.asList(sid), year);
		
		if (datas.containsKey(sid)) {
			
			return datas.get(sid);
		}
		
		return new ArrayList<>();
	}	
	
	
	/**
	 * 管理期間の36協定時間を取得
	 * @param sids 社員ID(List)
	 * @param year 年度
	 * @return 管理期間の36協定時間リスト
	 */
	public static Map<String,List<AgreementTimeOfManagePeriod>> get(RequireM1 require, List<String> sids, Year year) {
		
		/** ○ドメインモデル「３６協定運用設定」を取得 */
		val agreementSetting = require.agreementOperationSetting(AppContexts.user().companyId()).orElse(null);
		if (agreementSetting == null) {
			
			return new HashMap<>();
		}
		
		/** 年度から36協定の年月期間を取得 */
		val ymPeriod = agreementSetting.getYearMonthPeriod(year);
		
		/** [NO.612]年月期間を指定して管理期間の36協定時間を取得する */
		return get(require, sids, ymPeriod).stream().collect(Collectors.groupingBy(d -> d.getSid(), Collectors.toList()));
	}
	
	/**
	 * RequestList 612
	 * [NO.612]年月期間を指定して管理期間の36協定時間を取得する
	 * @param sids 社員ID(List)
	 * @param ymPeriod 年月期間
	 * @return
	 */
	public static List<AgreementTimeOfManagePeriod> get(RequireM2 require, List<String> sids, YearMonthPeriod ymPeriod) {
		
		/** ドメインモデル「管理期間の36協定時間」(List)を取得 */
		return require.agreementTimeOfManagePeriod(sids, ymPeriod.yearMonthsBetween());
	}
	
	public static interface RequireM1 extends RequireM2 {
		
		Optional<AgreementOperationSetting> agreementOperationSetting(String cid);
	}

	public static interface RequireM2 {
	
		List<AgreementTimeOfManagePeriod> agreementTimeOfManagePeriod(List<String> sids, List<YearMonth> yearMonths);
	}
}