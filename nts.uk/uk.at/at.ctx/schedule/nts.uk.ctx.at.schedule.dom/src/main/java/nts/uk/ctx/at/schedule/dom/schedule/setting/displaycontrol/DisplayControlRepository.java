package nts.uk.ctx.at.schedule.dom.schedule.setting.displaycontrol;

import java.util.Optional;

/**
 * 
 * @author TanLV
 *
 */
public interface DisplayControlRepository {
	/**
	 * Get Schedule Display Control
	 * @param companyId
	 * @return
	 */
	Optional<ScheDispControl> getScheDispControl(String companyId);
	
	/**
	 * Add Schedule Display Control
	 * @param scheDispControl
	 */
	void addScheDispControl(ScheDispControl scheDispControl);
	
	/**
	 * Update Schedule Display Control
	 * @param scheDispControl
	 */
	void updateScheDispControl(ScheDispControl scheDispControl);
}
