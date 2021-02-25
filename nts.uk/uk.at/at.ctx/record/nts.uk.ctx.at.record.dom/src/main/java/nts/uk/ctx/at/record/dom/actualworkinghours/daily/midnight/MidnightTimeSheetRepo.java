package nts.uk.ctx.at.record.dom.actualworkinghours.daily.midnight;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.midnighttimezone.MidNightTimeSheet;

/**
 * @author yennh
 */
public interface MidnightTimeSheetRepo {
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the list
	 */
	List<MidNightTimeSheet> findByCompanyId(String companyId);
	
	/**
	 * Add MidNightTimeSheet.
	 *
	 * @param midNightTimeSheet the mid night time sheet
	 */
	void add(MidNightTimeSheet midNightTimeSheet);

	/**
	 * Update MidNightTimeSheet.
	 *
	 * @param midNightTimeSheet the mid night time sheet
	 */
	void update(MidNightTimeSheet midNightTimeSheet);
	
	/**
	 * Find by CID.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	Optional<MidNightTimeSheet> findByCId(String companyId);
}
