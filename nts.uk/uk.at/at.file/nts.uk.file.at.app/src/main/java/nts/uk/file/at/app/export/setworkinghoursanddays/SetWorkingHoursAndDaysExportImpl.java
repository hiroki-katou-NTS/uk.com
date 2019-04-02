package nts.uk.file.at.app.export.setworkinghoursanddays;

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
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;

@DomainID(value = "SetWorkingHoursAndDays")
@Stateless
public class SetWorkingHoursAndDaysExportImpl implements MasterListData {
	@Inject
	private SetWorkingHoursAndDaysExRepository repo;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_154, TextResource.localize("KMK004_154"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_155, TextResource.localize("KMK004_155"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_156, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_157, TextResource.localize("KMK004_157"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_156_1, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_158, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_159, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_160, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_161, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_162, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_163, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_164, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_165, TextResource.localize("KMK004_165"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_166, TextResource.localize("KMK004_166"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_167, TextResource.localize("KMK004_167"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_156_2, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_168, TextResource.localize("KMK004_168"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_169, TextResource.localize("KMK004_169"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_170, TextResource.localize("KMK004_170"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_171, TextResource.localize("KMK004_171"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_156_3, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_172, TextResource.localize("KMK004_172"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_156_4, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_158_1, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_173, TextResource.localize("KMK004_173"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_174, TextResource.localize("KMK004_174"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_175, TextResource.localize("KMK004_175"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_159_1, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_160_1, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_161_1, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_162_1, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_163_1, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_164_1, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));

		return columns;
	}
	
	
	public List<MasterHeaderColumn> getHeaderEmploymentColumns() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_185, TextResource.localize("KMK004_185"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_186, TextResource.localize("KMK004_186"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_154, TextResource.localize("KMK004_154"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_155, TextResource.localize("KMK004_155"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_156, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_157, TextResource.localize("KMK004_157"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_156_1, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_158, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_159, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_160, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_161, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_162, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_163, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_164, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_165, TextResource.localize("KMK004_165"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_166, TextResource.localize("KMK004_166"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_167, TextResource.localize("KMK004_167"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_156_2, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_168, TextResource.localize("KMK004_168"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_169, TextResource.localize("KMK004_169"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_170, TextResource.localize("KMK004_170"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_171, TextResource.localize("KMK004_171"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_156_3, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_172, TextResource.localize("KMK004_172"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_156_4, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_158_1, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_173, TextResource.localize("KMK004_173"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_174, TextResource.localize("KMK004_174"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_175, TextResource.localize("KMK004_175"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_159_1, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_160_1, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_161_1, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_162_1, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_163_1, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_164_1, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterHeaderColumn> getHeaderWorkPlaceColumns() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_187, TextResource.localize("KMK004_187"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_188, TextResource.localize("KMK004_188"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_154, TextResource.localize("KMK004_154"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_155, TextResource.localize("KMK004_155"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_156, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_157, TextResource.localize("KMK004_157"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_156_1, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_158, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_159, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_160, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_161, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_162, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_163, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_164, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_165, TextResource.localize("KMK004_165"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_166, TextResource.localize("KMK004_166"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_167, TextResource.localize("KMK004_167"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_156_2, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_168, TextResource.localize("KMK004_168"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_169, TextResource.localize("KMK004_169"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_170, TextResource.localize("KMK004_170"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_171, TextResource.localize("KMK004_171"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_156_3, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_172, TextResource.localize("KMK004_172"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_156_4, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_158_1, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_173, TextResource.localize("KMK004_173"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_174, TextResource.localize("KMK004_174"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_175, TextResource.localize("KMK004_175"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_159_1, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_160_1, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_161_1, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_162_1, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_163_1, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_164_1, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	public List<MasterHeaderColumn> getHeaderEmployeeColumns(){
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_183, TextResource.localize("KMK004_183"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_184, TextResource.localize("KMK004_184"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_154, TextResource.localize("KMK004_154"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_155, TextResource.localize("KMK004_155"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_156, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_157, TextResource.localize("KMK004_157"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_156_1, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_158, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_159, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_160, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_161, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_162, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_163, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_164, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_165, TextResource.localize("KMK004_165"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_166, TextResource.localize("KMK004_166"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_167, TextResource.localize("KMK004_167"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_156_2, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_168, TextResource.localize("KMK004_168"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_169, TextResource.localize("KMK004_169"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_170, TextResource.localize("KMK004_170"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_171, TextResource.localize("KMK004_171"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_156_3, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_172, TextResource.localize("KMK004_172"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_156_4, TextResource.localize("KMK004_156"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_158_1, TextResource.localize("KMK004_158"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_173, TextResource.localize("KMK004_173"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_174, TextResource.localize("KMK004_174"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_175, TextResource.localize("KMK004_175"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_159_1, TextResource.localize("KMK004_159"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_160_1, TextResource.localize("KMK004_160"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_161_1, TextResource.localize("KMK004_161"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_162_1, TextResource.localize("KMK004_162"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_163_1, TextResource.localize("KMK004_163"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_164_1, TextResource.localize("KMK004_164"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	@Override
	public String mainSheetName(){
		return TextResource.localize("KMK004_189");
	}

	@Override
	public MasterListMode mainSheetMode(){
		return MasterListMode.YEAR_RANGE;
	}

	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		return repo.getCompanyExportData(startDate, endDate);
	}
	
	public List<MasterData> getMasterDatasEmployment(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		datas = repo.getEmploymentExportData(startDate, endDate);
		return datas;
	}
	
	public List<MasterData> getMasterDatasEmployee(MasterListExportQuery query) {
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		return repo.getEmployeeData(startDate, endDate);
	}
	
	public List<MasterData> getMasterDatasWorkPlace(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		datas = repo.getWorkPlaceExportData(startDate, endDate);
		return datas;
	}
	
	@Override
    public List<SheetData> extraSheets(MasterListExportQuery query) {
    	Object[] usage = repo.getUsage();
        List<SheetData> sheetDatas = new ArrayList<>();
        // Sheet Employment
		if (usage != null) {
			if (Integer.valueOf(usage[2].toString()) == 1) {
				SheetData sheetDataEmployment = SheetData.builder().mainData(this.getMasterDatasEmployment(query))
						.mainDataColumns(this.getHeaderEmploymentColumns())
						.sheetName(TextResource.localize("KMK004_191"))
						.mode(MasterListMode.YEAR_RANGE)
						.build();
				sheetDatas.add(sheetDataEmployment);
			}

			// Sheet WorkPlace
			if (Integer.valueOf(usage[1].toString()) == 1) {
				SheetData sheetDataWorkPlace = SheetData.builder().mainData(this.getMasterDatasWorkPlace(query))
						.mainDataColumns(this.getHeaderWorkPlaceColumns())
						.sheetName(TextResource.localize("KMK004_192"))
						.mode(MasterListMode.YEAR_RANGE)
						.build();
				sheetDatas.add(sheetDataWorkPlace);
			}

			// Sheet Employee
			if (Integer.valueOf(usage[0].toString()) == 1) {
				SheetData sheetDataEmployee = SheetData.builder().mainData(this.getMasterDatasEmployee(query))
						.mainDataColumns(this.getHeaderEmployeeColumns()).sheetName(TextResource.localize("KMK004_190"))
						.mode(MasterListMode.YEAR_RANGE)
						.build();
				sheetDatas.add(sheetDataEmployee);
			}
		}
        
        return sheetDatas;
    }
}
