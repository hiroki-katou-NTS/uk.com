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

@Stateless
@DomainID("PersonRole")
public class PersonRoleExportImpl implements MasterListData {

	private static final String CAS001_77 = "個人情報ロールコード";
	private static final String CAS001_78 = "個人情報ロール名称";
	private static final String CAS001_79 = "カテゴリ名";
	private static final String CAS001_80 = "設定済";
	private static final String CAS001_81 = "情報種類";
	private static final String CAS001_82 = "他人の情報利用";
	private static final String CAS001_83 = "他人.情報追加";
	private static final String CAS001_84 = "他人.情報削除";
	private static final String CAS001_85 = "他人.履歴追加";
	private static final String CAS001_86 = "他人.履歴削除";
	private static final String CAS001_87 = "他人.未来履歴";
	private static final String CAS001_88 = "他人.過去履歴";
	private static final String CAS001_89 = "本人の情報利用";
	private static final String CAS001_90 = "本人.情報追加 ";
	private static final String CAS001_91 = "本人.情報削除";
	private static final String CAS001_92 = "本人.履歴追加";
	private static final String CAS001_93 = "本人.履歴削除";
	private static final String CAS001_94 = "本人.未来履歴";
	private static final String CAS001_95 = "本人.過去履歴";
	private static final String CAS001_96 = "項目設定済";
	private static final String CAS001_97 = "項目";
	private static final String CAS001_98 = "他人情報権限設定";
	private static final String CAS001_99 = "本人情報権限設定";

	@Inject
	private PersonRoleRepository personRoleRepository;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(CAS001_77, TextResource.localize("CAS001_77"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_78, TextResource.localize("CAS001_78"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_79, TextResource.localize("CAS001_79"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_80, TextResource.localize("CAS001_80"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CAS001_81, TextResource.localize("CAS001_81"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_82, TextResource.localize("CAS001_82"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_83, TextResource.localize("CAS001_83"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_84, TextResource.localize("CAS001_84"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_85, TextResource.localize("CAS001_85"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_86, TextResource.localize("CAS001_86"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(
				new MasterHeaderColumn(CAS001_87, TextResource.localize("CAS001_87"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_88, TextResource.localize("CAS001_88"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CAS001_89, TextResource.localize("CAS001_89"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_90, TextResource.localize("CAS001_90"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_91, TextResource.localize("CAS001_91"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_92, TextResource.localize("CAS001_92"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn(CAS001_93, TextResource.localize("CAS001_93"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(
				new MasterHeaderColumn(CAS001_94, TextResource.localize("CAS001_94"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_95, TextResource.localize("CAS001_95"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CAS001_96, TextResource.localize("CAS001_96"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(
				new MasterHeaderColumn(CAS001_97, TextResource.localize("CAS001_97"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_98, TextResource.localize("CAS001_98"), ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(CAS001_99, TextResource.localize("CAS001_99"), ColumnTextAlign.LEFT, "", true));
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

}
