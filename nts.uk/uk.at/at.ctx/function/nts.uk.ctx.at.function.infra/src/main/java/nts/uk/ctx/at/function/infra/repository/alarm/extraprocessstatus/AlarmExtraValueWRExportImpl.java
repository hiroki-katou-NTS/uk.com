package nts.uk.ctx.at.function.infra.repository.alarm.extraprocessstatus;

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
@DomainID(value = "alarmList")
public class AlarmExtraValueWRExportImpl implements MasterListData {

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		List<LinkedHashMap<String, String>> alarmList = (List<LinkedHashMap<String, String>>) query.getData();
		alarmList.forEach(alarmExtraValueWRPrint -> {
			Map<String, Object> data = new HashMap<>();
			data.put("職場名", alarmExtraValueWRPrint.get("workplaceName"));
			data.put("個人コード", alarmExtraValueWRPrint.get("employeeCode"));
			data.put("個人名", alarmExtraValueWRPrint.get("employeeName"));
			data.put("日付", alarmExtraValueWRPrint.get("alarmValueDate"));	
			
			data.put("カテゴリ", alarmExtraValueWRPrint.get("categoryName"));	
			data.put("アラーム項目名", alarmExtraValueWRPrint.get("alarmItem"));	
			data.put("アラーム項目値", alarmExtraValueWRPrint.get("alarmValueMessage"));	
			data.put("コメント", alarmExtraValueWRPrint.get("comment"));	
			datas.add(new MasterData(data, null, ""));
		});
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn("職場名", TextResource.localize("KAL001_20"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("個人コード", TextResource.localize("KAL001_13"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("個人名", TextResource.localize("KAL001_14"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("日付", TextResource.localize("KAL001_15"), ColumnTextAlign.CENTER, "", true));
		
		columns.add(
				new MasterHeaderColumn("カテゴリ", TextResource.localize("KAL001_16"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("アラーム項目名", TextResource.localize("KAL001_17"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("アラーム項目値", TextResource.localize("KAL001_18"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("コメント", TextResource.localize("KAL001_19"), ColumnTextAlign.CENTER, "", true));
		return columns;
	}

}
