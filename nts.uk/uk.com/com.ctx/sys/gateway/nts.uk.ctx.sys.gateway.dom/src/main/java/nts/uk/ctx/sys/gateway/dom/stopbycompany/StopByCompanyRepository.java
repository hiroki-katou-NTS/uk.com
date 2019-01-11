package nts.uk.ctx.sys.gateway.dom.stopbycompany;

import java.util.List;
import java.util.Optional;

public interface StopByCompanyRepository {

	public void insert(StopByCompany domain);

	public void update(StopByCompany domain);

	public Optional<StopByCompany> findByKey(String contractCd, String companyCd);

	public List<StopByCompany> findByContractCodeAndState(String contractCd, int value);
}
