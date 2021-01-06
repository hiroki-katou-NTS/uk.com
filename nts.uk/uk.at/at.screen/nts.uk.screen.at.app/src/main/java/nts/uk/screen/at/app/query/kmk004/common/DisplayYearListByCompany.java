package nts.uk.screen.at.app.query.kmk004.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.time.calendar.Year;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSet.LaborWorkTypeAttr;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.monunit.MonthlyWorkTimeSetRepo;
import nts.uk.ctx.bs.company.dom.company.Company;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.company.dom.company.GetYearFromYearMonthPeriod;
import nts.uk.shr.com.context.AppContexts;

/**
 * 会社別年度リストを表示する
 * UKDesign.UniversalK.就業.KDW_日別実績.KMK_計算マスタ.KMK004_法定労働時間の登録（New）.メニュー別OCD（共通）.会社別年度リストを表示する.会社別年度リストを表示する
 * @author chungnt
 *
 */

@Stateless
public class DisplayYearListByCompany {

	@Inject
	private MonthlyWorkTimeSetRepo monthlyWorkTimeSetRepo;
	@Inject
	private CompanyRepository companyRepository;
	
	public List<YearDto> get(int workTypeAttr) {
	
		List<YearDto> result = new ArrayList<>();
		
		String cid = AppContexts.user().companyId();
		
		//1 call 会社別月単位労働時間
		List<MonthlyWorkTimeSetCom> monthlyWorkTimeSetComs = this.monthlyWorkTimeSetRepo.findMonthlyWorkTimeSetComByCid(cid, EnumAdaptor.valueOf(workTypeAttr, LaborWorkTypeAttr.class));
		
		//2 call DS 年月期間から年度を取得
		Require require = new Require(companyRepository);
		
		if(!monthlyWorkTimeSetComs.isEmpty()) {
			List<Year> list = GetYearFromYearMonthPeriod.getYearFromYearMonthPeriod(require,
					cid,
					monthlyWorkTimeSetComs.stream().map(m -> m.getYm()).collect(Collectors.toList()));
			
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
