package nts.uk.ctx.at.shared.infra.repository.holidaymanagement.treatmentholiday;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
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
		if(oldEntity.isPresent()) {
			KshmtTreatmentHoliday newEntity = KshmtTreatmentHoliday.toEntity(treatmentHoliday);
			oldEntity.get().addNonstatutoryHolidays = newEntity.addNonstatutoryHolidays;
			oldEntity.get().holidayCheckUnit = newEntity.holidayCheckUnit;
			oldEntity.get().startDateAtr = newEntity.startDateAtr;
			oldEntity.get().startMD = newEntity.startMD;
			oldEntity.get().startYMD = newEntity.startYMD;
			oldEntity.get().holidayDays = newEntity.holidayDays;
			oldEntity.get().numberHolidayLastweek = newEntity.numberHolidayLastweek;
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
