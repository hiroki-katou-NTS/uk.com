/**
 * 
 */
package nts.uk.ctx.at.record.app.find.login;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.app.find.login.dto.StampCompany;

/**
 * @author laitv
 * 打刻入力のログイン設定を取得する
 *
 */
@Stateless
public class LoginSettingsForTimeStampInput {
	
	@Inject
	private CompaniesHaveBeenStampedFinder comHaveBeenStampedFinder;
	
	public List<StampCompany> getLoginSettingsForTimeStampInput(){
		
		return comHaveBeenStampedFinder.getListOfCompaniesHaveBeenStamped(Optional.empty());
		
	}
}
