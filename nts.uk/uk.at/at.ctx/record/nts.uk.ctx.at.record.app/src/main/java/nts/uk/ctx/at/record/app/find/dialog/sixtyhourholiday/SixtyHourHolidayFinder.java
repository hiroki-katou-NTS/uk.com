package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SixtyHourSettingOutput;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The class SixtyHourHolidayFinder
 * 
 * @author LienPTK
 */
@Stateless
public class SixtyHourHolidayFinder {

	/** The absence ten process common. */
	@Inject
	private AbsenceTenProcessCommon absenceTenProcessCommon;

	/** The require service. */
	@Inject
	private RemainNumberTempRequireService requireService;
	
	/** The get holiday over 60 h rem num within period. */
	@Inject
	private GetHolidayOver60hRemNumWithinPeriod getHolidayOver60hRemNumWithinPeriod;

	/**
	 * KDL017
	 *  アルゴリズム「60H超休の表示」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public OverTimeIndicationInformationDetails getOverTimeIndicationInformationDetails(String employeeId,
			String baseDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");
		OverTimeIndicationInformationDetails result = new OverTimeIndicationInformationDetails();

		// 10-5.60H超休の設定を取得する
		SixtyHourSettingOutput settingOutput = this.absenceTenProcessCommon.getSixtyHourSetting(companyId, employeeId, inputDate);

		// IF 取得した60H超休管理区分　＝＝　Trueの場合
		if (!settingOutput.isSixtyHourOvertimeMngDistinction()) {
			// 60H超休管理区分　＝　取得した60H超休管理区分
			result.setDepartmentOvertime60H(false);
			// 他の項目がEmptyを渡す
			result.setPegManagementDtos(Collections.emptyList());
			result.setRemainNumberDetailDtos(Collections.emptyList());
			// 「60超過時間表示情報パラメータ」を返す
			return result;
		}

		// 社員に対応する締め期間を取得する
		DatePeriod closingPeriod = ClosureService.findClosurePeriod(require, cacheCarrier, employeeId, inputDate);

		// 期間中の60H超休残数を取得する
		GetHolidayOver60hRemNumWithinPeriod.RequireM1 require60h = new GetHolidayOver60hRemNumWithinPeriodImpl.GetHolidayOver60hRemNumWithinPeriodRequireM1();
		CacheCarrier cacheCarrier1 = new CacheCarrier();
		AggrResultOfHolidayOver60h aggrResultOfHolidayOver60h = this.getHolidayOver60hRemNumWithinPeriod.algorithm(require60h
				, cacheCarrier1
				, companyId
				, employeeId
				, closingPeriod
				, InterimRemainMngMode.MONTHLY
				, inputDate
				, Optional.empty()
				, Optional.empty()
				, Optional.empty());

		// 社員ID　＝　Input．社員ID
		

		// Step. 60超過時間表示情報詳細を作成
		List<RemainNumberDetailDto> remainNumberDetailDtos = aggrResultOfHolidayOver60h.getAsOfPeriodEnd().getGrantRemainingList().stream()
			.map(item -> {
				RemainNumberDetailDto remainNumberDetailDto = new RemainNumberDetailDto();
				// 残数情報．発生月　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．付与残数データ．60H超休付与残数データ．付与日の年月
				remainNumberDetailDto.setOccurrenceMonth(YearMonth.of(item.getGrantDate().localDate().getMonthValue()
						, item.getGrantDate().localDate().getYear()));

				// 残数情報．期限日　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．付与残数データ．60H超休付与残数データ．期限日
				remainNumberDetailDto.setDeadline(item.getDeadline());

				// 残数情報．発生時間　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．与残数データ．60H超休付与残数データ．詳細．付与数
//				remainNumberDetailDto.setOccurrenceTime(item.getDetails().getGrantNumber().getDays().v());

				// 残数情報．使用日　＝　取得した60H超休の集計結果．60H超休情報(消滅)．年月日
				remainNumberDetailDto.setUsageDate(aggrResultOfHolidayOver60h.getLapsed().getYmd());

				// 残数情報．使用時間　＝　取得した60H超休の集計結果．60H超休情報(消滅)．残数．60H超休(マイナスなし)．使用時間
				remainNumberDetailDto.setUsageTime(aggrResultOfHolidayOver60h.getLapsed()
												.getRemainingNumber()
												.getHolidayOver60hNoMinus()
												.getUsedTime().valueAsMinutes());
				// 残数情報．作成区分　＝　’実績’

				return remainNumberDetailDto;
			}).collect(Collectors.toList());


		// 繰越数　＝　60H超休．残数．繰越数
//		result.setCarryoverNumber(aggrResultOfHolidayOver60h.getAsOfPeriodEnd().getRemainingNumber());

		// 使用数　＝　60H超休．残数．60H超休(マイナスあり)．使用時間
		result.setUsageNumber(aggrResultOfHolidayOver60h.getAsOfPeriodEnd()
									.getRemainingNumber()
									.getHolidayOver60hWithMinus()
									.getUsedTime().v());

		// 残数　＝　60H超休．残数．60H超休(マイナスあり)．残時間
		result.setResidual(aggrResultOfHolidayOver60h.getAsOfPeriodEnd()
									.getRemainingNumber()
									.getHolidayOver60hWithMinus()
									.getRemainingTime().v());

		result.setRemainNumberDetailDtos(remainNumberDetailDtos);
		
		// 紐付け管理を作成
		List<PegManagementDto> pegManagementDtos = new ArrayList<>();
		
		result.setPegManagementDtos(pegManagementDtos);

		// return 作成した60超過時間表示情報詳細を返す
		return result;
	}
}
