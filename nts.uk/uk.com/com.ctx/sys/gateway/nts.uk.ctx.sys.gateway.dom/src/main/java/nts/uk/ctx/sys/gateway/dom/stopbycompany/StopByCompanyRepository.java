package nts.uk.ctx.sys.gateway.dom.stopbycompany;

import java.util.List;
import java.util.Optional;

public interface StopByCompanyRepository {

	public void insert(StopByCompany domain);

	public void update(StopByCompany domain);

	public Optional<StopByCompany> findByKey(String contractCd, String companyCd);

	public List<StopByCompany> findByContractCodeAndState(String contractCd, int value);
	/**
	 * @author hoatt
	 * get 会社単位の利用停止
	 * @param 契約コード - contractCd
	 * @return
	 */
	public List<StopByCompany> getListComByContractCD(String contractCd);
	/**
	  * ドメインモデル「会社単位の利用停止」を取得する
	  * @param 契約コード contractCd
	  * @param 利用停止モード stopMode
	  * @return
	  */
	public List<StopByCompany>findByCdStatus(String contractCd, int stopMode);
}
