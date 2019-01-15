package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.DaiPerformanceFun;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.FormatPerformance;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.MonPerformanceFun;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
/**
*
* @author HoiDD
*
*/
@Stateless
@DomainID(value = "OperationSetting")
public class OperationExcelRepoImpl implements MasterListData {

    @Inject
    OperationExcelRepo operationExcelRepo;
    
    @Inject
    RoleDailyExportExcelImpl roleDailyExportExcelImpl;

    @Inject
    RoleMonthlyExportExcelImpl roleMonthlyExportExcelImpl;


    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
        //sheet 共通_運用設定 (sheet 1)
        List<SheetData> sheetDatas = new ArrayList<>();
        // add the work place sheet
        //Sheet 2  共通_機能制限
        SheetData sheetWorkplaceData = new SheetData(getMasterDatasSheetRestriction(query),
                getHeaderColumnsSheetRestriction(query), null, null,TextResource.localize("KDW006_91") );//TextResource.localize("KDW006_91")
        //Sheet 3 共通_権限別機能制限
        SheetData sheetRole = new SheetData(getMasterDatasSheetRoled(query),
                getHeaderColumnsSheetRole(query), null, null,TextResource.localize("KDW006_105"));// TextResource.localize("KDW006_105")
        sheetDatas.add(sheetWorkplaceData);
        sheetDatas.add(sheetRole);
        sheetDatas.addAll(roleDailyExportExcelImpl.extraSheets(query));
        return sheetDatas;
    }

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {

        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn("項目", TextResource.localize("KDW006_88"), ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("comment", "", ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("値", TextResource.localize("KDW006_89"), ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
    	String companyId = AppContexts.user().companyId();
        Optional<DaiPerformanceFun> daiPerformanceFunOpt = operationExcelRepo.getDaiPerformanceFunById(companyId);
        Optional<MonPerformanceFun> monPerformanceFunOpt = operationExcelRepo.getMonPerformanceFunById(companyId);
        Optional<FormatPerformance> formatPerformance = operationExcelRepo.getFormatPerformanceById(companyId);
        
        List<MasterData> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        putDataEmptySetOperation(data);
        // put first row
        data.put("項目", TextResource.localize("KDW006_27"));
        data.put("comment", "");
        if (formatPerformance.isPresent()) {
                data.put("値", TextResource.localize(
                        "Enum_SettingUnitType_" + formatPerformance.get().getSettingUnitType().name()));
        }
        datas.add(alignMasterData(data));
        // next row
        data.put("項目", TextResource.localize("KDW006_28"));
        data.put("comment", TextResource.localize("KDW006_62"));
        if (daiPerformanceFunOpt.isPresent()) {
            if (daiPerformanceFunOpt.get().getComment() != null) {
                data.put("値", daiPerformanceFunOpt.get().getComment());
            }
        }
        datas.add(alignMasterData(data));
        // next row
        data.put("項目", "");
        data.put("comment", TextResource.localize("KDW006_63"));
        if (monPerformanceFunOpt.isPresent()) {
            if (monPerformanceFunOpt.get().getComment() != null) {
                data.put("値", monPerformanceFunOpt.get().getComment());
            }
        }

        datas.add(alignMasterData(data));

        return datas;
    }

    public MasterData alignMasterData(Map<String, Object> data) {
        MasterData masterData = new MasterData(data, null, "");
        masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("comment").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("値").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        return masterData;
    }

    @Override
    public String mainSheetName() {
        return TextResource.localize("KDW006_87");//共通_運用設定 //
    }

    private void putDataEmptySetOperation(Map<String, Object> data) {
        data.put("項目", "");
        data.put("comment", "");
        data.put("値", "");
    }

    /**
     *
     * @param Sheet 2
     * @return
     */
    public List<MasterHeaderColumn> getHeaderColumnsSheetRestriction(MasterListExportQuery query) {

        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn("項目", TextResource.localize("KDW006_88"), ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("option", "", ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("display", "", ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("値", TextResource.localize("KDW006_89"), ColumnTextAlign.LEFT, "", true));
        return columns;
    }

    public List<MasterData> getMasterDatasSheetRestriction(MasterListExportQuery query) {
        String useDailySelf = TextResource.localize("KDW006_36");
        String useDailyBoss = TextResource.localize("KDW006_37");
        String disp36Atr = TextResource.localize("KDW006_65");
        String flexDispAtr = TextResource.localize("KDW006_66");
        String manualFixAutoSetAtr = TextResource.localize("KDW006_68");
        String clearManuAtr = TextResource.localize("KDW006_70");
        String checkErrRefDisp = TextResource.localize("KDW006_84");
    	String companyId = AppContexts.user().companyId();
    	
    	Optional<DaiPerformanceFun> daiPerformanceFunOpt = operationExcelRepo.getDaiPerformanceFunById(companyId);
        Optional<MonPerformanceFun> monPerformanceFunOpt = operationExcelRepo.getMonPerformanceFunById(companyId);
        Optional<IdentityProcess> identityProcessOpt = operationExcelRepo.getIdentityProcessById(companyId);
        Optional<ApprovalProcess> approvalProcessOpt = operationExcelRepo.getApprovalProcessById(companyId);
        List<ApplicationCallExport> listApplicationCallExport = operationExcelRepo.findByCom(companyId);
        
        List<MasterData> datas = new ArrayList<>();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> dataChild = new HashMap<>();
        putDataEmptySetOperationRestriction(data);
        putDataEmptySetOperationRestriction(dataChild);
        // put dayli
        data.put("項目", TextResource.localize("KDW006_35"));
        List<String> listOption = new ArrayList<>();
        listOption.add(useDailySelf);
        listOption.add(useDailyBoss);
        listOption.add(disp36Atr);
        listOption.add(flexDispAtr);
        listOption.add(manualFixAutoSetAtr);
        listOption.add(clearManuAtr);
        listOption.add(checkErrRefDisp);

        data.put("option", listOption.get(0));
        if (identityProcessOpt.isPresent()) {
            // useDailySelfCk
            if (identityProcessOpt.get().getUseDailySelfCk() == 1) {
                data.put("値", "○");
                dataChild.put("値", TextResource.localize(
                        "Enum_YourselfConfirmError_" + identityProcessOpt.get().getYourselfConfirmError().name()));
                datas.add(alignMasterDataSheetRestriction(dataChild));
                putDataEmptySetOperationRestriction(dataChild);

            } else {
                data.put("値", "-");
            }
            datas.add(alignMasterDataSheetRestriction(data));
            putDataEmptySetOperationRestriction(data);
        }

        // next row
        for (int i = 1; i < listOption.size(); i++) {
            String name = listOption.get(i);
            data.put("option", listOption.get(i));
            // useDailyBossChk
            if (name.equals(useDailyBoss)) {
                if (approvalProcessOpt.isPresent()) {
                    if (approvalProcessOpt.get().getUseDailyBossChk() == 1) {
                        data.put("値", "○");
                        dataChild.put("値", TextResource.localize("Enum_YourselfConfirmError_"
                                + approvalProcessOpt.get().getSupervisorConfirmError().name()));
                        datas.add(alignMasterDataSheetRestriction(dataChild));
                        putDataEmptySetOperationRestriction(dataChild);
                    } else {
                        data.put("値", "-");
                    }

                }
                datas.add(alignMasterDataSheetRestriction(data));
            }
            if (daiPerformanceFunOpt.isPresent()) {
                // disp36Atr
                if (name.equals(disp36Atr)) {
                    if (daiPerformanceFunOpt.get().getDisp36Atr() == 1) {
                        data.put("値", "○");
                    } else {
                        data.put("値", "-");
                    }
                }
                // flexDispAtr
                if (name.equals(flexDispAtr)) {
                    if (daiPerformanceFunOpt.get().getFlexDispAtr() == 1) {
                        data.put("値", "○");
                    } else {
                        data.put("値", "-");
                    }
                }
                // manualFixAutoSetAtr
                if (name.equals(manualFixAutoSetAtr)) {
                    if (daiPerformanceFunOpt.get().getManualFixAutoSetAtr() == 1) {
                        data.put("値", "○");
                    } else {
                        data.put("値", "-");
                    }
                }

                // clearManuAtr
                if (name.equals(clearManuAtr)) {
                    if (daiPerformanceFunOpt.get().getClearManuAtr() == 1) {
                        data.put("値", "○");
                    } else {
                        data.put("値", "-");
                    }
                }
                // checkErrRefDisp
                if (name.equals(checkErrRefDisp)) {
                    if (daiPerformanceFunOpt.get().getCheckErrRefDisp() == 1) {
                        data.put("値", "○");
                    } else {
                        data.put("値", "-");
                    }
                }
            }
            if (i > 1) {
                datas.add(alignMasterDataSheetRestriction(data));
            }
            putDataEmptySetOperationRestriction(data);
        }

        // put apptype
        String appTypes = "";
        if (!CollectionUtil.isEmpty(listApplicationCallExport)) {
            List<String> listAppType = listApplicationCallExport.stream()
                    .map(developer -> new String(
                            TextResource.localize("Enum_ApplicationType_" + developer.getAppType().name())))
                    .collect(Collectors.toList());
            appTypes = String.join(",", listAppType);
        }
        data.put("値",appTypes);
        data.put("display",  TextResource.localize("KDW006_77"));
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        // put monthly
        data.put("項目", TextResource.localize("KDW006_60"));
        data.put("option", TextResource.localize("KDW006_61"));
        if (monPerformanceFunOpt.get().getDailySelfChkDispAtr() == 1) {
            data.put("値", "○");
        } else {
            data.put("値", "-");
        }
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        data.put("option", TextResource.localize("KDW006_36"));
        if (identityProcessOpt.get().getUseMonthSelfCK() == 1) {
            data.put("値", "○");
        } else {
            data.put("値", "-");
        }
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        data.put("option", TextResource.localize("KDW006_37"));
        if (approvalProcessOpt.get().getUseMonthBossChk() == 1) {
            data.put("値", "○");
        } else {
            data.put("値", "-");
        }
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        return datas;
    }

    private MasterData alignMasterDataSheetRestriction(Map<String, Object> data) {
        MasterData masterData = new MasterData(data, null, "");
        masterData.cellAt("項目").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("option").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("display").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("値").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        return masterData;
    }

    private void putDataEmptySetOperationRestriction(Map<String, Object> data) {
        data.put("項目", "");
        data.put("option", "");
        data.put("display", "");
        data.put("値", "");

    }
    /**
     * Sheet 3
     */
    public List<MasterHeaderColumn> getHeaderColumnsSheetRole(MasterListExportQuery query) {

        List<MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn("コード", TextResource.localize("KDW006_106"), ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn("名称",TextResource.localize("KDW006_107"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の登録", TextResource.localize("KDW006_108"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の保存", TextResource.localize("KDW006_109"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の計算", TextResource.localize("KDW006_110"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の再計算", TextResource.localize("KDW006_111"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の表示項目の選択", TextResource.localize("KDW006_112"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の抽出条件の選択", TextResource.localize("KDW006_113"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("日別実績の印刷", TextResource.localize("KDW006_114"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("本人締め処理", TextResource.localize("KDW006_115"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("エラー参照", TextResource.localize("KDW006_116"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("打刻参照", TextResource.localize("KDW006_117"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("就業確定", TextResource.localize("KDW006_118"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("月別実績の登録", TextResource.localize("KDW006_119"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("月別実績の保存", TextResource.localize("KDW006_120"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("月別実績の表示項目の選択", TextResource.localize("KDW006_121"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("月別実績の抽出条件の選択", TextResource.localize("KDW006_122"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("月別実績の印刷", TextResource.localize("KDW006_123"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("列幅の保存", TextResource.localize("KDW006_124"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("修正履歴参照", TextResource.localize("KDW006_125"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("一括本人確認", TextResource.localize("KDW006_126"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("一括承認", TextResource.localize("KDW006_127"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("年休参照", TextResource.localize("KDW006_128"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("積立年休参照", TextResource.localize("KDW006_129"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("代休参照", TextResource.localize("KDW006_130"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("振休参照", TextResource.localize("KDW006_131"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("60H超休参照", TextResource.localize("KDW006_132"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("ロックされた実績の操作", TextResource.localize("KDW006_133"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("ロック状態でも処理可能（日別実績の作成）", TextResource.localize("KDW006_134"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("ロック状態でも処理可能（日別実績の計算）", TextResource.localize("KDW006_135"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("ロック状態でも処理可能（承認結果の反映）", TextResource.localize("KDW006_136"), ColumnTextAlign.LEFT, "", true));
         columns.add(new MasterHeaderColumn("ロック状態でも処理可能（月別実績の集計）", TextResource.localize("KDW006_137"), ColumnTextAlign.LEFT, "", true));

        return columns;
    }
    
    public List<MasterData> getMasterDatasSheetRoled(MasterListExportQuery query) {
    	String companyId = AppContexts.user().companyId();
    	
        List<MasterData> datas = new ArrayList<>();
        List<RoleExport> listRoleExport = operationExcelRepo.findRole(companyId);
        Map<String,Object> data = new HashMap<>();
        if(listRoleExport.size()==1){
            RoleExport roleExport = listRoleExport.get(0);
            data = new HashMap<>();
            if(roleExport.getAvailability()==1){
                data.put(roleExport.getDescription(),"○");
            }
            data.put("コード", roleExport.getCodeRole());
            data.put("名称", roleExport.getNameRole());
            datas.add(alignMasterDataSheetRole(data));
            putDataEmptyRole(data);
        }
        boolean checkAvailable = false;
        for (int i = 0 ; i < listRoleExport.size(); i ++) {
            RoleExport roleExport = listRoleExport.get(i);
            if(i>0 && !roleExport.getCodeRole().equals(listRoleExport.get(i-1).getCodeRole())){
            	datas.add(alignMasterDataSheetRole(data));
            	checkAvailable = false;
            }
            if(checkAvailable == false){
            	data = new HashMap<>();
            	putDataEmptyRole(data);
            	 data.put("コード", roleExport.getCodeRole());
                 data.put("名称", roleExport.getNameRole());
                 checkAvailable=true;
            }
            if(roleExport.getAvailability()==1){
                data.put(roleExport.getDescription(),"○");
            }
            if(i==(listRoleExport.size()-1)){
            	datas.add(alignMasterDataSheetRole(data));
            	checkAvailable = false;
            }
        }
        return datas;
    }

    private void putDataEmptyRole(Map<String, Object> data) {
        data.put("コード", "");
        data.put("名称", "");
        data.put("日別実績の登録", "-");
        data.put("日別実績の保存" , "-");
        data.put("日別実績の計算" , "-");
        data.put("日別実績の再計算" , "-");
        data.put("日別実績の表示項目の選択" , "-");
        data.put("日別実績の抽出条件の選択" , "-");
        data.put("日別実績の印刷" , "-");
        data.put("本人締め処理" , "-");
        data.put("エラー参照", "-");
        data.put("打刻参照", "-");
        data.put("就業確定", "-");
        data.put("月別実績の登録", "-");
        data.put("月別実績の保存", "-");
        data.put("月別実績の表示項目の選択", "-");
        data.put("月別実績の抽出条件の選択", "-");
        data.put("月別実績の印刷", "-");
        data.put("列幅の保存", "-");
        data.put("修正履歴参照", "-");
        data.put("一括本人確認", "-");
        data.put("一括承認", "-");
        data.put("年休参照", "-");
        data.put("積立年休参照", "-");
        data.put("代休参照", "-");
        data.put("振休参照", "-");
        data.put("60H超休参照", "-");
        data.put("ロックされた実績の操作", "-");
        data.put("ロック状態でも処理可能（日別実績の作成）", "-");
        data.put("ロック状態でも処理可能（日別実績の計算）", "-");
        data.put("ロック状態でも処理可能（承認結果の反映）", "-");
        data.put("ロック状態でも処理可能（月別実績の集計）", "-");
    }
    private MasterData alignMasterDataSheetRole(Map<String, Object> data) {
        MasterData masterData = new MasterData(data, null, "");
        masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
        return masterData;
    }
}
