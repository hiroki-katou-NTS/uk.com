package nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.i18n.I18NText;
import nts.gul.collection.CollectionUtil;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("SelectionItem")
public class SelectionItemExportImp implements MasterListData {
	
	private String CPS016_27 = "名称";
	private String CPS016_28 = "コード型";
	private String CPS016_29 = "コード桁数";
	private String CPS016_30 = "名称桁数";
	private String CPS016_31 = "外部コード桁数";
	private String CPS016_32 = "統合コード";
	private String CPS016_33 = "メモ";
	
	@Inject 
	private IPerInfoSelectionItemRepository iPerInfoSelectionItemRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(CPS016_27, TextResource.localize("CPS016_27"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_28, TextResource.localize("CPS016_28"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_29, TextResource.localize("CPS016_29"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_30, TextResource.localize("CPS016_30"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_31, TextResource.localize("CPS016_31"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_32, TextResource.localize("CPS016_32"),
		        ColumnTextAlign.LEFT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_33, TextResource.localize("CPS016_33"),
		        ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		String contractCode = AppContexts.user().contractCode();
		List<MasterData> datas = new ArrayList<>();
		List<SelectionItemReportData> selectionItemReportDats = iPerInfoSelectionItemRepository.findByContractCd(contractCode);
		if(CollectionUtil.isEmpty(selectionItemReportDats)) {
			return datas;
		} else {
			selectionItemReportDats.stream().forEach(x -> {
				datas.add(toData(x));
			}); 
		}
		return datas;
		
	}
	
	private MasterData toData(SelectionItemReportData x){
		Map<String, MasterCellData> data = new HashMap<>();
		data.put(CPS016_27, MasterCellData.builder()
                .columnId(CPS016_27)
                .value(x.getSelectionItemName())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS016_28, MasterCellData.builder()
                .columnId(CPS016_28)
                .value(x.getCharacterType() == 0 ? I18NText.getText("Enum_SelectionCodeCharacter_NUMBER_TYPE") : I18NText.getText("Enum_SelectionCodeCharacter_CHARATERS_TYPE"))
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS016_29, MasterCellData.builder()
                .columnId(CPS016_29)
                .value(x.getCodeLength())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(CPS016_30, MasterCellData.builder()
                .columnId(CPS016_30)
                .value(x.getNameLength())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(CPS016_31, MasterCellData.builder()
                .columnId(CPS016_31)
                .value(x.getExternalCodeLength())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.RIGHT))
                .build());
            data.put(CPS016_32, MasterCellData.builder()
                .columnId(CPS016_32)
                .value(x.getIntegrationCode())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
            data.put(CPS016_33, MasterCellData.builder()
                .columnId(CPS016_33)
                .value(x.getMemo())
                .style(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT))
                .build());
		return MasterData.builder().rowData(data).build();
	}
	
	

}
