package nts.uk.ctx.at.shared.dom.workrule.workuse;

import java.util.Optional;

/**
 * @author HoangNDH
 * The Interface TemporaryWorkUseManageRepository.
 */
public interface TemporaryWorkUseManageRepository {
	
	/**
	 * Find by cid.
	 *
	 * @param companyId the company id
	 * @return the optional
	 */
	public Optional<TemporaryWorkUseManage> findByCid(String companyId);
	
	/**
	 * Insert domain 
	 *
	 * @param setting the setting
	 */
	public void insert (TemporaryWorkUseManage setting);
	
	/**
	 * Update domain
	 *
	 * @param setting the setting
	 */
	public void update (TemporaryWorkUseManage setting);
}
