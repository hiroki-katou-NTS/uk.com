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
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Stateless
public class NotificationOfLossInsCSVAposeFileGenerator extends AsposeCellsReportGenerator implements NotificationOfLossInsCSVFileGenerator {

    private static final String REPORT_ID = "CSV_GENERATOR";

    @Inject
    private JapaneseErasAdapter adapter;

    private static final String SHOWA = "昭和";

    private static final String HEISEI = "平成";



    @Override
    public void generate(FileGeneratorContext generatorContext, LossNotificationInformation data) {
        CompanyInfor company = data.getCompany();
        val reportContext = this.createEmptyContext(REPORT_ID);
        val workbook = reportContext.getWorkbook();
        val sheet = workbook.getWorksheets().get(0);
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.PEN_OFFICE) {
            fillPensionOffice(data.getHealthInsLoss(), sheet,data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.HEAL_INSUR_ASSO) {
            fillHealthInsAssociationOffice(data.getHealthInsLoss(), sheet,data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        if(data.getSocialInsurNotiCreateSet().getOutputFormat().get() == OutputFormatClass.THE_WELF_PEN) {
            fillEmpPensionFundOffice(data.getHealthInsAssociationData(), sheet, data.getInfor(), company, data.getSocialInsurNotiCreateSet(), data.getBaseDate());
        }
        String fileName = getFileName(data.getSocialInsurNotiCreateSet().getOutputFormat().get() ) + ".csv";
        reportContext.processDesigner();
        this.saveAsCSV(this.createNewFile(generatorContext,fileName), workbook);
    }

    private void saveAsCSV(OutputStream outputStream, Workbook workbook) {
        try {
            TxtSaveOptions opts = new TxtSaveOptions(SaveFormat.CSV);
            opts.setSeparator(',');
            opts.setQuoteType(TxtValueQuoteType.NEVER);
            opts.setEncoding(Encoding.getUTF8());
            workbook.save(outputStream, opts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String getFileName(OutputFormatClass output){
        if( output == OutputFormatClass.PEN_OFFICE) {
            return "SHFD0006";
        }
        if( output == OutputFormatClass.HEAL_INSUR_ASSO) {
            return "KPFD0006";
        }
        return "KNFD0006";
    }

    private void fillPensionOffice(List<InsLossDataExport> healthInsLoss, Worksheet worksheet,
                                   List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        int columnStart = 18;
        for(int i = 0; i < healthInsLoss.size(); i++){
            InsLossDataExport data = healthInsLoss.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(getPreferCode(data.getPrefectureNo(), ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?  data.getEndDate() : data.getEndDate2(), infor));
                cells.get(startRow, 1).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber1(), 2) : checkLength(data.getWelfOfficeNumber1(),2));
                cells.get(startRow, 2).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                        checkLength(data.getOfficeNumber2(),4) : checkLength(data.getWelfOfficeNumber2(),4));
                cells.get(startRow, 3).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 4).setValue(baseDate.toString("yyyyMMdd"));
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 5).setValue("22223" + ",[kanri],");
                    cells.get(startRow, 6).setValue("001," + getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
                }
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 5).setValue("22223" + "\r\n[kanri]\r\n");
                    cells.get(startRow, 6).setValue("001\r\n" + getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
                }

                cells.get(startRow, 7).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber1(),2) :
                        checkLength(data.getWelfOfficeNumber1(),2));
                cells.get(startRow, 8).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber2(),4):
                        checkLength(data.getWelfOfficeNumber2(),4));
                cells.get(startRow, 9).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber(),5) : checkLength(data.getWelfOfficeNumber(),5));
                cells.get(startRow, 10).setValue(checkLength(company.getPostCd(),3));
                cells.get(startRow, 11).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 12).setValue(checkLength(company.getAdd_1() + company.getAdd_2(),75));
                cells.get(startRow, 13).setValue(checkLength(company.getCompanyName(), 50));
                cells.get(startRow, 14).setValue(company.getRepname());
                cells.get(startRow, 15).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 16).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 17).setValue(formatPhoneNumber(company.getPhoneNum(),2) + ",[data]");
                }
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 17).setValue(formatPhoneNumber(company.getPhoneNum(),2) + "\r\n[data]");
                }

            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                startRow++;
                columnStart = 0;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 18;
            }
            fillPensionEmployee( data, cells, infor, ins, startRow, columnStart);
        }
    }

    private JapaneseDate toJapaneseDate (GeneralDate date) {
        Optional<JapaneseEraName> era = this.adapter.getAllEras().eraOf(date);
        return new JapaneseDate(date, era.get());
    }

    private boolean calculateAge(String birthDay){
        GeneralDate tempBrithday = GeneralDate.fromString(birthDay.substring(0,10), "yyyy-MM-dd");
        tempBrithday = tempBrithday.addYears(70);
        GeneralDate now = GeneralDate.today();
        return now.afterOrEquals(tempBrithday);
    }


    private void fillPensionEmployee(InsLossDataExport data, Cells cells,
                                     List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int columnStart){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate endJp = data.getEndDate().length() >= 10 ? toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd")) : null;
        JapaneseDate endJp2 = data.getEndDate2().length() >= 10 ? toJapaneseDate( GeneralDate.fromString(data.getEndDate2().substring(0,10), "yyyy-MM-dd")) : null;
        cells.get(startRow, columnStart).setValue("2201700," + getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 1 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                checkLength(data.getOfficeNumber1(),2) : checkLength(data.getWelfOfficeNumber1(),2));
        cells.get(startRow, 2 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ?
                checkLength(data.getOfficeNumber2(),4) : checkLength(data.getWelfOfficeNumber2(),4));
        cells.get(startRow, 3 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? checkLength(data.getOfficeNumber(),5) : checkLength(data.getWelfOfficeNumber(),5));
        cells.get(startRow, 4 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getHealInsNumber() : data.getWelfPenNumber());
        cells.get(startRow, 5 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonNameKana(),25) : checkLength(data.getOldNameKana(),25));
        cells.get(startRow, 6 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),12) : checkLength(data.getOldName(),12));
        cells.get(startRow, 7 + columnStart).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 8 + columnStart).setValue(convertJpDate(dateJp));
        cells.get(startRow, 9 + columnStart).setValue(ins.getPrintPersonNumber() != PersonalNumClass.DO_NOT_OUTPUT && ins.getPrintPersonNumber() != PersonalNumClass.OUTPUT_PER_NUMBER ? getBasicPenNumber(data.getBasicPenNumber()) : "");
        cells.get(startRow, 9 + columnStart).setValue(cells.get(startRow, 9 + columnStart).getStringValue() + ",9");
        cells.get(startRow, 9 + columnStart).setValue(cells.get(startRow, 9 + columnStart).getStringValue() + "," + (endJp2 != null ? convertJpDate(endJp2) : (endJp != null ? convertJpDate(endJp) : "")) );
        cells.get(startRow, 10 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getCause() == null ? "" : data.getCause() : data.getCause2() == null ? "": data.getCause2());
        cells.get(startRow, 10 + columnStart).setValue(cells.get(startRow, 10 + columnStart).getStringValue() + ",9");
        cells.get(startRow, 11 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getCause() == null ? ""  : data.getCause() == 4 || data.getCause() == 5 ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate()),"yyyyMMdd" )))
                : ""  : data.getCause2() == null ? "" : data.getCause2() == 4 || data.getCause2() == 5 ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate2()),"yyyyMMdd" ))) :  "");
        cells.get(startRow, 11 + columnStart).setValue(cells.get(startRow, 11 + columnStart).getStringValue() + "," + data.getIsMoreEmp());
        cells.get(startRow, 11 + columnStart).setValue(cells.get(startRow, 11 + columnStart).getStringValue() + "," + data.getContinReemAfterRetirement());
        cells.get(startRow, 12 + columnStart).setValue(ins.getBusinessArrSymbol() == BussEsimateClass.HEAL_INSUR_OFF_ARR_SYMBOL ? data.getOtherReason() : data.getOtherReason2());
        cells.get(startRow, 13 + columnStart).setValue(Objects.toString(endJp2 != null ? data.getCaInsurance2() : data.getCaInsurance(), ""));
        cells.get(startRow, 14 + columnStart).setValue(Objects.toString(endJp2 != null ? data.getNumRecoved2() : data.getNumRecoved(), ""));
        cells.get(startRow, 15 + columnStart).setValue(calculateAge(data.getBirthDay()) ? "1,9" : ",");
        cells.get(startRow, 16 + columnStart).setValue(calculateAge(data.getBirthDay()) ? (endJp2 != null ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate2()),"yyyyMMdd" ))) : (endJp != null ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate()),"yyyyMMdd" ))) : "")) : "");
        cells.get(startRow, 17 + columnStart).setValue(data.getCause2() == null ? "" : data.getCause2() == 6 ? "" : 1);
    }

    private String getBasicPenNumber(String number){
        if(number.isEmpty()) {
            return ",,";
        }
        if(number.length() >= 10) {
            return number + "," + number.substring(0,4) + "," + number.substring(4,10);
        }
        if(number.length() > 4) {
            return number + "," + number.substring(0,4) + "," + number.substring(4,number.length());
        }
        return number + "," + number.substring(0,number.length()) + ",";
    }


    private String convertDate(String date){
        if(date.length() < 10)  {
            return "";
        }
        GeneralDate temp = GeneralDate.fromString(date.substring(0,10), "yyyy-MM-dd");
        return temp.addDays(-1).toString("yyyyMMdd");
    }

    private String convertJpDate(JapaneseDate date){
        int d = date.day();
        int y = date.year() + 1;
        int m = date.month();
        StringBuilder result = new StringBuilder();
        result.append(y > 9 ? y: "0" + y);
        result.append(m > 9 ? m : "0" + m);
        result.append(d > 9 ? d: "0" + d);
        return result.toString();
    }

    private String getPreferCode(int prefectureNo, String endDate, List<SocialInsurancePrefectureInformation> infor){
        if(endDate.isEmpty()) {
            return "";
        }
        Optional<SocialInsurancePrefectureInformation> refecture =  infor.stream().filter(item -> item.getNo() == prefectureNo
                && item.getEndYearMonth() >= convertDateToYearMonth(endDate)
                && item.getStartYearMonth() <= convertDateToYearMonth(endDate)).findFirst();
        return refecture.isPresent() ? refecture.get().getPrefectureCode().v() : "";
    }

    private int convertDateToYearMonth(String date){
        return Integer.parseInt(date.substring(0,4) + date.substring(5,7));
    }

    private void fillHealthInsAssociationOffice(List<InsLossDataExport> healthInsAssociation, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor, CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        int columnStart = 16;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            InsLossDataExport data = healthInsAssociation.get(i);
            if(i == 0) {
                cells.get(startRow, 0).setValue(data.getUnionOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 3).setValue("");
                cells.get(startRow, 4).setValue(checkLength(data.getHealInsInherenPr(),10));
                cells.get(startRow, 5).setValue("");
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 6).setValue(",\r\n" + "[kanri]\r\n");
                    cells.get(startRow, 7).setValue("001\r\n" + data.getUnionOfficeNumber());
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 6).setValue("," + ",[kanri],");
                    cells.get(startRow, 7).setValue("001," + data.getUnionOfficeNumber());
                }

                cells.get(startRow, 8).setValue(checkLength(company.getPostCd(), 3));
                cells.get(startRow, 9).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 10).setValue(checkLength(company.getAdd_1() + company.getAdd_2(),75));
                cells.get(startRow, 11).setValue(checkLength(company.getCompanyName(), 50));
                cells.get(startRow, 12).setValue(company.getRepname());
                cells.get(startRow, 13).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 14).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 15).setValue(formatPhoneNumber(company.getPhoneNum(),2) + "\r\n" + "[data]");
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 15).setValue(formatPhoneNumber(company.getPhoneNum(),2) + ",[data]");
                }

            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                startRow++;
                columnStart = 0;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 16;
            }
            fillHealthInsAssociation( data, cells, infor, ins, startRow, columnStart);

        }
    }

    private void fillHealthInsAssociation(InsLossDataExport data, Cells cells,
                                          List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int columnStart){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate enDateJp = toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, columnStart).setValue("2201700");
        cells.get(startRow, 1 + columnStart).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2 + columnStart).setValue(checkLength(data.getOfficeNumber1(),2));
        cells.get(startRow, 3 + columnStart).setValue(checkLength(data.getOfficeNumber2(),4));
        cells.get(startRow, 4 + columnStart).setValue(data.getOfficeNumber() + "," + data.getHealInsNumber());
        cells.get(startRow, 5 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonNameKana(),25) : checkLength(data.getOldNameKana(),25));
        cells.get(startRow, 6 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),12) : checkLength(data.getOldName(),12));
        cells.get(startRow, 7 + columnStart).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 8 + columnStart).setValue(convertJpDate(dateJp));
        cells.get(startRow, 9 + columnStart).setValue("");
        cells.get(startRow, 10 + columnStart).setValue("");
        cells.get(startRow, 11 + columnStart).setValue(",9");
        cells.get(startRow, 12 + columnStart).setValue(convertJpDate(enDateJp));
        cells.get(startRow, 13 + columnStart).setValue(Objects.toString(data.getCause(), ""));
        cells.get(startRow, 13 + columnStart).setValue(cells.get(startRow, 13 + columnStart).getValue() + ",9");
        cells.get(startRow, 14 + columnStart).setValue(data.getCause()!= null && (data.getCause() == 4 || data.getCause() == 5) ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate()), "yyyyMMdd"))) : "");
        cells.get(startRow, 15 + columnStart).setValue(data.getIsMoreEmp() + "," + data.getContinReemAfterRetirement() + "," + data.getOtherReason() + "," + data.getCaInsurance() + "," + data.getNumRecoved() + ",,,,,"
                + data.getUnionOfficeNumber() + "," + data.getHealInsUnionNumber() + "," + data.getHealInsInherenPr());
    }

    private void fillEmpPensionFundOffice(List<PensFundSubmissData> healthInsAssociation, Worksheet worksheet,List<SocialInsurancePrefectureInformation> infor,
                                          CompanyInfor company, SocialInsurNotiCreateSet ins, GeneralDate baseDate){
        Cells cells = worksheet.getCells();
        int startRow = 0;
        int columnStart = 51;
        for(int i = 0; i < healthInsAssociation.size(); i++){
            PensFundSubmissData data = healthInsAssociation.get(i);
            if(i == 0 ) {
                cells.get(startRow, 0).setValue(data.getWelPenOfficeNumber());
                cells.get(startRow, 1).setValue(Objects.toString(ins.getFdNumber().orElse(null), "001"));
                cells.get(startRow, 2).setValue(baseDate.toString("yyyyMMdd"));
                cells.get(startRow, 13).setValue(data.getFunSpecific1());
                cells.get(startRow, 14).setValue(data.getFunSpecific2());
                cells.get(startRow, 15).setValue(data.getFunSpecific3());
                cells.get(startRow, 16).setValue(data.getFunSpecific4());
                cells.get(startRow, 17).setValue(data.getFunSpecific5());
                cells.get(startRow, 18).setValue(data.getFunSpecific6());
                cells.get(startRow, 19).setValue(data.getFunSpecific7());
                cells.get(startRow, 20).setValue(data.getFunSpecific8());
                cells.get(startRow, 21).setValue(data.getFunSpecific9());
                cells.get(startRow, 22).setValue(data.getFunSpecific10());
                cells.get(startRow, 23).setValue(",,,,,,,,,,,,");
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 40).setValue("\r\n[kanri]\r\n");
                    cells.get(startRow, 41).setValue("001\r\n" + data.getFunMember());
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 40).setValue(",[kanri],");
                    cells.get(startRow, 41).setValue("001," + data.getFunMember());
                }

                cells.get(startRow, 42).setValue(data.getWelPenOfficeNumber());
                cells.get(startRow, 43).setValue(checkLength(company.getPostCd(),3));
                cells.get(startRow, 44).setValue(company.getPostCd().length() == 8 ? company.getPostCd().substring(4, 8) : company.getPostCd().length() > 4 ? company.getPostCd().substring(4, company.getPostCd().length()) : "");
                cells.get(startRow, 45).setValue(checkLength(company.getAdd_1() + company.getAdd_2(), 75));
                cells.get(startRow, 46).setValue(checkLength(company.getCompanyName(),50));
                cells.get(startRow, 47).setValue(company.getRepname());
                cells.get(startRow, 48).setValue(formatPhoneNumber(company.getPhoneNum(),0));
                cells.get(startRow, 49).setValue(formatPhoneNumber(company.getPhoneNum(),1));
                if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 50).setValue(formatPhoneNumber(company.getPhoneNum(), 2) + "\r\n[data]");
                }
                if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD) {
                    cells.get(startRow, 50).setValue(formatPhoneNumber(company.getPhoneNum(), 2) + ",[data]");
                }
            }
            if(ins.getLineFeedCode().get() != LineFeedCode.DO_NOT_ADD) {
                startRow++;
                columnStart = 0;
            }
            if(ins.getLineFeedCode().get() == LineFeedCode.DO_NOT_ADD && i > 0) {
                columnStart = columnStart + 51;
            }
            fillEmpPensionFund(data, cells, infor , ins, startRow, columnStart);
        }

    }

    private String formatPhoneNumber(String phone, int stt) {
        String[] sub = phone.split("-");
        if (stt == 2 && sub.length >= 3) {
            return sub[2];
        }
        if (stt == 0 && sub.length > 1) {
            return sub[0];
        }
        if (stt == 1 && sub.length > 2) {
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

    private void fillEmpPensionFund(PensFundSubmissData data, Cells cells,
                       List<SocialInsurancePrefectureInformation> infor, SocialInsurNotiCreateSet ins, int startRow, int columnStart){
        JapaneseDate dateJp = toJapaneseDate( GeneralDate.fromString(data.getBirthDay().substring(0,10), "yyyy-MM-dd"));
        JapaneseDate endDateJp = toJapaneseDate( GeneralDate.fromString(data.getEndDate().substring(0,10), "yyyy-MM-dd"));
        cells.get(startRow, columnStart).setValue("2201700");
        cells.get(startRow, 1 + columnStart).setValue(getPreferCode(data.getPrefectureNo(), data.getEndDate(), infor));
        cells.get(startRow, 2 + columnStart).setValue(checkLength(data.getOfficeNumber1(),2));
        cells.get(startRow, 3 + columnStart).setValue(checkLength(data.getOfficeNumber2(),4));
        cells.get(startRow, 4 + columnStart).setValue(checkLength(data.getWelPenOfficeNumber(),5));
        cells.get(startRow, 5 + columnStart).setValue(data.getWelNumber());
        cells.get(startRow, 6 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonNameKana(),25) : checkLength(data.getOldNameKana(),25));
        cells.get(startRow, 7 + columnStart).setValue(ins.getSubmittedName() == SubNameClass.PERSONAL_NAME ? checkLength(data.getPersonName(),12) : checkLength(data.getOldName(),12));
        cells.get(startRow, 8 + columnStart).setValue(dateJp.era().equals(HEISEI) ? 7 : dateJp.era().equals(SHOWA) ? 5 : 9);
        cells.get(startRow, 9 + columnStart).setValue(convertJpDate(dateJp));
        cells.get(startRow, 11 + columnStart).setValue(data.getBasicPenNumber().length() >= 4 ? data.getBasicPenNumber().substring(0,4) : data.getBasicPenNumber());
        cells.get(startRow, 12 + columnStart).setValue(data.getBasicPenNumber().length() >= 10 ? data.getBasicPenNumber().substring(4,10) :
                data.getBasicPenNumber().length() > 4 ? data.getBasicPenNumber().substring(4,data.getBasicPenNumber().length()) : "");
        cells.get(startRow, 13 + columnStart).setValue(9);
        cells.get(startRow, 14 + columnStart).setValue(convertJpDate(endDateJp));
        cells.get(startRow, 15 + columnStart).setValue(data.getCause());
        cells.get(startRow, 16 + columnStart).setValue(9);
        cells.get(startRow, 17 + columnStart).setValue(data.getCause() == 4 || data.getCause() == 5 ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate()), "yyyyMMdd"))) : "");
        cells.get(startRow, 18 + columnStart).setValue(data.getIsMoreEmp());
        cells.get(startRow, 19 + columnStart).setValue(data.getContinReemAfterRetirement());
        cells.get(startRow, 20 + columnStart).setValue(data.getOtherReason());
        cells.get(startRow, 23 + columnStart).setValue(calculateAge(data.getBirthDay()) ? 1 : "");
        cells.get(startRow, 24 + columnStart).setValue(calculateAge(data.getBirthDay()) ? 9 : "");
        cells.get(startRow, 25 + columnStart).setValue(calculateAge(data.getBirthDay()) ? convertJpDate(toJapaneseDate(GeneralDate.fromString(convertDate(data.getEndDate()), "yyyyMMdd"))) : "");
        cells.get(startRow, 27 + columnStart).setValue(data.getFunMember());
        cells.get(startRow, 28 + columnStart).setValue(data.getWelPenOfficeNumber());
        cells.get(startRow, 29 + columnStart).setValue(data.getMemberNumber());
        cells.get(startRow, 30 + columnStart).setValue("1".equals(data.getLivingAbroad()) ? "999" : checkLength(data.getPortCd(),3));
        cells.get(startRow, 31 + columnStart).setValue("1".equals(data.getLivingAbroad()) ? "9999" : data.getPortCd().length() > 7 ? data.getPortCd().substring(4,8) : "");
        cells.get(startRow, 32 + columnStart).setValue(checkLength(data.getRetirementAddBefore(),75));
        cells.get(startRow, 33 + columnStart).setValue(checkLength(data.getRetirementAdd(),37));
        cells.get(startRow, 34 + columnStart).setValue(data.getReasonForLoss());
        cells.get(startRow, 35 + columnStart).setValue(data.getAddAppCtgSal());
        cells.get(startRow, 36 + columnStart).setValue(data.getReason());
        cells.get(startRow, 37 + columnStart).setValue(data.getAddSal().isEmpty() ? "" : Double.parseDouble(data.getAddSal().trim()) > 10000000 ? "9999999" : data.getAddSal());
        cells.get(startRow, 38 + columnStart).setValue(data.getStandSal().isEmpty() ? "" : Double.parseDouble(data.getStandSal().trim()) > 10000000 ? "9999" : data.getStandSal().length() > 4 ? data.getStandSal().substring(0,data.getStandSal().length() - 3) : data.getStandSal());
        cells.get(startRow, 39 + columnStart).setValue(data.getSecAddSalary().isEmpty() ? "" : Double.parseDouble(data.getSecAddSalary().trim()) > 10000000 ? "9999999" : data.getSecAddSalary());
        cells.get(startRow, 40 + columnStart).setValue(data.getSecStandSal().isEmpty() ? "" : Double.parseDouble(data.getSecStandSal().trim()) > 10000000 ? "9999" : data.getSecStandSal().length() > 4 ? data.getSecStandSal().substring(0,data.getSecStandSal().length() - 3) : data.getSecStandSal());
        cells.get(startRow, 41 + columnStart).setValue(data.getFunSpecific1());
        cells.get(startRow, 42 + columnStart).setValue(data.getFunSpecific2());
        cells.get(startRow, 43 + columnStart).setValue(data.getFunSpecific3());
        cells.get(startRow, 44 + columnStart).setValue(data.getFunSpecific4());
        cells.get(startRow, 45 + columnStart).setValue(data.getFunSpecific5());
        cells.get(startRow, 46 + columnStart).setValue(data.getFunSpecific6());
        cells.get(startRow, 47 + columnStart).setValue(data.getFunSpecific7());
        cells.get(startRow, 48 + columnStart).setValue(data.getFunSpecific8());
        cells.get(startRow, 49 + columnStart).setValue(data.getFunSpecific9());
        cells.get(startRow, 50 + columnStart).setValue(data.getFunSpecific10());
    }


    private String checkLength(String s, int digitsNumber){
        return (s != null && s.length() >= digitsNumber) ? s.substring(0,digitsNumber) : s;
    }

}


