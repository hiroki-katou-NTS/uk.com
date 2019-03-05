package nts.uk.file.com.app.person.info.category;

import nts.uk.ctx.pereg.dom.person.info.category.CategoryType;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.*;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Stateless
@DomainID(value ="ChangePerInforDefinitionExport")
public class ChangePerInforDefinitionExportImpl implements MasterListData{

    private static final String CPS006_73 = "個人情報カテゴリ名";
    private static final String CPS006_74 = "個人情報カテゴリ廃止";
    private static final String CPS006_75 = "既定名称";
    private static final String CPS006_76 = "履歴区分";
    private static final String CPS006_77 = "単一複数区分";
    private static final String CPS006_78 = "項目名";
    private static final String CPS006_79 = "個人情報項目定義.既定名称";
    private static final String CPS006_80 = "必須区分";
    private static final String CPS006_81 = "項目廃止";

    @Inject
    private ChangePerInforDefinitionExRepository perInforDefinition ;

    @Override
    public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0){
        List <MasterHeaderColumn> columns = new ArrayList<>();
        columns.add(new MasterHeaderColumn(CPS006_73, TextResource.localize("CPS006_73"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_74, TextResource.localize("CPS006_74"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_75, TextResource.localize("CPS006_75"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_76, TextResource.localize("CPS006_76"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_77, TextResource.localize("CPS006_77"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_78, TextResource.localize("CPS006_78"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_79, TextResource.localize("CPS006_79"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_80, TextResource.localize("CPS006_80"),
        ColumnTextAlign.LEFT, "", true));
        columns.add(new MasterHeaderColumn(CPS006_81, TextResource.localize("CPS006_81"),
        ColumnTextAlign.LEFT, "", true));
        return columns;
    }

	private MasterData putData(Object[] obj) {
        Map<String,MasterCellData> data = new HashMap<>();
            data.put(CPS006_73, MasterCellData.builder()
                .columnId(CPS006_73)
                .value(obj[0] == null ? "" : obj[0])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_74, MasterCellData.builder()
                .columnId(CPS006_74)
                .value(this.getTextDeprecatedCategory(obj[1]))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_75, MasterCellData.builder()
                .columnId(CPS006_75)
                .value(obj[2] == null ? "" : obj[2])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_76, MasterCellData.builder()
                .columnId(CPS006_76)
                .value(obj[3] == null ? "" : this.getTextCategory(((BigDecimal)obj[3]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_77, MasterCellData.builder()
                .columnId(CPS006_77)
                .value(obj[3] == null ? "" : this.getTextSingleMultipleSection(((BigDecimal)obj[3]).intValue()))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_78, MasterCellData.builder()
                .columnId(CPS006_78)
                .value(obj[4])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS006_79, MasterCellData.builder()
                .columnId(CPS006_79)
                .value(obj[5])
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            
            String required = Objects.isNull(obj[6]) ? null : 
            		((BigDecimal)obj[6]).intValue() == 0 ? TextResource.localize("CPS006_26") : 
            				TextResource.localize("CPS006_27") ;
            data.put(CPS006_80, MasterCellData.builder()
                .columnId(CPS006_80)
                .value(required)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            
            String abolition =  Objects.isNull(obj[7]) ? null:
            						((BigDecimal)obj[7]).intValue() == 1 ? "○" : "" ;
            data.put(CPS006_81, MasterCellData.builder()
                .columnId(CPS006_81)
                .value(abolition)
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
        return MasterData.builder().rowData(data).build();
    }

    private String getTextCategory(int value){
        if(CategoryType.SINGLEINFO.value == value || CategoryType.MULTIINFO.value == value) {
            return TextResource.localize("CPS006_46");
        }
        return TextResource.localize("CPS006_45");
    }

    private String getTextDeprecatedCategory(Object obj){
        if(obj != null && ((BigDecimal) obj).intValue() == 1 )
            return "○" ;
        return "";
    }

    private String getTextSingleMultipleSection(int value){

        if(CategoryType.SINGLEINFO.value == value ) {
            return TextResource.localize("CPS006_47");
        }
        if(CategoryType.MULTIINFO.value == value ) {
            return TextResource.localize("CPS006_48");
        }
        if(CategoryType.CONTINUOUSHISTORY.value == value ||
                CategoryType.CONTINUOUS_HISTORY_FOR_ENDDATE.value == value) {
            return TextResource.localize("Enum_HistoryTypes_CONTINUOUS");
        }
        if(CategoryType.NODUPLICATEHISTORY.value == value ) {
            return TextResource.localize("Enum_HistoryTypes_NO_DUPLICATE");
        }
        if(CategoryType.DUPLICATEHISTORY.value == value ) {
            return TextResource.localize("Enum_HistoryTypes_DUPLICATE");
        }
        return "";
    }

    @Override
    public List<MasterData> getMasterDatas(MasterListExportQuery query) {
        String companyId = AppContexts.user().companyId();
        String conTracCd = companyId.substring(0, 12);
        String companyIdRoot = AppContexts.user().zeroCompanyIdInContract();

        int payroll = NotUseAtr.NOT_USE.value;
        int personnel = NotUseAtr.NOT_USE.value;
        int atttendance = NotUseAtr.NOT_USE.value;
        List<InstalledProduct> installProduct = AppContexts.system().getInstalledProducts();
        for (InstalledProduct productType : installProduct) {
            switch (productType.getProductType()) {
                case ATTENDANCE:
                    atttendance = NotUseAtr.USE.value;
                    break;
                case PAYROLL:
                    payroll = NotUseAtr.USE.value;
                    break;
                case PERSONNEL:
                    personnel = NotUseAtr.USE.value;
                    break;
                default:
                    break;
            }
        }

        List<Object[]> perInforDef = perInforDefinition.getChangePerInforDefinitionToExport(companyId, conTracCd, companyIdRoot,payroll, personnel, atttendance);
        return perInforDef.stream().map(i -> this.putData(i)).collect(Collectors.toList());
    }

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
