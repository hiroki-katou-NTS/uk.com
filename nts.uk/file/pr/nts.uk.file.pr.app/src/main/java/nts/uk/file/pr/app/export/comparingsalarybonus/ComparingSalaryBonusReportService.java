package nts.uk.file.pr.app.export.comparingsalarybonus;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusHeaderReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.data.ComparingSalaryBonusReportData;
import nts.uk.file.pr.app.export.comparingsalarybonus.query.ComparingSalaryBonusQuery;
@Stateless
public class ComparingSalaryBonusReportService extends ExportService<ComparingSalaryBonusQuery> {

	@Inject
	private ComparingSalaryBonusGenerator compareGenertor;

	@Override
	protected void handle(ExportServiceContext<ComparingSalaryBonusQuery> context) {
		ComparingSalaryBonusReportData  reportData = fakeData();
		this.compareGenertor.generate(context.getGeneratorContext(), reportData);

	}

	private ComparingSalaryBonusReportData fakeData() {
		ComparingSalaryBonusReportData reportData = new ComparingSalaryBonusReportData();
		ComparingSalaryBonusHeaderReportData headerData = new ComparingSalaryBonusHeaderReportData();
		headerData.setTitleReport("明細金額比較表");
		headerData.setNameCompany("【日通 システム株式会社】");
		headerData.setNameDeparment("【部門： 】");
		headerData.setTypeDeparment("【分類： 】");
		headerData.setPostion("【職位： 】");
		headerData.setTargetYearMonth("【処理年月： 】");
		reportData.setHeaderData(headerData);

		return reportData;
	}

}
