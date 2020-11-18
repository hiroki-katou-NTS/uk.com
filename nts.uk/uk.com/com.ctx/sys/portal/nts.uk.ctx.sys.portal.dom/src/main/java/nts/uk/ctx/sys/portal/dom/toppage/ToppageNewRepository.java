package nts.uk.ctx.sys.portal.dom.toppage;

import java.util.List;
import java.util.Optional;

/**
 * 
 * @author NWS-Hieutt
 *
 */
public interface ToppageNewRepository {
	
	/**
	 * 
	 * @param domain
	 */
	void insert(ToppageNew domain);
	
	/**
	 * 
	 * @param domain
	 */
	void update(ToppageNew domain);
	
	/**
	 * 
	 * @param CompanyId
	 * @param topPageCode
	 */
	void delete(String companyId, String topPageCode);
	
	/**
	 * 
	 * @param companyId
	 * @return
	 */
	List<ToppageNew> getByCid(String companyId);
	
	/**
	 * ドメインモデル「トップページ」を取得する
	 * 
	 * @param companyId 
	 * @param topPageCode トップページコード
	 * @return
	 */
	Optional<ToppageNew> getByCidAndCode(String companyId, String topPageCode);
}
