package nts.uk.ctx.exio.dom.exi.condset;

import java.util.List;
import java.util.Optional;

/**
 * 受入条件設定（定型）
 */
public interface StdAcceptCondSetRepository {

	/**
	 * Find all standard acceptance condition settings by company id.
	 * 
	 * @param companyId the company id
	 * @return the <code>StdAcceptCondSet</code> list
	 */
	List<StdAcceptCondSet> findAllStdAcceptCondSetsByCompanyId(String companyId);

	List<StdAcceptCondSet> getStdAcceptCondSetBySysType(String cid, int sysType);

	Optional<StdAcceptCondSet> getStdAcceptCondSetById(String cid, int sysType, String conditionSetCd);

	void add(StdAcceptCondSet domain);

	void update(StdAcceptCondSet domain);

	void updateFromD(StdAcceptCondSet domain);

	void remove(String cid, int sysType, String conditionSetCd);

	boolean isSettingCodeExist(String cid, int sysType, String conditionSetCd);

}
