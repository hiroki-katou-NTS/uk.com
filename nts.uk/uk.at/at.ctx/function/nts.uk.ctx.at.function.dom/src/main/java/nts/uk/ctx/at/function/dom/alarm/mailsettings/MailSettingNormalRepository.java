package nts.uk.ctx.at.function.dom.alarm.mailsettings;

import java.util.Optional;

/**
 * @author thanhpv
 *
 */
public interface MailSettingNormalRepository {

	/**
	 * @param CompanyId
	 * @return Optional MailSettingAutomatic
	 */
	public Optional<MailSettingNormal> findByCompanyId(String companyId);
	
	/**
	 * @param MailSettingAutomatic
	 */
	public void create(MailSettingNormal mailSettingNormal); 
	
	/**
	 * @param MailSettingAutomatic
	 */
	public void update(MailSettingNormal mailSettingNormal);
}
