package nts.uk.ctx.at.shared.dom.yearholidaygrant.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantReferenceDate;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantSimultaneity;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthOfService;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class LengthServiceTblServiceImpl implements LengthServiceTblService {
	@Inject
	private LengthServiceRepository lengthServiceRepository;

	@Override
	public List<NextAnnualLeaveGrant> calAnnualHdAwardDate(GeneralDate entryDate, GeneralDate standardDate, Period period, String yearHolidayCode,
			Optional<GeneralDate> simultaneousGrandMD, Optional<Boolean> singleDayFlag) {

		String companyId = AppContexts.user().companyId();
		List<NextAnnualLeaveGrant> dataResult = new ArrayList<>();

		// 勤続年数を取得
		Optional<LengthServiceTbl>table = lengthServiceRepository.findByCode(companyId, yearHolidayCode);
		if(!table.isPresent() || table.get().getLengthOfServices().isEmpty()) {
			return Collections.emptyList();
		}
		LengthServiceTbl lengthServiceTbl = table.get();

		// 勤続年数でループ
		for(int i = 0; i < lengthServiceTbl.getLengthOfServicesSize(); i++){
			// 勤続年数から付与日を計算
			NextAnnualLeaveGrant nextAnnualLeaveGrant = grantDateCalYearsService(lengthServiceTbl.getALengthOfService(i), entryDate, standardDate, simultaneousGrandMD, null);

			// 前回付与日←次回年休付与．付与年月日
			GeneralDate lastGrantDate = nextAnnualLeaveGrant.grantDate;

			// 期間中の年休付与かチェック
			StatusResult status = checkOnAnnualHolidaysDuringThePeriod(period, lastGrantDate);

			// 期間より前
			if(status == StatusResult.BEFORE_PERIOD) {
				continue;
			}

			// 期間内
			if(status == StatusResult.WITHIN_PERIOD) {
				// パラメータ「単一日フラグ」をチェック
				if(singleDayFlag != null && singleDayFlag.isPresent() || singleDayFlag.get()) {
					// 「次回年休付与」を「次回年休付与List」に追加
					dataResult.add(nextAnnualLeaveGrant);
				}
			}

			// 期間より後
			if(status == StatusResult.AFTER_PERIOD) {
				dataResult.add(nextAnnualLeaveGrant);
			}
		}
		return dataResult;
	}

	/**
	 * 勤続年数から付与日を計算
	 *
	 * @param data
	 * @param entryDate
	 * @param standardDate
	 * @param simultaneousGrandMD
	 * @param lastGrantDate
	 * @return
	 */
	public NextAnnualLeaveGrant grantDateCalYearsService(LengthOfService data, GeneralDate entryDate, GeneralDate standardDate,
			Optional<GeneralDate> simultaneousGrandMD, Optional<GeneralDate> lastGrantDate) {
		GeneralDate refDate = GeneralDate.today();
		GeneralDate lastDate = GeneralDate.today();
		NextAnnualLeaveGrant result = new NextAnnualLeaveGrant();

		// 「付与基準日」をチェック
		if(data.getStandGrantDay() == GrantReferenceDate.HIRE_DATE) {
			// 基準日←パラメータ「入社年月日」
			refDate = entryDate;
		} else {
			// 基準日←パラメータ「年休付与基準日」
			refDate = standardDate;
		}

		// 「一斉付与する」をチェック
		if(data.getAllowStatus() == GrantSimultaneity.USE) {
			// パラメータ「一斉付与日」が存在するかチェック
			if(simultaneousGrandMD != null && simultaneousGrandMD.isPresent()) {
				if(lastGrantDate != null && lastGrantDate.isPresent()) {
					// パラメータ「前回付与日」より後に該当日がないかチェック
					lastDate = lastGrantDate.get().addYears(data.getYear().v()).addMonths(data.getMonth().v());
					if(lastGrantDate.get().after(simultaneousGrandMD.get()) && simultaneousGrandMD.get().before(lastDate)) {
						// 年休付与日を計算
						// 付与基準年月日＋年数・月数以前で一番遅い一斉付与日（一斉付与扱い）
						GeneralDate tempDate = refDate.addYears(data.getYear().v()).addMonths(data.getMonth().v());
						if(simultaneousGrandMD.get().after(tempDate)) {
							refDate = GeneralDate.ymd(tempDate.year(), tempDate.month(), tempDate.day());
						} else {
							refDate = GeneralDate.ymd(tempDate.year() - 1, tempDate.month(), tempDate.day());
						}
					} else {
						// 年休付与日を計算
						// 付与基準年月日＋年数・月数
						refDate.addYears(data.getYear().v()).addMonths(data.getMonth().v());
					}
				}
			} else {
				// 年休付与日を計算
				// 付与基準年月日＋年数・月数
				refDate.addYears(data.getYear().v()).addMonths(data.getMonth().v());
			}
		} else {
			// 年休付与日を計算
			// 付与基準年月日＋年数・月数
			refDate.addYears(data.getYear().v()).addMonths(data.getMonth().v());
		}

		result.grantDate = refDate;
		// 回数をセット
		result.time = data.getGrantNum().v();
		result.grantDays = null;
		result.halfDayAnnualLeaveMaxTimes = null;
		result.timeAnnualLeaveMaxDays = null;
		result.timeAnnualLeaveMaxTime = null;

		return result;
	}

	/**
	 * 期間中の年休付与かチェック
	 *
	 * @param period
	 * @param grantDate
	 * @return
	 */
	private StatusResult checkOnAnnualHolidaysDuringThePeriod(Period period, GeneralDate grantDate) {
		// パラメータ「期間」と「付与年月日」を比較
		// 付与年月日<期間．開始日
		if(grantDate.before(period.getStartDate())) {
			return StatusResult.BEFORE_PERIOD;
		}

		// 期間．開始日<=付与年月日<=期間．終了日
		if(period.getStartDate().before(grantDate) && grantDate.before(period.getEndDate())) {
			return StatusResult.WITHIN_PERIOD;
		}

		// 期間.終了日<付与年月日
		if(period.getEndDate().before(grantDate)) {
			return StatusResult.AFTER_PERIOD;
		}

		return null;
	}
}
