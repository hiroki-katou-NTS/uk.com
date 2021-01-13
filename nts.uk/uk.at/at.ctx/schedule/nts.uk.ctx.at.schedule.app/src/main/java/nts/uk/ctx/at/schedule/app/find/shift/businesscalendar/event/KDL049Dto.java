package nts.uk.ctx.at.schedule.app.find.shift.businesscalendar.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HieuLt
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class KDL049Dto {

	/** Optional<会社行事>、 **/
	private CompanyEventDto optComEvent;
	/** Optional<職場行事> **/
	private WorkplaceEventDto optWorkplaceEvent;
}
