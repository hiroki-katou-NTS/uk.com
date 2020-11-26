package nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;

@Stateless
public class StartProcessTreatmentHoliday {
	
	@Inject
	private TreatmentHolidayRepository treatmentHolidayRepo;
	
	/**
	 * 起動処理
	 * UKDesign.UniversalK.就業.KDW_日別実績.KMF_休暇マスタ.KMF001_休暇の設定.M：休日の設定.アルゴリズム.起動処理.起動処理
	 * @param companyId
	 * @return
	 */
	public HolidaySettingInfo startProcess(String companyId) {
		HolidaySettingInfo holidaySettingInfo = new HolidaySettingInfo();
		//ドメイン「休日の扱い」を取得する
		TreatmentHoliday treatmentHoliday = treatmentHolidayRepo.get(companyId);
		holidaySettingInfo.setTreatmentHoliday(treatmentHoliday);
		//管理期間の単位を取得する
		HolidayCheckUnit holidayCheckUnit = treatmentHoliday.getHolidayManagement().getUnitManagementPeriod();
		holidaySettingInfo.setHolidayCheckUnit(holidayCheckUnit);
		//取得した「休日チェック単位」をチェックする
		StartDateClassification startDateClassification = null;
		if(holidayCheckUnit == HolidayCheckUnit.FOUR_WEEK) {
			FourWeekHolidayAcqMana fourWeekHolidayAcqMana =  (FourWeekHolidayAcqMana)treatmentHoliday.getHolidayManagement();
			startDateClassification= fourWeekHolidayAcqMana.getStartDateType();
		}
		holidaySettingInfo.setStartDateClassification(Optional.ofNullable(startDateClassification));
		//TODO:ドメイン「週の管理」取得する
		//TODO:メニューの表示名を取得する
		return holidaySettingInfo;		
	}
}
