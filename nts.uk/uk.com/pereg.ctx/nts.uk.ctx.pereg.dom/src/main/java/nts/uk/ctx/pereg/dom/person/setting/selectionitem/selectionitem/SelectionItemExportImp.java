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
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.SelectionItemReportData;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
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
		        ColumnTextAlign.RIGHT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_30, TextResource.localize("CPS016_30"),
		        ColumnTextAlign.RIGHT, "", true));
		        columns.add(new MasterHeaderColumn(CPS016_31, TextResource.localize("CPS016_31"),
		        ColumnTextAlign.RIGHT, "", true));
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
			throw new BusinessException("Msg_1480");
		} else {
			selectionItemReportDats.stream().forEach(x -> {
				Map<String, Object> data = new HashMap<>();
				data.put(CPS016_27, x.getSelectionItemName());
				data.put(CPS016_28, x.getCharacterType() == 0 ? I18NText.getText("Enum_SelectionCodeCharacter_NUMBER_TYPE") : I18NText.getText("Enum_SelectionCodeCharacter_CHARATERS_TYPE"));
				data.put(CPS016_29, x.getCodeLength()); 
				data.put(CPS016_30, x.getNameLength());
				data.put(CPS016_31, x.getExternalCodeLength());
				data.put(CPS016_32, x.getIntegrationCode());
				data.put(CPS016_33, x.getMemo());
				datas.add(new MasterData(data, null, ""));
			}); 
		}
		return datas;
		
	}

	

}
