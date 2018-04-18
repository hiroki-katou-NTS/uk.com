package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import java.util.Optional;

/**
 * @author thanhpv
 *
 */
public interface MailSettingAutomaticRepository {

	/**
	 * @param CompanyId
	 * @return MailSettingAutomatic Optional
	 */
	public Optional<MailSettingAutomatic> findByCompanyId(String companyId);
	
	/**
	 * @param MailSettingAutomatic
	 */
	public void create(MailSettingAutomatic mailSettingAutomatic); 
	
	/**
	 * @param MailSettingAutomatic
	 */
	public void update(MailSettingAutomatic mailSettingAutomatic);
	
}
