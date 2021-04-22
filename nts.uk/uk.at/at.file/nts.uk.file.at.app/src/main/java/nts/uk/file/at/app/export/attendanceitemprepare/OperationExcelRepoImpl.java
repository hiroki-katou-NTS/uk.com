package nts.uk.file.at.app.export.attendanceitemprepare;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformFuncRepo;
import nts.uk.ctx.at.record.dom.workrecord.authormanage.DailyPerformanceFunction;
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
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;
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
	
    @Inject
	private DailyPerformFuncRepo dailyPerfFunctionRepo;

    @Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
    	List<DailyPerformanceFunction> listDailyPerformanceFunction= dailyPerfFunctionRepo.getAll();
    	listDailyPerformanceFunction.sort(Comparator.comparing(DailyPerformanceFunction::getDisplayOrder));
    	List<String> header = new ArrayList<>();
    	header.add("コード");
    	header.add("名称");
    	listDailyPerformanceFunction.stream().forEach(x->{
    		header.add(x.getDisplayName().v());
    	});
        //sheet 共通_運用設定 (sheet 1)
        List<SheetData> sheetDatas = new ArrayList<>();
        // add the work place sheet
        //Sheet 2  共通_機能制限
        SheetData sheetWorkplaceData = new SheetData(getMasterDatasSheetRestriction(query),
                getHeaderColumnsSheetRestriction(query), null, null,TextResource.localize("KDW006_91"), MasterListMode.NONE);
        //Sheet 3 共通_権限別機能制限
        SheetData sheetRole = new SheetData(getMasterDatasSheetRoled(query, header),
                getHeaderColumnsSheetRole(query, header), null, null,TextResource.localize("KDW006_105"), MasterListMode.NONE);
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
        data.put("項目", TextResource.localize("KDW006_210"));
        data.put("comment", "");
        if (formatPerformance.isPresent()) {
                data.put("値", TextResource.localize(
                        "Enum_SettingUnitType_" + formatPerformance.get().getSettingUnitType().name()));
        }
        datas.add(alignMasterData(data));
        // next row
        data.put("項目", TextResource.localize("KDW006_211"));
        data.put("comment", TextResource.localize("KDW006_212"));
        if (daiPerformanceFunOpt.isPresent()) {
            if (daiPerformanceFunOpt.get().getComment() != null) {
                data.put("値", daiPerformanceFunOpt.get().getComment());
            }
        }
        datas.add(alignMasterData(data));
        // next row
        data.put("項目", "");
        data.put("comment", TextResource.localize("KDW006_213"));
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

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
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
        String useDailySelf = TextResource.localize("KDW006_224");
        String useDailyBoss = TextResource.localize("KDW006_215");
        String disp36Atr = TextResource.localize("KDW006_216");
        String flexDispAtr = TextResource.localize("KDW006_97");
        String manualFixAutoSetAtr = TextResource.localize("KDW006_98");
        String clearManuAtr = TextResource.localize("KDW006_219");
        String checkErrRefDisp = TextResource.localize("KDW006_220");
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
        
        List<String> listOption = new ArrayList<>();
        listOption.add(useDailySelf);
        listOption.add(useDailyBoss);
        listOption.add(disp36Atr);
        listOption.add(flexDispAtr);
        listOption.add(manualFixAutoSetAtr);
        listOption.add(clearManuAtr);
        listOption.add(checkErrRefDisp);
        data.put("項目", TextResource.localize("KDW006_214"));
        data.put("option", listOption.get(0));
        if (identityProcessOpt.isPresent()) {
            // useDailySelfCk
            if (identityProcessOpt.get().getUseDailySelfCk() == 1) {
                data.put("値", "○");
                dataChild.put("値", TextResource.localize(
                        "Enum_YourselfConfirmError_" + identityProcessOpt.get().getYourselfConfirmError().name()));
                datas.add(alignMasterDataSheetRestriction(data));
                datas.add(alignMasterDataSheetRestriction(dataChild));
                putDataEmptySetOperationRestriction(dataChild);
                putDataEmptySetOperationRestriction(data);
            } else {
                data.put("値", "-");
                datas.add(alignMasterDataSheetRestriction(data));
                putDataEmptySetOperationRestriction(data);
            }
        }else {
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
                        datas.add(alignMasterDataSheetRestriction(data));
                        datas.add(alignMasterDataSheetRestriction(dataChild));
                        putDataEmptySetOperationRestriction(dataChild);
                    } else {
                        data.put("値", "-");
                        datas.add(alignMasterDataSheetRestriction(data));
                        putDataEmptySetOperationRestriction(data);
                    }

                }else {
                	datas.add(alignMasterDataSheetRestriction(data));
                	putDataEmptySetOperationRestriction(data);
				}
               
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
//                // manualFixAutoSetAtr
//                if (name.equals(manualFixAutoSetAtr)) {
//                    if (daiPerformanceFunOpt.get().getManualFixAutoSetAtr() == 1) {
//                        data.put("値", "○");
//                    } else {
//                        data.put("値", "-");
//                    }
//                }
//
//                // clearManuAtr
//                if (name.equals(clearManuAtr)) {
//                    if (daiPerformanceFunOpt.get().getClearManuAtr() == 1) {
//                        data.put("値", "○");
//                    } else {
//                        data.put("値", "-");
//                    }
//                }
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
                    .map(developer -> developer.getAppType().nameId)
                    .collect(Collectors.toList());
            appTypes = String.join(",", listAppType);
        }
        data.put("値",appTypes);
        data.put("display",  TextResource.localize("KDW006_221"));
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        // put monthly
        data.put("項目", TextResource.localize("KDW006_222"));
        data.put("option", TextResource.localize("KDW006_223"));
        if(monPerformanceFunOpt.isPresent()){
	        if (monPerformanceFunOpt.get().getDailySelfChkDispAtr() == 1) {
	            data.put("値", "○");
	        } else {
	            data.put("値", "-");
	        }
        }
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        data.put("option", TextResource.localize("KDW006_224"));
        if (identityProcessOpt.isPresent()){
	        if (identityProcessOpt.get().getUseMonthSelfCK() == 1) {
	            data.put("値", "○");
	        } else {
	            data.put("値", "-");
	        }
        }
        datas.add(alignMasterDataSheetRestriction(data));
        putDataEmptySetOperationRestriction(data);
        data.put("option", TextResource.localize("KDW006_215"));
        if (approvalProcessOpt.isPresent()){
	        if (approvalProcessOpt.get().getUseMonthBossChk() == 1) {
	            data.put("値", "○");
	        } else {
	            data.put("値", "-");
	        }
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
     * @param header 
     */
    public List<MasterHeaderColumn> getHeaderColumnsSheetRole(MasterListExportQuery query, List<String> header) {
        List<MasterHeaderColumn> columns = new ArrayList<>();
        header.stream().forEach(x->{
        	columns.add(new MasterHeaderColumn(x, x, ColumnTextAlign.LEFT, "", true));
    	});
        return columns;
    }
    
    public List<MasterData> getMasterDatasSheetRoled(MasterListExportQuery query, List<String> header) {
    	String companyId = AppContexts.user().companyId();
        List<MasterData> datas = new ArrayList<>();
        List<RoleExport> listRoleExport = operationExcelRepo.findRole(companyId);
        if(CollectionUtil.isEmpty(listRoleExport)){
        	return null;
        }
        listRoleExport = listRoleExport.stream().sorted(
				Comparator.comparing(RoleExport::getCodeRole, Comparator.nullsLast(String::compareTo)))
				.collect(Collectors.toList());
        Map<String,Object> data = new HashMap<>();
        if(listRoleExport.size()==1){
            RoleExport roleExport = listRoleExport.get(0);
            data = new HashMap<>();
            if(roleExport.getAvailability()==1){
                data.put(roleExport.getDescription(),"○");
            }
            data.put("コード", roleExport.getCodeRole()==null?"":roleExport.getCodeRole());
            data.put("名称", roleExport.getNameRole()==null?TextResource.localize("KDW006_226"):roleExport.getNameRole());
            datas.add(alignMasterDataSheetRole(data,header));
            putDataEmptyRole(data, header);
        }
        boolean checkAvailable = false;
        for (int i = 0 ; i < listRoleExport.size(); i ++) {
            RoleExport roleExport = listRoleExport.get(i);
            if(i>0 &&!roleExport.getRoleId().equals(listRoleExport.get(i-1).getRoleId())){
            	datas.add(alignMasterDataSheetRole(data,header));
            	checkAvailable = false;
            }
            if(checkAvailable == false){
            	data = new HashMap<>();
            	putDataEmptyRole(data, header);
            	data.put("コード", roleExport.getCodeRole()==null?"":roleExport.getCodeRole());
                data.put("名称", roleExport.getNameRole()==null?TextResource.localize("KDW006_226"):roleExport.getNameRole());
                 checkAvailable=true;
            }
            if(roleExport.getAvailability()==1){
                data.put(roleExport.getDescription(),"○");
            }
            if(i==(listRoleExport.size()-1)){
            	datas.add(alignMasterDataSheetRole(data,header));
            	checkAvailable = false;
            }
        }
        return datas;
    }

    private void putDataEmptyRole(Map<String, Object> data, List<String> header) {
        for(int i = 0 ; i <header.size();i++ ){
        	if(i<2){
        		data.put(header.get(i), "");
        	}else {
        		data.put(header.get(i), "-");
			}
        }
    }
    private MasterData alignMasterDataSheetRole(Map<String, Object> data, List<String> header) {
        MasterData masterData = new MasterData(data, null, "");
        for (String string : header) {
        	masterData.cellAt(string).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		}
        return masterData;
    }
}
