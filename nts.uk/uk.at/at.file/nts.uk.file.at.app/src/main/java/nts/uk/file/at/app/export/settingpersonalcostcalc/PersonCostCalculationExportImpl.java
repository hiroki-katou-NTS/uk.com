package nts.uk.file.at.app.export.settingpersonalcostcalc;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.schedule.app.find.budget.premium.PersonCostCalculationFinder;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PersonCostCalDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumItemDto;
import nts.uk.ctx.at.schedule.app.find.budget.premium.dto.PremiumSettingAndNameDto;
import nts.uk.ctx.at.schedule.dom.budget.premium.service.AttendanceNamePriniumDto;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Stateless
@DomainID(value = "PersonCostCalculation")
public class PersonCostCalculationExportImpl implements MasterListData {

    @Inject
    private PersonCostCalculationFinder personCostCalculationSettingFinder;

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        List<MasterData> datas = new ArrayList<>();
        String languageId = query.getLanguageId();
        Map<String, Object> data = new HashMap<>();
        // Get company id
        //String companyId = AppContexts.user().companyId();
        GeneralDate basedate = query.getBaseDate();
        List<PersonCostCalDto> listPersonCostCalculationSetting = personCostCalculationSettingFinder.findPersonCostCal().
                stream().filter(x -> x.getEndDate().date().getTime() >= basedate.date().getTime() &&
                basedate.date().getTime() >= x.getStartDate().date().getTime()).collect(Collectors.toList());

        if (!CollectionUtil.isEmpty(listPersonCostCalculationSetting)) {
            for (PersonCostCalDto personCostCalculationSettingDto : listPersonCostCalculationSetting) {
                //PersonCostCalculationSettingDto personCostCalculationSetting	= personCostCalculationSettingFinder.findByHistoryID(personCostCalculationSettingDto.getHistoryID());
                List<PremiumSettingAndNameDto> personCostCalculationSetting = personCostCalculationSettingDto.getPremiumSettingList();
                data = putEntryMasterDatas();
                data.put("有効開始日", personCostCalculationSettingDto.getStartDate() != null ? personCostCalculationSettingDto.getStartDate().toString() : "");
                data.put("終了日", personCostCalculationSettingDto.getEndDate() != null ? personCostCalculationSettingDto.getEndDate().toString() : "");

                data.put("計算設定", getTextResRatePrice(personCostCalculationSettingDto.getHowToSetUnitPrice())); //A-6-8

                data.put("計算用単価", !Objects.isNull(personCostCalculationSettingDto.getUnitPrice()) == true ?
                        getTextResource(personCostCalculationSettingDto.getUnitPrice()) : "");// A-6-3

                data.put("単価の丸め", !Objects.isNull(personCostCalculationSettingDto.getPersonCostRoundingSetting()) == true ?
                        getTextUnitPriceRounding(personCostCalculationSettingDto.getPersonCostRoundingSetting().getUnitPriceRounding()) : "");// A-6-9
                data.put("金額の丸め単位", !Objects.isNull(personCostCalculationSettingDto.getPersonCostRoundingSetting()) == true ?
                        getTextResUnit(personCostCalculationSettingDto.getPersonCostRoundingSetting().getUnit()) : "");//A-6-10
                data.put("金額の丸め", !Objects.isNull(personCostCalculationSettingDto.getPersonCostRoundingSetting()) == true ?
                        getTextRounding(personCostCalculationSettingDto.getPersonCostRoundingSetting().getRounding()) : "");// A-6-10

                data.put("備考", personCostCalculationSettingDto.getRemarks());


                boolean checkshow = true;
                if (personCostCalculationSetting != null) {
                    List<PremiumSettingAndNameDto> premiumSets = personCostCalculationSettingDto.getPremiumSettingList();
                    List<PremiumItemDto> listPremiumItemLanguage = personCostCalculationSettingFinder.findWorkTypeLanguage(languageId);
                    premiumSets.sort((PremiumSettingAndNameDto o1, PremiumSettingAndNameDto o2) -> o1.getID() - o2.getID());
                    if (!CollectionUtil.isEmpty(premiumSets)) {
                        for (PremiumSettingAndNameDto premiumSetDto : premiumSets) {
                            if (premiumSetDto.getUseAtr() == 1) {
                                String nameEnglish = "";
                                for (PremiumItemDto premiumItemDto : listPremiumItemLanguage) {
                                    if (premiumItemDto.getDisplayNumber() == premiumSetDto.getID()) {
                                        nameEnglish = premiumItemDto.getName();
                                        break;
                                    }
                                }

                                //data.put("他言語名称",nameEnglish);
                                data.put("名称", premiumSetDto.getName());
                                data.put("割増率", premiumSetDto.getRate() + TextResource.localize("KML001_60"));
                                data.put("単価", premiumSetDto.getUnitPrice());
                                //
                                List<AttendanceNamePriniumDto> rs = premiumSetDto.getAttendanceItems();
                                rs.sort((AttendanceNamePriniumDto o1, AttendanceNamePriniumDto o2) -> o1.getAttendanceItemDisplayNumber() - o2.getAttendanceItemDisplayNumber());
                                String codeName = "";
                                if (!CollectionUtil.isEmpty(rs)) {
                                    codeName = rs.get(0).getAttendanceItemDisplayNumber() + rs.get(0).getAttendanceItemName();
                                    for (int i = 1; i < rs.size(); i++) {
                                        AttendanceNamePriniumDto attdanceName = rs.get(i);
                                        codeName = codeName + "," + attdanceName.getAttendanceItemDisplayNumber() + attdanceName.getAttendanceItemName();
                                    }
                                }
                                data.put("人件費計算用時間", codeName);
                                //
                                if (!checkshow) {
                                    data.put("有効開始日", "");
                                    data.put("終了日", "");
                                    data.put("計算設定", "");
                                    data.put("計算用単価", "");
                                    data.put("単価の丸め", "");
                                    data.put("金額の丸め単位", "");
                                    data.put("金額の丸め", "");
                                    data.put("備考", "");
                                }
                                checkshow = false;
                                MasterData masterData = new MasterData(data, null, "");
                                Map<String, MasterCellData> rowData = masterData.getRowData();
                                rowData.get("有効開始日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
                                rowData.get("終了日").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
                                rowData.get("計算設定").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("計算用単価").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("単価の丸め").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("金額の丸め単位").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT));
                                rowData.get("金額の丸め").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("備考").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("単価").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                rowData.get("人件費計算用時間").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
                                datas.add(masterData);
                            }
                        }
                    }
                }


            }

        }
        return datas;
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {

        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn("有効開始日", TextResource.localize("KML001_9"), ColumnTextAlign.LEFT, "", true));// START DATE
        columns.add(new MasterHeaderColumn("終了日", TextResource.localize("KML001_57"), ColumnTextAlign.LEFT, "", true));// END DATE
        columns.add(new MasterHeaderColumn("計算設定", TextResource.localize("KML001_71"), ColumnTextAlign.LEFT, "", true));// RATE
        columns.add(new MasterHeaderColumn("計算用単価", TextResource.localize("KML001_75"), ColumnTextAlign.LEFT, "", true));// UNIT PRICE
        columns.add(new MasterHeaderColumn("単価の丸め", TextResource.localize("KML001_77"), ColumnTextAlign.LEFT, "", true));//Rounding the unit price
        columns.add(new MasterHeaderColumn("金額の丸め単位", TextResource.localize("KML001_79"), ColumnTextAlign.LEFT, "", true));//Amount rounding unit
        columns.add(new MasterHeaderColumn("金額の丸め", TextResource.localize("KML001_81"), ColumnTextAlign.LEFT, "", true));//Rounding amounts
        columns.add(new MasterHeaderColumn("備考", TextResource.localize("KML001_11"), ColumnTextAlign.LEFT, "", true));//Remarks
        columns.add(new MasterHeaderColumn("名称", TextResource.localize("KML001_12"), ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("割増率", TextResource.localize("KML001_13"), ColumnTextAlign.RIGHT, "", true));
        columns.add(new MasterHeaderColumn("単価", TextResource.localize("KML001_11"), ColumnTextAlign.LEFT, "", true));//Unit price
        columns.add(new MasterHeaderColumn("人件費計算用時間", TextResource.localize("KML001_14"), ColumnTextAlign.LEFT, "", true));


        return columns;


    }

    @Override
    public MasterListMode mainSheetMode() {
        return MasterListMode.BASE_DATE;
    }

    private Map<String, Object> putEntryMasterDatas() {
        Map<String, Object> data = new HashMap<>();
        data.put("有効開始日", "");
        data.put("終了日", "");
        data.put("計算設定", "");
        data.put("計算用単価", "");
        data.put("単価の丸め", "");
        data.put("金額の丸め単位", "");
        data.put("金額の丸め", "");
        data.put("備考", "");
        data.put("名称", "");
        data.put("割増率", "");
        data.put("単価", "");
        data.put("人件費計算用時間", "");

        return data;
    }

    // TextResource.localize("KMK007_67")
    private String getTextResource(int att) {
        String value = "";
        switch (att) {
            case 0:
                value = TextResource.localize("KML001_22");
                break;
            case 1:
                value = TextResource.localize("KML001_23");
                break;
            case 2:
                value = TextResource.localize("KML001_24");
                break;
            case 3:
                value = TextResource.localize("KML001_25");
                break;
            case 4:
                value = TextResource.localize("KML001_26");
                break;
            default:
                break;
        }
        return value;
    }

    private String getTextResUnit(int att) {
        String value = "";
        switch (att) {
            case 1:
                value = TextResource.localize("Enum_AmountUnit_oneYen");
                break;
            case 10:
                value = TextResource.localize("Enum_AmountUnit_tenYen");
                break;
            case 100:
                value = TextResource.localize("Enum_AmountUnit_oneHundredYen");
                break;
            case 1000:
                value = TextResource.localize("Enum_AmountUnit_oneThousandYen");
                break;
            default:
                break;
        }
        return value;
    }

    private String getTextResRatePrice(int att) {
        String value = "";
        switch (att) {
            case 0:
                value = TextResource.localize("SET_PREMIUM_RATE");
                break;
            case 1:
                value = TextResource.localize("SET_UNIT_PRICE");
                break;
            default:
                break;
        }
        return value;

    }

    private String getTextUnitPriceRounding(int att) {
        String value = "";
        switch (att) {
            case 0:
                value = TextResource.localize("Enum_UnitPriceRounding_roundUp");
                break;
            case 1:
                value = TextResource.localize("Enum_UnitPriceRounding_truncation");
                break;
            case 2:
                value = TextResource.localize("Enum_UnitPriceRounding_down4Up5");
                break;
            default:
                break;
        }
        return value;
    }

    private String getTextRounding(int att) {
        String value = "";
        switch (att) {
            case 0:
                value = TextResource.localize("Enum_Rounding_Down");
                break;
            case 1:
                value = TextResource.localize("Enum_Rounding_Up");
                break;
            case 2:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_1_UP_2");
                break;
            case 3:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_2_UP_3");
                break;
            case 4:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_3_UP_4");
                break;
            case 5:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_4_UP_5");
                break;
            case 6:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_5_UP_6");
                break;
            case 7:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_6_UP_7");
                break;
            case 8:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_7_UP_8");
                break;
            case 9:
                value = TextResource.localize("ENUM_ROUNDING_DOWN_8_UP_9");
                break;
            default:
                break;
        }
        return value;
    }


}
