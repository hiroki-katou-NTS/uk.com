package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import java.util.Optional;

/**
 * 
 * @author tutk
 *
 */
public interface TreatmentHolidayRepository {
	
	/**
	 * [1] Insert(休日の扱い)
	 * @param treatmentHoliday
	 */
	public void insert(TreatmentHoliday treatmentHoliday);
	
	/**
	 * [2] Update(休日の扱い)
	 * @param treatmentHoliday
	 */
	public void update(TreatmentHoliday treatmentHoliday);
	
	/**
	 * [3] get
	 * @param companyId
	 * @return
	 */
	public Optional<TreatmentHoliday> get(String companyId);

}
