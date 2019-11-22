package nts.uk.file.pr.infra.report.printconfig.empinsreportsetting;

import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import javax.ejb.Stateless;

import com.aspose.cells.Cells;
import com.aspose.cells.Encoding;
import com.aspose.cells.SaveFormat;
import com.aspose.cells.TxtSaveOptions;
import com.aspose.cells.TxtValueQuoteType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.WorksheetCollection;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.adapter.employee.employee.EmployeeInfoEx;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.CurrentPersonResidence;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.OfficeCls;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsHist;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsLossInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfo;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.EmpInsNumInfoRepository;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.ScheduleForReplenishment;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportSettingExportData;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportTxtSettingCsvGenerator;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class EmpInsReportTxtSettingCsvFileGenerator extends AsposeCellsReportGenerator
		implements EmpInsReportTxtSettingCsvGenerator {

	private static final String REPORT_ID = "CSV_GENERATOR";

	private static final String FILE_NAME = "10191-soshitsu.csv";

	// row 1
	private static final List<String> ROW_1_HEADERS = Arrays.asList("都市区符号", "事業所記号", "ＦＤ通番", "作成年月日", "代表届書コード",
			"連記式項目バージョン");

	// row 2
	private static final String A1_11 = "22223";
	private static final String A1_12 = "03";

	// row 3
	private static final String A1_13 = "[kanri]";

	// row 4
	private static final String A1_14 = "社会保険労務士氏名";
	private static final String A1_15 = "事業所情報数";

	// row 5
	private static final String A1_16 = "";
	private static final String A1_17 = "001";

	// row 6
	private static final List<String> ROW_6_HEADERS = Arrays.asList("都市区符号", "事業所記号", "事業所番号", "親番号（郵便番号）", "子番号（郵便番号）",
			"事業所所在地", "事業所名称", "事業主氏名", "電話番号", "雇用保険適用事業所番号（安定所番号）", "雇用保険適用事業所番号（一連番号）", "雇用保険適用事業所番号（CD）");

	// row 8
	private static final String A1_42 = "[data]";

	// row 9
	private static final List<String> ROW_9_HEADERS = Arrays.asList("帳票種別", "安定所番号", "個人番号", "被保険者番号4桁", "被保険者番号6桁",
			"被保険者番号CD", "事業所番号（安定所番号）", "事業所番号（一連番号）", "事業所番号（CD）", "資格取得年月日（元号）", "資格取得年月日（年）", "資格取得年月日（月）",
			"資格取得年月日（日）", "離職等年月日（元号）", "離職等年月日（年）", "離職等年月日（月）", "離職等年月日（日）", "喪失原因", "離職票交付希望", "喪失時被保険者種類",
			"新氏名フリガナ（カタカナ）", "新氏名", "補充採用予定の有無", "被保険者氏名フリガナ（カタカナ）", "被保険者氏名", "性別", "生年月日（元号）", "生年月日（年）", "生年月日（月）",
			"生年月日（日）", "被保険者の住所又は居所", "事業所名称", "氏名変更年月日（元号）", "氏名変更年月日（年）", "氏名変更年月日（月）", "氏名変更年月日（日）",
			"被保険者でなくなったことの原因", "１週間の所定労働時間（時間）", "１週間の所定労働時間（分）", "あて先", "被保険者氏名（ローマ字）", "国籍・地域", "国籍地域コード", "在留資格",
			"在留資格コード", "在留期間（年）", "在留期間（月）", "在留期間（日）", "資格外活動許可の有無", "派遣・請負就労区分", "備考欄（審査者）", "確認通知年月日（元号）",
			"確認通知年月日（年）", "確認通知年月日（月）", "確認通知年月日（日）");

	@Override
	public void generate(FileGeneratorContext generatorContext, EmpInsReportSettingExportData dataSource) {
		try (val reportContext = this.createEmptyContext(REPORT_ID)) {
			Workbook workbook = reportContext.getWorkbook();
			WorksheetCollection worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.get(0);
			this.fillData(worksheet, dataSource);
			reportContext.getDesigner().setWorkbook(workbook);
			reportContext.processDesigner();
			this.saveAsCSV(this.createNewFile(generatorContext, FILE_NAME), workbook);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void saveAsCSV(OutputStream outputStream, Workbook workbook) {
		try {
			TxtSaveOptions opts = new TxtSaveOptions(SaveFormat.CSV);
			opts.setSeparator(',');
			opts.setEncoding(Encoding.getUTF8());
			opts.setQuoteType(TxtValueQuoteType.NEVER);
			workbook.save(outputStream, opts);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void fillData(Worksheet worksheet, EmpInsReportSettingExportData dataSource) {
		int row = 0;
		Cells cells = worksheet.getCells();
		// row 1
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String header = ROW_1_HEADERS.get(c);
			cells.get(row, c).setValue(header);
		}
		row++;

		// row 2
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String value = "";
			if (c == 0) {
				value = "LaborInsuranceOffice.EmpInsInfo.cityCode";
			}
			if (c == 1) {
				value = "LaborInsuranceOffice.EmpInsInfo.businessSymbol";
			}
			if (c == 2) {
				value = dataSource.getEmpInsReportTxtSetting().getFdNumber() + "";
			}
			if (c == 3) {
				value = dataSource.getCreateDate().toString("yyyyMMdd");
			}
			if (c == 4) {
				value = A1_11;
			}
			if (c == 5) {
				value = A1_12;
			}
			cells.get(row, c).setValue(value);
		}
		row++;

		// row 3
		cells.get(row, 0).setValue(A1_13);
		row++;

		// row 4
		cells.get(row, 0).setValue(A1_14);
		cells.get(row, 1).setValue(A1_15);
		row++;

		// row 5
		cells.get(row, 0).setValue(A1_16);
		cells.get(row, 1).setValue(A1_17);
		row++;

		// row 6
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String header = ROW_6_HEADERS.get(c);
			cells.get(row, c).setValue(header);
		}
		row++;

		// row 7
		int officeCls = dataSource.getEmpInsReportSetting().getOfficeClsAtr();
		CompanyInfor companyInfo = dataSource.getCompanyInfo();
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String value = "";
			if (c == 0) {
				value = "LaborInsuranceOffice.EmpInsInfo.cityCode";
			}
			if (c == 1) {
				value = "LaborInsuranceOffice.EmpInsInfo.businessSymbol";
			}
			if (c == 2) {
				value = "LaborInsuranceOffice.code";
			}
			if (c == 3) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getPostCd();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.ZipCode";
			}
			if (c == 4) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getPostCd();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.ZipCode";
			}
			if (c == 5) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getAdd_1() + " - " + companyInfo.getAdd_2();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.Address 1 + 2";
			}
			if (c == 6) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getCompanyName();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "LaborInsuranceOffice.Name";
			}
			if (c == 7) {
				value = "LaborInsuranceOffice.BasicInformation";
			}
			if (c == 8) {
				value = "LaborInsuranceOffice.BasicInformationme";
			}
			if (c == 9) {
				value = "LaborInsuranceOffice.EmploymentInsuranceInformation";
			}
			if (c == 10) {
				value = "LaborInsuranceOffice.EmploymentInsuranceInformation";
			}
			if (c == 11) {
				value = "LaborInsuranceOffice.EmploymentInsuranceInformation";
			}
			cells.get(row, c).setValue(value);
		}
		row++;

		// row 8
		cells.get(row, 0).setValue(A1_42);
		row++;

		// main data
		this.fillDataRows(cells, row, dataSource);
	}

	private void fillDataRows(Cells cells, int row, EmpInsReportSettingExportData dataSource) {
		// row 9
		for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
			String header = ROW_9_HEADERS.get(c);
			cells.get(row, c).setValue(header);
		}
		row++;

		// data
		List<String> employeeIds = dataSource.getEmployeeIds();
		EmpInsRptSettingCommand empInsRptSetting = dataSource.getEmpInsReportSetting();
		CompanyInfor companyInfo = dataSource.getCompanyInfo();
		for (String employeeId : employeeIds) {
			EmpInsNumInfo empInsNumInfo = dataSource.getEmpInsNumInfoMap().get(employeeId);
			String employeeInsuranceNumber = empInsNumInfo != null ? empInsNumInfo.getEmpInsNumber().v() : "";
			DateHistoryItem empInsHist = dataSource.getEmpInsHistMap().get(employeeId);
			EmpInsLossInfo empInsLossInfo = dataSource.getEmpInsLossInfoMap().get(employeeId);
			EmployeeInfoEx employeeInfo = dataSource.getEmployeeInfoMap().get(employeeId);
			CurrentPersonResidence personInfo = employeeInfo != null
					? dataSource.getCurrentPersonAddressMap().get(employeeInfo.getPId()) : null;
			LaborInsuranceOffice laborInsuranceOffice = dataSource.getLaborInsuranceOfficeMap().get(employeeId);
			for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
				String value = "";
				if (c == 0) {
					value = "10191";
				}
				if (c == 2) {
					value = "";
				}
				if (c == 3) {
					value = employeeInsuranceNumber.length() > 4 ? employeeInsuranceNumber.substring(0, 4)
							: employeeInsuranceNumber;
				}
				if (c == 4) {
					if (employeeInsuranceNumber.length() > 10)
						value = employeeInsuranceNumber.substring(4, 10);
					if (employeeInsuranceNumber.length() > 4)
						value = employeeInsuranceNumber.substring(4);
				}
				if (c == 5 && employeeInsuranceNumber.length() >= 11) {
					value = employeeInsuranceNumber.substring(10);
				}
				if (c == 6 && laborInsuranceOffice != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation() != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber1().isPresent()) {
					value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber1().get().v();
				}
				if (c == 7 && laborInsuranceOffice != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation() != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber2().isPresent()) {
					value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber2().get().v();
				}
				if (c == 8 && laborInsuranceOffice != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation() != null
						&& laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber3().isPresent()) {
					value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber3().get().v();
				}
				if (c == 9 && empInsHist != null) {
					value = empInsHist.start().toString("yyyyMMdd");
				}
				if (c == 10 && empInsHist != null) {
					value = empInsHist.start().year() + "";
				}
				if (c == 11 && empInsHist != null) {
					value = empInsHist.start().month() + "";
				}
				if (c == 12 && empInsHist != null) {
					value = empInsHist.start().day() + "";
				}
				if (c == 13 && empInsHist != null) {
					value = empInsHist.end().toString("yyyyMMdd");
				}
				if (c == 14 && empInsHist != null) {
					value = empInsHist.end().year() + "";
				}
				if (c == 15 && empInsHist != null) {
					value = empInsHist.end().month() + "";
				}
				if (c == 16 && empInsHist != null) {
					value = empInsHist.end().day() + "";
				}
				if (c == 17 && empInsLossInfo != null && empInsLossInfo.getCauseOfLossAtr().isPresent()) {
					value = (empInsLossInfo.getCauseOfLossAtr().get().value + 1) + "";
				}
				if (c == 18) {
					value = "2";
				}
				if (c == 22 && empInsLossInfo != null && empInsLossInfo.getScheduleForReplenishment().isPresent()) {
					value = empInsLossInfo.getScheduleForReplenishment()
							.get().value == ScheduleForReplenishment.NO.value ? "無" : "有";
				}
				if (c == 23 && personInfo != null) {
					value = empInsRptSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value
							? personInfo.getPersonNameKana() : personInfo.getTodokedeNameKana();
				}
				if (c == 24 && personInfo != null) {
					value = empInsRptSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value
							? personInfo.getPersonName() : personInfo.getTodokedeName();
				}
				if (c == 25 && employeeInfo != null) {
					value = employeeInfo.getGender() + "";
				}
				if (c == 26 && employeeInfo != null && employeeInfo.getBirthDay() != null) {
					value = employeeInfo.getBirthDay().toString("yyyyMMdd");
				}
				if (c == 27 && employeeInfo != null && employeeInfo.getBirthDay() != null) {
					value = employeeInfo.getBirthDay().year() + "";
				}
				if (c == 28 && employeeInfo != null && employeeInfo.getBirthDay() != null) {
					value = employeeInfo.getBirthDay().month() + "";
				}
				if (c == 29 && employeeInfo != null && employeeInfo.getBirthDay() != null) {
					value = employeeInfo.getBirthDay().day() + "";
				}
				if (c == 30 && personInfo != null) {
					value = personInfo.getAddress1() + personInfo.getAddress2();
				}
				if (c == 31) {
					value = empInsRptSetting.getOfficeClsAtr() == OfficeCls.OUTPUT_COMPANY.value
							? companyInfo.getCompanyName()
							: laborInsuranceOffice != null && laborInsuranceOffice.getLaborOfficeName() != null
									? laborInsuranceOffice.getLaborOfficeName().v() : "";
				}
				if (c == 36 && empInsLossInfo != null && empInsLossInfo.getCauseOfLossEmpInsurance().isPresent()) {
					value = empInsLossInfo.getCauseOfLossEmpInsurance().get().v();
				}
				if (c == 37 && empInsLossInfo != null && empInsLossInfo.getScheduleWorkingHourPerWeek().isPresent()) {
					value = empInsLossInfo.getScheduleWorkingHourPerWeek().get().hour() + "";
				}
				if (c == 38 && empInsLossInfo != null && empInsLossInfo.getScheduleWorkingHourPerWeek().isPresent()) {
					value = empInsLossInfo.getScheduleWorkingHourPerWeek().get().minute() + "";
				}
				if (c == 39) {
					value = "publicEmpSecurityOffice.name";
				}
				if (c == 40 && personInfo != null) {
					value = personInfo.getPersonName();
				}
				if (c == 41 && c <= 49) {
					value = "dummy";
				}
				cells.get(row, c).setValue(value);
			}
			row++;
		}
	}
}
