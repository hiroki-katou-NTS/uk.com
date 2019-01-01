package nts.uk.file.com.app.person.setting;

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

@Stateless
@DomainID(value="PerInfoInit")
public class PerInfoInitValueSetCtgExportImpl implements MasterListData {
	
	@Inject
	private PerInfoInitValueSetCtgRepository perInfoInitValueSetCtgRepository;
	
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_77, TextResource.localize("CPS009_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_78, TextResource.localize("CPS009_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_79, TextResource.localize("CPS009_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_81, TextResource.localize("CPS009_81"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_82, TextResource.localize("CPS009_82"), ColumnTextAlign.LEFT, "",
				true));
		columns.add(new MasterHeaderColumn(PerInfoInitValueSetCtgUtils.CPS009_89, TextResource.localize("CPS009_89"), ColumnTextAlign.LEFT, "",
				true));
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
		datas = perInfoInitValueSetCtgRepository.getDataExport(payroll, personnel, atttendance);
		return datas;
	}
}
