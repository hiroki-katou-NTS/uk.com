package nts.uk.ctx.at.request.dom.setting.company.request;
/**
 * 
 * @author Doan Duy Hung
 *
 */
public interface RequestSettingRepository {
	
	public RequestSetting findByCompany(String companyID);
	
}
