package nts.uk.file.com.app.rolesetmenu;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.gul.collection.CollectionUtil;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("RoleSetMenu")
public class RoleSetMenuExportImpl implements MasterListData {
	
	@Inject
	private RoleSetMenuRepository roleSetMenuRepository;
	
	/** The Constant TABLE_ONE. */
	private static final String TABLE_ONE = "Table 001";

	/** The Constant TABLE_TWO. */
	private static final String TABLE_TWO = "Table 002";

	
	@Override
	public Map<String, List<MasterHeaderColumn>> getExtraHeaderColumn(MasterListExportQuery query) {
		Map<String, List<MasterHeaderColumn>> mapColum = new LinkedHashMap<>();
		mapColum.put(TABLE_ONE, this.getHeaderColumnOnes(query));
		mapColum.put(TABLE_TWO, this.getHeaderColumnTwos(query));
		return mapColum;
	}
	
	@Override
	public Map<String, List<MasterData>> getExtraMasterData(MasterListExportQuery query) {
		Map<String, List<MasterData>> mapTableData = new LinkedHashMap<>();
		mapTableData.put(TABLE_ONE, this.getMasterDataOne(query));
		mapTableData.put(TABLE_TWO, this.getMasterDataTwo(query));
		return mapTableData;
	}

	
	public List<MasterHeaderColumn> getHeaderColumnOnes(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_39, TextResource.localize("CAS011_39"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_40, TextResource.localize("CAS011_40"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_41, TextResource.localize("CAS011_41"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_42, TextResource.localize("CAS011_42"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_43, TextResource.localize("CAS011_43"), ColumnTextAlign.CENTER, "", true));
		return columns;
	}
	
	private List<MasterData> getMasterDataOne(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		datas = roleSetMenuRepository.findTable1();
		if (CollectionUtil.isEmpty(datas)) {
			throw new BusinessException("Msg_7");
		}
		return datas;
	}
	
	
	public List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_39, TextResource.localize("CAS011_39"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_40, TextResource.localize("CAS011_40"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_44, TextResource.localize("CAS011_44"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn(RoleSetMenuColumn.CAS011_45, TextResource.localize("CAS011_45"), ColumnTextAlign.CENTER, "", true));
		return columns;
	}
	
	
	private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		datas = roleSetMenuRepository.findTable2();
		if (CollectionUtil.isEmpty(datas)) {
			throw new BusinessException("Msg_7");
		}
		return datas;
	}
	
}
