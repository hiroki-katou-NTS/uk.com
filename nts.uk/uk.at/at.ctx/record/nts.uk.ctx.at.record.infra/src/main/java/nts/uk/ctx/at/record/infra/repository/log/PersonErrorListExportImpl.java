package nts.uk.ctx.at.record.infra.repository.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID(value = "personError")
public class PersonErrorListExportImpl implements MasterListData {

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<PersonError> personErrors = (List<PersonError>) query.getData();
		personErrors.forEach(personError -> {
			Map<String, Object> data = new HashMap<>();
			data.put("社員コード", personError.getEmployeeID());
			data.put("名称", personError.getEmployeeID());
			data.put("処理日", personError.getDisposalDay());
			data.put("エラー内容内", personError.getDisposalDay());
			datas.add(new MasterData(data, null, ""));
		});
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn("社員コード", TextResource.localize("KDW001_33"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("名称	", TextResource.localize("KDW001_35"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("処理日", TextResource.localize("KDW001_36"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("エラー内容内", TextResource.localize("KDW001_37"), ColumnTextAlign.CENTER, "", true));
		return columns;
	}

}
