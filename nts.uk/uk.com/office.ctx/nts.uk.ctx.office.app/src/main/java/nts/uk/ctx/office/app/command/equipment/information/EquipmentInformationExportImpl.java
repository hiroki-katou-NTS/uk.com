package nts.uk.ctx.office.app.command.equipment.information;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.office.dom.equipment.classificationmaster.EquipmentClassificationRepository;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformation;
import nts.uk.ctx.office.dom.equipment.information.EquipmentInformationRepository;
import nts.uk.ctx.office.dom.equipment.information.EquipmentRemark;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID("EquipmentInformation")
public class EquipmentInformationExportImpl implements MasterListData {
	
	private static final String SHEET_NAME = "マスタリスト";
	private static final String[] HEADER_NAMES = {
			"OEM022_27", "OEM022_28", "OEM022_29", "OEM022_30", "OEM022_31", "OEM022_32"
	};
	private static final String DATE_FORMAT = "yyyy/MM/dd";
	
	@Inject
	private EquipmentInformationRepository equipmentInformationRepository;
	
	@Inject
	private EquipmentClassificationRepository equipmentClassificationRepository;
	
	@Override
	public String mainSheetName() {
		return SHEET_NAME;
	}
	
	@Override
	public MasterListMode mainSheetMode() {
		return MasterListMode.NONE;
	}
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		return Stream.of(HEADER_NAMES).map(this::createHeader).collect(Collectors.toList());
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<EquipmentInformation> datas = this.equipmentInformationRepository
				.findByCid(AppContexts.user().companyId());
		return datas.stream().map(this::createData).collect(Collectors.toList());
	}
	
	private MasterData createData(EquipmentInformation data) {
		String equipmentClsName = this.equipmentClassificationRepository
				.getByClassificationCode(AppContexts.user().contractCode(), data.getEquipmentClsCode().v())
				.map(d -> d.getName().v())
				.orElse(null);
		Map<String, Object> rowData = new HashMap<>();
		rowData.put(HEADER_NAMES[0], equipmentClsName);
		rowData.put(HEADER_NAMES[1], data.getEquipmentCode().v());
		rowData.put(HEADER_NAMES[2], data.getEquipmentName().v());
		rowData.put(HEADER_NAMES[3], data.getValidPeriod().start().toString(DATE_FORMAT));
		rowData.put(HEADER_NAMES[4], data.getValidPeriod().end().toString(DATE_FORMAT));
		rowData.put(HEADER_NAMES[5], data.getEquipmentRemark().map(EquipmentRemark::v).orElse(null));
		
		MasterData masterData = new MasterData(rowData, null, "");
		for (String header : HEADER_NAMES) {
			masterData.cellAt(header).getStyle().horizontalAlign(ColumnTextAlign.LEFT);
		}
		return masterData;
	}
	
	private MasterHeaderColumn createHeader(String resourceId) {
		String header = TextResource.localize(resourceId);
		return new MasterHeaderColumn(resourceId, header, ColumnTextAlign.LEFT, "", true);
	}
}
