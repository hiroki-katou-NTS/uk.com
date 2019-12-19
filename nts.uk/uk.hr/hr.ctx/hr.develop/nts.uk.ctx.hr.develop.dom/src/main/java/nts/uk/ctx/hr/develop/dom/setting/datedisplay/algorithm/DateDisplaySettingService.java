package nts.uk.ctx.hr.develop.dom.setting.datedisplay.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.IGetYearStartEndDateByDate;
import nts.uk.ctx.hr.develop.dom.humanresourcedev.hryear.service.YearStartEnd;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySetting;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingRepository;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.DateDisplaySettingValue;
import nts.uk.ctx.hr.develop.dom.setting.datedisplay.dto.PeriodDisplaySettingList;

/**
 * @author anhdt
 *
 */
@Stateless
public class DateDisplaySettingService {

	@Inject
	private DateDisplaySettingRepository dateDisplaySettingRepo;

	@Inject
	private IGetYearStartEndDateByDate getYearStartEndSv;

	// 日付表示設定の取得
	public List<PeriodDisplaySettingList> getDateDisplaySetting(String programId, String companyId) {
		List<DateDisplaySetting> results = new ArrayList<>();
		if (StringUtils.isEmpty(programId)) {
			// ドメインモデル [日付表示設定] を取得する(Lấy domain [DateDisplaySetting])
			results = dateDisplaySettingRepo.getSettingByCompanyId(companyId);
		} else {
			// ドメインモデル [日付表示設定] を取得する(Lấy domain [DateDisplaySetting])
			results = dateDisplaySettingRepo.getSettingByCompanyIdAndProgramId(programId, companyId);
		}

		if (CollectionUtil.isEmpty(results)) {
			return Collections.emptyList();
		}

		return results.stream().map(d -> {

			DateDisplaySettingValue startSet = d.getStartDateSetting();
			DateDisplaySettingValue endSet = d.getEndDateSetting().isPresent() ? d.getEndDateSetting().get() : null;

			return PeriodDisplaySettingList.builder().programId(d.getProgramId())
					.startDateSettingCategory(startSet.getSettingClass().value)
					.startDateSettingDate(startSet.getSettingDate()).startDateSettingMonth(startSet.getSettingMonth())
					.startDateSettingNum(startSet.getSettingNum())
					.endDateSettingCategory(endSet != null ? endSet.getSettingClass().value : null)
					.endDateSettingDate(endSet != null ? endSet.getSettingDate() : null)
					.endDateSettingMonth(endSet != null ? endSet.getSettingMonth() : null)
					.endDateSettingNum(endSet != null ? endSet.getSettingNum() : null).build();
		}).collect(Collectors.toList());
	}

	// 日付表示設定の追加
	public void add(String companyId, List<DateDisplaySetting> dateSetting) {
		this.dateDisplaySettingRepo.add(companyId, dateSetting);
	}

	// 日付表示設定の更新
	public void update(String companyId, List<DateDisplaySetting> dateSetting) {
		this.dateDisplaySettingRepo.update(companyId, dateSetting);
	}

	public GeneralDate getDisplayDate(String companyId, DateDisplaySettingValue setting) {
		int settingClass = setting.getSettingClass().value;
		int settingNum = setting.getSettingNum();
		int settingDate = setting.getSettingDate();
		int settingMonth = setting.getSettingMonth();
		GeneralDate baseDate = GeneralDate.today();
		GeneralDate displayDate = null;
		// 期間設定区分 = 0 (PeriodSettingClass=0)
		if (settingClass == 0) {
			return null;
		}
		// 値を返す(Trả về value) 期間設定区分 = 1～7(PeriodSettingClass = 1～7)
		if (settingClass >= 1 && settingClass <= 7) {
			switch (settingClass) {
			case 1:
				// （T/H PeriodSettingClass = 1) ・DisplayDate = BaseDate
				displayDate = baseDate;
				break;
			case 2:
				// ・DisplayDate = BaseDate．add(DATE, -SettingNum) ・DisplayDate =
				// BaseDate．add(DATE, -SettingNum)
				displayDate = baseDate.addDays(-settingNum);
				break;
			case 3:
				// （T/H PeriodSettingClass = 3 ） ・DisplayDate =
				// BaseDate．add(MONTH, -SettingNum）
				displayDate = baseDate.addMonths(-settingNum);
				break;
			case 4:
				// （T/H PeriodSettingClass = 4） ・DisplayDate =
				// BaseDate．add(DATE, SettingNum)
				displayDate = baseDate.addDays(settingNum);
				break;
			case 5:
				// （T/H PeriodSettingClass = 5 ）・DisplayDate =
				// BaseDate．add(MONTH, SettingNum)
				displayDate = baseDate.addMonths(settingNum);
				break;
			case 6:
				// （T/H PeriodSettingClass = 6 ）
				int year6 = baseDate.year();
				try {
					displayDate = GeneralDate.ymd(year6, settingMonth, settingDate);
				} catch (Exception e) {
					// displayDate = GeneralDate.max();
					displayDate = GeneralDate.ymd(year6, settingMonth, 1);
					displayDate = GeneralDate.ymd(year6, settingMonth, displayDate.lastDateInMonth());
				}
				
				break;
			case 7:
				GeneralDate nextYear = baseDate.addYears(1);
				int year7 = nextYear.year();
				try {
					
					displayDate = GeneralDate.ymd(year7, settingMonth, settingDate);
				} catch (Exception e) {
					// displayDate = GeneralDate.max();
					displayDate = GeneralDate.ymd(year7, settingMonth, 1);
					displayDate = GeneralDate.ymd(year7, settingMonth, displayDate.lastDateInMonth());
				}
				
				break;
			default:
				break;
			}
		}

		if (settingClass >= 8 && settingClass <= 11) {
			GeneralDate inputDate = null;
			switch (settingClass) {
			case 8:
				// (T/H PeriodSettingClass=8)
				inputDate = baseDate;
				break;
			case 9:
				// (T/H PeriodSettingClass=9)
				inputDate = baseDate.addYears(1);
				break;
			case 10:
				// (T/H PeriodSettingClass=10)
				inputDate = baseDate;
				break;
			case 11:
				// (T/H PeriodSettingClass=11)
				inputDate = baseDate.addYears(1);
				break;
			default:
				break;
			}
			// アルゴリズム [基準日から年度開始年月日、年度終了年月日の取得] を実行する
			Optional<YearStartEnd> yearStartEnd = getYearStartEndSv.getYearStartEndDateByDate(companyId, inputDate);

			switch (settingClass) {
			case 8:
				// (T/H PeriodSettingClass=8)
				displayDate = yearStartEnd.get().getYearStartYMD();
				break;
			case 9:
				// (T/H PeriodSettingClass=9)
				displayDate = yearStartEnd.get().getYearStartYMD();
				break;
			case 10:
				// (T/H PeriodSettingClass=10)
				displayDate = yearStartEnd.get().getYearEndYMD();
				break;
			case 11:
				// (T/H PeriodSettingClass=11)
				displayDate = yearStartEnd.get().getYearEndYMD();
				break;
			default:
				break;
			}

		}

		return displayDate;
	}
}
