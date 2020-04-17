package nts.uk.file.pr.infra.report.printconfig.empinsreportsetting;

import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.gul.text.KatakanaConverter;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.laborinsurance.laborinsuranceoffice.LaborInsuranceOffice;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptSettingCommand;
import nts.uk.ctx.pr.report.app.command.printconfig.empinsreportsetting.EmpInsRptTxtSettingCommand;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.EmpSubNameClass;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.LineFeedCodeAtr;
import nts.uk.ctx.pr.report.dom.printconfig.empinsreportsetting.OfficeCls;
import nts.uk.ctx.pr.shared.dom.empinsqualifiinfo.employmentinsqualifiinfo.RetirementReasonClsInfoRepository;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoCsvGenerator;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoExportData;
import nts.uk.file.pr.app.report.printconfig.empinsreportsetting.EmpInsLossInfoExportRow;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseEras;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Stateless
public class EmpInsLossInfoCsvFileGenerator extends AsposeCellsReportGenerator
		implements EmpInsLossInfoCsvGenerator {
	@Inject
	private JapaneseErasAdapter erasAdapter;
	
	@Inject
	private RetirementReasonClsInfoRepository reasonRepo;

	private static final String REPORT_ID = "CSV_GENERATOR";

	private static final String FILE_NAME = "10191-soshitsu.csv";

	// row 1
	private static final List<String> ROW_1_HEADERS = Arrays.asList("郡市区符号", "事業所記号", "ＦＤ通番", "作成年月日", "代表届書コード",
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
	private static final List<String> ROW_6_HEADERS = Arrays.asList("郡市区符号", "事業所記号", "事業所番号", "親番号（郵便番号）", "子番号（郵便番号）",
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
	public void generate(FileGeneratorContext generatorContext, EmpInsLossInfoExportData dataSource) {
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
			OutputStreamWriter writer = new OutputStreamWriter(outputStream, Charset.forName("Shift_JIS"));
			writer.write(value);
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private JapaneseDate toJapaneseDate(GeneralDate date, JapaneseEras eras) {
		Optional<JapaneseEraName> era = eras.eraOf(date);
		return new JapaneseDate(date, era.get());
	}

	private void fillData(StringBuilder valueBuilder, EmpInsLossInfoExportData dataSource) {
		int lineFeedCode = dataSource.getEmpInsReportTxtSetting().getLineFeedCode();
		LaborInsuranceOffice laborInsuranceOffice = dataSource.getLaborInsuranceOffice();
		int officeCls = dataSource.getEmpInsReportTxtSetting().getOfficeAtr();
		
		// row 1
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String header = ROW_1_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_1_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}

		}

		// row 2
		for (int c = 0; c < ROW_1_HEADERS.size(); c++) {
			String value = "";
			if (c == 0 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getCityCode().map(i -> i.v()).orElse("");
			}
			if (c == 1 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeCode().map(i -> i.v()).orElse("");
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
			} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 3
		valueBuilder.append(A1_13);
		if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 4
		valueBuilder.append(A1_14);
		valueBuilder.append(SEPERATOR);
		valueBuilder.append(A1_15);
		if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 5
		valueBuilder.append(A1_16);
		valueBuilder.append(SEPERATOR);
		valueBuilder.append(A1_17);
		if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// row 6
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String header = ROW_6_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_6_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 7
		CompanyInfor companyInfo = dataSource.getCompanyInfo();
		for (int c = 0; c < ROW_6_HEADERS.size(); c++) {
			String value = "";
			if (c == 0 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getCityCode().map(i -> i.v()).orElse("");
			}
			if (c == 1 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeCode().map(i -> i.v()).orElse("");
			}
			if (c == 2 && laborInsuranceOffice != null) {
				// dummy data
				value = "00001";
			}
			if (c == 3) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value && !companyInfo.getPostCd().isEmpty())
					value = companyInfo.getPostCd().replace("-", "").substring(0, 3);
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null)
					value = laborInsuranceOffice.getBasicInformation().getStreetAddress().getPostalCode().map(i -> i.v().replace("-", "").substring(0, 3)).orElse("");
			}
			if (c == 4) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value && !companyInfo.getPostCd().isEmpty())
					value = companyInfo.getPostCd().replace("-", "").substring(3);
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null)
					value = laborInsuranceOffice.getBasicInformation().getStreetAddress().getPostalCode().map(i -> i.v().replace("-", "").substring(3)).orElse("");
			}
			if (c == 5) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getAdd_1() + companyInfo.getAdd_2();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null)
					value = laborInsuranceOffice.getBasicInformation().getStreetAddress().getAddress1().map(i -> i.v()).orElse("")
							+ laborInsuranceOffice.getBasicInformation().getStreetAddress().getAddress2().map(i -> i.v()).orElse("");
			}
			if (c == 6) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getCompanyName();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null)
					value = laborInsuranceOffice.getLaborOfficeName().v();
			}
			if (c == 7) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getRepname();
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null)
					value = laborInsuranceOffice.getBasicInformation().getRepresentativeName().map(i -> i.v()).orElse("");
			}
			if (c == 8) {
				if (officeCls == OfficeCls.OUTPUT_COMPANY.value)
					value = companyInfo.getPhoneNum().substring(0, Math.min(companyInfo.getPhoneNum().length(), 12));
				if (officeCls == OfficeCls.OUPUT_LABOR_OFFICE.value && laborInsuranceOffice != null) {
					String phoneNumber = laborInsuranceOffice.getBasicInformation().getStreetAddress().getPhoneNumber().map(i -> i.v()).orElse("");
					value = phoneNumber.substring(0, Math.min(phoneNumber.length(), 12));
				}
			}
			if (c == 9 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber1().map(i -> i.v()).orElse("");
			}
			if (c == 10 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber2().map(i -> i.v()).orElse("");
			}
			if (c == 11 && laborInsuranceOffice != null) {
				value = laborInsuranceOffice.getEmploymentInsuranceInfomation().getOfficeNumber3().map(i -> i.v()).orElse("");
			}
			valueBuilder.append(value);
			if (c < ROW_6_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// row 8
		valueBuilder.append(A1_42);
		if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
			valueBuilder.append(LINE_BREAK);
		}

		// main data
		this.fillDataRows(valueBuilder, dataSource);
	}

	private void fillDataRows(StringBuilder valueBuilder, EmpInsLossInfoExportData dataSource) {
		int lineFeedCode = dataSource.getEmpInsReportTxtSetting().getLineFeedCode();
		Map<String, String> reasonMap = reasonRepo.getRetirementReasonClsInfoById(AppContexts.user().companyId()).stream().collect(Collectors.toMap(i -> i.getRetirementReasonClsCode().v(), i -> i.getRetirementReasonClsName().v()));
		
		// row 9
		for (int c = 0; c < ROW_9_HEADERS.size(); c++) {
			String header = ROW_9_HEADERS.get(c);
			valueBuilder.append(header);
			if (c < ROW_9_HEADERS.size() - 1) {
				valueBuilder.append(SEPERATOR);
			} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
				valueBuilder.append(LINE_BREAK);
			}
		}

		// data
		JapaneseEras eras = this.erasAdapter.getAllEras();
		List<EmpInsLossInfoExportRow> rows = dataSource.getRowsData();
		EmpInsRptSettingCommand empInsRptSetting = dataSource.getEmpInsReportSetting();
		EmpInsRptTxtSettingCommand empInsRptTxtSetting = dataSource.getEmpInsReportTxtSetting();
		for (EmpInsLossInfoExportRow row : rows) {
			String employeeInsuranceNumber = row.getInsuranceNumber();
			JapaneseDate empInsHistStart = this.toJapaneseDate(row.getEmployeeInsurancePeriodStart(), eras);
			JapaneseDate empInsHistEnd = this.toJapaneseDate(row.getEmployeeInsurancePeriodEnd(), eras);
			JapaneseDate birthDay = this.toJapaneseDate(row.getPersonBirthDay(), eras);
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
					else if (employeeInsuranceNumber.length() > 4)
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
					value = (empInsHistStart.year() + 1) < 10 ? "0" + (empInsHistStart.year() + 1) : (empInsHistStart.year() + 1) + "";
				}
				if (c == 11 && empInsHistStart != null) {
					value = empInsHistStart.month() < 10 ? "0" + empInsHistStart.month() : empInsHistStart.month() + "";
				}
				if (c == 12 && empInsHistStart != null) {
					value = empInsHistStart.day() < 10 ? "0" + empInsHistStart.day() : empInsHistStart.day() + "";
				}
				if (c == 13 && empInsHistEnd != null) {
					value = empInsHistEnd.era();
				}
				if (c == 14 && empInsHistEnd != null) {
					value = (empInsHistEnd.year() + 1) < 10 ? "0" + (empInsHistEnd.year() + 1) : (empInsHistEnd.year() + 1) + "";
				}
				if (c == 15 && empInsHistEnd != null) {
					value = empInsHistEnd.month() < 10 ? "0" + empInsHistEnd.month() : empInsHistEnd.month() + "";
				}
				if (c == 16 && empInsHistEnd != null) {
					value = empInsHistEnd.day() < 10 ? "0" + empInsHistEnd.day() : empInsHistEnd.day() + "";
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
							? KatakanaConverter.fullKatakanaToHalf(KatakanaConverter.hiraganaToKatakana(row.getPersonNameKana()))
							: KatakanaConverter.fullKatakanaToHalf(KatakanaConverter.hiraganaToKatakana(row.getPersonReportNameKana()));
				}
				if (c == 24) {
					value = empInsRptSetting.getSubmitNameAtr() == EmpSubNameClass.PERSONAL_NAME.value
							? row.getPersonName() : row.getPersonReportName();
				}
				if (c == 25 && row.getPersonGender() != null) {
					value = row.getPersonGender() + "";
				}
				if (c == 26 && birthDay != null) {
					value = birthDay.era();
				}
				if (c == 27 && birthDay != null) {
					value = (birthDay.year() + 1) < 10 ? "0" + (birthDay.year() + 1) : (birthDay.year() + 1) + "";
				}
				if (c == 28 && birthDay != null) {
					value = birthDay.month() < 10 ? "0" + birthDay.month() : birthDay.month() + "";
				}
				if (c == 29 && birthDay != null) {
					value = birthDay.day() < 10 ? "0" + birthDay.day() : birthDay.day() + "";
				}
				if (c == 30) {
					value = row.getPersonCurrentAddress();
				}
				if (c == 31) {
					if (empInsRptTxtSetting.getOfficeAtr() == OfficeCls.OUTPUT_COMPANY.value)
						value = row.getCompanyName();
					if (empInsRptTxtSetting.getOfficeAtr() == OfficeCls.OUPUT_LABOR_OFFICE.value)
						value = row.getLaborInsuranceOfficeName();
				}
				if (c == 36) {
					value = reasonMap.containsKey(row.getCauseOfLossInsurance()) ? reasonMap.get(row.getCauseOfLossInsurance()) : "";
				}
				if (c == 37 && row.getScheduleWorkingHourPerWeek() != null) {
					if (row.getScheduleWorkingHourPerWeek().hour() > 99) {
						value = "99";
					} else {
						int hour = row.getScheduleWorkingHourPerWeek().hour();
						value = hour < 10 ? "0" + hour : "" + hour;
					}
				}
				if (c == 38 && row.getScheduleWorkingHourPerWeek() != null) {
					if (row.getScheduleWorkingHourPerWeek().hour() > 99) {
						value = "59";
					} else {
						int minute = row.getScheduleWorkingHourPerWeek().minute();
						value = minute < 10 ? "0" + minute : "" + minute;
					}
				}
				if (c == 39) {
					value = row.getPublicEmploymentSecurityOfficeName();
				}
				if (c == 40) {
					value = row.getPersonNameRomanji();
				}
				if (c == 41) {
					value = row.getNationalityName();
				}
				if (c == 42) {
					value = row.getNationalityCode();
				}
				if (c == 43) {
					value = row.getStatusOfResidenceCode();
				}
                if (c == 44) {
                    value = row.getStatusOfResidenceName();
                }
				if (c == 45 && row.getPeriodOfStayEnd() != null) {
					value = row.getPeriodOfStayEnd().year() + "";
				}
				if (c == 46 && row.getPeriodOfStayEnd() != null) {
					value = row.getPeriodOfStayEnd().month() < 10 ? "0" + row.getPeriodOfStayEnd().month() : row.getPeriodOfStayEnd().month() + "";
				}
				if (c == 47 && row.getPeriodOfStayEnd() != null) {
					value = row.getPeriodOfStayEnd().day() < 10 ? "0" + row.getPeriodOfStayEnd().day() : row.getPeriodOfStayEnd().day() + "";
				}
				if (c == 48 && row.getUnqualifiedActivityPermission() != null) {
					value = "00" + row.getUnqualifiedActivityPermission();
				}
				if (c == 49) {
					value = "00" + row.getContractWorkAtr();
				}
				valueBuilder.append(value);
				if (c < ROW_9_HEADERS.size() - 1) {
					valueBuilder.append(SEPERATOR);
				} else if (lineFeedCode != LineFeedCodeAtr.NO_ADD.value) {
					valueBuilder.append(LINE_BREAK);
				}
			}
		}
	}

	private String formatPhoneNumber(String number) {
		if (number.matches("(\\+*\\d*\\(\\d*\\)\\d*)")) {
			return number;
		}
		String numberPhone = "";
		String[] numberSplit = number.split("-");
		String[] temp = new String[3];

		if (numberSplit.length == 2) {
			if (numberSplit[1].length() <= 3) {
				temp[0] = numberSplit[1];
				numberPhone = numberSplit[0] + "（ " + temp[0] + " ）";
			} else {
				temp[0] = numberSplit[1].substring(0, 3);
				temp[1] = numberSplit[1].substring(3);
				numberPhone = numberSplit[0] + "（ " + temp[0] + " ）" + temp[1];
			}
		} else if (numberSplit.length >= 3) {
			numberPhone = numberSplit[0] + "（ " + numberSplit[1] + " ）" + numberSplit[2];
		} else if (numberSplit.length == 1) {
			if (number.length() <= 3) {
				temp[0] = number;
				numberPhone = temp[0];
			} else if (number.length() <= 6) {
				temp[0] = number.substring(0, 3);
				temp[1] = number.substring(3);
				numberPhone = temp[0] + "（ " + temp[1] + " ）";
			} else {
				temp[0] = number.substring(0, 3);
				temp[1] = number.substring(3, 6);
				temp[2] = number.substring(6);
				numberPhone = temp[0] + "（ " + temp[1] + " ）" + temp[2];
			}
		}
		return numberPhone;
	}
}
