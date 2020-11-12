package nts.uk.ctx.sys.portal.dom.layout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface LayoutNewRepository {

	/**
	 * 
	 * @param domain
	 */
	void insert(LayoutNew domain);
	
	/**
	 * 
	 * @param domain
	 */
	void update(LayoutNew domain);
	
	/**
	 * 
	 * @param CompanyId
	 * @param topPageCode
	 */
	void delete(String CompanyId, String topPageCd, BigDecimal layoutNo);
	
	/**
	 * 
	 * @param companyId
	 * @param topPageCode
	 */
	void delete(String companyId, String topPageCode, List<BigDecimal> lstLayoutNo);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<LayoutNew> getByCid(String companyId);
	
	/**
	 * 
	 * @param companyId
	 * @param topPageCode
	 * @return
	 */
	Optional<LayoutNew> getByCidAndCode(String companyId, String topPageCd, BigDecimal layoutNo);
	
	Optional<WidgetSetting> getByCidAndCodeAndWidgetType(String companyId, String topPageCd, BigDecimal layoutNo, Integer widgetType);
	
	Optional<LayoutNew> getByCidAndCode(String companyId, String topPageCd);
	
	List<BigDecimal> getLstLayoutNo(String topPageCd);
	
	void insertWidget (LayoutNew layout, WidgetSetting widget);
	
	void updateWidget (LayoutNew layout, WidgetSetting widget);
	
	void deleteWidget (String CompanyId, BigDecimal LayoutNo, String topPageCd, BigDecimal widgetType);
}
