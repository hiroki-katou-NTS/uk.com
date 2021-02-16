package nts.uk.ctx.at.shared.infra.repository.holidaymanagement.treatmentholiday;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.FourWeekHolidayAcqMana;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayCheckUnit;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.StartDateClassification;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHoliday;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.TreatmentHolidayRepository;
import nts.uk.ctx.at.shared.infra.entity.holidaymanagement.treatmentholiday.KshmtTreatmentHoliday;
/**
 * 
 * @author tutk
 *
 */
@Stateless
public class JpaTreatmentHolidayRepository extends JpaRepository implements TreatmentHolidayRepository {
	private static final String SELECT_BY_KEY = "SELECT c FROM KshmtTreatmentHoliday c WHERE c.companyId = :companyId ";
	
	@Override
	public void insert(TreatmentHoliday treatmentHoliday) {
		this.commandProxy().insert(KshmtTreatmentHoliday.toEntity( treatmentHoliday));
		
	}

	@Override
	public void update(TreatmentHoliday treatmentHoliday) {
		Optional<KshmtTreatmentHoliday> oldEntity = this.queryProxy().query(SELECT_BY_KEY, KshmtTreatmentHoliday.class)
				.setParameter("companyId", treatmentHoliday.getCompanyId())
				.getSingle();
		Integer startDateAtr = null;
		if(oldEntity.isPresent()) {
			KshmtTreatmentHoliday newEntity = KshmtTreatmentHoliday.toEntity(treatmentHoliday);
//			oldEntity.get().addNonstatutoryHolidays = newEntity.addNonstatutoryHolidays;
//			oldEntity.get().holidayCheckUnit = newEntity.holidayCheckUnit;
//			oldEntity.get().startDateAtr = newEntity.startDateAtr;
//			oldEntity.get().startMD = newEntity.startMD;
//			oldEntity.get().startYMD = newEntity.startYMD;
//			oldEntity.get().holidayDays = newEntity.holidayDays;
//			oldEntity.get().numberHolidayLastweek = newEntity.numberHolidayLastweek;
			
			HolidayCheckUnit holidayCheckUnit = treatmentHoliday.getHolidayManagement().getUnitManagementPeriod();
			if (holidayCheckUnit == HolidayCheckUnit.ONE_WEEK) {
				oldEntity.get().holidayCheckUnit = newEntity.holidayCheckUnit;
				oldEntity.get().holidayDays = newEntity.holidayDays;
				oldEntity.get().addNonstatutoryHolidays = newEntity.addNonstatutoryHolidays;
			} else {
				FourWeekHolidayAcqMana fourWeekHolidayAcqMana = (FourWeekHolidayAcqMana) treatmentHoliday.getHolidayManagement();
				startDateAtr = fourWeekHolidayAcqMana.getStartDateType().value;
				if (startDateAtr == StartDateClassification.SPECIFY_MD.value) {
					oldEntity.get().startDateAtr = newEntity.startDateAtr;
					oldEntity.get().holidayCheckUnit = newEntity.holidayCheckUnit;
					oldEntity.get().startMD = newEntity.startMD;
					oldEntity.get().holidayDays = newEntity.holidayDays;
					oldEntity.get().numberHolidayLastweek = newEntity.numberHolidayLastweek;
					oldEntity.get().addNonstatutoryHolidays = newEntity.addNonstatutoryHolidays;
				} else {
					oldEntity.get().startDateAtr = newEntity.startDateAtr;
					oldEntity.get().holidayCheckUnit = newEntity.holidayCheckUnit;
					oldEntity.get().startYMD = newEntity.startYMD;
					oldEntity.get().holidayDays = newEntity.holidayDays;
					oldEntity.get().addNonstatutoryHolidays = newEntity.addNonstatutoryHolidays;
				}
			}
			
			this.commandProxy().update(oldEntity.get());
		}
		
	}

	@Override
	public Optional<TreatmentHoliday> get(String companyId) {
		Optional<TreatmentHoliday> treatmentHoliday = this.queryProxy().query(SELECT_BY_KEY, KshmtTreatmentHoliday.class)
				.setParameter("companyId", companyId)
				.getSingle(c -> c.toDomain());
		return treatmentHoliday;
	}

}
