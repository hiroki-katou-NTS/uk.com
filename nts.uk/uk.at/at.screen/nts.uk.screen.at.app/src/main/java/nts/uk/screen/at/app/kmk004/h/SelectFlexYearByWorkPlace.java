package nts.uk.screen.at.app.kmk004.h;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.screen.at.app.kmk004.g.YearMonthPeriodDto;
import nts.uk.screen.at.app.query.kmk004.common.MonthlyWorkingHoursByWorkplace;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnlb
 *
 *         UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.H：職場別法定労働時間の登録（フレックス勤務）.メニュー別OCD.職場別年度（フレックス勤務）を選択する
 */
@Stateless
public class SelectFlexYearByWorkPlace {

	@Inject
	private MonthlyWorkingHoursByWorkplace monthlyWorkingHoursByWorkplace;

	@Inject
	private CompanyAdapter companyRepository;

	public SelectFlexYearByWorkPlaceDto selectFlexYearByWorkPlace(String wkpId, int year) {
		
		SelectFlexYearByWorkPlaceDto result = new SelectFlexYearByWorkPlaceDto();
		
		result.setYearMonthPeriod(
				YearMonthPeriodDto.fromDomain(this.companyRepository.get(AppContexts.user().companyId(), year)));

		// 職場ID＝選択中の職場ID
		// 勤務区分＝2：フレックス勤務
		// 年度＝選択中の年度
		result.setTimeSetComs(this.monthlyWorkingHoursByWorkplace.get(wkpId, LaborWorkTypeAttr.FLEX, year));

		return result;
	}
}
