package nts.uk.ctx.at.record.dom.dailyperformanceformat.repository;

import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;

public interface BusinessTypesRepository {
	
	List<BusinessType> findAll(String companyId);
	/**
	 * author: HoangYen
	 * @param workTypeName
	 */
	void updateBusinessTypeName(BusinessType businessType);  
	/**
	 * author: HoangYen
	 * @param workTypeCode
	 * @param workTypeName
	 */
	void insertBusinessType(BusinessType businessType);
	/**
	 * author: HoangYen
	 * @param companyId
	 * @param workTypeCode
	 * @param workTypeName
	 * @return
	 */
	Optional<BusinessType> findBusinessType(String companyId, String workTypeCode);
	/**
	 * author: HoangYen
	 * @param companyId
	 * @param workTypeCode
	 */
	void deleteBusinessType(String companyId, String workTypeCode);
}
