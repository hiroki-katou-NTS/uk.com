package nts.uk.ctx.bs.employee.dom.employee.history;

public interface AffCompanyInfoRepository {

	public void add(AffCompanyInfo domain);
	public void update(AffCompanyInfo domain);
	
	public void remove(AffCompanyInfo domain);
	public void remove(String histId);
	
	public AffCompanyInfo getAffCompanyInfoByHistId(String histId);

}
