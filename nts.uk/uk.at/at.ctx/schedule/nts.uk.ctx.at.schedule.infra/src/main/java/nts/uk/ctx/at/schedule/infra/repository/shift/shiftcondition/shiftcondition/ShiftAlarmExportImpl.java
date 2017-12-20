package nts.uk.ctx.at.schedule.infra.repository.shift.shiftcondition.shiftcondition;

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
/**
 * ShiftAlram Export implement
 * @author trungtran
 *
 */
@Stateless
@DomainID(value = "ShiftAlarm")
public class ShiftAlarmExportImpl implements MasterListData {

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		@SuppressWarnings("unchecked")
		List<Map<String, String>> errors = (List<Map<String, String>>) query.getData();
		errors.stream().forEach(error -> {
			Map<String, Object> data = new HashMap<>();
			data.put("社員コード", error.get("employeeCode"));
			data.put("社員名ヘッダ", error.get("employeeName"));
			data.put("年月日ヘッダ", error.get("date"));
			data.put("カテゴリヘッダ", error.get("category"));
			data.put("条件ヘッダ", error.get("condition"));
			data.put("内容ヘッダ", error.get("message"));
			datas.add(new MasterData(data, null, ""));
		});
		return datas;
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn("社員コード", TextResource.localize("KSU001_204"), ColumnTextAlign.CENTER, "", true));
		columns.add(new MasterHeaderColumn("社員名ヘッダ", TextResource.localize("KSU001_205"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn("年月日ヘッダ", TextResource.localize("KSU001_206"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(new MasterHeaderColumn("カテゴリヘッダ", TextResource.localize("KSU001_207"), ColumnTextAlign.CENTER, "",
				true));
		columns.add(
				new MasterHeaderColumn("条件ヘッダ", TextResource.localize("KSU001_208"), ColumnTextAlign.CENTER, "", true));
		columns.add(
				new MasterHeaderColumn("内容ヘッダ", TextResource.localize("KSU001_209"), ColumnTextAlign.CENTER, "", true));

		return columns;
	}

}
