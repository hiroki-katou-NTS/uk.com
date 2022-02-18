package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.remainmerge.RemainMerge;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainData;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.vacation.specialholiday.SpecialHolidayRemainDataRepository;

@Stateless
public class SpecialHolidayRemainDataSeviceImpl implements SpecialHolidayRemainDataSevice{
	@Inject
	private SpecialHolidayRemainDataRepository speRemainDataRepo;

	@Override
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth,
			YearMonth endMonth) {
//		List<SpecialHolidayRemainDataOutput> lstOutData = new ArrayList<>();
//		for (YearMonth month = startMonth; month.lessThanOrEqualTo(endMonth); month = month.addMonths(1)) {
//
//			//ドメインモデル「特別休暇月別残数データ」を取得
//			List<SpecialHolidayRemainData> lstRemainData = speRemainDataRepo.findByYearMonthOrderByStartYmd(sid, month);
//			for (SpecialHolidayRemainData remainData : lstRemainData) {
//				SpecialHolidayRemainDataOutput dataOut = new SpecialHolidayRemainDataOutput();
//				List<SpecialHolidayRemainDataOutput> lstTmp = new ArrayList<>();
//				for (SpecialHolidayRemainDataOutput tmpData : lstOutData) {
//					if(tmpData.getSpecialHolidayCd() == remainData.getSpecialHolidayCd() && tmpData.getYm() == month && tmpData.getSid() == remainData.getSid()) {
//						lstTmp.add(tmpData);
//					}
//				}
//				if(lstTmp.isEmpty()) {
//					dataOut.setSid(sid);
//					dataOut.setYm(month);
//					dataOut.setSpecialHolidayCd(remainData.getSpecialHolidayCd());
//					dataOut.setUseDays(remainData.getSpecialLeave().getRemain().getDays().v());
//					dataOut.setBeforeUseDays(remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
//					dataOut.setAfterUseDays(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0);
//					if (remainData.getSpecialLeave().getUseNumber().getUseTimes().isPresent()){
//						val useTimes = remainData.getSpecialLeave().getUseNumber().getUseTimes().get();
//						dataOut.setUseTimes(useTimes.getUseTimes().v());
//						dataOut.setBeforeUseTimes(useTimes.getBeforeUseGrantTimes().v());
//						dataOut.setAfterUseTimes(useTimes.getAfterUseGrantTimes().isPresent() ? useTimes.getAfterUseGrantTimes().get().v() : 0 );
//						dataOut.setUseNumber(useTimes.getUseNumber().v());
//						dataOut.setFactUseNumber(useTimes.getUseNumber().v());
//					}
//					else {
//						dataOut.setUseTimes(0);
//						dataOut.setBeforeUseTimes(0);
//						dataOut.setAfterUseTimes(0);
//						dataOut.setUseNumber(0);
//						dataOut.setFactUseNumber(0);
//					}
//					dataOut.setFactUseDays(remainData.getActualSpecial().getUseNumber().getUseDays().getUseDays().v());
//					dataOut.setRemainDays(remainData.getSpecialLeave().getRemain().getDays().v());
//					dataOut.setRemainTimes(remainData.getSpecialLeave().getRemain().getTime().isPresent() ? remainData.getSpecialLeave().getRemain().getTime().get().v() : 0);
//					dataOut.setFactRemainDays(remainData.getActualSpecial().getRemain().getDays().v());
//					dataOut.setFactRemainTimes(remainData.getActualSpecial().getRemain().getTime().isPresent() ? remainData.getActualSpecial().getRemain().getTime().get().v() : 0);
//					dataOut.setBeforeRemainDays(remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
//					dataOut.setBeforeRemainTimes(remainData.getSpecialLeave().getBeforeRemainGrant().getTime().isPresent() ? remainData.getSpecialLeave().getBeforeRemainGrant().getTime().get().v() : 0);
//					dataOut.setBeforeFactRemainDays(remainData.getActualSpecial().getBeforRemainGrant().getDays().v());
//					dataOut.setBeforeFactRemainTimes(remainData.getActualSpecial().getBeforRemainGrant().getTime().isPresent() ? remainData.getActualSpecial().getBeforRemainGrant().getTime().get().v() : 0);
//					dataOut.setAfterRemainDays(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0);
//					dataOut.setAfterRemainTimes(remainData.getSpecialLeave().getAfterRemainGrant().isPresent() && remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().get().v() : 0);
//					dataOut.setAfterFactRemainDays(remainData.getActualSpecial().getAfterRemainGrant().isPresent() ? remainData.getActualSpecial().getAfterRemainGrant().get().getDays().v() : 0);
//					dataOut.setNotUseDays(remainData.getSpecialLeave().getUnDegestionNumber().getDays().v());
//					dataOut.setNotUseTime(remainData.getSpecialLeave().getUnDegestionNumber().getTimes().isPresent() ? remainData.getSpecialLeave().getUnDegestionNumber().getTimes().get().v() : 0);
//					dataOut.setGrantAtr(remainData.isGrantAtr() ? 1 : 0);
//					dataOut.setGrantDays(remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0);
//					lstOutData.add(dataOut);
//				} else {
//					SpecialHolidayRemainDataOutput tmp = lstTmp.get(0);
//					lstOutData.remove(tmp);
//					tmp.setUseDays(tmp.getUseDays() + remainData.getSpecialLeave().getRemain().getDays().v());
//					tmp.setBeforeUseDays(tmp.getBeforeUseDays() + remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
//					tmp.setAfterUseDays(tmp.getAfterUseDays() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0));
//					if (remainData.getSpecialLeave().getUseNumber().getUseTimes().isPresent()){
//						val useTimes = remainData.getSpecialLeave().getUseNumber().getUseTimes().get();
//						tmp.setUseTimes(tmp.getUseTimes() + useTimes.getUseTimes().v());
//						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes() + useTimes.getBeforeUseGrantTimes().v());
//						tmp.setAfterUseTimes(tmp.getAfterUseTimes() + (useTimes.getAfterUseGrantTimes().isPresent() ? useTimes.getAfterUseGrantTimes().get().v() : 0 ));
//						tmp.setUseNumber(tmp.getUseNumber() + useTimes.getUseNumber().v());
//						tmp.setFactUseNumber(tmp.getFactUseNumber() + useTimes.getUseNumber().v());
//					}
//					else {
//						tmp.setUseTimes(tmp.getUseTimes());
//						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes());
//						tmp.setAfterUseTimes(tmp.getAfterUseTimes());
//						tmp.setUseNumber(tmp.getUseNumber());
//						tmp.setFactUseNumber(tmp.getFactUseNumber());
//					}
//					tmp.setFactUseDays(tmp.getFactUseDays() + remainData.getActualSpecial().getUseNumber().getUseDays().getUseDays().v());
//					tmp.setRemainDays(tmp.getRemainDays() + remainData.getSpecialLeave().getRemain().getDays().v());
//					tmp.setRemainTimes(tmp.getRemainTimes() + (remainData.getSpecialLeave().getRemain().getTime().isPresent() ? remainData.getSpecialLeave().getRemain().getTime().get().v() : 0));
//					tmp.setFactRemainDays(tmp.getFactRemainDays() + remainData.getActualSpecial().getRemain().getDays().v());
//					tmp.setFactRemainTimes(tmp.getAfterFactRemainTimes() + (remainData.getActualSpecial().getRemain().getTime().isPresent() ? remainData.getActualSpecial().getRemain().getTime().get().v() : 0));
//					tmp.setBeforeRemainDays(tmp.getBeforeRemainDays() + remainData.getSpecialLeave().getBeforeRemainGrant().getDays().v());
//					tmp.setBeforeRemainTimes(tmp.getBeforeRemainTimes() + (remainData.getSpecialLeave().getBeforeRemainGrant().getTime().isPresent() ? remainData.getSpecialLeave().getBeforeRemainGrant().getTime().get().v() : 0));
//					tmp.setBeforeFactRemainDays(tmp.getBeforeFactRemainDays() + remainData.getActualSpecial().getBeforRemainGrant().getDays().v());
//					tmp.setBeforeFactRemainTimes(tmp.getBeforeFactRemainTimes() + (remainData.getActualSpecial().getBeforRemainGrant().getTime().isPresent() ? remainData.getActualSpecial().getBeforRemainGrant().getTime().get().v() : 0));
//					tmp.setAfterRemainDays(tmp.getAfterRemainDays() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getDays().v() : 0));
//					tmp.setAfterRemainTimes(tmp.getAfterRemainTimes() + (remainData.getSpecialLeave().getAfterRemainGrant().isPresent() && remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().isPresent() ? remainData.getSpecialLeave().getAfterRemainGrant().get().getTime().get().v() : 0));
//					tmp.setAfterFactRemainDays(tmp.getAfterFactRemainDays() + (remainData.getActualSpecial().getAfterRemainGrant().isPresent() ? remainData.getActualSpecial().getAfterRemainGrant().get().getDays().v() : 0));
//					tmp.setNotUseDays(tmp.getNotUseDays() + remainData.getSpecialLeave().getUnDegestionNumber().getDays().v());
//					tmp.setNotUseTime(tmp.getNotUseTime() + (remainData.getSpecialLeave().getUnDegestionNumber().getTimes().isPresent() ? remainData.getSpecialLeave().getUnDegestionNumber().getTimes().get().v() : 0));
//					tmp.setGrantAtr(tmp.getGrantAtr() == 1 ? 1 : (remainData.isGrantAtr() ? 1 : 0));
//					tmp.setGrantDays(tmp.getGrantDays() + (remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0));
//					lstOutData.add(tmp);
//				}
//
//			}
//		}
//		return lstOutData;


		List<SpecialHolidayRemainDataOutput> lstOutData = new ArrayList<>();
		for (YearMonth month = startMonth; month.lessThanOrEqualTo(endMonth); month = month.addMonths(1)) {

			//ドメインモデル「特別休暇月別残数データ」を取得
			List<SpecialHolidayRemainData> lstRemainData = speRemainDataRepo.findByYearMonthOrderByStartYmd(sid, month);
			for (SpecialHolidayRemainData remainData : lstRemainData) {
				SpecialHolidayRemainDataOutput dataOut = new SpecialHolidayRemainDataOutput();
				List<SpecialHolidayRemainDataOutput> lstTmp = new ArrayList<>();
				for (SpecialHolidayRemainDataOutput tmpData : lstOutData) {
					if(tmpData.getSpecialHolidayCd() == remainData.getSpecialHolidayCd() && tmpData.getYm() == month && tmpData.getSid() == remainData.getSid()) {
						lstTmp.add(tmpData);
					}
				}
				if(lstTmp.isEmpty()) { // 以下、同一コードあり（１/３）

					/** 特別休暇月別残数データ.社員ID */
					dataOut.setSid(sid);

					/** 特別休暇月別残数データ.年月 */
					dataOut.setYm(month);

					/** 特別休暇月別残数データ.締めID */
					dataOut.setSpecialHolidayCd(remainData.getSpecialHolidayCd());

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
					dataOut.setUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
					dataOut.setBeforeUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用日数.使用日数 */
					dataOut.setAfterUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用時間.使用時間 */
					dataOut.setUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						dataOut.setUseTimes(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用時間.使用時間 */
					dataOut.setBeforeUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
						remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes();
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用時間.使用時間 */
					dataOut.setAfterUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
						if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
							dataOut.setAfterUseTimes(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v());
						}
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.特休使用回数 （1日2回使用した場合２回でカウント） */
					dataOut.setUseNumber(0);
					dataOut.setFactUseNumber(0);
					dataOut.setUseNumber(remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					dataOut.setFactUseNumber(remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());

					// 要修正 jinno 保留　特休使用日数 （1日2回使用した場合１回でカウント）
//					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
//					c.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedDayTimes();

					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
					dataOut.setFactUseDays(remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残日数 */
					dataOut.setRemainDays(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残時間 */
					dataOut.setRemainTimes(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残日数 */
					dataOut.setFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残時間 */
					dataOut.setFactRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残日数 */
					dataOut.setBeforeRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残時間 */
					dataOut.setBeforeRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残日数 */
					dataOut.setBeforeFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残時間 */
					dataOut.setBeforeRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残日数 */
					dataOut.setAfterRemainDays(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残時間 */
					dataOut.setAfterRemainTimes(
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残日数 */
					dataOut.setAfterFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残時間 */
					dataOut.setAfterFactRemainTimes(
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.未消化数.日数 */
					dataOut.setNotUseDays(remainData.getUnDegestionNumber().getDays().v());

					/** 特別休暇月別残数データ.未消化数.時間 */
					dataOut.setNotUseTime(remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0);

					/** 特別休暇月別残数データ.付与区分 */
					dataOut.setGrantAtr(remainData.isGrantAtr() ? 1 : 0);

					/** 特別休暇月別残数データ.特別休暇付与情報: 付与日数 */
					dataOut.setGrantDays(remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0);

					lstOutData.add(dataOut);

				} else {

					SpecialHolidayRemainDataOutput tmp = lstTmp.get(0);
					lstOutData.remove(tmp);

					tmp.setUseDays(tmp.getUseDays() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setBeforeUseDays(tmp.getBeforeUseDays() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setAfterUseDays(tmp.getAfterUseDays() + (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ? remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0));
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						tmp.setUseTimes(tmp.getUseTimes() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
						tmp.setAfterUseTimes(tmp.getAfterUseTimes()
								+ (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()
										&& remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()
										? remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v() : 0 ));

						tmp.setUseNumber(tmp.getUseNumber() + remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
						tmp.setFactUseNumber(tmp.getFactUseNumber() + remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					}
					else {
						tmp.setUseTimes(tmp.getUseTimes());
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes());
						tmp.setAfterUseTimes(tmp.getAfterUseTimes());
						tmp.setUseNumber(tmp.getUseNumber());
						tmp.setFactUseNumber(tmp.getFactUseNumber());
					}

					tmp.setFactUseDays(tmp.getFactUseDays() + remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setRemainDays(tmp.getRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setRemainTimes(tmp.getRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setFactRemainDays(tmp.getFactRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setFactRemainTimes(tmp.getFactRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setBeforeRemainDays(tmp.getBeforeRemainDays() + remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
					tmp.setBeforeRemainTimes(tmp.getBeforeRemainTimes() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
					tmp.setBeforeFactRemainDays(tmp.getBeforeFactRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
					tmp.setBeforeFactRemainTimes(tmp.getBeforeFactRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
					tmp.setAfterRemainDays(tmp.getAfterRemainDays() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					tmp.setAfterRemainTimes(tmp.getAfterRemainTimes() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0));
					tmp.setAfterFactRemainDays(tmp.getAfterFactRemainDays() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					tmp.setNotUseDays(tmp.getNotUseDays() + remainData.getUnDegestionNumber().getDays().v());
					tmp.setNotUseTime(tmp.getNotUseTime() + (remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0));
					tmp.setGrantAtr(tmp.getGrantAtr() == 1 ? 1 : (remainData.isGrantAtr() ? 1 : 0));
					tmp.setGrantDays(tmp.getGrantDays() + (remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0));

					lstOutData.add(tmp);
				}
			}
		}
		return lstOutData;
	}

	@Override
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfPeriodAndCodes(String sid, YearMonth startMonth,
			YearMonth endMonth, List<Integer> speCodes) {
		List<SpecialHolidayRemainDataOutput> lstOutput = new ArrayList<>();
		speCodes.stream().forEach(x -> {
			List<SpecialHolidayRemainDataOutput> lstOutputCode = this.getSpeHoliOfConfirmedMonthly(sid, startMonth, endMonth, x);
			if(!lstOutputCode.isEmpty()) {
				lstOutput.addAll(lstOutputCode);
			}
		});
		return lstOutput;
	}
	@Override
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth,
			YearMonth endMonth, Integer speCode) {

		List<SpecialHolidayRemainDataOutput> lstOutData = new ArrayList<>();
		for (YearMonth month = startMonth; month.lessThanOrEqualTo(endMonth); month = month.addMonths(1)) {

			//ドメインモデル「特別休暇月別残数データ」を取得
			List<SpecialHolidayRemainData> lstRemainData = speRemainDataRepo.getByYmCode(sid, month, speCode);
			for (SpecialHolidayRemainData remainData : lstRemainData) {
				SpecialHolidayRemainDataOutput dataOut = new SpecialHolidayRemainDataOutput();
				List<SpecialHolidayRemainDataOutput> lstTmp = new ArrayList<>();
				for (SpecialHolidayRemainDataOutput tmpData : lstOutData) {
					if(tmpData.getSpecialHolidayCd() == remainData.getSpecialHolidayCd() && tmpData.getYm() == month && tmpData.getSid() == remainData.getSid()) {
						lstTmp.add(tmpData);
					}
				}
				if(lstTmp.isEmpty()) { // 以下、同一コードあり（１/３）

					/** 特別休暇月別残数データ.社員ID */
					dataOut.setSid(sid);

					/** 特別休暇月別残数データ.年月 */
					dataOut.setYm(month);

					/** 特別休暇月別残数データ.締めID */
					dataOut.setSpecialHolidayCd(remainData.getSpecialHolidayCd());

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
					dataOut.setUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数.使用日数 */
					dataOut.setBeforeUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用日数.使用日数 */
					dataOut.setAfterUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.使用時間.使用時間 */
					dataOut.setUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						dataOut.setUseTimes(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用時間.使用時間 */
					dataOut.setBeforeUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
						remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes();
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用時間.使用時間 */
					dataOut.setAfterUseTimes(0);
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
						if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
							dataOut.setAfterUseTimes(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v());
						}
					}

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.特休使用回数 （1日2回使用した場合２回でカウント） */
					dataOut.setUseNumber(0);
					dataOut.setFactUseNumber(0);
					dataOut.setUseNumber(remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					dataOut.setFactUseNumber(remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());

					// 要修正 jinno 保留　特休使用日数 （1日2回使用した場合１回でカウント）
//					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
//					c.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedDayTimes();

					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.使用日数.使用日数 */
					dataOut.setFactUseDays(remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残日数 */
					dataOut.setRemainDays(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.合計.合計残時間 */
					dataOut.setRemainTimes(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残日数 */
					dataOut.setFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.合計.合計残時間 */
					dataOut.setFactRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残日数 */
					dataOut.setBeforeRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残時間 */
					dataOut.setBeforeRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残日数 */
					dataOut.setBeforeFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.合計残時間 */
					dataOut.setBeforeRemainTimes(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残日数 */
					dataOut.setAfterRemainDays(remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.合計残時間 */
					dataOut.setAfterRemainTimes(
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残日数 */
					dataOut.setAfterFactRemainDays(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.合計残時間 */
					dataOut.setAfterFactRemainTimes(
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.未消化数.日数 */
					dataOut.setNotUseDays(remainData.getUnDegestionNumber().getDays().v());

					/** 特別休暇月別残数データ.未消化数.時間 */
					dataOut.setNotUseTime(remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0);

					/** 特別休暇月別残数データ.付与区分 */
					dataOut.setGrantAtr(remainData.isGrantAtr() ? 1 : 0);

					/** 特別休暇月別残数データ.特別休暇付与情報: 付与日数 */
					dataOut.setGrantDays(remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0);

					lstOutData.add(dataOut);

				} else {

					SpecialHolidayRemainDataOutput tmp = lstTmp.get(0);
					lstOutData.remove(tmp);

					tmp.setUseDays(tmp.getUseDays() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setBeforeUseDays(tmp.getBeforeUseDays() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setAfterUseDays(tmp.getAfterUseDays() + (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ? remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0));
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						tmp.setUseTimes(tmp.getUseTimes() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes() + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
						tmp.setAfterUseTimes(tmp.getAfterUseTimes()
								+ (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()
										&& remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()
										? remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v() : 0 ));

						tmp.setUseNumber(tmp.getUseNumber() + remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
						tmp.setFactUseNumber(tmp.getFactUseNumber() + remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					}
					else {
						tmp.setUseTimes(tmp.getUseTimes());
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes());
						tmp.setAfterUseTimes(tmp.getAfterUseTimes());
						tmp.setUseNumber(tmp.getUseNumber());
						tmp.setFactUseNumber(tmp.getFactUseNumber());
					}

					tmp.setFactUseDays(tmp.getFactUseDays() + remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setRemainDays(tmp.getRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setRemainTimes(tmp.getRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setFactRemainDays(tmp.getFactRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setFactRemainTimes(tmp.getFactRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setBeforeRemainDays(tmp.getBeforeRemainDays() + remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
					tmp.setBeforeRemainTimes(tmp.getBeforeRemainTimes() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
					tmp.setBeforeFactRemainDays(tmp.getBeforeFactRemainDays() + remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
					tmp.setBeforeFactRemainTimes(tmp.getBeforeFactRemainTimes() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
					tmp.setAfterRemainDays(tmp.getAfterRemainDays() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					tmp.setAfterRemainTimes(tmp.getAfterRemainTimes() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
							&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
									remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0));
					tmp.setAfterFactRemainDays(tmp.getAfterFactRemainDays() + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
							remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					tmp.setNotUseDays(tmp.getNotUseDays() + remainData.getUnDegestionNumber().getDays().v());
					tmp.setNotUseTime(tmp.getNotUseTime() + (remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0));
					tmp.setGrantAtr(tmp.getGrantAtr() == 1 ? 1 : (remainData.isGrantAtr() ? 1 : 0));
					tmp.setGrantDays(tmp.getGrantDays() + (remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0));

					lstOutData.add(tmp);
				}
			}
		}
		return lstOutData;
	}


//
/////////////////////////////////////////////////////////////////////
// 2022.02.01 #120673 稲熊 変更 START
/////////////////////////////////////////////////////////////////////
	/**
	 * @author hoatt
	 * Doi ung response KDR001
	 * RequestList263: 社員の月毎の確定済み特別休暇を取得する - ver2
	 * @param sid 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth 年月期間
	 * @return
	 */
	@Override
	public List<SpecialHolidayRemainDataOutput> getSpeHdOfConfMonVer2(String sid, YearMonthPeriod period, Map<YearMonth, List<RemainMerge>> mapRemainMer) {
		List<SpecialHolidayRemainDataOutput> lstOutData = new ArrayList<>();
		//
		// 月別実績データの数の分ループ
		for (Map.Entry<YearMonth, List<RemainMerge>> entry : mapRemainMer.entrySet()) {
			//
			// ドメインモデル「特別休暇月別残数データ」を１レコード分抽出
			List<SpecialHolidayRemainData> lstRemainData = new ArrayList<>();
			for(RemainMerge speMer : entry.getValue()){
				lstRemainData.addAll(speMer.getSpecialHolidayRemainData());
			}
			// 年月
			YearMonth month = entry.getKey();
			// 
			// 特別休暇コード分ループ
			for (SpecialHolidayRemainData remainData : lstRemainData) {
				SpecialHolidayRemainDataOutput dataOut = new SpecialHolidayRemainDataOutput();
				List<SpecialHolidayRemainDataOutput> lstTmp = new ArrayList<>();
				// 
				for (SpecialHolidayRemainDataOutput tmpData : lstOutData) {
					if(tmpData.getSpecialHolidayCd() == remainData.getSpecialHolidayCd() &&		// 特別休暇コード
					   tmpData.getYm()               == month &&                            	// 年月
					   tmpData.getSid()              == remainData.getSid()) {					// 社員ID
						lstTmp.add(tmpData);
					}
				}
				///////////////////////////////////////////////////////////////////////////////////////////////
				// 通常は、月１回締めなので１レコードのみのデータセットをする
				///////////////////////////////////////////////////////////////////////////////////////////////
				if(lstTmp.isEmpty()) { // 以下、同一コードあり（１/３）

					/** 特別休暇月別残数データ.社員ID */
					dataOut.setSid(sid);

					/** 特別休暇月別残数データ.年月 */
					dataOut.setYm(month);

					/** 特別休暇月別残数データ.特別休暇コード */
					dataOut.setSpecialHolidayCd(remainData.getSpecialHolidayCd());
					//
					//================================================================================/
					// 使用数
					//================================================================================/
					//////////////////////////////////////
					// 使用数  特別休暇
					//////////////////////////////////////
					//*********************/
					//       (日数)       */
					//*********************/
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計　.使用日数 */
					dataOut.setUseDays      (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数 */
					dataOut.setBeforeUseDays(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用日数 */
					dataOut.setAfterUseDays (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ?
											 remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0);
					//*********************/
					//       (時間)       */
					//*********************/
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計　.使用時間 */
							dataOut.setUseTimes(0);
					if (                              remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
							dataOut.setUseTimes(      remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
					}
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用時間 */
							dataOut.setBeforeUseTimes(0);
					if (                              remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
							dataOut.setBeforeUseTimes(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
					}
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用時間 */
							dataOut.setAfterUseTimes(0);
					if (                              remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
						if (                          remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
							dataOut.setAfterUseTimes( remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v());
						}
					}
					//
					//////////////////////////////////////
					// 使用回数  特別休暇
					//////////////////////////////////////
					//
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.時間休暇使用回数 （1日2回使用した場合２回でカウント） */
					dataOut.setUseNumber(0);
					dataOut.setUseNumber(remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					//
					// 要修正 jinno 保留　特休使用日数 （1日2回使用した場合１回でカウント）
//					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.特休使用日数 （1日2回使用した場合１回でカウント） */
//					c.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedDayTimes();

					//
					//////////////////////////////////////
					// 使用数  実特別休暇
					//////////////////////////////////////
					//*********************/
					//       (日数)       */
					//*********************/
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.　使用日数 */
					dataOut.setFactUseDays      (		remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					//
					//======================================
					// == 2022.02.01 #120673 稲熊 追加 START
					//======================================
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用日数 */
					dataOut.setBeforeFactUseDays(		remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));

					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用日数 */
					dataOut.setAfterFactUseDays(		remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ?
											 			remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0);
					//*********************/
					//       (時間)       */
					//*********************/
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計　.使用時間 */
							dataOut.setFactUseTimes(0);
					if (                                  remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
							dataOut.setFactUseTimes(      remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
					}
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用時間 */
							dataOut.setBeforeFactUseTimes(0);
					if (                                  remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().isPresent()){
							dataOut.setBeforeFactUseTimes(remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
					}
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用時間 */
							dataOut.setAfterFactUseTimes(0);
					if (                                  remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()){
						if (                              remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()){
							dataOut.setAfterFactUseTimes( remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v());
						}
					}
					//======================================
					// == 2022.02.01 #120673 稲熊 追加 END
					//======================================
					//
					//////////////////////////////////////
					// 使用回数  実特別休暇
					//////////////////////////////////////
					//
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.時間休暇使用回数 （1日2回使用した場合２回でカウント） */
					dataOut.setFactUseNumber(0);
					dataOut.setFactUseNumber(remainData.getActualSpecial().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					//

					//
					//================================================================================/
					// 残数
					//================================================================================/
					//////////////////////////////////////
					// 残数  特別休暇
					//////////////////////////////////////
					//*********************/
					//       (日数)       */
					//*********************/
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.残日数 */
					dataOut.setBeforeRemainDays(	remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.残日数 */
					dataOut.setAfterRemainDays(		remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
													remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.残日数　(期末:後締めの残数) */
//del 2022.02.01	dataOut.setRemainDays(          remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					dataOut.setRemainDays(			remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
													dataOut.getAfterRemainDays()
												  : dataOut.getBeforeRemainDays() );
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////
					//
					//*********************/
					//       (時間)       */
					//*********************/
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.残時間 */
					dataOut.setBeforeRemainTimes(	remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
													remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.残時間 */
					dataOut.setAfterRemainTimes(	remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
												 && remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
													remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.残時間　(期末:後締めの残数) */
//del 2022.02.01	dataOut.setRemainTimes(			remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
//del 2022.02.01						   			remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);
					dataOut.setRemainTimes(			remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
												 && remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
													dataOut.getAfterRemainTimes()
												  : dataOut.getBeforeRemainTimes() );
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////
					//
					//////////////////////////////////////
					// 残数  実特別休暇
					//////////////////////////////////////
					//*********************/
					//       (日数)       */
					//*********************/
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.残日数 */
					dataOut.setBeforeFactRemainDays(	remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.残日数 */
					dataOut.setAfterFactRemainDays(		remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
												    	remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0);

					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.残日数　(期末:後締めの残数) */
//del 2022.02.01	dataOut.setFactRemainDays(			remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					dataOut.setFactRemainDays(			remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
												    	dataOut.getAfterFactRemainDays()
													  : dataOut.getBeforeFactRemainDays() );
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////
					//
					//*********************/
					//       (時間)       */
					//*********************/
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.残時間 */
					dataOut.setBeforeFactRemainTimes(	remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
												 		remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0);

					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.残時間 */
					dataOut.setAfterFactRemainTimes(	remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
													 && remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
														remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0);

					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.残時間　(期末:後締めの残数) */
//del 2022.02.01	dataOut.setFactRemainTimes(			remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
//del 2022.02.01							   			remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0);
					dataOut.setFactRemainTimes(			remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
													 && remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
												    	dataOut.getAfterFactRemainTimes()
													  : dataOut.getBeforeFactRemainTimes() );
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////

					//================================================================================/
					//
					////////////////////////////////////////////////////////////////////////////////
					// 2022.02.01 #120673 稲熊 追加 START
					//////////////////////////////////////////
					/**	optional 実特別休暇．残数付与後		*/
					//////////////////////////////////////////
					dataOut.setOptAfterFactRemain(		remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() );
					// 2022.02.01 #120673 稲熊 追加 END
					////////////////////////////////////////////////////////////////////////////////
					//
					//////////////////////////////////////
					// 未消化数
					//////////////////////////////////////
					//
					/** 特別休暇月別残数データ.未消化数.日数 */
					dataOut.setNotUseDays(remainData.getUnDegestionNumber().getDays().v());

					/** 特別休暇月別残数データ.未消化数.時間 */
					dataOut.setNotUseTime(remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0);
					//
					//////////////////////////////////////
					// 付与区分
					//////////////////////////////////////
					/** 特別休暇月別残数データ.付与区分 */
					dataOut.setGrantAtr(remainData.isGrantAtr() ? 1 : 0);
					//
					//////////////////////////////////////
					// 付与日数
					//////////////////////////////////////
					/** 特別休暇月別残数データ.特別休暇付与情報.付与日数 */
					dataOut.setGrantDays(remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0);

					lstOutData.add(dataOut);
					//////////////////////////////////////
				}
				else {
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// 月２回目の締めがある場合は、２レコード目のデータを合算する。(残数は、最後に締めたデータを採用する)
				//////////////////////////////////////////////////////////////////////////////////////////////////////////////
					SpecialHolidayRemainDataOutput tmp = lstTmp.get(0);
					lstOutData.remove(tmp);

					//================================================================================/
					// 使用数  (合算する)
					//================================================================================/
					//////////////////////////////////////
					// 使用数  特別休暇  (日数)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.　使用日数 */
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用日数 */
					/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用日数 */
					tmp.setUseDays      (tmp.getUseDays()       +  remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setBeforeUseDays(tmp.getBeforeUseDays() +  remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setAfterUseDays (tmp.getAfterUseDays()  + (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ? 
					                                               remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0));

					//////////////////////////////////////
					// 使用数  特別休暇  (時間)
					//////////////////////////////////////
					if (remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.合計.　使用時間 */
						/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与前.使用時間 */
						/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.付与後.使用時間 */
						tmp.setUseTimes      (tmp.getUseTimes()      + remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes()+ remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
						tmp.setAfterUseTimes (tmp.getAfterUseTimes() +(remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()
																	&& remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()
																	 ? remainData.getSpecialLeave().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v() : 0 ));

						//////////////////////////////////////
						// 使用回数  特別休暇
						//////////////////////////////////////
						/** 特別休暇月別残数データ.特別休暇.特別休暇使用情報.時間休暇使用回数 */
						tmp.setUseNumber     (tmp.getUseNumber()      +  remainData.getSpecialLeave().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					}
					else {
						tmp.setUseTimes      (tmp.getUseTimes()       );
						tmp.setBeforeUseTimes(tmp.getBeforeUseTimes() );
						tmp.setAfterUseTimes (tmp.getAfterUseTimes()  );
						tmp.setUseNumber     (tmp.getUseNumber()      );
					}

					//////////////////////////////////////
					// 使用数  実特別休暇  (日数)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.　使用日数 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用日数 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用日数 */
					tmp.setFactUseDays      (tmp.getFactUseDays()      + remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber()             .getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setBeforeFactUseDays(tmp.getBeforeFactUseDays()+ remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant()  .getUseDays().map(x -> x.v()).orElse(0.0));
					tmp.setAfterFactUseDays (tmp.getAfterFactUseDays() +(remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent() ? 
					                                                     remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseDays().map(x -> x.v()).orElse(0.0) : 0));

					//////////////////////////////////////
					// 使用数  実特別休暇  (時間)
					//////////////////////////////////////
					if (remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().isPresent()){
						/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.合計.　使用時間 */
						/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与前.使用時間 */
						/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.付与後.使用時間 */
						tmp.setFactUseTimes      (tmp.getFactUseTimes()      +  remainData.getActualSpecial().getUsedNumberInfo().getUsedNumber().getUseTimes().get().getUseTimes().v());
						tmp.setBeforeFactUseTimes(tmp.getBeforeFactUseTimes()+  remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberBeforeGrant().getUseTimes().get().getUseTimes().v());
						tmp.setAfterFactUseTimes (tmp.getAfterFactUseTimes()
																			 + (remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().isPresent()
																			 && remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().isPresent()
																			  ? remainData.getActualSpecial().getUsedNumberInfo().getUsedNumberAfterGrantOpt().get().getUseTimes().get().getUseTimes().v() : 0 ));
						//////////////////////////////////////
						// 使用回数   実特別休暇
						//////////////////////////////////////
						/** 特別休暇月別残数データ.実特別休暇.特別休暇使用情報.時間休暇使用回数 */
						tmp.setFactUseNumber     (tmp.getFactUseNumber()    +  remainData.getActualSpecial().getUsedNumberInfo().getSpecialLeaveUsedTimes().v());
					}
					else {
						tmp.setFactUseTimes      (tmp.getFactUseTimes()       );
						tmp.setBeforeFactUseTimes(tmp.getBeforeFactUseTimes() );
						tmp.setAfterFactUseTimes (tmp.getAfterFactUseTimes()  );
						tmp.setFactUseNumber     (tmp.getFactUseNumber()      );
					}

					//
					//================================================================================/
					// 残数  (最後に締めたデータを採用する)
					//================================================================================/
					//////////////////////////////////////
					// 残数  特別休暇  (日数)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.残日数               */
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.残日数               */
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.残日数　(期末:後締めの残数) */
//del 2022.02.01	tmp.setBeforeRemainDays(tmp.getBeforeRemainDays() +  remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
//del 2022.02.01	tmp.setAfterRemainDays (tmp.getAfterRemainDays()  + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
//del 2022.02.01														 remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					//
					tmp.setBeforeRemainDays(							 remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v() );
					tmp.setAfterRemainDays (						    (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
																		 remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0) );
					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
//del 2022.02.01	tmp.setRemainDays (tmp.getRemainDays()            +  remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setRemainDays (								    (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
																		 tmp.getAfterRemainDays()
																	   : tmp.getBeforeRemainDays() ));
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////

					//////////////////////////////////////
					// 残数  特別休暇  (時間)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与前.残時間 */
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.付与後.残時間 */
					/** 特別休暇月別残数データ.特別休暇.特別休暇残数情報.残時間　(期末:後締めの残数) */
//del 2022.02.01	tmp.setBeforeRemainTimes(tmp.getBeforeRemainTimes() + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
//del 2022.02.01														   remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
//del 2022.02.01	tmp.setAfterRemainTimes	(tmp.getAfterRemainTimes()  + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
//del 2022.02.01														&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
//del 2022.02.01														   remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0));
					tmp.setBeforeRemainTimes(							  (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
																		   remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0) );
					tmp.setAfterRemainTimes	(							  (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
																		&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
																		   remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0) );


					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
//del 2022.02.01	tmp.setRemainTimes	(tmp.getRemainTimes()           + (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
//del 2022.02.01														   remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setRemainTimes	(								  (remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
																		&& remainData.getSpecialLeave().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
																		   tmp.getAfterRemainTimes()
																		 : tmp.getBeforeRemainTimes() ));
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////


					//////////////////////////////////////
					// 残数  実特別休暇  (日数)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.残日数 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.残日数 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.残日数　(期末:後締めの残数) */
					tmp.setBeforeFactRemainDays(								 remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getDayNumberOfRemain().v());
					tmp.setAfterFactRemainDays (								(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
																				 remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getDayNumberOfRemain().v() : 0));
					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
//del 2022.02.01	tmp.setFactRemainDays (tmp.getFactRemainDays()            +  remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getDayNumberOfRemain().v());
					tmp.setFactRemainDays (										(remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() ?
																				 tmp.getAfterFactRemainDays()
																			   : tmp.getBeforeFactRemainDays() ));
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////
					//
					//////////////////////////////////////
					// 残数  実特別休暇  (時間)
					//////////////////////////////////////
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与前.残時間 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.付与後.残時間 */
					/** 特別休暇月別残数データ.実特別休暇.特別休暇残数情報.残時間　(期末:後締めの残数) */
					tmp.setBeforeFactRemainTimes(								  (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().isPresent() ?
																				   remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberBeforeGrant().getTimeOfRemain().get().v() : 0));
					tmp.setAfterFactRemainTimes	(								  (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
																				&& remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
																				   remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().get().v() : 0));
					///////////////////////////////////////////////////////////////////////////////////
					// 2022.02.11 #120673 稲熊 変更 START
//del 2022.02.01	tmp.setFactRemainTimes	(tmp.getFactRemainTimes()           + (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().isPresent() ?
//del 2022.02.01																   remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumber().getTimeOfRemain().get().v() : 0));
					tmp.setFactRemainTimes	(									  (remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent()
																				&& remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().get().getTimeOfRemain().isPresent() ?
																				   tmp.getAfterFactRemainTimes()
																			     : tmp.getBeforeFactRemainTimes() ));
					// 2022.02.11 #120673 稲熊 変更 END
					///////////////////////////////////////////////////////////////////////////////////
					//
					////////////////////////////////////////////////////////////////////////////////
					// 2022.02.01 #120673 稲熊 追加 START
					//////////////////////////////////////////
					/**	optional 実特別休暇．残数付与後		*/
					//////////////////////////////////////////
					tmp.setOptAfterFactRemain(									   remainData.getActualSpecial().getRemainingNumberInfo().getRemainingNumberAfterGrantOpt().isPresent() );
					// 2022.02.01 #120673 稲熊 追加 END
					////////////////////////////////////////////////////////////////////////////////
					//
					//////////////////////////////////////
					// 未消化数
					//////////////////////////////////////
					//
					tmp.setNotUseDays(tmp.getNotUseDays() +  remainData.getUnDegestionNumber().getDays().v());
					tmp.setNotUseTime(tmp.getNotUseTime() + (remainData.getUnDegestionNumber().getTimes().isPresent() ? remainData.getUnDegestionNumber().getTimes().get().v() : 0));
					//
					//////////////////////////////////////
					// 付与区分
					//////////////////////////////////////
					tmp.setGrantAtr(tmp.getGrantAtr() == 1 ? 1 : (remainData.isGrantAtr() ? 1 : 0));
					//
					//////////////////////////////////////
					// 付与日数
					//////////////////////////////////////
					tmp.setGrantDays(tmp.getGrantDays() + (remainData.getGrantDays().isPresent() ? remainData.getGrantDays().get().v() : 0));

					//////////////////////////////////////
					lstOutData.add(tmp);
				}
			}
		}
		return lstOutData;
	}
/////////////////////////////////////////////////////////////////////
// 2022.02.01 #120673 稲熊 変更 END
/////////////////////////////////////////////////////////////////////
}
