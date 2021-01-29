package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetSha;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetYearFromYearMonthPeriod;
import nts.uk.shr.com.context.AppContexts;

/**
 * 社員別年度リストを表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.社員別年度リストを表示する.社員別年度リストを表示する
 * @author chungnt
 *
 */

@Stateless
public class YearListByEmployee {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	
	@Inject
	private CompanyRepository companyRepository;
	
	/**
	 * 
	 * @param sid		　社員ID
	 * @param laborAttr	　勤務区分
	 */
	public List<YearDto> get(String sid, LaborWorkTypeAttr laborAttr){
		
		String cid = AppContexts.user().companyId();
		List<YearDto> result = new ArrayList<>();
		
		//1 Call 社員別月単位労働時間
		List<MonthlyWorkTimeSetSha> timeSetShas = monthlyWorkTimeSetRepo.findEmployee(cid, sid, laborAttr);
		
		//2 Call 年月期間から年度を取得
		Require require = new Require(companyRepository);

		if(!timeSetShas.isEmpty()) {
			List<Year> list = GetYearFromYearMonthPeriod.getYearFromYearMonthPeriod(require, cid,
					timeSetShas.stream().map(m -> m.getYm()).collect(Collectors.toList()));

			result = list.stream().map(m -> {
				return new YearDto(m.v());
			}).collect(Collectors.toList());
		}
		
		return result;
	}
	
	@AllArgsConstructor
	public static class Require implements GetYearFromYearMonthPeriod.Require {

		private CompanyRepository companyRepository;

		@Override
		public Optional<Company> find(String companyId) {
			return companyRepository.find(AppContexts.user().companyId());
		}
	}
	
}
