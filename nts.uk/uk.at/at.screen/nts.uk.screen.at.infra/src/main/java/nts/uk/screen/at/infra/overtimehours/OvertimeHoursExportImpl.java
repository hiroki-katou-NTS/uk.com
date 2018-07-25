package nts.uk.screen.at.infra.overtimehours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
@DomainID(value = "overtime")
public class OvertimeHoursExportImpl implements MasterListData{

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<LinkedHashMap<String, String>> listOverTime = (List<LinkedHashMap<String, String>>) query.getData();
		listOverTime.forEach(overTime -> {
			Map<String, Object> data = new HashMap<>();
			data.put("社員ヘッダ", overTime.get("employee"));
			data.put("限度時間ヘッダ", overTime.get("timeLimit"));
			data.put("実績ヘッダ", overTime.get("actualTime"));
			data.put("申請ヘッダ", overTime.get("applicationTime"));
			data.put("社員合計ヘッダ", overTime.get("totalTime"));	
			datas.add(new MasterData(data, null, ""));
		});
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn("社員ヘッダ", TextResource.localize("Com_Person"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("限度時間ヘッダ", TextResource.localize("KTG027_7"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("実績ヘッダ", TextResource.localize("KTG027_8"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("申請ヘッダ", TextResource.localize("KTG027_9"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("社員合計ヘッダ", TextResource.localize("KTG027_10"), ColumnTextAlign.CENTER, "", true));
		return columns;
	}

}
