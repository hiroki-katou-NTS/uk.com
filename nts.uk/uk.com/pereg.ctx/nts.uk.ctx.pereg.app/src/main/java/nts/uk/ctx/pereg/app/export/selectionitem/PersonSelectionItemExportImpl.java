package nts.uk.ctx.pereg.app.export.selectionitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.export.PersonSelectionItemExportData;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionitem.IPerInfoSelectionItemRepository;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.selectionorder.primitive.InitSelection;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value = "PersonSelectionItem")
public class PersonSelectionItemExportImpl implements MasterListData {
	@Inject
	private IPerInfoSelectionItemRepository perInfoSelectionItemRepo;
	private static final String CPS017_55 = "選択項目名称ヘッダー";
	private static final String CPS017_56 = "履歴開始日ヘッダー";
	private static final String CPS017_57 = "履歴終了日ヘッダー";
	private static final String CPS017_58 = "既定値ヘッダー";
	private static final String CPS017_59 = "コードヘッダー";
	private static final String CPS017_60 = "名称ヘッダー";
	private static final String CPS017_61 = "外部コードヘッダー";
	private static final String CPS017_62 = "メモヘッダー";

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(CPS017_55, TextResource.localize("CPS017_55"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CPS017_56, TextResource.localize("CPS017_56"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CPS017_57, TextResource.localize("CPS017_57"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CPS017_58, TextResource.localize("CPS017_58"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(
				new MasterHeaderColumn(CPS017_59, TextResource.localize("CPS017_59"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CPS017_60, TextResource.localize("CPS017_60"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CPS017_61, TextResource.localize("CPS017_61"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CPS017_62, TextResource.localize("CPS017_62"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String contractCd = AppContexts.user().contractCode();
		List<MasterData> datas = new ArrayList<>();
		List<PersonSelectionItemExportData> listPersonSelectionItemExportData = perInfoSelectionItemRepo
				.findAllSelection(contractCd);
		if (CollectionUtil.isEmpty(listPersonSelectionItemExportData)) {
			throw new BusinessException("Msg_1480");
		} else {
			listPersonSelectionItemExportData.stream().forEach(c -> {
				Map<String, Object> data = new HashMap<>();
				data.put(CPS017_55, c.getSelectionItemName());
				data.put(CPS017_56, c.getStartDate());
				data.put(CPS017_57, c.getEndDate());
				if (c.getInitSelection() == InitSelection.INIT_SELECTION.value) {
					data.put(CPS017_58, "●");
				} else {
					data.put(CPS017_58, "");
				}
				data.put(CPS017_59, c.getSelectionCD());
				data.put(CPS017_60, c.getSelectionName());
				data.put(CPS017_61, c.getExternalCD());
				data.put(CPS017_62, c.getMemoSelection());
				datas.add(new MasterData(data, null, ""));
			});
		}
		return datas;

	}

}
