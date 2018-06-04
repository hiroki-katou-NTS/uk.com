package nts.uk.ctx.at.record.dom.remainingnumber.publicholiday;

import java.util.Optional;

/**
 * 公休付与残数データ
 * @author Hop.NT
 *
 */
public interface PublicHolidayRemainRepository{
	
	Optional<PublicHolidayRemain> get(String sid);
	
	void add(PublicHolidayRemain domain);
	
	void update(PublicHolidayRemain domain);
	
	void delete(String sid);
}
