package nts.uk.ctx.at.function.app.export.annualworkschedule;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingAdapter;
import nts.uk.ctx.at.function.dom.adapter.standardtime.AgreementOperationSettingImport;
import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleGenerator;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.shared.dom.common.Year;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AnnualWorkScheduleExportService extends ExportService<AnnualWorkScheduleExportQuery> {
	@Inject
	private AnnualWorkScheduleRepository repostory;
	@Inject
	private AnnualWorkScheduleGenerator generator;
	@Inject
	private AgreementOperationSettingAdapter agreementOperationSettingAdapter;

	@Override
	protected void handle(ExportServiceContext<AnnualWorkScheduleExportQuery> context) {
		String companyId = AppContexts.user().companyId();
		AnnualWorkScheduleExportQuery query = context.getQuery();
		List<Employee> employees = query.getEmployees().stream()
				.map(m -> new Employee(m.getEmployeeId(), m.getCode(), m.getName(), m.getWorkplaceName()))
				.collect(Collectors.toList());
		Year fiscalYear = null;
		YearMonth startYm = null;
		YearMonth endYm = null;
		if (query.getPrintFormat() == 1) {
			fiscalYear = new Year(Integer.parseInt(query.getFiscalYear()));
			startYm = this.getStartYearMonth(companyId, fiscalYear);
			endYm = startYm.plusMonths(11);
		} else {
			startYm = YearMonth.parse(query.getStartYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
			endYm = YearMonth.parse(query.getEndYearMonth(), DateTimeFormatter.ofPattern("uuuu/MM"));
		}

		ExportData data = this.repostory.outputProcess(companyId, query.getSetItemsOutputCd(), fiscalYear, startYm,
				endYm, employees, query.getPrintFormat(), query.getBreakPage());
		// invoke generator
		this.generator.generate(context.getGeneratorContext(), data);
	}

	/**
	 * Get startYm, endYm
	 * 
	 * @param startYm
	 *            output
	 * @param endYm
	 *            output
	 */
	private YearMonth getStartYearMonth(String cid, Year fiscalYear) {
		// ドメインモデル「３６協定運用設定」を取得する
		Optional<AgreementOperationSettingImport> agreementSetObj = agreementOperationSettingAdapter.find(cid);
		String month = "01";
		// 「36協定運用設定」．起算月から年度の期間を求める
		if (agreementSetObj.isPresent()) {
			switch (agreementSetObj.get().getStartingMonth()) {
			case JANUARY:
				month = "01";
				break;
			case FEBRUARY:
				month = "02";
				break;
			case MARCH:
				month = "03";
				break;
			case APRIL:
				month = "04";
				break;
			case MAY:
				month = "05";
				break;
			case JUNE:
				month = "06";
				break;
			case JULY:
				month = "07";
				break;
			case AUGUST:
				month = "08";
				break;
			case SEPTEMBER:
				month = "09";
				break;
			case OCTOBER:
				month = "10";
				break;
			case NOVEMBER:
				month = "11";
				break;
			case DECEMBER:
				month = "12";
				break;
			}
		}
		StringBuilder ym = new StringBuilder();
		ym.append(fiscalYear.toString());
		ym.append('/');
		ym.append(month);
		return YearMonth.parse(ym.toString(), DateTimeFormatter.ofPattern("uuuu/MM"));
	}
}
