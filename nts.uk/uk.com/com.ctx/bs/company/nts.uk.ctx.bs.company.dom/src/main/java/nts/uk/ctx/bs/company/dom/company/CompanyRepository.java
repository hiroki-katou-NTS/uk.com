/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.company.dom.company;

import java.util.List;
import java.util.Optional;

/**
 * The Interface CompanyRepository.
 */

public interface CompanyRepository {
	
	/**
	 * Gets the comany id.
	 *
	 * @param companyId the company id
	 * @return the comany id
	 */
	Optional<Company> getComanyById(String companyId);
	
	List<Company>  getAllCompany();
	
	/** 
	 * get all company from database
	 * author: Hoang Yen
	 */
	List<CompanyInforNew> findAll(String contractCd);
	
	/**
	 * find a company infor item by code 
	 * @param contractCd
	 * @param companyId
	 * @param companyCd
	 * @return
	 * author: Hoang Yen
	 */
	Optional<CompanyInforNew> findComByCode(String contractCd, String companyId, String companyCd);
	
	/**
	 * get address
	 * @param companyId
	 * @return
	 * author: Hoang Yen
	 */
	List<AddInfor> findAdd();
	
	/** 
	 * update a company
	 * author: Hoang Yen
	 */
	void updateCom(CompanyInforNew company);
	
	/**
	 * update add infor
	 * @param addInfor
	 * author: Hoang Yen
	 */
	void updateAdd(AddInfor addInfor);
	
	/** 
	 * insert a company 
	 * author: Hoang Yen
	 */
	void insertCom(CompanyInforNew company);

	/**
	 * insert Address information company
	 * @param addInfor
	 * author: Hoang Yen
	 */
	void insertAdd(AddInfor addInfor);
	
	/**
	 * delete a company item
	 * @param comId
	 * @param contractCd
	 * @param companyCode
	 * author: Hoang Yen
	 */
	void deleteCom(String comId, String contractCd, String companyCode);
	
	/**
	 * RequestList #108.
	 *
	 * @param companyId the company id
	 * @return the comany
	 */
	Optional<Company> getComanyInfoByCid(String cid);
	
}

