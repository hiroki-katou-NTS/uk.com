package nts.uk.file.com.app.JobInfo;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

@Stateless
@DomainID("JobInfo")
public class JobInfoExportImpl implements MasterListData{
	
	@Inject
	private JobInfoRepository repository;
	
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_41, TextResource.localize("CAS014_41"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_42, TextResource.localize("CAS014_42"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_43, TextResource.localize("CAS014_43"), 
						ColumnTextAlign.LEFT, "", true));
		columns.add(
				new MasterHeaderColumn(JobInfoColumn.CAS014_44, TextResource.localize("CAS014_44"), 
						ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		String date = query.getOption().toString();
		datas = repository.getDataExport(date);
		return datas;
	}
	
}
