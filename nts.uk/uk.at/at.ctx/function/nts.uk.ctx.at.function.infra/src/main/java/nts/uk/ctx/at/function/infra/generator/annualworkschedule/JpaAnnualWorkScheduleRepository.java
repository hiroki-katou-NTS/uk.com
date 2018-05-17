package nts.uk.ctx.at.function.infra.generator.annualworkschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.annualworkschedule.SetOutItemsWoSc;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.AnnualWorkScheduleRepository;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.EmployeeData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportData;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.ExportItem;
import nts.uk.ctx.at.function.dom.annualworkschedule.export.HeaderData;
import nts.uk.ctx.at.function.dom.annualworkschedule.repository.SetOutItemsWoScRepository;

@Stateless
public class JpaAnnualWorkScheduleRepository implements AnnualWorkScheduleRepository {

	@Inject
	private SetOutItemsWoScRepository setOutItemsWoScRepository;

	/**
	 * TODO linh.nd
	 */
	@Override
	public ExportData getData(String cid, String setItemsOutputCd) {
		ExportData data = new ExportData();

		//ドメインモデル「年間勤務表（36チェックリスト）の出力項目設定」を取得する
		SetOutItemsWoSc setOutItemsWoSc = setOutItemsWoScRepository.getSetOutItemsWoScById(cid, setItemsOutputCd).get();
		HeaderData header = new HeaderData();
		header.setTitle(setOutItemsWoSc.getName().v());
		data.setExportItems(setOutItemsWoSc.getListItemOutTblBook().stream().map(m -> new ExportItem(m.getHeadingName().v())).collect(Collectors.toList()));
		data.setHeader(header);
		
		//TODO アルゴリズム「年間勤務表の作成」を実行する
		//36協定時間を出力するかのチェックをする (2018/05/03: always true)
		boolean outputAgreementTime36 = true;
		//
		if (outputAgreementTime36) {
			
		} else {
			
		}

		//TODO 社員の情報を取得する
		List<EmployeeData> employees = new ArrayList<>();
		List<String> workplace1 = Arrays.asList("Workplace1");
		List<String> workplace2 = Arrays.asList("Workplace2");
		List<String> workplace3 = Arrays.asList("Workplace3");
		List<AnnualWorkScheduleData> scheduleDatas = setOutItemsWoSc.getListItemOutTblBook()
				.stream().map(m -> new AnnualWorkScheduleData(m.getHeadingName().v()))
				.collect(Collectors.toList());
		EmployeeData ed = new EmployeeData();
		EmployeeData ed1 = new EmployeeData();
		EmployeeData ed2 = new EmployeeData();
		EmployeeData ed3 = new EmployeeData();
		EmployeeData ed4 = new EmployeeData();
		ed.setEmployeeInfo(workplace1);
		ed.setAnnualWorkSchedule(scheduleDatas);
		ed1.setEmployeeInfo(workplace1);
		ed1.setAnnualWorkSchedule(scheduleDatas);
		ed2.setEmployeeInfo(workplace1);
		ed2.setAnnualWorkSchedule(scheduleDatas);
		ed3.setEmployeeInfo(workplace1);
		ed3.setAnnualWorkSchedule(scheduleDatas);
		ed4.setEmployeeInfo(workplace1);
		ed4.setAnnualWorkSchedule(scheduleDatas);
		employees.addAll(Arrays.asList(ed, ed1, ed2, ed3, ed4));
		EmployeeData ed5 = new EmployeeData();
		EmployeeData ed6 = new EmployeeData();
		EmployeeData ed7 = new EmployeeData();
		EmployeeData ed8 = new EmployeeData();
		ed5.setEmployeeInfo(workplace2);
		ed5.setAnnualWorkSchedule(scheduleDatas);
		ed6.setEmployeeInfo(workplace2);
		ed6.setAnnualWorkSchedule(scheduleDatas);
		ed7.setEmployeeInfo(workplace2);
		ed7.setAnnualWorkSchedule(scheduleDatas);
		ed8.setEmployeeInfo(workplace2);
		ed8.setAnnualWorkSchedule(scheduleDatas);
		employees.addAll(Arrays.asList(ed5, ed6, ed7, ed8));
		EmployeeData ed9 = new EmployeeData();
		EmployeeData ed10 = new EmployeeData();
		EmployeeData ed11 = new EmployeeData();
		ed9.setEmployeeInfo(workplace3);
		ed9.setAnnualWorkSchedule(scheduleDatas);
		ed10.setEmployeeInfo(workplace3);
		ed10.setAnnualWorkSchedule(scheduleDatas);
		ed11.setEmployeeInfo(workplace3);
		ed11.setAnnualWorkSchedule(scheduleDatas);
		employees.addAll(Arrays.asList(ed9, ed10, ed11));

		data.setEmployees(employees);
		return data;
	}
}
