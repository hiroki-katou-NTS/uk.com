package nts.uk.ctx.at.shared.infra.entity.holidaymanagement.treatmentholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMD;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.WeeklyHolidayAcqMana;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.MonthDay;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutk
 *
 */
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HD_TREATMENT")
public class KshmtTreatmentHoliday extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Id
	@Column(name = "CID")
	public String companyId;

	/**
	 * 法定外休日を休日取得数に加える
	 */
	@Column(name = "NON_LEGAL_HD_ATR")
	public int addNonstatutoryHolidays;

	/**
	 * 管理期間の単位を取得する()
	 */
	@Column(name = "HD_UNIT")
	public int holidayCheckUnit;

	/**
	 * 起算日の種類を取得する
	 */
	@Column(name = "START_DATE_ATR")
	public Integer startDateAtr;

	/**
	 * 起算月日
	 */
	@Column(name = "START_MD")
	public Integer startMD;

	/**
	 * 起算年月日
	 */
	@Column(name = "START_YMD")
	public GeneralDate startYMD;

	/**
	 * 休日日数
	 */
	@Column(name = "HD_DAYS")
	public Double holidayDays;

	/**
	 * 最終週の休日日数
	 */
	@Column(name = "LAST_WEEK_HD_DAYS")
	public Double numberHolidayLastweek;

	@Override
	protected Object getKey() {
		return this.companyId;
	}

	public KshmtTreatmentHoliday(String companyId, int addNonstatutoryHolidays, int holidayCheckUnit,
			Integer startDateAtr, Integer startMD, GeneralDate startYMD, Double holidayDays,
			Double numberHolidayLastweek) {
		super();
		this.companyId = companyId;
		this.addNonstatutoryHolidays = addNonstatutoryHolidays;
		this.holidayCheckUnit = holidayCheckUnit;
		this.startDateAtr = startDateAtr;
		this.startMD = startMD;
		this.startYMD = startYMD;
		this.holidayDays = holidayDays;
		this.numberHolidayLastweek = numberHolidayLastweek;
	}

	public static KshmtTreatmentHoliday toEntity(TreatmentHoliday domain) {
		Integer startDateAtr = null;
		Integer startMD = null;
		GeneralDate startYMD = null;
		Double holidayDays = 0.0;
		Double numberHolidayLastweek = null;

		HolidayCheckUnit holidayCheckUnit = domain.getHolidayManagement().getUnitManagementPeriod();
		if (holidayCheckUnit == HolidayCheckUnit.ONE_WEEK) {
			startDateAtr = null;
			startMD = null;
			startYMD = null;
			WeeklyHolidayAcqMana weeklyHolidayAcqMana = (WeeklyHolidayAcqMana) domain.getHolidayManagement();
			holidayDays = weeklyHolidayAcqMana.getWeeklyDays().v();
			numberHolidayLastweek = null;
		} else {
			FourWeekHolidayAcqMana fourWeekHolidayAcqMana = (FourWeekHolidayAcqMana) domain.getHolidayManagement();
			startDateAtr = fourWeekHolidayAcqMana.getStartDateType().value;
			if (startDateAtr == StartDateClassification.SPECIFY_MD.value) {
				HolidayAcqManageByMD holidayAcqManageByMD = (HolidayAcqManageByMD) domain.getHolidayManagement();
				startMD = convertMonthDayToInt(holidayAcqManageByMD.getStartingMonthDay());
				startYMD = null;
				holidayDays = holidayAcqManageByMD.getFourWeekHoliday().v();
				numberHolidayLastweek = holidayAcqManageByMD.getNumberHolidayLastweek().v();
			} else {
				HolidayAcqManageByYMD holidayAcqManageByYMD = (HolidayAcqManageByYMD) domain.getHolidayManagement();
				startMD = null;
				startYMD = holidayAcqManageByYMD.getStartingDate();
				holidayDays = holidayAcqManageByYMD.getFourWeekHoliday().v();
				numberHolidayLastweek = null;
			}
		}

		return new KshmtTreatmentHoliday(domain.getCompanyId(), domain.getAddNonstatutoryHolidays().value,
				holidayCheckUnit.value, startDateAtr, startMD, startYMD, holidayDays, numberHolidayLastweek);

	}

	public TreatmentHoliday toDomain() {
		String companyId = this.companyId;
		NotUseAtr addNonstatutoryHolidays = NotUseAtr.valueOf(this.addNonstatutoryHolidays);

		if (this.holidayCheckUnit == HolidayCheckUnit.ONE_WEEK.value) {
			WeeklyHolidayAcqMana weeklyHolidayAcqMana = new WeeklyHolidayAcqMana(new WeeklyDays(this.holidayDays));
			return new TreatmentHoliday(companyId, addNonstatutoryHolidays, weeklyHolidayAcqMana);
		}
		if (this.startDateAtr == StartDateClassification.SPECIFY_MD.value) {
			HolidayAcqManageByMD holidayAcqManageByMD = new HolidayAcqManageByMD(
					convertIntToMonthDay(this.startMD),
					new FourWeekDays(this.holidayDays), 
					new WeeklyDays(this.numberHolidayLastweek));
			return new TreatmentHoliday(companyId, addNonstatutoryHolidays, holidayAcqManageByMD);
		}
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(
				this.startYMD,
				new FourWeekDays(this.holidayDays));
		return new TreatmentHoliday(companyId, addNonstatutoryHolidays, holidayAcqManageByYMD);
	}

	private static Integer convertMonthDayToInt(MonthDay monthDay) {
		if (monthDay == null) {
			return null;
		}
		return monthDay.getMonth() * 100 + monthDay.getDay();
	}

	private static MonthDay convertIntToMonthDay(Integer monthDay) {
		if (monthDay == null) {
			return null;
		}
		return new MonthDay(monthDay / 100, monthDay % 100);
	}
}
