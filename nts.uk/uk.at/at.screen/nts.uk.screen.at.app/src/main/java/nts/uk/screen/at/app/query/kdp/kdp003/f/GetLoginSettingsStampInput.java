/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * @author laitv
 * 打刻入力のログイン設定を取得する
 *
 */
@Stateless
public class GetLoginSettingsStampInput {
	
	@Inject
	private GetListCompanyHasStamped comHaveBeenStampedFinder;
	
	public List<GetListCompanyHasStampedDto> getLoginSettingsForTimeStampInput(){
		
		return comHaveBeenStampedFinder.getListOfCompaniesHaveBeenStamped(Optional.empty());
		
	}
}
