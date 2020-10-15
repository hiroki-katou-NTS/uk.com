package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.worklabor.defor;

import java.util.List;
import java.util.Optional;

/**
 * @author yennh
 *
 */
public interface DeformLaborOTRepository {
	/**
	 * Find by CID
	 * 
	 * @param companyId
	 * @return
	 */
	List<DeformLaborOT> findByCompanyId(String companyId);

	/**
	 * Add DeformLaborOT
	 * 
	 * @param deformLaborOT
	 */
	void add(DeformLaborOT deformLaborOT);

	/**
	 * Update DeformLaborOT
	 * 
	 * @param deformLaborOT
	 */
	void update(DeformLaborOT deformLaborOT);
	
	/**
	 * Find by CID
	 * 
	 * @param companyId
	 * @return
	 */
	Optional<DeformLaborOT> findByCId(String companyId);
}
