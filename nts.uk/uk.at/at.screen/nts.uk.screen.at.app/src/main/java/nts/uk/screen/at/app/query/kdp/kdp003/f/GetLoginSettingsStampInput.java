/**
 * 
 */
package nts.uk.screen.at.app.query.kdp.kdp003.f;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.query.kdp.kdp003.f.dto.GetListCompanyHasStampedDto;

/**
 * @author laitv
 * 打刻入力のログイン設定を取得する
 * Screen 1
 */
@Stateless
public class GetLoginSettingsStampInput {
	
	@Inject
	private GetListCompanyhaveBeenStamped finder;
	
	/**
	 * 【input】
	 * 【output】 ・打刻会社一覧
	 */
	public List<GetListCompanyHasStampedDto> getLoginSettingsForTimeStampInput(GetLoginSettingsStampParam input){
		return finder.getListOfCompaniesHaveBeenStamped(Optional.empty(), input);
		
	}
}
