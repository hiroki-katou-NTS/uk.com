package nts.uk.ctx.pr.proto.dom.layout;

import java.util.List;
import java.util.Optional;



public interface LayoutMasterRepository {
	// layout master
	/**
	 * find layout master by company code, layout master(name or code), start
	 * date
	 * 
	 * @param companyCode
	 * @param layoutMaster
	 * @param strYm
	 * @return
	 */
	Optional<LayoutMaster> findAll(String companyCode, String layoutMaster, int strYm);

	/**
	 * find all layout master by company code, start date
	 * 
	 * @return layout master
	 */
	List<LayoutMaster> find(String companyCode);

	/**
	 * Add a new layout master
	 * 
	 * @param layoutMaster
	 */
	void add(LayoutMaster layoutMaster);

	/**
	 * update a layout master
	 * 
	 * @param layoutMaster
	 */
	void update(LayoutMaster layoutMaster);

	/**
	 * delete a layout master
	 * 
	 * @param companyCode
	 * @param layoutCode
	 * @param startYm
	 */
	void remove(String companyCode, String layoutCode, int startYm);

}
