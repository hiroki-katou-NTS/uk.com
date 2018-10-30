package nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice;

import java.util.List;
import java.util.Optional;

public interface SocialInsuranceOfficeRepository {
	
	List<SocialInsuranceOffice> findByCid(String cid);
	
	Optional<SocialInsuranceOffice> findByCodeAndCid(String cid, String code);
	
	public void add(SocialInsuranceOffice domain);
	
	public void update(SocialInsuranceOffice domain);
	
	public void remove(String cid, String code);
	
}
