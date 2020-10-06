package nts.uk.ctx.at.record.app.find.dialog.sixtyhourholiday;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriod;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.GetHolidayOver60hRemNumWithinPeriodImpl;
import nts.uk.ctx.at.record.dom.remainingnumber.holidayover60h.export.param.AggrResultOfHolidayOver60h;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.export.InterimRemainMngMode;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.holidayover60h.interim.TmpHolidayOver60hMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.RemainType;
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
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
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
	
	@Inject
	private TmpHolidayOver60hMngRepository tmpHolidayOver60hMngRepository;

	/**
	 * KDL017
	 *  アルゴリズム「60H超休の表示」を実行する
	 * 
	 * @param employeeId
	 * @param baseDate
	 * @return
	 */
	public OverTimeIndicationInformationDetailsDto getOverTimeIndicationInformationDetails(String employeeId,
			String baseDate) {
		val require = requireService.createRequire();
		val cacheCarrier = new CacheCarrier();
		String companyId = AppContexts.user().companyId();
		GeneralDate inputDate = GeneralDate.fromString(baseDate, "yyyyMMdd");
		OverTimeIndicationInformationDetailsDto result = new OverTimeIndicationInformationDetailsDto();

		// 10-5.60H超休の設定を取得する
		SixtyHourSettingOutput settingOutput = this.absenceTenProcessCommon.getSixtyHourSetting(companyId, employeeId, inputDate);

		// 60H超休管理区分　＝　取得した60H超休管理区分
		result.setDepartmentOvertime60H(settingOutput.isSixtyHourOvertimeMngDistinction());
		// IF 取得した60H超休管理区分　＝＝　Trueの場合
		if (!settingOutput.isSixtyHourOvertimeMngDistinction()) {
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
				, this.getDatePeroid(closingPeriod.start())
				, InterimRemainMngMode.MONTHLY
				, inputDate
				, Optional.empty()
				, Optional.empty()
				, Optional.empty());

		// ドメインモデル「暫定60H超休管理データ」を取得
		List<TmpHolidayOver60hMng> lstHolidayOver60hMngs = this.tmpHolidayOver60hMngRepository
				.getByEmployeeIdAndDatePeriodAndRemainType(employeeId
						, closingPeriod
						, RemainType.SIXTY_OVER_BREAK.value);

		// Step. 60超過時間表示情報詳細を作成
		List<RemainNumberDetailDto> remainNumberDetailDtos = aggrResultOfHolidayOver60h.getAsOfPeriodEnd().getGrantRemainingList().stream()
			.map(item -> {
				RemainNumberDetailDto remainNumberDetailDto = new RemainNumberDetailDto();
				// 残数情報．発生月　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．付与残数データ．60H超休付与残数データ．付与日の年月
				if (item.getGrantDate() != null) {
					remainNumberDetailDto.setOccurrenceMonth(item.getGrantDate().yearMonth());
				}

				// 残数情報．期限日　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．付与残数データ．60H超休付与残数データ．期限日
				remainNumberDetailDto.setDeadline(item.getDeadline());

				// 残数情報．発生時間　＝　取得した60H超休の集計結果．60H超休情報(期間終了日時点)．与残数データ．60H超休付与残数データ．詳細．付与数．時間
				if (item.getDetails().getGrantNumber().getMinutes().isPresent()) {
					remainNumberDetailDto.setOccurrenceTime(item.getDetails().getGrantNumber().getMinutes().get().v());
				}
				return remainNumberDetailDto;
			})
			.collect(Collectors.toList());

		remainNumberDetailDtos.addAll(lstHolidayOver60hMngs.stream().map(item -> {
			RemainNumberDetailDto mapResult = new RemainNumberDetailDto();
			// 残数情報．使用日　＝　取得した暫定60H超休管理データ．対象日
			mapResult.setUsageDate(item.getYmd());
	
			// 残数情報．使用時間　＝　取得した暫定60H超休管理データ．使用時間
			if (item.getUseTime().isPresent()) {
				mapResult.setUsageTime(item.getUseTime().get().v());
			}
			// 残数情報．作成区分　＝　取得した暫定60H超休管理データ．作成元区分
			mapResult.setCreationCategory(item.getCreatorAtr().value);
			return mapResult;
		})
		.collect(Collectors.toList()));

		result.setRemainNumberDetailDtos(remainNumberDetailDtos);

		// 繰越数　＝　60H超休．残数．繰越数
		result.setCarryoverNumber(aggrResultOfHolidayOver60h.getAsOfPeriodEnd().getRemainingNumber().getCarryForwardTimes().v());

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
		
		// 締め期間　＝　取得した期間
		result.setDeadline(closingPeriod);

		// 紐付け管理を作成
		List<PegManagementDto> pegManagementDtos = new ArrayList<>();

		// 作成した残数情報をループする
		remainNumberDetailDtos.stream().forEach(t -> {

			if (t.getOccurrenceMonth() != null) {
				// ループ中の発生月の取得した期間．開始日の日
				LocalDate startDate = LocalDate.of(t.getOccurrenceMonth().year()
						, t.getOccurrenceMonth().month()
						, closingPeriod.start().day());

				// ループ中の発生月+1月の取得した期間．開始日の日-1日
				LocalDate endDate = startDate.plusMonths(1).minusDays(1);

				remainNumberDetailDtos.stream().forEach(item -> {

					// ループ中の発生月の取得した期間．開始日の日　＜＝　ループ中の残数情報．使用日　＜＝　ループ中の発生月+1月の取得した期間．開始日の日-1日
					if (item.getUsageDate() != null
					 && (item.getUsageDate().localDate().isAfter(startDate) || item.getUsageDate().localDate().isEqual(startDate))
					 && (item.getUsageDate().localDate().isBefore(endDate) || item.getUsageDate().localDate().isEqual(endDate))) {
						// 紐付け管理を作成
						PegManagementDto pegManagementDto = new PegManagementDto();

						// ・発生年月　＝　ループ中の発生月
						pegManagementDto.setOccurrenceMonth(t.getOccurrenceMonth());
						// ・使用日　＝　ループ中の使用日
						pegManagementDto.setUsageDate(item.getUsageDate());
						// ・使用日　＝　ループ中の使用日
						pegManagementDto.setUsageNumber(t.getUsageTime());
						pegManagementDtos.add(pegManagementDto);
					}
				});
			}
		});

		result.setPegManagementDtos(pegManagementDtos);

		// return 作成した60超過時間表示情報詳細を返す
		return result;
	}

	private DatePeriod getDatePeroid(GeneralDate startDate) {
		return new DatePeriod(startDate, startDate.addYears(1).addDays(-1));
	}
}
