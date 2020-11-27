package nts.uk.file.at.infra.vacation.set.holiday;

import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumManageDistinct;
import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumSixtyHourExtra;
import static nts.uk.file.at.infra.vacation.set.CommonTempHolidays.getTextEnumTimeDigestiveUnit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.i18n.I18NText;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.HolidaySettingInfo;
import nts.uk.ctx.at.shared.app.query.holidaymanagement.treatmentholiday.StartProcessTreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.file.at.app.export.vacation.set.EmployeeSystemImpl;
import nts.uk.file.at.app.export.vacation.set.holiday.HolidayRepository;
import nts.uk.file.at.infra.vacation.set.CommonTempHolidays;
import nts.uk.file.at.infra.vacation.set.DataEachBox;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;

@Stateless
public class JpaHolidayReposiroty implements HolidayRepository {
	@Inject
	private TreatmentHolidayRepository treatmentHolidayRepository;
	
	@Override
	public List<MasterData> getAllHoliday(String cid) {
		List<MasterData> datas = new ArrayList<>();
		TreatmentHoliday treatmentHoliday = treatmentHolidayRepository.get(cid);
		datas = this.buildMasterListData(treatmentHoliday);
		return datas;
	}
	
	private List<MasterData> buildMasterListData(TreatmentHoliday treatmentHoliday ) {
		List<MasterData> datas = new ArrayList<>();
		FourWeekHolidayAcqMana fourWeekHolidayAcqMana = null;
		HolidayAcqManageByMD holidayAcqManageByMD = null;
		HolidayAcqManageByYMD  holidayAcqManageByYMD = null;
		WeeklyHolidayAcqMana weeklyHolidayAcqMana = null;
		
		/* ※18 */
		boolean checkHolidayCheckUnit = (treatmentHoliday.getHolidayManagement()
				.getUnitManagementPeriod() == HolidayCheckUnit.FOUR_WEEK);
		/* ※19 */
		boolean checkStartDateClassification = false;
		if (checkHolidayCheckUnit) {
			fourWeekHolidayAcqMana = (FourWeekHolidayAcqMana) treatmentHoliday.getHolidayManagement();
			if (fourWeekHolidayAcqMana.getStartDateType() == StartDateClassification.SPECIFY_MD) {
				holidayAcqManageByMD = (HolidayAcqManageByMD) fourWeekHolidayAcqMana;
				checkStartDateClassification = true;
			}else {
				holidayAcqManageByYMD = (HolidayAcqManageByYMD) fourWeekHolidayAcqMana;
				
			}
		}else {
			weeklyHolidayAcqMana = (WeeklyHolidayAcqMana) treatmentHoliday.getHolidayManagement();
		}
		String data_a27_5 = "";
		if(checkHolidayCheckUnit) {
			if(checkStartDateClassification) {
				data_a27_5 = holidayAcqManageByMD.getFourWeekHoliday().v()+ I18NText.getText("KMF001_287");
			}else {
				data_a27_5 = holidayAcqManageByYMD.getFourWeekHoliday().v()+ I18NText.getText("KMF001_287");
			}
		}else {
			data_a27_5 = weeklyHolidayAcqMana.getWeeklyDays().v() + I18NText.getText("KMF001_287");
		}
		
		String a27_1 = (treatmentHoliday.getHolidayManagement().getUnitManagementPeriod() == HolidayCheckUnit.ONE_WEEK)?
				I18NText.getText("KMF001_279"):
				I18NText.getText("KMF001_278");
		String a27_2 = checkHolidayCheckUnit ? 
				fourWeekHolidayAcqMana.getStartDateType() == StartDateClassification.SPECIFY_MD?
						I18NText.getText("KMF001_282"):
						I18NText.getText("KMF001_281")
				:null;
		String a27_3 = checkHolidayCheckUnit && checkStartDateClassification?
				this.convertToMonthDay(holidayAcqManageByMD.getStartingMonthDay())
				:null;
		String a27_4 = checkHolidayCheckUnit && !checkStartDateClassification?
				holidayAcqManageByYMD.getStartingDate().toString():
				null;
		String a27_5 = data_a27_5;
		String a27_6 =checkHolidayCheckUnit && checkStartDateClassification?
				holidayAcqManageByMD.getNumberHolidayLastweek().v() + I18NText.getText("KMF001_287")
				:null; 
		String a27_7 = CommonTempHolidays.checkOcurrType(treatmentHoliday.getAddNonstatutoryHolidays().value);

		// Row 1
//		datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_166"), ColumnTextAlign.LEFT),
//				new DataEachBox(null, ColumnTextAlign.LEFT),
//				new DataEachBox(I18NText.getText("KMF001_167"),ColumnTextAlign.LEFT)));
		// Row 2
		datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_277"), ColumnTextAlign.LEFT),
				new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(a27_1, ColumnTextAlign.LEFT)));
		// Row 3
		datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(I18NText.getText("KMF001_280"), ColumnTextAlign.LEFT),
				new DataEachBox(a27_2, ColumnTextAlign.RIGHT)));
		
		// Row 4
		datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(I18NText.getText("KMF001_283"), ColumnTextAlign.LEFT),
				new DataEachBox(a27_3, ColumnTextAlign.RIGHT)));
		// Row 5
		datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(I18NText.getText("KMF001_285"), ColumnTextAlign.LEFT),
				new DataEachBox(a27_4, ColumnTextAlign.LEFT)));
		// Row 6
		datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_286"), ColumnTextAlign.LEFT),
				new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(a27_5, ColumnTextAlign.LEFT)));
		// Row 7
		datas.add(buildARow(new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(I18NText.getText("KMF001_290"), ColumnTextAlign.LEFT),
				new DataEachBox(a27_6, ColumnTextAlign.LEFT)));
		// Row 8
		datas.add(buildARow(new DataEachBox(I18NText.getText("KMF001_288"), ColumnTextAlign.LEFT),
				new DataEachBox(null, ColumnTextAlign.LEFT),
				new DataEachBox(a27_7, ColumnTextAlign.LEFT)));
		
		
		return datas;
    }
	
	private String convertToMonthDay (MonthDay monthday) {
		String month ="";
		String day ="";
		if(monthday.getMonth()>=10) {
			month = ""+monthday.getMonth();
		}else {
			month = "0"+monthday.getMonth();
		}
		
		if(monthday.getMonth()>=10) {
			day = ""+monthday.getDay();
		}else {
			day = "0"+monthday.getDay();
		}
		return month+"/"+day;
	}
	
	private MasterData buildARow(DataEachBox value1, DataEachBox value2, DataEachBox value3) {
        Map<String, MasterCellData> data = new HashMap<>();

        data.put(EmployeeSystemImpl.KMF001_166, MasterCellData.builder() 
                .columnId(EmployeeSystemImpl.KMF001_166)
                .value(value1.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value1.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_B01, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_B01)
                .value(value2.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value2.getPositon()))
                .build());
        data.put(EmployeeSystemImpl.KMF001_167, MasterCellData.builder()
                .columnId(EmployeeSystemImpl.KMF001_167)
                .value(value3.getValue())
                .style(MasterCellStyle.build().horizontalAlign(value3.getPositon()))
                .build());

        return MasterData.builder().rowData(data).build();
    }

}
