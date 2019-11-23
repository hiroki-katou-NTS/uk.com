package nts.uk.file.pr.infra.report.printconfig.empinsreportsetting;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.LineFeedCode;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.OfficeCls;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoExportRow;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportSettingExportData;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsReportTxtSettingCsvGenerator;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

@Stateless
public class EmpInsReportTxtSettingCsvFileGenerator extends AsposeCellsReportGenerator
		implements EmpInsReportTxtSettingCsvGenerator {
	@Inject
	private JapaneseErasAdapter erasAdapter;

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

	private static final String A1_98 = "10191";

	private static final String A1_116 = "2";

	private static final String SEPERATOR = ",";

	private static final String LINE_BREAK = "\r\n";

	@Override
	public void generate(FileGeneratorContext generatorContext, EmpInsReportSettingExportData dataSource) {
		try (val reportContext = this.createEmptyContext(REPORT_ID)) {
			StringBuilder valueBuilder = new StringBuilder();
			this.fillData(valueBuilder, dataSource);
			this.saveAsCSV(this.createNewFile(generatorContext, FILE_NAME), valueBuilder.toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void saveAsCSV(OutputStream outputStream, String value) {
		try {
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
			writer.write("\ufeff"); // write UTF-8-BOM
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JapaneseDate toJapaneseDate(GeneralDate date) {
		Optional<JapaneseEraName> era = this.erasAdapter.getAllEras().eraOf(date);
		return new JapaneseDate(date, era.get());
	}

	private void fillData(StringBuilder valueBuilder, EmpInsReportSettingExportData dataSource) {
		int lineFeedCode = dataSource.getEmpInsReportTxtSetting().getLineFeedCode();

		// row 1
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String header = ROW_1_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_1_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}

		}

		// row 2
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String value = "";
			if (c == 0) {
				// LaborInsuranceOffice.EmpInsInfo.cityCode
				value = "cityCode";
			}
			if (c == 1) {
				// LaborInsuranceOffice.EmpInsInfo.businessSymbol
				value = "businessSymbol";
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
			valueBuilder.append(value);
			if (c < ROW_1_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 3
		valueBuilder.append(A1_13);
		if (lineFeedCode != LineFeedCode.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 4
		valueBuilder.append(A1_14);
		valueBuilder.append(SEPERATOR);
		valueBuilder.append(A1_15);
		if (lineFeedCode != LineFeedCode.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 5
		valueBuilder.append(A1_16);
		valueBuilder.append(SEPERATOR);
		valueBuilder.append(A1_17);
		if (lineFeedCode != LineFeedCode.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 6
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String header = ROW_6_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_6_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 7
		int officeCls = dataSource.getEmpInsReportSetting().getOfficeClsAtr();
		CompanyInfor companyInfo = dataSource.getCompanyInfo();
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String value = "";
			if (c == 0) {
				// LaborInsuranceOffice.EmpInsInfo.cityCode
				value = "cityCode";
			}
			if (c == 1) {
				// LaborInsuranceOffice.EmpInsInfo.businessSymbol
				value = "businessSymbol";
			}
			if (c == 2) {
				// LaborInsuranceOffice.code
				value = "code";
			}
			if (c == 3) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getPostCd();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "ZipCode"; // LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.ZipCode
			}
			if (c == 4) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getPostCd();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "ZipCode"; // LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.ZipCode
			}
			if (c == 5) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getAdd_1() + " - " + companyInfo.getAdd_2();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "Address 1 + 2"; // LaborInsuranceOffice.BasicInformation.LaborInsuranceOfficeAddress.Address
												// 1 + 2
			}
			if (c == 6) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getCompanyName();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value)
					value = "Name"; // LaborInsuranceOffice.Name
			}
			if (c == 7) {
				value = "BasicInformation"; // LaborInsuranceOffice.BasicInformation
			}
			if (c == 8) {
				value = "BasicInformationme"; // LaborInsuranceOffice.BasicInformation
			}
			if (c == 9) {
				value = "EmploymentInsuranceInformation"; // LaborInsuranceOffice.EmploymentInsuranceInformation
			}
			if (c == 10) {
				value = "EmploymentInsuranceInformation"; // LaborInsuranceOffice.EmploymentInsuranceInformation
			}
			if (c == 11) {
				value = "EmploymentInsuranceInformation"; // LaborInsuranceOffice.EmploymentInsuranceInformation
			}
			valueBuilder.append(value);
			if (c < ROW_6_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 8
		valueBuilder.append(A1_42);
		if (lineFeedCode != LineFeedCode.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// main data
		this.fillDataRows(valueBuilder, dataSource);
	}

	private void fillDataRows(StringBuilder valueBuilder, EmpInsReportSettingExportData dataSource) {
		int lineFeedCode = dataSource.getEmpInsReportTxtSetting().getLineFeedCode();

		// row 9
		for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
			String header = ROW_9_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_9_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// data
		List<EmpInsLossInfoExportRow> rows = dataSource.getRowsData();
		EmpInsRptSettingCommand empInsRptSetting = dataSource.getEmpInsReportSetting();
		for (EmpInsLossInfoExportRow row : rows) {
			String employeeInsuranceNumber = row.getInsuranceNumber();
			JapaneseDate empInsHistStart = this.toJapaneseDate(row.getEmployeeInsurancePeriodStart());
			JapaneseDate empInsHistEnd = this.toJapaneseDate(row.getEmployeeInsurancePeriodEnd());
			JapaneseDate birthDay = this.toJapaneseDate(row.getPersonBirthDay());
			for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
				String value = "";
				if (c == 0) {
					value = A1_98;
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
				if (c == 6) {
					value = row.getLaborInsuranceOfficeNumber1();
				}
				if (c == 7) {
					value = row.getLaborInsuranceOfficeNumber2();
				}
				if (c == 8) {
					value = row.getLaborInsuranceOfficeNumber3();
				}
				if (c == 9 && empInsHistStart != null) {
					value = empInsHistStart.era();
				}
				if (c == 10 && empInsHistStart != null) {
					value = empInsHistStart.year() + "";
				}
				if (c == 11 && empInsHistStart != null) {
					value = empInsHistStart.month() + "";
				}
				if (c == 12 && empInsHistStart != null) {
					value = empInsHistStart.day() + "";
				}
				if (c == 13 && empInsHistEnd != null) {
					value = empInsHistEnd.era();
				}
				if (c == 14 && empInsHistEnd != null) {
					value = empInsHistEnd.year() + "";
				}
				if (c == 15 && empInsHistEnd != null) {
					value = empInsHistEnd.month() + "";
				}
				if (c == 16 && empInsHistEnd != null) {
					value = empInsHistEnd.day() + "";
				}
				if (c == 17 && row.getCauseOfLossAtr() != null) {
					value = (row.getCauseOfLossAtr() + 1) + "";
				}
				if (c == 18) {
					value = A1_116;
				}
				if (c == 22) {
					value = row.getScheduleOfReplenishment();
				}
				if (c == 23) {
					value = empInsRptSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value
							? KatakanaConverter.fullKatakanaToHalf(row.getPersonNameKana())
							: KatakanaConverter.fullKatakanaToHalf(row.getPersonReportNameKana());
				}
				if (c == 24) {
					value = empInsRptSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value
							? row.getPersonName() : row.getPersonReportName();
				}
				if (c == 25 && row.getPersonGender() != null) {
					value = (row.getPersonGender() + 1) + "";
				}
				if (c == 26 && birthDay != null) {
					value = birthDay.era();
				}
				if (c == 27 && birthDay != null) {
					value = birthDay.year() + "";
				}
				if (c == 28 && birthDay != null) {
					value = birthDay.month() + "";
				}
				if (c == 29 && birthDay != null) {
					value = birthDay.day() + "";
				}
				if (c == 30) {
					value = row.getPersonCurrentAddress();
				}
				if (c == 31) {
					value = empInsRptSetting.getOfficeClsAtr() == OfficeCls.OUTPUT_COMPANY.value ? row.getCompanyName()
							: row.getLaborInsuranceOfficeName();
				}
				if (c == 36) {
					value = row.getCauseOfLossInsurance();
				}
				if (c == 37 && row.getScheduleWorkingHourPerWeek() != null) {
					int hour = row.getScheduleWorkingHourPerWeek().hour();
					value = hour < 10 ? "0" + hour : "" + hour;
				}
				if (c == 38 && row.getScheduleWorkingHourPerWeek() != null) {
					int minute = row.getScheduleWorkingHourPerWeek().minute();
					value = minute < 10 ? "0" + minute : "" + minute;
				}
				if (c == 39) {
					value = row.getPublicEmploymentSecurityOfficeName();
				}
				if (c == 40) {
					value = row.getPersonNameRomanji();
				}
				if (c >= 41 && c <= 49) {
					value = "dummy foreigner";
				}
				valueBuilder.append(value);
				if (c < ROW_9_HEADERS.size() - 1) {
					valueBuilder.append(SEPERATOR);
				} else if (lineFeedCode != LineFeedCode.NO_ADD.value) {
					valueBuilder.append(LINE_BREAK);
				}
			}
		}
	}
}
