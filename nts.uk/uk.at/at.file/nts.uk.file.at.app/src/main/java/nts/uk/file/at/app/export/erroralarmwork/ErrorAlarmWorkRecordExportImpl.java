package nts.uk.file.at.app.export.erroralarmwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.JobTitleItemDto;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;
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
 * @author minhvv
 *
 */

@Stateless
@DomainID(value = "ErrorAlarmWorkRecord")
public class ErrorAlarmWorkRecordExportImpl implements MasterListData{
	
	@Inject
	private ErrorAlarmWorkRecordRepository repository;
	
	@Inject 
	private CompanyDailyItemService companyDailyItemService;
	
	@Inject
	private EmploymentRepository employmentRepository;
	
	@Inject
	private ClassificationRepository classificationRepository;
	
	@Inject
	private JobTitleInfoRepository jobTitleInfoRepository;
	
	@Inject
	private BusinessTypesRepository businessTypesRepository;
	
	public static String value1= "value1";
	public static String value2= "value2";
	public static String value3= "value3";
	public static String value4= "value4";
	public static String value5= "value5";
	public static String value6= "value6";
	public static String value7= "value7";
	public static String value8= "value8";
	public static String value9= "value9";
	public static String value10= "value10";
	public static String value11= "value11";
	public static String value12= "value12";
	public static String value13= "value13";
	public static String value14= "value14";
	public static String value15= "value15";
	public static String value16= "value16";
	public static String value17= "value17";
	public static String value18= "value18";
	public static String value19= "value19";
	public static String value20= "value20";
	public static String value21= "value21";
	public static String value22= "value22";
	public static String value23= "value23";
	public static String value24= "value24";
	public static String value25= "value25";
	public static String value26= "value26";
	public static String value27= "value27";
	public static String value28= "value28";
	public static String value29= "value29";
	public static String value30= "value30";
	public static String value31= "value31";
	public static String value32= "value32";
	public static String value33= "value33";
	public static String value34= "value34";
	public static String value35= "value35";
	public static String value36= "value36";
	public static String value37= "value37";
	public static String value38= "value38";
	public static String value39= "value39";
	public static String value40= "value40";
	
	
	
	
	@Override
	public List<MasterData> getMasterDatas(MasterListExportQuery query) {
		
		LoginUserContext loginUserContext = AppContexts.user();
		
		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
		List<ErrorAlarmWorkRecord> listErrorAlarmWorkRecord = repository.getListErrorAlarmWorkRecord(companyId, 0);// fixedAtr":1
		
		List<ErrorAlarmWorkRecordDto> lstDto = listErrorAlarmWorkRecord.stream()
				.map(eral -> ErrorAlarmWorkRecordDto.fromDomain(eral, eral.getErrorAlarmCondition()))
				.sorted(Comparator.comparing(ErrorAlarmWorkRecordDto::getCode))
				.collect(Collectors.toList());

		//16
		List<JobTitleInfo> listJobs = jobTitleInfoRepository.findAll(companyId, GeneralDate.today());
		List<JobTitleItemDto> listJobDtos = listJobs.stream()
				.map(job -> JobTitleItemDto.builder()
						.id(job.getJobTitleId())
						.code(job.getJobTitleCode().v())
						.name(job.getJobTitleName().v())
						.build())
				.sorted((job1, job2) -> job1.getCode().compareTo(job2.getCode()))
				.collect(Collectors.toList());
		
		Map<String, JobTitleItemDto> mapListJobs = listJobDtos.stream()
				.collect(Collectors.toMap(JobTitleItemDto::getId, Function.identity()));

		//18
		List<BusinessType> listBusinessType = businessTypesRepository.findAll(companyId);

		Map<BusinessTypeCode , BusinessType> maplistBusinessType = listBusinessType.stream()
				.collect(Collectors.toMap(BusinessType::getBusinessTypeCode, Function.identity()));
				
		
		if(CollectionUtil.isEmpty(lstDto)){
			throw new BusinessException("Msg_393");
		}else{
			lstDto.stream().forEach(c->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataOne(data);
				
				String code = c.getCode().substring(0,3);
				data.put(value1, c.getCode());
				data.put(value2, c.getName());
				
				if(c.getUseAtr()==1){
					data.put(value3, "使用する");
				}else{
					data.put(value3, "使用しない");
				}
				
				if(c.getTypeAtr() == 0){
					data.put(value4, "エラー");
				}else if(c.getTypeAtr() == 1){
					data.put(value4, "アラーム");
				}else{
					data.put(value4, "その他");
					data.put(value5, "");
					data.put(value6, "");
					data.put(value7, "");
					data.put(value8, "");
					data.put(value9, "");
					data.put(value10, "");
				}
				
				if(c.getRemarkCancelErrorInput() == 1){
					data.put(value5, "解除する");
					List<Integer> lista = new ArrayList<>();
					lista.add(c.getRemarkColumnNo());
					
					List<AttendanceNameDivergenceDto> dataA = companyDailyItemService
							.getDailyItems(companyId, Optional.empty(), lista, Collections.emptyList())
							.stream()
							.map(x -> {
								AttendanceNameDivergenceDto dto = new AttendanceNameDivergenceDto(x.getAttendanceItemId(),
										x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber());
								return dto;
							}).collect(Collectors.toList());
					if(CollectionUtil.isEmpty(dataA)){
						data.put(value6, "");
					}else{
						dataA.stream().forEach(a ->{
							data.put(value6, a.getAttendanceItemName());
						});
					}
					
				}else{
					data.put(value5, "解除しない");
					data.put(value6, "");
				}

				List<Integer> listdaily = new ArrayList<>();
				listdaily.add(c.getErrorDisplayItem());
				
				//7
				List<AttendanceNameDivergenceDto> dataDailyAttendanceItemIds = companyDailyItemService
						.getDailyItems(companyId, Optional.empty(), listdaily, Collections.emptyList())
						.stream()
						.map(x -> {
							AttendanceNameDivergenceDto dto = new AttendanceNameDivergenceDto(x.getAttendanceItemId(),
									x.getAttendanceItemName(), x.getAttendanceItemDisplayNumber());
							return dto;
						}).collect(Collectors.toList());
				
				if(CollectionUtil.isEmpty(dataDailyAttendanceItemIds)){
					data.put(value7, "");
				}else{
					dataDailyAttendanceItemIds.stream().forEach(b->{
						data.put(value7, b.getAttendanceItemName());
					});
				}
				data.put(value8, c.getMessageColor());
				data.put(value9, c.getDisplayMessage());
				
				if(c.getBoldAtr() ==0){
					data.put(value10, "-");
				}else{
					data.put(value10, "○");
				}
				//11
				if(c.getAlCheckTargetCondition().isFilterByEmployment() == false){
					data.put(value11, "-");
				}else{
					data.put(value11, "○");
				}
				//12
				List<String> lst1 = new ArrayList<>();
				
				for(int i=0;i<c.getAlCheckTargetCondition().getLstEmployment().size();i++){
					lst1.add(c.getAlCheckTargetCondition().getLstEmployment().get(i));
				}
				
				String employmentName = "";
				if(CollectionUtil.isEmpty(lst1)){
					data.put(value12, employmentName);
				}else{
					for(int i=0; i<lst1.size();i++){
						Optional<Employment> employment = this.employmentRepository.findEmployment(companyId, lst1.get(i));
						
						if( i== 0){
							employmentName  = employment.get().getEmploymentName().v();
						}else{
							employmentName = employmentName+", "+employment.get().getEmploymentName().v();
						}
					}
					
					data.put(value12, employmentName);
				}
				//13 filterByClassification 
				if(c.getAlCheckTargetCondition().isFilterByClassification() == false){
					data.put(value13, "-");
				}else{
					data.put(value13, "○");
				}
				String sValues14 = "";
				
				List<String> lst2 = new ArrayList<>();
				
				for(int i=0;i<c.getAlCheckTargetCondition().getLstClassification().size();i++){
					lst2.add(c.getAlCheckTargetCondition().getLstClassification().get(i));
				}
				
				if(CollectionUtil.isEmpty(lst2)){
					data.put(value14, "");
				}else{
					for(int i=0;i<lst2.size();i++){
						Optional<Classification> optClassification = classificationRepository.findClassification(companyId, lst2.get(i));
						
						if( i== 0){
							sValues14  = optClassification.get().getClassificationName().v();
						}else{
							sValues14 = sValues14+", "+optClassification.get().getClassificationName().v();
						}
					}
					data.put(value14, sValues14);
				}
				
				//15 filterByJobTitle 
				if(c.getAlCheckTargetCondition().isFilterByJobTitle() == false){
					data.put(value15, "-");
				}else{
					data.put(value15, "○");
				}
				
				List<String> lst3 = new ArrayList<>();
				
				for(int i=0;i<c.getAlCheckTargetCondition().getLstJobTitle().size();i++){
					lst3.add(c.getAlCheckTargetCondition().getLstJobTitle().get(i));
				}
				
				String sValue3 = "";
				if(CollectionUtil.isEmpty(lst3)) {
					data.put(value16, "");
				}else{
					sValue3 = lst3.stream()
			        .map(x -> {
						Optional<JobTitleItemDto> dto = Optional.ofNullable(mapListJobs.get(x));
						if (dto.isPresent()) {
							return dto.get().getName();
						}
						return "";
					})
			        .collect( Collectors.joining( "," ));
					data.put(value16, sValue3);
				}
				
				//17
				if(c.getAlCheckTargetCondition().isFilterByBusinessType() == false){
					data.put(value17, "-");
				}else{
					data.put(value17, "○");
				}
				//
				List<String> lst4 = new ArrayList<>();
				
				for(int i=0;i<c.getAlCheckTargetCondition().getLstBusinessType().size();i++){
					lst4.add(c.getAlCheckTargetCondition().getLstBusinessType().get(i));
				}
				String sValue4 = "";
				String sValue5 = "";
				if(CollectionUtil.isEmpty(lst4)){
					data.put(value18, "");
				}else{
					for(int i=0;i<lst4.size();i++){
						Optional<BusinessType>  optiBusinessType = businessTypesRepository.findByCode(companyId,lst4.get(i));

						if( i== 0){
							sValues14  = optiBusinessType.get().getBusinessTypeName().v();
						}else{
							sValues14 = sValues14+", "+optiBusinessType.get().getBusinessTypeName().v();
						}
						
					}
					data.put(value18, sValue4);
				}
				

				MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt(value1).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value2).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value3).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value4).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value5).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value6).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value7).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value8).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT).backgroundColor(c.getMessageColor()));
				masterData.cellAt(value9).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value10).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value11).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value12).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value13).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value14).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value15).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value16).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value17).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value18).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				datas.add(masterData);
			});
		}
		
		
		return datas;
	}
	private void putEmptyDataOne(Map<String, Object> data) {
		data.put(value1, "");
		data.put(value2, "");
		data.put(value3, "");
		data.put(value4, "");
		data.put(value5, "");
		data.put(value6, "");
		data.put(value7, "");
		data.put(value8, "");
		data.put(value9, "");
		data.put(value10, "");
		data.put(value11, "");
		data.put(value12, "");
		data.put(value13, "");
		data.put(value14, "");
		data.put(value15, "");
		data.put(value16, "");
		data.put(value17, "");
		data.put(value18, "");
		data.put(value19, "");
		data.put(value20, "");
		data.put(value21, "");
		data.put(value22, "");
		data.put(value23, "");
		data.put(value24, "");
		data.put(value25, "");
		data.put(value26, "");
		data.put(value27, "");
		data.put(value28, "");
		data.put(value29, "");
		data.put(value30, "");
		data.put(value31, "");
		data.put(value32, "");
		data.put(value33, "");
		data.put(value34, "");
		data.put(value35, "");
		data.put(value36, "");
		data.put(value37, "");
		data.put(value38, "");
		data.put(value39, "");
		data.put(value40, "");

		
		
	}
	@Override
	public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn(value1, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value2, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value3, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value4, "設定 区分", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value5, "設定 備考入力でエラーを解除する", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value6, "設定 備考NO", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value7, "設定 色表示対象項目", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value8, "設定 メッセージ色", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value9, "設定 表示するメッセージ", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value10, "設定 メッセージを太字にする", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value11, "チェック対象範囲設定 雇用", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value12, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value13, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value14, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value15, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value16, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value17, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value18, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value19, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value20, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value21, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value22, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value23, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value24, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value25, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value26, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value27, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value28, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value29, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value30, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value31, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value32, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value33, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value34, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value35, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value36, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value37, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value38, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value39, "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn(value40, "コード", ColumnTextAlign.LEFT,
				"", true));
		
		return columns;
	}
	
	
	@Override
	public String mainSheetName() {
		return TextResource.localize("KMK009_25");
	}
	
	@Override
	public List<SheetData> extraSheets(MasterListExportQuery query){
		List<SheetData> listSheetData = new ArrayList<>();
		SheetData sheetDataTwo = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, TextResource.localize("CMM013_54"));
		listSheetData.add(sheetDataTwo);
		
		SheetData sheetDataTwo1 = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "2");
		listSheetData.add(sheetDataTwo1);
		
		SheetData sheetDataTwo2 = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "3");
		listSheetData.add(sheetDataTwo2);
		SheetData sheetDataTwo3 = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "4");
		listSheetData.add(sheetDataTwo3);
		SheetData sheetDataTwo4 = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "5");
		listSheetData.add(sheetDataTwo4);
		SheetData sheetDataTwo5 = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, "6");
		listSheetData.add(sheetDataTwo5);
		
		
		
		
		return listSheetData;
	}
	private List<MasterHeaderColumn> getHeaderColumnTwos(MasterListExportQuery query) {
		List<MasterHeaderColumn> columns = new ArrayList<>();
		
		columns.add(new MasterHeaderColumn("コード", "コード", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("名称", "名称", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("使用区分", "使用区分", ColumnTextAlign.LEFT,
				"", true));
		columns.add(new MasterHeaderColumn("申請", "申請", ColumnTextAlign.LEFT,
				"", true));
		
		return columns;
	}
	private List<MasterData> getMasterDataTwo(MasterListExportQuery query) {
		
		LoginUserContext loginUserContext = AppContexts.user();

		String companyId = loginUserContext.companyId();
		
		List<MasterData> datas = new ArrayList<>();
		
	    List<ErrorAlarmWorkRecord> listErrorAlarmWorkRecord = repository.getListErrorAlarmWorkRecord(companyId, 1);// fixedAtr":1
	    
	    listErrorAlarmWorkRecord = listErrorAlarmWorkRecord.stream().filter(eral -> eral.getCode().v().startsWith("S"))
	    		.sorted(Comparator.comparing(ErrorAlarmWorkRecord::getCode, Comparator.nullsLast(ErrorAlarmWorkRecordCode::compareTo)))
				.collect(Collectors.toList());
	    
	    
	    if(CollectionUtil.isEmpty(listErrorAlarmWorkRecord)){
	    	throw new BusinessException("Msg_393");
	    }else{
	    	listErrorAlarmWorkRecord.stream().forEach(c->{
	    		String lstApp = "";
	    		Map<String, Object> data = new HashMap<>();
				putEmptyDataTwo(data);
				
				data.put("コード", c.getCode());
				data.put("名称", c.getName());
				if(c.getUseAtr()){
					data.put("使用区分", "使用しない");
				}else{
					data.put("使用区分", "使用する");
				}
				List<Integer> listApplication  = c.getLstApplication();
				listApplication = listApplication.stream().sorted()
						.collect(Collectors.toList());
				if(CollectionUtil.isEmpty(listApplication)){
					data.put("申請", "");
				}else{

					for(int i=0; i<listApplication.size();i++){
						if(i==0){
							if(listApplication.get(i).intValue()==0){
								lstApp = listApplication.get(i).intValue()+"残業申請（早出）";
							}else if(listApplication.get(i).intValue()==1){
								lstApp = listApplication.get(i).intValue()+"残業申請（通常）";
							}else if(listApplication.get(i).intValue()==2){
								lstApp = listApplication.get(i).intValue()+"残業申請（早出・通常）";
							}else if(listApplication.get(i).intValue()==3){
								lstApp = listApplication.get(i).intValue()+"休暇申請";
							}else if(listApplication.get(i).intValue()==4){
								lstApp = listApplication.get(i).intValue()+"勤務変更申請";
							}else if(listApplication.get(i).intValue()==6){
								lstApp = listApplication.get(i).intValue()+"直行直帰申請";
							}else if(listApplication.get(i).intValue()==7){
								lstApp = listApplication.get(i).intValue()+"休出時間申請";
							}else if(listApplication.get(i).intValue()==15){
								lstApp = listApplication.get(i).intValue()+"振休振出申請";
							}
						}else{
							if(listApplication.get(i).intValue()==0){
								lstApp =lstApp+ " ,"+listApplication.get(i).intValue()+"残業申請（早出）";
							}else if(listApplication.get(i).intValue()==1){
								lstApp =lstApp+ " ,"+listApplication.get(i).intValue()+"残業申請（通常）";
							}else if(listApplication.get(i).intValue()==2){
								lstApp =lstApp+" ,"+ listApplication.get(i).intValue()+"残業申請（早出・通常）";
							}else if(listApplication.get(i).intValue()==3){
								lstApp =lstApp+" ,"+ listApplication.get(i).intValue()+"休暇申請";
							}else if(listApplication.get(i).intValue()==4){
								lstApp =lstApp+ " ,"+listApplication.get(i).intValue()+"勤務変更申請";
							}else if(listApplication.get(i).intValue()==6){
								lstApp = lstApp+" ,"+listApplication.get(i).intValue()+"直行直帰申請";
							}else if(listApplication.get(i).intValue()==7){
								lstApp =lstApp+ " ,"+listApplication.get(i).intValue()+"休出時間申請";
							}else if(listApplication.get(i).intValue()==15){
								lstApp =lstApp+ " ,"+listApplication.get(i).intValue()+"振休振出申請";
							}
						}
						
					}
					
					data.put("申請", lstApp);
				}
				

	    		
	    		
	    		
	    		MasterData masterData = new MasterData(data, null, "");
				masterData.cellAt("コード").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("名称").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("使用区分").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt("申請").setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				datas.add(masterData);
	    	});
	    	
	    }
	    
		
		return datas;
	}
	private void putEmptyDataTwo(Map<String, Object> data){
		data.put("コード", "");
		data.put("名称", "");
		data.put("使用区分", "");
		data.put("申請", "");
		
	}

}