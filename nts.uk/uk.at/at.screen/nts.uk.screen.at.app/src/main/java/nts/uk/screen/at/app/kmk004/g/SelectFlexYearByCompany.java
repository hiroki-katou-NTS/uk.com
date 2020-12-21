package nts.uk.screen.at.app.kmk004.g;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingHoursByCompany;
import nts.uk.screen.at.app.query.kmk004.common.DisplayMonthlyWorkingInput;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;

/**
 * 
 * @author sonnlb
 * 
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.G：会社別法定労働時間の登録（フレックス勤務）.メニュー別OCD.会社別年度（フレックス勤務）を選択する
 */

@Stateless
public class SelectFlexYearByCompany {

	@Inject
	private DisplayMonthlyWorkingHoursByCompany displayMonthlyWorkingHoursByCompany;
	
	@Inject
	private CompanyAdapter companyRepository;

	public SelectFlexYearByCompanyDto selectYearByCompany(int year) {
		
		SelectFlexYearByCompanyDto result = new SelectFlexYearByCompanyDto();
		
		result.setYearMonthPeriod(
				YearMonthPeriodDto.fromDomain(this.companyRepository.get(AppContexts.user().companyId(), year)));
		
		// input：
		// 勤務区分＝2：フレックス勤務
		// 年度＝選択中の年度
		result.setTimeSetComs(this.displayMonthlyWorkingHoursByCompany.get(new DisplayMonthlyWorkingInput(2, year)));
		
		return result;
	}
}
