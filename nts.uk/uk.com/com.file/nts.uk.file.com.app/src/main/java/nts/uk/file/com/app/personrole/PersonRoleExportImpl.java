package nts.uk.file.com.app.personrole;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.system.config.InstalledProduct;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@Stateless
@DomainID("PersonRole")
public class PersonRoleExportImpl implements MasterListData {

	@Inject
	private PersonRoleRepository personRoleRepository;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		//A7_1
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_77, TextResource.localize("CAS001_77"), ColumnTextAlign.LEFT, "", true));
		//A7_2
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_78, TextResource.localize("CAS001_78"), ColumnTextAlign.LEFT, "", true));
		//A7_3
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_79, TextResource.localize("CAS001_79"), ColumnTextAlign.LEFT, "", true));
		//A7_4
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_81, TextResource.localize("CAS001_80"), ColumnTextAlign.LEFT, "", true));
		//A7_5
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_82, TextResource.localize("CAS001_81"), ColumnTextAlign.LEFT, "",
				true));
		//A7_6
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_89, TextResource.localize("CAS001_82"), ColumnTextAlign.LEFT, "",
				true));
		//A7_7
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_83, TextResource.localize("CAS001_83"), ColumnTextAlign.LEFT, "",
				true));
		//A7_8
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_84, TextResource.localize("CAS001_84"), ColumnTextAlign.LEFT, "",
				true));
		//A7_9
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_85, TextResource.localize("CAS001_85"), ColumnTextAlign.LEFT, "",
				true));
		//A7_10
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_90, TextResource.localize("CAS001_86"), ColumnTextAlign.LEFT, "",
				true));
		//A7_11
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_91, TextResource.localize("CAS001_87"), ColumnTextAlign.LEFT, "", true));
		//A7_12
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_86, TextResource.localize("CAS001_88"), ColumnTextAlign.LEFT, "", true));
		//A7_13
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_87, TextResource.localize("CAS001_89"), ColumnTextAlign.LEFT, "",
				true));
		//A7_14
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_88, TextResource.localize("CAS001_90"), ColumnTextAlign.LEFT, "",
				true));
		//A7_15
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_92, TextResource.localize("CAS001_91"), ColumnTextAlign.LEFT, "",
				true));
		//A7_16
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_93, TextResource.localize("CAS001_92"), ColumnTextAlign.LEFT, "",
				true));
		//A7_17
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_94, TextResource.localize("CAS001_93"), ColumnTextAlign.LEFT, "",
				true));
		//A7_18
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_95, TextResource.localize("CAS001_94"), ColumnTextAlign.LEFT, "", true));
		//A7_19
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_97, TextResource.localize("CAS001_95"), ColumnTextAlign.LEFT, "", true));
		//A7_20
		columns.add(new MasterHeaderColumn(PersonRoleColumn.CAS001_98, TextResource.localize("CAS001_96"), ColumnTextAlign.LEFT, "",
				true));
		//A7_21
		columns.add(
				new MasterHeaderColumn(PersonRoleColumn.CAS001_99, TextResource.localize("CAS001_97"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
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
		datas = personRoleRepository.getDataExport(payroll, personnel, atttendance);
		return datas;
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.NONE;
	}
}
