package nts.uk.file.pr.infra.core.socinsurnoticreset;

import com.aspose.cells.*;
import lombok.val;
import nts.arc.layer.infra.file.export.FileGeneratorContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.adapter.company.CompanyInfor;
import nts.uk.ctx.pr.core.dom.socialinsurance.socialinsuranceoffice.SocialInsurancePrefectureInformation;
import nts.uk.ctx.pr.file.app.core.socialinsurnoticreset.*;
import nts.uk.ctx.pr.report.dom.printconfig.socinsurnoticreset.*;
import nts.uk.shr.com.time.japanese.JapaneseDate;
import nts.uk.shr.com.time.japanese.JapaneseEraName;
import nts.uk.shr.com.time.japanese.JapaneseErasAdapter;
import nts.uk.shr.infra.file.report.aspose.cells.AsposeCellsReportGenerator;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class GuaByTheInsurCSVAposeFileGenerator extends AsposeCellsReportGenerator implements GuaByTheInsurExportCSVGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";


    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";

    private static final String PEACE = "令和";

    private static final String TAISO = "明治";

    private static final String MEI = "大正";

    @Inject
    private JapaneseErasAdapter adapter;

    @Override
    public void generate(FileGeneratorContext generatorContext, ExportDataCsv exportData) {
        try (val reportContext = this.createEmptyContext(REPORT_ID)) {
            Workbook workbook = reportContext.getWorkbook();
            WorksheetCollection worksheets = workbook.getWorksheets();
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
                fillPensionOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
                fillHealthInsAssociationOffice(exportData.getPensionOfficeData(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            if (exportData.getIns().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
                fillEmpPensionFundOffice(exportData.getEmpPenFundSub(), worksheets.get(0), exportData.getInfor(), exportData.getCompany(), exportData.getIns(), exportData.getBaseDate(), convertYearMonthToInt(exportData.getStartDate()), convertYearMonthToInt(exportData.getEndDate()));
            }
            reportContext.getDesigner().setWorkbook(workbook);
            reportContext.processDesigner();
            this.saveAsCSV(this.createNewFile(generatorContext, getFileName(exportData.getIns().getOutputFormat().get()) + ".csv"),workbook);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(OutputFormatClass output) {
        if (output == OutputFormatClass.PEN_OFFICE) {
            return "SHFD0006";
        }
        if (output == OutputFormatClass.HEAL_INSUR_ASSO) {
            return "KPFD0006";
        }
        return "KNFD0006";
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
    public int convertYearMonthToInt(GeneralDate yearMonth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMM");
        String formattedString = yearMonth.localDate().format(formatter);
        return Integer.valueOf(formattedString);

    }

    private void fillPensionOffice(List<PensionOfficeDataExport> pensionOfficeData, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int columnStart = 18;
        int startRow = 0;
        for (int i = 0; i < pensionOfficeData.size(); i++) {
            PensionOfficeDataExport data = pensionOfficeData.get(i);
            if (i == 0) {
                cells.get(startRow, 0).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) : checkLength(data.getWelOfficeNumber1(), 2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) : checkLength(data.getWelOfficeNumber2(), 4));
                cells.get(startRow, 3).setValue(ins.getFdNumber().isPresent() ? ins.getFdNumber().get() : "001");
                cells.get(startRow, 4).setValue(baseDate.toString("yyyyMMdd"));
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 5).setValue("22223,[kanri],");
                    cells.get(startRow, 6).setValue("001,"+getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
                }
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 5).setValue("22223\r\n[kanri]\r\n");
                    cells.get(startRow, 6).setValue("001\r\n"+getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
                }
                cells.get(startRow, 7).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) :checkLength(data.getWelOfficeNumber1(), 2));
                cells.get(startRow, 8).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) :checkLength(data.getWelOfficeNumber2(), 4));
                cells.get(startRow, 9).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber(),5) : checkLength(data.getWelOfficeNumber(),5));
                String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                cells.get(startRow, 10).setValue(formatPortCd(portCd, 1));
                cells.get(startRow, 11).setValue(formatPortCd(portCd, 2));
                String add1 = ins.getOfficeInformation().value == 0 ? company.getAdd_1() + company.getAdd_2() : data.getAdd();
                cells.get(startRow, 12).setValue(checkLength(add1, 37));

                String companyName = ins.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                String repName = ins.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                cells.get(startRow, 13).setValue(companyName.length() > 25 ? companyName.substring(0, 25) : companyName);
                cells.get(startRow, 14).setValue(repName);
                String phoneNumber = ins.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();
                cells.get(startRow, 15).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 16).setValue(formatPhone(phoneNumber, 1));
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 17).setValue(formatPhone(phoneNumber, 2) + ",[data]");
                }
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 17).setValue(formatPhone(phoneNumber, 2) + "\r\n[data]");
                }

            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                startRow++;
                columnStart = 0;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 18 ;
            }
            fillPensionEmployee(data, cells, infor, ins, startRow, startDate, endDate, company, columnStart);

        }
    }

    private String formatPhone(String phone, int stt) {
        String[] sub = phone.split("-");
        if (stt == 2 && sub.length >= 3) {
            return sub[2];
        }
        if (stt == 0 && sub.length > 1) {
            return sub[0];
        }
        if (stt == 1 && sub.length >= 2) {
            return sub[1];
        }
        if (sub.length == 1 && stt == 0 && phone.length() >= 3) {
            return phone.substring(0, 3);
        }
        if (sub.length == 1 && stt == 2 && phone.length() > 6) {
            return phone.substring(6, phone.length());
        }
        if (sub.length == 1 && stt == 1 && phone.length() >= 6) {
            return phone.substring(3, 6);
        }
        if (sub.length == 1 && phone.length() < 3) {
            return phone;
        }
        return "";
    }

    private JapaneseDate toJapaneseDate(GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private String formatPortCd(String portCd, int stt) {
        String result = portCd.replace("-", "");
        if (result.length() >= 3 && stt == 1) {
            return result.substring(0, 3);
        }
        if (stt == 2 && result.length() > 3) {
            return result.substring(3, result.length());
        }
        return result;
    }

    private void fillPensionEmployee(PensionOfficeDataExport data, Cells cells,
                                     List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate,CompanyInfor company, int columnStart) {
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate healStart = toJapaneseDate(GeneralDate.fromString(data.getStartDate1().substring(0, 10), "yyyy-MM-dd"));
        cells.get(startRow, columnStart).setValue("2200700");
        cells.get(startRow, 1 + columnStart).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber1(), 2) :checkLength(data.getWelOfficeNumber1(), 2));
        cells.get(startRow, 3 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber2(), 4) : checkLength(data.getWelOfficeNumber2(), 4));
        cells.get(startRow, 4 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getHealOfficeNumber(),5) : checkLength(data.getWelOfficeNumber(),5));

        cells.get(startRow, 6 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonNameKana().length() > 25 ? data.getPersonNameKana().substring(0, 25) : data.getPersonNameKana() :
                data.getOldNameKana().length() > 25 ? data.getOldNameKana().substring(0, 25) : data.getOldNameKana());
        cells.get(startRow, 7 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName().length() > 12 ? data.getPersonName().substring(0, 12) : data.getPersonName() :
                data.getOldName().length() > 12 ? data.getOldName().substring(0, 12) : data.getOldName());
        cells.get(startRow, 8 + columnStart).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9 + columnStart).setValue(convertJpDate(dateJp));
        cells.get(startRow, 10 + columnStart).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? data.getGender() : data.getHealInsCtg());
        cells.get(startRow, 11 + columnStart).setValue(data.getDistin()+",");

        cells.get(startRow, 11 + columnStart).setValue(cells.get(startRow, 11 + columnStart).getValue()+","+(data.getLivingAbroad() == 0 ? "" : data.getLivingAbroad()).toString());

        cells.get(startRow, 12 + columnStart).setValue(data.getResonAndOtherContent()+","+",");

        if (!data.getStartDate2().equals("")) {
            JapaneseDate welStart = toJapaneseDate(GeneralDate.fromString(data.getStartDate2().substring(0, 10), "yyyy-MM-dd"));
            cells.get(startRow, 13+ columnStart).setValue(conpareDate(data.getStartDate1(), data.getStartDate2()) ? healStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9 : welStart.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
            cells.get(startRow, 13+ columnStart).setValue(cells.get(startRow, 13 + columnStart).getIntValue()+","+(conpareDate(data.getStartDate1(), data.getStartDate2()) ? convertJpDate(healStart) : convertJpDate(welStart)));

        }
//        //bổ sung DD
//        GeneralDate a = GeneralDate.fromString(data.getStartDate1().substring(0, 10), "yyyy-MM-dd");
//        a.addYears(1);
//        GeneralDate b = GeneralDate.fromString(data.getStartDate2().substring(0, 10), "yyyy-MM-dd");
//        b.addYears(1);
//        if (a.afterOrEquals(b)) {
//            cells.get(startRow, 17).setValue( findEra(toJapaneseDate(a).era()));
//            cells.get(startRow, 17).setValue(  cells.get(startRow, 17)+","+convertJpDate(toJapaneseDate(a)));
//
//        }
//        else{
//            cells.get(startRow, 17).setValue( findEra(toJapaneseDate(b).era()));
//            cells.get(startRow, 17).setValue(  cells.get(startRow, 17).getValue()+","+convertJpDate(toJapaneseDate(b)));
//        }


        cells.get(startRow, 14 + columnStart).setValue(data.getDepenAppoint());
        cells.get(startRow, 14 + columnStart).setValue(cells.get(startRow, 14 + columnStart).getValue()+","+(data.getRemunMonthlyAmount() >= 10000000 ? "9999999" : data.getRemunMonthlyAmount()).toString());
        cells.get(startRow, 14 + columnStart).setValue(cells.get(startRow, 14 + columnStart).getValue()+","+(data.getRemunMonthlyAmountKind() >= 10000000 ? "9999999" : data.getRemunMonthlyAmountKind()));
        int total = data.getRemunMonthlyAmount()+data.getRemunMonthlyAmountKind();
        cells.get(startRow, 14 + columnStart).setValue( cells.get(startRow, 14 + columnStart).getValue()+","+(total>= 10000000 ? "9999999" : total));
        cells.get(startRow, 15 + columnStart).setValue(data.getPercentOrMore() == 1 ? 1 : "");
        cells.get(startRow, 15 + columnStart).setValue( cells.get(startRow, 15 + columnStart).getValue()+","+(data.getIsMoreEmp() == 1 ? 1 : "").toString());
        cells.get(startRow, 15 + columnStart).setValue( cells.get(startRow, 15 + columnStart).getValue()+","+(data.getShortTimeWorkes() == 1 ? 1 : "").toString());
        cells.get(startRow, 15 + columnStart).setValue( cells.get(startRow, 15 + columnStart).getValue()+","+(data.getContinReemAfterRetirement() == 1 ? 1 : "").toString());
        cells.get(startRow, 16 + columnStart).setValue(checkLength(data.getRemarksAndOtherContent(), 37)+","+"郵便番号 3 - dummy data"+","+"郵便番号 4- dummy data"+","+"住所カナ - dummy dataa"+","+"住所 - dummy dataa");

        // bổ sung design
        String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd(): data.getPortCd();
        String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1() +" "+ company.getAdd_2(): data.getAdd();
        String addKana = ins.getOfficeInformation().value == 0 ? company.getAddKana_1() + company.getAddKana_2(): data.getAddKana();

        cells.get(startRow, 17 + columnStart).setValue(data.getPercentOrMore() == 1 ? 1 : "");

    }


    private boolean conpareDate(String start1, String start2) {
        return GeneralDate.fromString(start1.substring(0, 10), "yyyy-MM-dd").after(GeneralDate.fromString(start2.substring(0, 10), "yyyy-MM-dd"));
    }

    private void fillHealthInsAssociationOffice(List<PensionOfficeDataExport> healthInsAssociation,
                                                Worksheet worksheet, List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int columnStart = 13;
        int startRow = 0;
        for (int i = 0; i < healthInsAssociation.size(); i++) {
            PensionOfficeDataExport data = healthInsAssociation.get(i);
            if (i == 0) {
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 3).setValue((data.getHealInsInherenPr().length() > 4 ? data.getHealInsInherenPr().substring(0, 5) : data.getHealInsInherenPr())+ ",,,,\r\n[kanri]\r\n" );
                    cells.get(startRow, 4).setValue("001\r\n" + data.getUnionOfficeNumber());
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 3).setValue((data.getHealInsInherenPr().length() > 4 ? data.getHealInsInherenPr().substring(0, 5) : data.getHealInsInherenPr())+ ",,,,,[kanri]," );
                    cells.get(startRow, 4).setValue("001," + data.getUnionOfficeNumber());
                }
                String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                cells.get(startRow, 5).setValue(formatPortCd(portCd, 1));
                cells.get(startRow, 6).setValue(formatPortCd(portCd, 2));
                String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1() +" "+ company.getAdd_2() : data.getAdd();
                cells.get(startRow, 7).setValue(checkLength(add, 37));
                String companyName = ins.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                cells.get(startRow, 8).setValue(companyName.length() > 25 ? companyName.substring(0, 25) : companyName);
                String repName = ins.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                cells.get(startRow, 9).setValue(repName.length() > 7 ? repName.substring(0, 7) :repName);
                String phoneNumber = ins.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();
                cells.get(startRow, 10).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 11).setValue(formatPhone(phoneNumber, 1));
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 12).setValue(formatPhone(phoneNumber, 2) + "\r\n[data]");
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 12).setValue(formatPhone(phoneNumber, 2) + ",[data]");
                }
            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                startRow++;
                columnStart = 0;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 13;
            }
            fillHealthInsAssociation(data, cells, infor, ins, startRow, startDate, endDate, columnStart);

        }
    }

    private void fillHealthInsAssociation(PensionOfficeDataExport data, Cells cells,
                                          List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate, int columnStart) {
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate startDateJp = toJapaneseDate(GeneralDate.fromString(data.getStartDate1().substring(0, 10), "yyyy-MM-dd"));
        cells.get(startRow, columnStart).setValue("2200700");
        cells.get(startRow, 1 + columnStart).setValue(getPreferCode(data.getHealPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2 + columnStart).setValue(data.getHealOfficeNumber1().length() > 2 ? data.getHealOfficeNumber1().substring(0, 2) : data.getHealOfficeNumber1());
        cells.get(startRow, 2 + columnStart).setValue(cells.get(startRow, 2 + columnStart).getValue()+","+(data.getHealOfficeNumber2().length() > 4 ? data.getHealOfficeNumber2().substring(0, 4) : data.getHealOfficeNumber2()));
        cells.get(startRow, 2 + columnStart).setValue((cells.get(startRow, 2 + columnStart).getValue()+","+(data.getHealOfficeNumber())));
        if(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME){

            cells.get(startRow, 4 + columnStart).setValue(data.getPersonNameKana().length() > 12 ? data.getPersonNameKana().substring(0, 12) : data.getPersonNameKana());
            cells.get(startRow, 4 + columnStart).setValue(  cells.get(startRow, 4 + columnStart).getValue()+","+(data.getPersonName().length() > 25 ? data.getPersonName().substring(0, 25) : data.getPersonName()) );
        }
        else{

            cells.get(startRow, 4 + columnStart).setValue(data.getOldNameKana().length() > 12 ? data.getOldNameKana().substring(0, 12) : data.getOldNameKana());
            cells.get(startRow, 4 + columnStart).setValue( cells.get(startRow, 4 + columnStart).getValue()+","+(data.getOldName().length() > 25 ? data.getOldName().substring(0, 25) : data.getOldName()));

        }
        cells.get(startRow, 5 + columnStart).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 6 + columnStart).setValue(convertJpDate(dateJp));
        // gender
        //Male(1), Female(2)
        String hisId = data.getHisId();
        int gender = data.getGender();
        int undergoundDivision = data.getUnderDivision();
        if (hisId.equals("")) {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 7 + columnStart).setValue("1");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 7 + columnStart).setValue("2");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 7 + columnStart).setValue("3");

            }
        } else {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 7 + columnStart).setValue("5");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 7 + columnStart).setValue("6");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 7 + columnStart).setValue("7");

            }
        }
        //
        cells.get(startRow, 8 + columnStart).setValue(data.getDistin()+",");
        //
        cells.get(startRow, 9 + columnStart).setValue(data.getLivingAbroad() == 0 ? "" : data.getLivingAbroad());
        //
        cells.get(startRow, 10 + columnStart).setValue(data.getResonAndOtherContent()+","+",");
        cells.get(startRow, 11 + columnStart).setValue(startDateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 11 + columnStart).setValue( cells.get(startRow, 11 + columnStart).getIntValue()+","+convertJpDate(toJapaneseDate(GeneralDate.fromString(data.getStartDate1().substring(0,10), "yyyy-MM-dd"))));
        cells.get(startRow, 11 + columnStart).setValue( cells.get(startRow, 11 + columnStart).getValue()+","+data.getDepenAppoint());


        cells.get(startRow, 11 + columnStart).setValue( cells.get(startRow, 11 + columnStart).getValue()+","+(data.getRemunMonthlyAmount() >= 10000000 ? "9999999" : data.getRemunMonthlyAmount()));
        cells.get(startRow, 11 + columnStart).setValue( cells.get(startRow, 11 + columnStart).getValue()+","+(data.getRemunMonthlyAmountKind()>= 10000000 ? "9999999" : data.getRemunMonthlyAmountKind()));
        int total = data.getRemunMonthlyAmount()+data.getRemunMonthlyAmountKind();
        cells.get(startRow, 11 + columnStart).setValue( cells.get(startRow, 11 + columnStart).getValue()+","+(total>= 10000000 ? "9999999" : total));

        cells.get(startRow, 12 + columnStart).setValue(data.getPercentOrMore() == 1 ? 1 : "");
        cells.get(startRow, 12 + columnStart).setValue(  cells.get(startRow, 12 + columnStart).getValue()+","+(data.getIsMoreEmp() == 1 ? 1 : ""));
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+(data.getShortTimeWorkes() == 1 ? 1 : ""));
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+(data.getContinReemAfterRetirement() == 1 ? 1 : ""));
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+(checkLength(data.getRemarksAndOtherContent(), 37)));

        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+"郵便番号 3 = dummy data"+","+"郵便番号 4 = dummy data"+","+"住所カナ = dummy data"+","+"住所 = dummy data"+",");
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+(data.getUnionOfficeNumber()));
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+data.getHealUnionNumber());
        cells.get(startRow, 12 + columnStart).setValue(cells.get(startRow, 12 + columnStart).getValue()+","+data.getHealInsInherenPr());
    }

    private void fillEmpPensionFundOffice(List<EmpPenFundSubData> empPenFundSub, Worksheet worksheet, List<SocialInsurancePrefectureInformation> infor,
                                          CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate, int startDate, int endDate) {
        Cells cells = worksheet.getCells();
        int startRow = 0;
        int columnStart = 22;
        for (int i = 0; i < empPenFundSub.size(); i++) {
            EmpPenFundSubData data = empPenFundSub.get(i);
            if (i == 0) {
                cells.get(startRow, 0).setValue(data.getWelPenOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 3).setValue(data.getFunSpecific1());
                cells.get(startRow, 4).setValue(data.getFunSpecific2());
                cells.get(startRow, 5).setValue(data.getFunSpecific3());
                cells.get(startRow, 6).setValue(data.getFunSpecific4());
                cells.get(startRow, 7).setValue(data.getFunSpecific5());
                cells.get(startRow, 8).setValue(data.getFunSpecific6());
                cells.get(startRow, 9).setValue(data.getFunSpecific7());
                cells.get(startRow, 10).setValue(data.getFunSpecific8());
                cells.get(startRow, 11).setValue(data.getFunSpecific9());
                cells.get(startRow, 12).setValue(data.getFunSpecific10());
                String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 13).setValue(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,\r\n[kanri]\r\n");
                    cells.get(startRow, 14).setValue("001\r\n"+data.getFunMember()+","+data.getWelPenOfficeNumber()+","+formatPortCd(portCd, 1));
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 13).setValue(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,[kanri],");
                    cells.get(startRow, 14).setValue("001,"+data.getFunMember()+","+data.getWelPenOfficeNumber()+","+formatPortCd(portCd, 1));
                }

                cells.get(startRow, 15).setValue(formatPortCd(portCd, 2));

                String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1()+" "+company.getAdd_2() : data.getAdd();
                String addKana = ins.getOfficeInformation().value == 0 ? company.getAddKana_1()+" "+company.getAddKana_2() : data.getAddKana();
                String companyName = ins.getOfficeInformation().value == 0 ? company.getCompanyName() : data.getCompanyName();
                String repName = ins.getOfficeInformation().value == 0 ? company.getRepname() : data.getRepName();
                String phoneNumber = ins.getOfficeInformation().value == 0 ? company.getPhoneNum() : data.getPhoneNumber();

                cells.get(startRow, 16).setValue(checkLength(add, 25));
                cells.get(startRow, 17).setValue(companyName.length() > 25 ?companyName.substring(0, 25) : companyName);
                cells.get(startRow, 18).setValue(repName);
                cells.get(startRow, 19).setValue(formatPhone(phoneNumber, 0));
                cells.get(startRow, 20).setValue(formatPhone(phoneNumber, 1));
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 21).setValue(formatPhone(phoneNumber, 2) + "\r\n[data]");
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 21).setValue(formatPhone(phoneNumber, 2) + ",[data]");
                }

            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                columnStart = 0;
                startRow++;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 21 ;
            }
            fillEmpPensionFund(data, cells, infor, ins, startRow, startDate, endDate, company, columnStart);
        }

    }

    private void fillEmpPensionFund(EmpPenFundSubData data, Cells cells,
                                    List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int startDate, int endDate,CompanyInfor company, int columnStart) {
        JapaneseDate addAppCtgSal = !data.getBirthDay().isEmpty() ? toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd")) : null;
        JapaneseDate dateJp = toJapaneseDate(GeneralDate.fromString(data.getBirthDay().substring(0, 10), "yyyy-MM-dd"));
        JapaneseDate startDateKohoInfo = toJapaneseDate(GeneralDate.fromString(data.getStartDate().substring(0, 10), "yyyy-MM-dd"));
        cells.get(startRow, columnStart).setValue("2200700");
        cells.get(startRow, 1 + columnStart).setValue(getPreferCode(data.getPrefectureNo(), startDate, endDate, infor));
        cells.get(startRow, 2 + columnStart).setValue(data.getWelOfficeNumber1().length() > 2 ? data.getWelOfficeNumber1().substring(0, 2) : data.getWelOfficeNumber1());
        cells.get(startRow, 2 + columnStart).setValue( cells.get(startRow, 2 + columnStart).getValue()+","+(data.getWelOfficeNumber2().length() > 4 ? data.getWelOfficeNumber2().substring(0, 4) : data.getWelOfficeNumber2()));
        cells.get(startRow, 2 + columnStart).setValue(cells.get(startRow, 2+ columnStart).getValue()+","+data.getWelPenOfficeNumber()+",");
        cells.get(startRow, 3 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonNameKana() : data.getOldNameKana());
        cells.get(startRow, 3 + columnStart).setValue( cells.get(startRow, 3+ columnStart).getValue()+","+(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? data.getPersonName() : data.getOldName()));
        cells.get(startRow, 4 + columnStart).setValue(findEra(dateJp.era()));
        cells.get(startRow, 4 + columnStart).setValue( cells.get(startRow, 4+ columnStart).getValue()+","+convertJpDate(dateJp));
        //Male(1), Female(2)
        String hisId = data.getHisId();
        int undergoundDivision = data.getUnderDivision();
        int gender = data.getGender();
        if (hisId.equals("")) {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 5 + columnStart).setValue("1");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 5 + columnStart).setValue("2");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 5 + columnStart).setValue("3");

            }
        } else {
            if (gender == 1 && undergoundDivision == 0) {
                cells.get(startRow, 5 + columnStart).setValue("5");

            }
            if (gender == 2 && undergoundDivision == 0) {
                cells.get(startRow, 5 + columnStart).setValue("6");

            }
            if (undergoundDivision == 1) {
                cells.get(startRow, 5+ columnStart).setValue("7");

            }
        }

        cells.get(startRow, 6 + columnStart).setValue(data.getQualifiDistin()+","+","+","+","+","+","+findEra(startDateKohoInfo.era())+","+convertJpDate(startDateKohoInfo));



        cells.get(startRow, 7 + columnStart).setValue(data.getDepenAppoint());
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getIntValue()+","+(data.getRemunMonthlyAmount() >= 10000000 ? "9999999" : data.getRemunMonthlyAmount()));
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(data.getRemunMonthlyAmountKind()>= 10000000 ? "9999999" : data.getRemunMonthlyAmountKind()));
        int total = data.getRemunMonthlyAmount()+data.getRemunMonthlyAmountKind();
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(total>= 10000000 ? "9999999" : total));
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(data.getPercentOrMore() == 1 ? 1 : ""));
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(Integer.valueOf(data.getIsMoreEmp()) == 1 ? 1 : "").toString());
        cells.get(startRow, 7+ columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(data.getShortStay() == 1 ? 1 : ""));
        cells.get(startRow, 7 + columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+(Integer.valueOf(data.getContinReemAfterRetirement()) == 1 ? 1 : ""));
        cells.get(startRow, 7+ columnStart).setValue(cells.get(startRow, 7+ columnStart).getValue()+","+data.getRemarksAndOtherContents());
        String portCd = ins.getOfficeInformation().value == 0 ? company.getPostCd() : data.getPortCd();
        String add = ins.getOfficeInformation().value == 0 ? company.getAdd_1()+" "+company.getAdd_2() : data.getAdd();
        String addKana = ins.getOfficeInformation().value == 0 ? company.getAddKana_1()+" "+company.getAddKana_2() : data.getAddKana();
        cells.get(startRow, 8+ columnStart).setValue("郵便番号 3 -dummy data");
        cells.get(startRow, 9+ columnStart).setValue("郵便番号 4 -dummy data");
        cells.get(startRow, 10+ columnStart).setValue("住所カナ -dummy data");
        cells.get(startRow, 11+ columnStart).setValue("住所 -dummy data"+",");
//
        cells.get(startRow, 12+ columnStart).setValue(data.getFunMember());
        cells.get(startRow, 12+ columnStart).setValue(cells.get(startRow, 12+ columnStart).getValue()+","+data.getWelPenOfficeNumber());
        cells.get(startRow, 12+ columnStart).setValue(cells.get(startRow, 12+ columnStart).getValue()+","+data.getMemberNumber());
        cells.get(startRow, 12+ columnStart).setValue(cells.get(startRow, 12+ columnStart).getValue()+","+data.getSubType());
//        cells.get(startRow, 37).setValue(findEra(addAppCtgSal.era()));
//        cells.get(startRow, 38).setValue(convertJpDate(addAppCtgSal));
        cells.get(startRow, 15+ columnStart).setValue(data.getAddAppCtgSal());
        cells.get(startRow, 15+ columnStart).setValue( cells.get(startRow, 15+ columnStart).getValue()+","+findSubscriptionType(data.getAppFormCls()));
        cells.get(startRow, 16+ columnStart).setValue(data.getAddSal().isEmpty() ? "" : Double.parseDouble(data.getAddSal().trim()) > 10000000 ? "9999999" : data.getAddSal());
        cells.get(startRow, 16+ columnStart).setValue(cells.get(startRow, 16+ columnStart).getValue()+","+(data.getStandSal().isEmpty() ? "" : Double.parseDouble(data.getStandSal().trim()) > 10000000 ? "9999" : data.getStandSal().length() > 4 ? data.getStandSal().substring(0,4) : data.getStandSal()));
        cells.get(startRow, 16+ columnStart).setValue(cells.get(startRow, 16+ columnStart).getValue()+","+(data.getSecAddSalary().isEmpty() ? "" : Double.parseDouble(data.getSecAddSalary().trim()) > 10000000 ? "9999999" : data.getSecAddSalary()));
        cells.get(startRow, 16+ columnStart).setValue(cells.get(startRow, 16+ columnStart).getValue()+","+(data.getSecStandSal().isEmpty() ? "" : Double.parseDouble(data.getSecStandSal().trim()) > 10000000 ? "9999" : data.getSecStandSal().length() > 4 ? data.getSecStandSal().substring(0,4) : data.getSecStandSal()));
        cells.get(startRow, 17+ columnStart).setValue(checkLengtFunSpecific(data.getFunSpecific1()));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+(checkLengtFunSpecific(data.getFunSpecific2())));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+checkLengtFunSpecific(data.getFunSpecific3()));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+checkLengtFunSpecific(data.getFunSpecific4()));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+checkLengtFunSpecific(data.getFunSpecific5()));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+checkLengtFunSpecific(data.getFunSpecific6()));
        cells.get(startRow, 17+ columnStart).setValue(cells.get(startRow, 17+ columnStart).getValue()+","+checkLengtFunSpecific(data.getFunSpecific7()));
        cells.get(startRow, 18+ columnStart).setValue(checkLengtFunSpecific(data.getFunSpecific8()));
        cells.get(startRow, 19+ columnStart).setValue(checkLengtFunSpecific(data.getFunSpecific9()));
        cells.get(startRow, 20+ columnStart).setValue(checkLengtFunSpecific(data.getFunSpecific10()));
    }
    private String checkLengtFunSpecific(String funSpecific){
        if(funSpecific.length() > 4){
            return funSpecific.substring(0,5);
        }
        return funSpecific;
    }
    private String findEra(String era) {
        if (era.equals(TAISO)) {
            return "1";
        }
        if (era.equals(MEI)) {
            return "3";
        }
        if (era.equals(SHOWA)) {
            return "5";
        }
        if (era.equals(HEISEI)) {
            return "7";
        }
        if (era.equals(PEACE)) {
            return "9";
        }
        return "";
    }
    private String findSubscriptionType(int applFormClass) {
        //    NEW(1, "新規"),
        //    MOVING(2, "転入"),
        //    REVIVAL(3, "復活"),
        //    REJOIN(4, "再加入");
        switch (applFormClass){
            case 1 : {
                return "1";
            }
            case 2 : {
                return "2";
            }
            case 3 : {
                return "3";
            }
            case 4 : {
                return "4";
            }

        }
        return "";
    }

    private String convertJpDate(JapaneseDate date) {
        int m = date.month();
        int d = date.day();
        int y = date.year() + 1;
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y : "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d : "0" + d);
        return result.toString();
    }

    private String checkLength(String s, int digitsNumber) {
        return (s != null && s.length() > digitsNumber) ? s.substring(0, digitsNumber) : s;
    }

    private String getPreferCode(int prefectureNo, int startDate, int endDate, List<SocialInsurancePrefectureInformation> infor) {
        Optional<SocialInsurancePrefectureInformation> refecture = infor.stream()
                .filter( item -> item.getNo() == prefectureNo
                        && item.getStartYearMonth() <= startDate
                        && item.getEndYearMonth() >= startDate )
                .findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

}
