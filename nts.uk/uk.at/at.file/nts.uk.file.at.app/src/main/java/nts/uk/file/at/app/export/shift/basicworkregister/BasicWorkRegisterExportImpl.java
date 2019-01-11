
package nts.uk.file.at.app.export.shift.basicworkregister;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WkpConfigInfoFindObject;
import nts.uk.ctx.bs.employee.app.find.workplace.config.dto.WorkplaceHierarchyDto;
import nts.uk.ctx.bs.employee.app.find.workplace.config.info.WorkplaceConfigInfoFinder;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.infra.file.report.masterlist.annotation.DomainID;
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.MasterListData;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;

/**
 *
 * SpecificDay Setting export implements
 * 
 * @author HiepTH
 *
 */
@Stateless
@DomainID(value = "BasicWorkrRegister")
public class BasicWorkRegisterExportImpl implements MasterListData {

	@Inject
	private BasicWorkRegisterReportRepository basicWorkRegisterReportRepository;
	
	@Inject
	private WorkplaceConfigInfoFinder workplaceConfigInfoFinder;
	
	 @Override
	 public List<SheetData> extraSheets(MasterListExportQuery query) {
		 List<SheetData> sheetDatas = new ArrayList<>();
		 SheetData sheetWorkplaceData = new SheetData(getMasterDatasWorkplace(query), getHeaderColumnsWorkspace(query),
				 null, null, TextResource.localize("Com_Workplace"));
		 SheetData sheetClassData = new SheetData(getMasterDatasForClass(query), getHeaderColumnsForClass(query),
				 null, null, TextResource.localize("Com_Class"));
		 sheetDatas.add(sheetWorkplaceData);
		 sheetDatas.add(sheetClassData);
		 return sheetDatas;
	 }
	 
	@Override
	public String mainSheetName() {
		return TextResource.localize("Com_Company");
	}

	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("稼働日 勤務種類", TextResource.localize("KSM006_15"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("稼働日 就業時間帯", TextResource.localize("KSM006_16"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法内） 勤務種類", TextResource.localize("KSM006_17"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法内） 就業時間帯", TextResource.localize("KSM006_18"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法外） 勤務種類", TextResource.localize("KSM006_19"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法外） 就業時間帯", TextResource.localize("KSM006_20"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> masterDatas = new ArrayList<>();
		
		Map<String, Object> data = new HashMap<>();
		putEmptyToColumCompany(data);
		List<CompanyBasicWorkData> companyBasicWorkDatas = basicWorkRegisterReportRepository.findCompanyBasicWork(companyId);
		companyBasicWorkDatas.stream().sorted(Comparator.comparing(CompanyBasicWorkData::getWorkDayAtr)).forEachOrdered(x->{
			int workDayAtr = x.getWorkDayAtr();
			if (workDayAtr == 0) {
				data.put("稼働日 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
				data.put("稼働日 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
			}
			else if (workDayAtr == 1) {
				data.put("非稼働日（法内） 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
				data.put("非稼働日（法内） 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
			}
			else if (workDayAtr == 2) {
				data.put("非稼働日（法外） 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
				data.put("非稼働日（法外） 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
			}
			
		});
		MasterData rowData = new MasterData(data, null, "");
		alignDataCompany(rowData);
		masterDatas.add(rowData);
		
		return masterDatas;
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumCompany(Map<String, Object> data) {
		data.put("稼働日 勤務種類", "");
		data.put("稼働日 就業時間帯", "");
		data.put("非稼働日（法内） 勤務種類", "");
		data.put("非稼働日（法内） 就業時間帯", "");
		data.put("非稼働日（法外） 勤務種類", "");
		data.put("非稼働日（法外） 就業時間帯", "");
	}
	
	/**
	 * align data company
	 * @param rowData
	 */
	private void alignDataCompany(MasterData rowData) {
		rowData.cellAt("稼働日 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("稼働日 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法内） 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法内） 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法外） 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法外） 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	}
	

	public List<MasterHeaderColumn> getHeaderColumnsWorkspace(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		columns.add(new MasterHeaderColumn("コード", TextResource.localize("KSM006_21"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("名称", TextResource.localize("KSM006_22"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("稼働日 勤務種類", TextResource.localize("KSM006_15"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("稼働日 就業時間帯", TextResource.localize("KSM006_16"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法内） 勤務種類", TextResource.localize("KSM006_17"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法内） 就業時間帯", TextResource.localize("KSM006_18"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法外） 勤務種類", TextResource.localize("KSM006_19"), ColumnTextAlign.LEFT, "", true));
		columns.add(new MasterHeaderColumn("非稼働日（法外） 就業時間帯", TextResource.localize("KSM006_20"), ColumnTextAlign.LEFT, "", true));
		return columns;
	}

	private List<MasterData> getMasterDatasWorkplace(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		Optional<List<WorkplaceBasicWorkData>> workplaceBasicWorkDatas = Optional.ofNullable(basicWorkRegisterReportRepository.findWorkplaceBasicWork(companyId));
		if (workplaceBasicWorkDatas.isPresent()) {
			Map<String, List<WorkplaceBasicWorkData>> mapWorkplaceBasicWorkDatas = 
					workplaceBasicWorkDatas.get().stream().collect(Collectors.groupingBy(WorkplaceBasicWorkData::getWorkplaceId));
			
			WkpConfigInfoFindObject wkpConfigInfoFindObject = new WkpConfigInfoFindObject();
			wkpConfigInfoFindObject.setSystemType(2);
			wkpConfigInfoFindObject.setBaseDate(GeneralDate.ymd(9999, 12, 31));
			wkpConfigInfoFindObject.setRestrictionOfReferenceRange(true);
			List<WorkplaceHierarchyDto> workplaceHierarchyDtos = spreadOutWorkplaceInfos(workplaceConfigInfoFinder.findAllByBaseDate(wkpConfigInfoFindObject));
			
			workplaceHierarchyDtos.stream().collect(Collectors.groupingBy(WorkplaceHierarchyDto::getHierarchyCode))
			.entrySet().stream().sorted(Map.Entry.comparingByKey())
			.forEachOrdered(dto -> {
				List<WorkplaceHierarchyDto> wpHierarchyDtoSameWpIDs = dto.getValue();
				WorkplaceHierarchyDto firstObj = wpHierarchyDtoSameWpIDs.get(0);
				String workPlaceId = firstObj.getWorkplaceId();
				Optional<List<WorkplaceBasicWorkData>> dataByCode = Optional
						.ofNullable(mapWorkplaceBasicWorkDatas.get(workPlaceId));
				if (dataByCode.isPresent()) {
					dataByCode.get().stream().forEach(x -> {
						x.setWorkplaceCode(firstObj.getCode());
						x.setWorkplaceName(firstObj.getName());
						x.setHierarchyCode(firstObj.getHierarchyCode());
					});
					
					datas.add(newWorkplaceMasterData(dataByCode.get()));
				}
				else {
					List<WorkplaceBasicWorkData> workDatas = new ArrayList<>();
					WorkplaceBasicWorkData workData = new WorkplaceBasicWorkData();
					workData.setWorkplaceCode(firstObj.getCode());
					workData.setWorkplaceName(firstObj.getName());
					workDatas.add(workData);
					datas.add(newWorkplaceMasterData(workDatas));
				}
			});
		}
		
		return datas;
	}
	

	/**
	 * 
	 * @param workplaceHierarchyDtos
	 * @return
	 */
	private List<WorkplaceHierarchyDto> spreadOutWorkplaceInfos(List<WorkplaceHierarchyDto> workplaceHierarchyDtos) {
		List<WorkplaceHierarchyDto> listWorkplaceHierarchyDtos = new ArrayList<>();
		workplaceHierarchyDtos.stream().forEach(x -> {
			listWorkplaceHierarchyDtos.add(x);
			if (!CollectionUtil.isEmpty(x.getChilds())) {
				listWorkplaceHierarchyDtos.addAll(spreadOutWorkplaceInfos(x.getChilds()));
			}
			
		});
		return listWorkplaceHierarchyDtos;
	}
	
	private MasterData newWorkplaceMasterData(List<WorkplaceBasicWorkData> workplaceBasicWorkDatas) {
		Map<String, Object> data = new HashMap<>();
		putEmptyToColumsWorkplace(data);
		
		data.put("コード", workplaceBasicWorkDatas.get(0).getWorkplaceCode());
		data.put("名称", workplaceBasicWorkDatas.get(0).getWorkplaceName());
		if (!CollectionUtil.isEmpty(workplaceBasicWorkDatas)) {
			workplaceBasicWorkDatas.stream().forEach(x->{
					Optional<Integer> workDayAtr = Optional.ofNullable(x.getWorkDayAtr());
					if (workDayAtr.isPresent()) {
						if (workDayAtr.get() == 0) {
							data.put("稼働日 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
							data.put("稼働日 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
						} else if (workDayAtr.get() == 1) {
							data.put("非稼働日（法内） 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
							data.put("非稼働日（法内） 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
						} else if (workDayAtr.get() == 2) {
							data.put("非稼働日（法外） 勤務種類", x.getWorkTypeCD() + x.getWorkTypeName());
							data.put("非稼働日（法外） 就業時間帯", x.getWorkTimeCD() + x.getWorkTimeName());
						}
					}
			});
		}
		
		MasterData rowData = new MasterData(data, null, "");
		alignDataWorkplace(rowData);
		return rowData;
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataWorkplace(MasterData rowData) {
		rowData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("稼働日 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("稼働日 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法内） 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法内） 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法外） 勤務種類").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
		rowData.cellAt("非稼働日（法外） 就業時間帯").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
	}

	private void putEmptyToColumsWorkplace(Map<String, Object> data) {
		data.put("コード", "");
		data.put("名称", "");
		data.put("稼働日 勤務種類", "");
		data.put("稼働日 就業時間帯", "");
		data.put("非稼働日（法内） 勤務種類", "");
		data.put("非稼働日（法内） 就業時間帯", "");
		data.put("非稼働日（法外） 勤務種類", "");
		data.put("非稼働日（法外） 就業時間帯", "");
	}
	
	/**
	 * get header for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterHeaderColumn> getHeaderColumnsForClass(MasterListExportQuery query) {
		return getHeaderColumnsWorkspace(query);
	}
	
	
	
	/**
	 * get data for WorkPlace Sheet
	 * @param query
	 * @return
	 */
	private List<MasterData> getMasterDatasForClass(MasterListExportQuery query) {
		String companyId = AppContexts.user().companyId();
		List<MasterData> datas = new ArrayList<>();
		
		List<ClassBasicWorkData> classBasicWorkDatas = basicWorkRegisterReportRepository.findClassBasicWork(companyId);
		
		classBasicWorkDatas.stream().collect(Collectors.groupingBy(ClassBasicWorkData::getClassCode))
		.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x-> {
			List<ClassBasicWorkData> rowData = x.getValue();
			datas.add(newClassMasterData(rowData));
		});
		return datas;
	}
	
	/**
	 * create row data for WorkPlace sheet
	 * @param yearMonth
	 * @param specificdaySetReportDatas
	 * @return
	 */
	private MasterData newClassMasterData(List<ClassBasicWorkData> classCalendarReportDatas) {
		Map<String, Object> data = new HashMap<>();
		putEmptyToColumClass(data);
		
		data.put("コード", classCalendarReportDatas.get(0).getClassCode());
		data.put("名称", classCalendarReportDatas.get(0).getClassName());
		classCalendarReportDatas.stream().forEach(x->{
			Optional<Integer> workDayAtr = x.getWorkDayAtr();
			if (workDayAtr.isPresent()) {
				if (workDayAtr.get() == 0) {
					data.put("稼働日 勤務種類", x.getWorkTypeCD().get() + x.getWorkTypeName().get());
					data.put("稼働日 就業時間帯", x.getWorkTimeCD().get() + x.getWorkTimeName().get());
				} else if (workDayAtr.get() == 1) {
					data.put("非稼働日（法内） 勤務種類", x.getWorkTypeCD().get() + x.getWorkTypeName().get());
					data.put("非稼働日（法内） 就業時間帯", x.getWorkTimeCD().get() + x.getWorkTimeName().get());
				} else if (workDayAtr.get() == 2) {
					data.put("非稼働日（法外） 勤務種類", x.getWorkTypeCD().get() + x.getWorkTypeName().get());
					data.put("非稼働日（法外） 就業時間帯", x.getWorkTimeCD().get() + x.getWorkTimeName().get());
				}
			}
		});
		
		MasterData rowData = new MasterData(data, null, "");
		alignDataClass(rowData);
		return rowData;
	}
	
	/**
	 * 
	 * @param rowData
	 */
	private void alignDataClass(MasterData rowData) {
		alignDataWorkplace(rowData);
	}
	
	/**
	 * 
	 * @param data
	 */
	private void putEmptyToColumClass(Map<String, Object> data) {
		putEmptyToColumsWorkplace(data);
	}
}
