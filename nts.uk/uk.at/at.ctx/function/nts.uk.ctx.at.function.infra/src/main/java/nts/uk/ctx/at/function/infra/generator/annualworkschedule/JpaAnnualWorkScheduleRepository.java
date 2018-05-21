package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.Employee;
import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.enums.OutputAgreementTime;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.HeaderData;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class JpaAnnualWorkScheduleRepository implements AnnualWorkScheduleRepository {

	@Inject
	private SetOutItemsWoScRepository setOutItemsWoScRepository;

	/**
	 * TODO linh.nd
	 */
	@Override
	public ExportData getData(String cid, String setItemsOutputCd, String startYearMonth,
			String endYearMonth, List<Employee> employees) {
		ExportData data = new ExportData();

		//ドメインモデル「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		SetOutItemsWoSc setOutItemsWoSc = setOutItemsWoScRepository.getSetOutItemsWoScById(cid, setItemsOutputCd).get();
		HeaderData header = new HeaderData();
		header.setTitle(setOutItemsWoSc.getName().v());
		header.setPeriod(TextResource.localize("KWR008_41") + " " + startYearMonth + "～" + endYearMonth);
		//出力項目数による個人情報の出力制限について
		int sizeItemOut = setOutItemsWoSc.getListItemOutTblBook().size();
		if (sizeItemOut == 1) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_44"));
		} else if (sizeItemOut == 2) {
			header.setEmpInfoLabel(TextResource.localize("KWR008_43"));
		} else {
			header.setEmpInfoLabel(TextResource.localize("KWR008_42"));
		}

		YearMonth startYm = YearMonth.parse(startYearMonth, DateTimeFormatter.ofPattern("uuuu/MM"));
		YearMonth endYm = YearMonth.parse(endYearMonth, DateTimeFormatter.ofPattern("uuuu/MM"));
		header.setOutputAgreementTime(setOutItemsWoSc.getDisplayFormat());
		//set C2_3, C2_5
		header.setGroupMonths(this.createGroupMonths(startYm, endYm, setOutItemsWoSc.getDisplayFormat()));
		// C1_2
		List<String> months = Arrays.asList("", "", "", "", "", "", "", "", "", "", "", "");
		int i = 0;
		while (!startYm.isAfter(endYm)) {
			months.set(i, String.valueOf(startYm.getMonthValue()));
			startYm = startYm.plusMonths(1);
			i++;
		}
		header.setMonths(months);

		//TODO
		data.setExportItems(setOutItemsWoSc.getListItemOutTblBook().stream().filter(item -> item.isUseClassification())
				.map(m -> new ExportItem(m.getHeadingName().v())).collect(Collectors.toList()));
		data.setHeader(header);
		
		//TODO アルゴリズム「年間勤務表の作成」を実行する
		//36協定時間を出力するかのチェックをする (2018/05/03: always true)
		boolean outputAgreementTime36 = true;
		//
		if (outputAgreementTime36) {
			
		} else {
			
		}
		//TODO 社員の情報を取得する
		List<EmployeeData> employeesData = employees.stream()
			.sorted((m1, m2) -> m1.getWorkplaceName().compareTo(m2.getWorkplaceName()))
			.map(m -> {
			EmployeeData emp = new EmployeeData();
			emp.setEmployeeInfo(Arrays.asList(TextResource.localize("KWR008_50") + " " + m.getWorkplaceName()));
			//TODO
			if (sizeItemOut == 1) {
				emp.getEmployeeInfo().add(m.getEmployeeCode() + " " + m.getName());
			} else if (sizeItemOut == 2) {
				emp.getEmployeeInfo().add(m.getEmployeeCode() + " " + m.getName());
			} else {
				emp.getEmployeeInfo().add(m.getEmployeeCode() + " " + m.getName());
			}
			emp.setAnnualWorkSchedule(new ArrayList<>());
			data.getExportItems().forEach(item -> emp.getAnnualWorkSchedule().add(new AnnualWorkScheduleData(item.getHeadingName())));
			return emp;
		}).collect(Collectors.toList());
		

		data.setEmployees(employeesData);
		return data;
	}

	/**
	 * Create C2_3, C2_5
	 * @param startYm
	 * @param endYm
	 * @param outputAgreementTime
	 * @return
	 */
	private List<String> createGroupMonths(YearMonth startYm, YearMonth endYm, OutputAgreementTime outputAgreementTime) {
		List<String> groupMonths = null;
		int standardMonth = 4; //TODO
		YearMonth stardardYm = YearMonth.of(startYm.getYear(), standardMonth);
		if (stardardYm.isAfter(startYm)) stardardYm = stardardYm.minusYears(1);

		int distances = 0;
		if (OutputAgreementTime.TWO_MONTH.equals(outputAgreementTime)) distances = 2;
		else if (OutputAgreementTime.THREE_MONTH.equals(outputAgreementTime)) distances = 3;

		if (distances != 0) {
			groupMonths = new ArrayList<>();
			stardardYm = stardardYm.plusMonths(stardardYm.until(startYm, ChronoUnit.MONTHS)/distances * distances);
			int groupStartM, groupEndM;
			do {
				groupStartM = (stardardYm.isBefore(startYm)? startYm : stardardYm).getMonthValue();
				stardardYm = stardardYm.plusMonths(distances - 1);
				groupEndM = (stardardYm.isAfter(endYm)? endYm : stardardYm).getMonthValue();
				if (groupStartM == groupEndM) groupMonths.add(String.valueOf(groupStartM));
				else groupMonths.add(groupStartM + "～" + groupEndM);
				stardardYm = stardardYm.plusMonths(1);
			} while (!stardardYm.isAfter(endYm));
		}
		return groupMonths;
	}
}
