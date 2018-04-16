package nts.uk.ctx.at.shared.dom.workrule.deformed;

import java.util.Optional;

/**
 * Aggregate deformed labor setting repository
 * @author HoangNDH
 *
 */
public interface AggDeformedLaborSettingRepository {
	
	/**
	 * Find by company ID
	 * @param companyId
	 * @return
	 */
	public Optional<AggDeformedLaborSetting> findByCid(String companyId);
	
	/**
	 * Add new aggregate deformed labor setting
	 * @param setting
	 */
	public void insert(AggDeformedLaborSetting setting);
	
	/**
	 * Update existing aggregate deformed labor setting
	 * @param setting
	 */
	public void update(AggDeformedLaborSetting setting);
}
