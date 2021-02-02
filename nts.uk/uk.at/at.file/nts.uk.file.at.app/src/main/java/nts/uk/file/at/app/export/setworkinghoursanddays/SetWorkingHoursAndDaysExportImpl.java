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
	
	@Inject
	private GetKMK004CompanyExportRepository companyRepo;
	
	@Inject
	private GetKMK004WorkPlaceExportRepository wkpRepo;
	
	@Inject
	private GetKMK004EmploymentExportRepository empRepo;
	
	@Inject
	private GetKMK004EmployeeExportRepository shaRepo;

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery arg0) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_372, TextResource.localize("KMK004_372"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_373, TextResource.localize("KMK004_373"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_374, TextResource.localize("KMK004_374"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_375, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_376, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_377, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_378, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_379, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_380, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_381, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_382, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_383, TextResource.localize("KMK004_383"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_384, TextResource.localize("KMK004_384"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_385, TextResource.localize("KMK004_385"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_386, TextResource.localize("KMK004_386"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_387, TextResource.localize("KMK004_387"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_388, TextResource.localize("KMK004_388"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_389, TextResource.localize("KMK004_389"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_390, TextResource.localize("KMK004_390"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_391, TextResource.localize("KMK004_391"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_392, TextResource.localize("KMK004_392"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_393, TextResource.localize("KMK004_393"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_394, TextResource.localize("KMK004_394"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_395, TextResource.localize("KMK004_395"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_396, TextResource.localize("KMK004_396"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_397, TextResource.localize("KMK004_397"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_375_1, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_376_1, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_398, TextResource.localize("KMK004_398"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_399, TextResource.localize("KMK004_399"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_400, TextResource.localize("KMK004_400"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_377_1, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_378_1, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_379_1, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_380_1, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_381_1, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(CompanyColumn.KMK004_382_1, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));

		return columns;
	}
	
	
	public List<MasterHeaderColumn> getHeaderEmploymentColumns() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_185, TextResource.localize("KMK004_185"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_186, TextResource.localize("KMK004_186"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_372, TextResource.localize("KMK004_372"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_373, TextResource.localize("KMK004_373"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_374, TextResource.localize("KMK004_374"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_375, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_376, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_377, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_378, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_379, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_380, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_381, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_382, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_383, TextResource.localize("KMK004_383"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_384, TextResource.localize("KMK004_384"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_385, TextResource.localize("KMK004_385"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_386, TextResource.localize("KMK004_386"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_387, TextResource.localize("KMK004_387"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_388, TextResource.localize("KMK004_388"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_389, TextResource.localize("KMK004_389"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_390, TextResource.localize("KMK004_390"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_391, TextResource.localize("KMK004_391"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_392, TextResource.localize("KMK004_392"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_393, TextResource.localize("KMK004_393"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_394, TextResource.localize("KMK004_394"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_395, TextResource.localize("KMK004_395"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_396, TextResource.localize("KMK004_396"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_397, TextResource.localize("KMK004_397"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_375_1, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_376_1, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_398, TextResource.localize("KMK004_398"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_399, TextResource.localize("KMK004_399"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_400, TextResource.localize("KMK004_400"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_377_1, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_378_1, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_379_1, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_380_1, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_381_1, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmploymentColumn.KMK004_382_1, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	public List<MasterHeaderColumn> getHeaderWorkPlaceColumns() {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_187, TextResource.localize("KMK004_187"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_188, TextResource.localize("KMK004_188"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_372, TextResource.localize("KMK004_372"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_373, TextResource.localize("KMK004_373"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_374, TextResource.localize("KMK004_374"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_375, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_376, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_377, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_378, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_379, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_380, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_381, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_382, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_383, TextResource.localize("KMK004_383"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_384, TextResource.localize("KMK004_384"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_385, TextResource.localize("KMK004_385"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_386, TextResource.localize("KMK004_386"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_387, TextResource.localize("KMK004_387"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_388, TextResource.localize("KMK004_388"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_389, TextResource.localize("KMK004_389"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_390, TextResource.localize("KMK004_390"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_391, TextResource.localize("KMK004_391"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_392, TextResource.localize("KMK004_392"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_393, TextResource.localize("KMK004_393"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_394, TextResource.localize("KMK004_394"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_395, TextResource.localize("KMK004_395"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_396, TextResource.localize("KMK004_396"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_397, TextResource.localize("KMK004_397"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_375_1, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_376_1, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_398, TextResource.localize("KMK004_398"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_399, TextResource.localize("KMK004_399"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_400, TextResource.localize("KMK004_400"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_377_1, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_378_1, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_379_1, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_380_1, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_381_1, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(WorkPlaceColumn.KMK004_382_1, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		
		return columns;
	}
	
	public List<MasterHeaderColumn> getHeaderEmployeeColumns(){
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_183, TextResource.localize("KMK004_183"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_184, TextResource.localize("KMK004_184"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_372, TextResource.localize("KMK004_372"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_373, TextResource.localize("KMK004_373"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_374, TextResource.localize("KMK004_374"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_375, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_376, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_377, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_378, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_379, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_380, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_381, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_382, TextResource.localize("KMK004_382"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_383, TextResource.localize("KMK004_383"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_384, TextResource.localize("KMK004_384"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_385, TextResource.localize("KMK004_385"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_386, TextResource.localize("KMK004_386"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_387, TextResource.localize("KMK004_387"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_388, TextResource.localize("KMK004_388"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_389, TextResource.localize("KMK004_389"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_390, TextResource.localize("KMK004_390"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_391, TextResource.localize("KMK004_391"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_392, TextResource.localize("KMK004_392"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_393, TextResource.localize("KMK004_393"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_394, TextResource.localize("KMK004_394"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_395, TextResource.localize("KMK004_395"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_396, TextResource.localize("KMK004_396"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_397, TextResource.localize("KMK004_397"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_375_1, TextResource.localize("KMK004_375"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_376_1, TextResource.localize("KMK004_376"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_398, TextResource.localize("KMK004_398"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_399, TextResource.localize("KMK004_399"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_400, TextResource.localize("KMK004_400"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_377_1, TextResource.localize("KMK004_377"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_378_1, TextResource.localize("KMK004_378"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_379_1, TextResource.localize("KMK004_379"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_380_1, TextResource.localize("KMK004_380"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_381_1, TextResource.localize("KMK004_381"),
				ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn(EmployeeColumn.KMK004_382_1, TextResource.localize("KMK004_382"),
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
		return companyRepo.getCompanyExportData(startDate, endDate);
	}
	
	public List<MasterData> getMasterDatasEmployment(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		datas = empRepo.getEmploymentExportData(startDate, endDate);
		return datas;
	}
	
	public List<MasterData> getMasterDatasEmployee(MasterListExportQuery query) {
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		return shaRepo.getEmployeeData(startDate, endDate);
	}
	
	public List<MasterData> getMasterDatasWorkPlace(MasterListExportQuery query) {
		List<MasterData> datas = new ArrayList<>();
		int startDate = query.getStartDate().year();
		int endDate = query.getEndDate().year();
		datas = wkpRepo.getWorkPlaceExportData(startDate, endDate);
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
