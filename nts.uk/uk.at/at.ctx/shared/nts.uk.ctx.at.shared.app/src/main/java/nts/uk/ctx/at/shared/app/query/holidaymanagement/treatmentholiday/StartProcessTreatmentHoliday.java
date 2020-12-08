package nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.DayOfWeek;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagement;
import nts.uk.ctx.at.shared.dom.workrule.weekmanage.WeekRuleManagementRepo;

@Stateless
public class StartProcessTreatmentHoliday {
	
	@Inject
	private TreatmentHolidayRepository treatmentHolidayRepo;
	
	@Inject
	private WeekRuleManagementRepo weekRuleManagementRepo;
	
	/**
	 * 起動処理
	 * UKDesign.UniversalK.就業.KDW_日別実績.KMF_休暇マスタ.KMF001_休暇の設定.M：休日の設定.アルゴリズム.起動処理.起動処理
	 * @param companyId
	 * @return
	 */
	public HolidaySettingInfoDto startProcess(String companyId) {
		HolidaySettingInfo holidaySettingInfo = new HolidaySettingInfo();
		//ドメイン「休日の扱い」を取得する
		Optional<TreatmentHoliday> treatmentHoliday = treatmentHolidayRepo.get(companyId);
		if(!treatmentHoliday.isPresent()) {
			//return new HolidaySettingInfo(Optional.empty(), Optional.empty(), null, Optional.empty(), Optional.empty());
			return null;
		}
		holidaySettingInfo.setTreatmentHoliday(treatmentHoliday);
		//管理期間の単位を取得する
		HolidayCheckUnit holidayCheckUnit = treatmentHoliday.get().getHolidayManagement().getUnitManagementPeriod();
		holidaySettingInfo.setHolidayCheckUnit(Optional.of(holidayCheckUnit));
		//取得した「休日チェック単位」をチェックする
		StartDateClassification startDateClassification = null;
		if(holidayCheckUnit == HolidayCheckUnit.FOUR_WEEK) {
			FourWeekHolidayAcqMana fourWeekHolidayAcqMana =  (FourWeekHolidayAcqMana)treatmentHoliday.get().getHolidayManagement();
			startDateClassification= fourWeekHolidayAcqMana.getStartDateType();
		}
		holidaySettingInfo.setStartDateClassification(Optional.ofNullable(startDateClassification));
		//ドメイン「週の管理」取得する
		Optional<WeekRuleManagement> optWeekRuleManagement = weekRuleManagementRepo.find(companyId);
		if(optWeekRuleManagement.isPresent()) {
			holidaySettingInfo.setWeekStart(Optional.of(optWeekRuleManagement.get().getWeekStart()));
		}else {
			holidaySettingInfo.setWeekStart(Optional.empty());
		}
		
		//メニューの表示名を取得する
		// Khác context nên k gọi được, dùng web service để lấy 
		// WS : "sys/portal/standardmenu/findPgName/{0}/{1}/{2}"
		//nếu k có thì gán KMF001_337
		HolidaySettingInfoDto infoDto = new HolidaySettingInfoDto();
		if (holidaySettingInfo.getHolidayCheckUnit().get().value == HolidayCheckUnit.FOUR_WEEK.value) {
			FourWeekHolidayAcqMana fourWeekHolidayAcqMana = (FourWeekHolidayAcqMana) holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
			if (fourWeekHolidayAcqMana.getStartDateType().value == StartDateClassification.SPECIFY_YMD.value) {
				HolidayAcqManageByYMD x = (HolidayAcqManageByYMD)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setStandardDate(x.getStartingDate());
				infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(holidaySettingInfo.getStartDateClassification().get().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
				if(optWeekRuleManagement.isPresent()){
					if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.MONDAY ){
						infoDto.setHdDay("月曜日");
					}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.TUESDAY ){
						infoDto.setHdDay("火曜日");		
					}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.WEDNESDAY ){
						infoDto.setHdDay("水曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.THURSDAY ){
						infoDto.setHdDay("木曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.FRIDAY ){
						infoDto.setHdDay("金曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SATURDAY ){
						infoDto.setHdDay("土曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SUNDAY ){
						infoDto.setHdDay("日曜日");		
					}		
				}
			} else {
				HolidayAcqManageByMD x = (HolidayAcqManageByMD)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
				infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
				infoDto.setSelectedClassification(holidaySettingInfo.getStartDateClassification().get().value);
				infoDto.setMonthDay(x.getStartingMonthDay().getMonth()*100 + x.getStartingMonthDay().getDay());
				infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
				infoDto.setHolidayValue(x.getFourWeekHoliday().v());
				infoDto.setAddHolidayValue(x.getNumberHolidayLastweek().v());
				if(optWeekRuleManagement.isPresent()){
					if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.MONDAY ){
						infoDto.setHdDay("月曜日");
					}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.TUESDAY ){
						infoDto.setHdDay("火曜日");		
					}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.WEDNESDAY ){
						infoDto.setHdDay("水曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.THURSDAY ){
						infoDto.setHdDay("木曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.FRIDAY ){
						infoDto.setHdDay("金曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SATURDAY ){
						infoDto.setHdDay("土曜日");		
					}
					else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SUNDAY ){
						infoDto.setHdDay("日曜日");		
					}		
				}
			}

		} else {
			WeeklyHolidayAcqMana x = (WeeklyHolidayAcqMana)holidaySettingInfo.getTreatmentHoliday().get().getHolidayManagement();
			infoDto.setHolidayCheckUnit(holidaySettingInfo.getHolidayCheckUnit().get().value);
			infoDto.setNonStatutory(holidaySettingInfo.getTreatmentHoliday().get().getAddNonstatutoryHolidays().value);
			infoDto.setHolidayValue(x.getWeeklyDays().v());
			if(optWeekRuleManagement.isPresent()){
				if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.MONDAY ){
					infoDto.setHdDay("月曜日");
				}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.TUESDAY ){
					infoDto.setHdDay("火曜日");		
				}else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.WEDNESDAY ){
					infoDto.setHdDay("水曜日");		
				}
				else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.THURSDAY ){
					infoDto.setHdDay("木曜日");		
				}
				else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.FRIDAY ){
					infoDto.setHdDay("金曜日");		
				}
				else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SATURDAY ){
					infoDto.setHdDay("土曜日");		
				}
				else if (optWeekRuleManagement.get().getDayOfWeek() == DayOfWeek.SUNDAY ){
					infoDto.setHdDay("日曜日");		
				}		
			}
		}
		return infoDto;		
	}
}
