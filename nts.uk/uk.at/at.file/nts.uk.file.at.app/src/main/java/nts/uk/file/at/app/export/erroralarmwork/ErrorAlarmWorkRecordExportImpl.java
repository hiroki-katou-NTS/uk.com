package nts.uk.file.at.app.export.erroralarmwork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly.CompareOperatorText;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttendanceItemsFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.ErAlAtdItemConditionDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.dom.divergencetime.service.attendance.AttendanceNameDivergenceDto;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.primitivevalue.ErrorAlarmWorkRecordCode;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.bs.employee.app.find.jobtitle.dto.JobTitleItemDto;
import nts.uk.ctx.bs.employee.dom.classification.Classification;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationCode;
import nts.uk.ctx.bs.employee.dom.classification.ClassificationRepository;
import nts.uk.ctx.bs.employee.dom.employment.Employment;
import nts.uk.ctx.bs.employee.dom.employment.EmploymentCode;
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
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
//
    @Inject
    private WorkTypeFinder find;
	@Inject
	private AttendanceItemsFinder attendanceItemsFinder;
	
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
	public static String value41= "value41";
	
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
		 //22
		 List<WorkTypeDto> listWorkTypeDto = find.findByCompanyId() ;
         Map<String,WorkTypeDto> mapListWorkTypeDto = listWorkTypeDto.stream()
                       .collect(Collectors.toMap(WorkTypeDto::getWorkTypeCode, Function.identity()));
         //12
         List<Employment> listEmp =  employmentRepository.findAll(companyId);
         Map<EmploymentCode,Employment> mapListEmp = listEmp.stream()
                 .collect(Collectors.toMap(Employment::getEmploymentCode, Function.identity()));
         //14
         List<Classification> listClass = classificationRepository.getAllManagementCategory(companyId);
         Map<ClassificationCode,Classification> mapListClass = listClass.stream()
                 .collect(Collectors.toMap(Classification::getClassificationCode, Function.identity()));
//         //24
         List<WorkTimeSetting> lstWorktimeSetting = workTimeSettingRepository.findByCompanyId(companyId);  
         Map<WorkTimeCode,WorkTimeSetting> mapListWorkTime = lstWorktimeSetting.stream()
                 .collect(Collectors.toMap(WorkTimeSetting::getWorktimeCode, Function.identity()));
         //>30
         List<AttdItemDto> listAttItemDto2 = attendanceItemsFinder.findListByAttendanceAtr(2);
         List<AttdItemDto> listAttItemDto3 = attendanceItemsFinder.findListByAttendanceAtr(3);
         List<AttdItemDto> listAttItemDto5 = attendanceItemsFinder.findListByAttendanceAtr(5);
         List<AttdItemDto> listAttItemDto6 = attendanceItemsFinder.findListByAttendanceAtr(6);
         Map<Integer,AttdItemDto> mapListAttItemDto2 = listAttItemDto2.stream()
                 .collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));
         Map<Integer,AttdItemDto> mapListAttItemDto3 = listAttItemDto3.stream()
        		 .collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));
         Map<Integer,AttdItemDto> mapListAttItemDto5 = listAttItemDto5.stream()
        		 .collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));
         Map<Integer,AttdItemDto> mapListAttItemDto6 = listAttItemDto6.stream()
        		 .collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));
         
		if(CollectionUtil.isEmpty(lstDto)){
			throw new BusinessException("Msg_393");
		}else{
			lstDto.stream().forEach(c->{
				Map<String, Object> data = new HashMap<>();
				putEmptyDataOne(data);
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
				List<String> lstEmpCode =c.getAlCheckTargetCondition().getLstEmployment();
				Collections.sort(lstEmpCode);
				List<String> codeAndNameEmp = new ArrayList<>();
				for (String key : lstEmpCode) {
					EmploymentCode keyEmp = new EmploymentCode(key);
					Employment emp = mapListEmp.get(keyEmp);
					if(emp!=null){
					codeAndNameEmp.add(emp.getEmploymentCode().v()+emp.getEmploymentName().v());
					}
				}
				
				
				String listStringemp = "";
				if(CollectionUtil.isEmpty(lstEmpCode)){
					data.put(value12, listStringemp);
				}else{
					listStringemp = String.join(",", codeAndNameEmp);
					data.put(value12, listStringemp);
				}
				//13 filterByClassification 
				if(c.getAlCheckTargetCondition().isFilterByClassification() == false){
					data.put(value13, "-");
				}else{
					data.put(value13, "○");
				}
				String sValues14 = "";
				List<String> lstClassificationCode= c.getAlCheckTargetCondition().getLstClassification();
				Collections.sort(lstClassificationCode);
				List<String> codeAndNameClassification = new ArrayList<>();
				for (String key : lstClassificationCode) {
					ClassificationCode keyClass = new ClassificationCode(key);
					Classification classification = mapListClass.get(keyClass);
					if(classification!=null)
						codeAndNameClassification.add(classification.getClassificationCode().v()+classification.getClassificationName().v());
				}
				if(!CollectionUtil.isEmpty(codeAndNameClassification)){
					sValues14 = String.join(",", codeAndNameClassification);
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
				//16
				String sValues16 = "";
				List<String> lstJobTitleCode= c.getAlCheckTargetCondition().getLstJobTitle();
				Collections.sort(lstJobTitleCode);
				List<String> codeAndNameJobTitle = new ArrayList<>();
				for (String key : lstJobTitleCode) {
					JobTitleItemDto jobTitleItemDto= mapListJobs.get(key);
					if(jobTitleItemDto!=null)
						codeAndNameJobTitle.add(jobTitleItemDto.getCode()+jobTitleItemDto.getName());
				}
				if(!CollectionUtil.isEmpty(codeAndNameJobTitle)){
					sValues16 = String.join(",", codeAndNameJobTitle);
					data.put(value16, sValues16);
				}
				
				//17
				if(c.getAlCheckTargetCondition().isFilterByBusinessType() == false){
					data.put(value17, "-");
				}else{
					data.put(value17, "○");
				}
				//18
				String sValues18 = "";
				List<String> lstBusinessTypeCode= c.getAlCheckTargetCondition().getLstBusinessType();
				Collections.sort(lstBusinessTypeCode);
				List<String> codeAndBusinessType = new ArrayList<>();
				for (String key : lstBusinessTypeCode) {
					BusinessTypeCode keyBz = new BusinessTypeCode(key);
					BusinessType businessType = maplistBusinessType.get(keyBz);
					if(businessType!=null)
						codeAndBusinessType.add(businessType.getBusinessTypeCode().v()+businessType.getBusinessTypeName());
				}
				if(!CollectionUtil.isEmpty(codeAndBusinessType)){
					sValues18 = String.join(",", codeAndBusinessType);
					data.put(value18, sValues18);
				}
				
				// 19
				if(c.getWorkTypeCondition().getComparePlanAndActual() == 0){
					data.put(value19, TextResource.localize("Enum_FilterByCompare_NotCompare"));
				}else if(c.getWorkTypeCondition().getComparePlanAndActual() == 1){
					data.put(value19, TextResource.localize("Enum_FilterByCompare_Extract_Same"));
					//26
					data.put(value26,"");
				}else{
					data.put(value19, TextResource.localize("Enum_FilterByCompare_Extract_Different"));
				}
				//20
				if(c.getWorkTimeCondition().getComparePlanAndActual() ==0){
					data.put(value20, TextResource.localize("Enum_FilterByCompare_NotCompare"));
				}else if(c.getWorkTimeCondition().getComparePlanAndActual() ==1){
					data.put(value20, TextResource.localize("Enum_FilterByCompare_NotCompare"));
				}else{
					data.put(value20, TextResource.localize("Enum_FilterByCompare_NotCompare"));
				}
				//21 22
				if(c.getWorkTypeCondition().isPlanFilterAtr() ==false){
					data.put(value21, "-");
					data.put(value22, "");
				}else{
					data.put(value21, "○");
					List<String> listWorkTypeCode = c.getWorkTypeCondition().getPlanLstWorkType();
					String sValues22 = "";
					Collections.sort(listWorkTypeCode);
					List<String> codeAndNameWorkType = new ArrayList<>();
					for (String key : listWorkTypeCode) {
						WorkTypeDto workTypeDto = mapListWorkTypeDto.get(key);
						if(workTypeDto!=null){
							codeAndNameWorkType.add(workTypeDto.getWorkTypeCode()+workTypeDto.getName());
						}
					}
					if(!CollectionUtil.isEmpty(codeAndNameWorkType)){
						sValues22 = String.join(",", codeAndNameWorkType);
						data.put(value22, sValues22);
					}
				}
				// 23 24
				if(c.getWorkTimeCondition().isPlanFilterAtr() ==false){
					data.put(value23, "-");
					data.put(value24, "");
				}else{
					data.put(value23, "○");
					List<String> listWorkTimeCode = c.getWorkTimeCondition().getPlanLstWorkTime();
					String sValues24 = "";
					Collections.sort(listWorkTimeCode);
					List<String> codeAndNameWorkTime = new ArrayList<>();
					for (String key : listWorkTimeCode) {
						WorkTimeCode keyWorkTime = new WorkTimeCode(key);
						WorkTimeSetting workTypeDto = mapListWorkTime.get(keyWorkTime);
						if(workTypeDto!=null){
							codeAndNameWorkTime.add(workTypeDto.getWorktimeCode().v()+workTypeDto.getWorkTimeDisplayName().getWorkTimeAbName().v());
						}
					}
					if(!CollectionUtil.isEmpty(codeAndNameWorkTime)){
						sValues24 = String.join(",", codeAndNameWorkTime);
						data.put(value24, sValues24);
					}
//					List<SimpleWorkTimeSettingDto> lst23= lstWorktimeSetting.stream().map(item -> {
//						return SimpleWorkTimeSettingDto.builder().isAbolish(item.getAbolishAtr() == AbolishAtr.ABOLISH)
//								.worktimeCode(item.getWorktimeCode().v())
//								.workTimeName(item.getWorkTimeDisplayName().getWorkTimeName().v()).build();
//						}).collect(Collectors.toList());
//					
//					String s24 = "";
//					if(CollectionUtil.isEmpty(c.getWorkTimeCondition().getPlanLstWorkTime())){
//						data.put(value24, "");
//					}else{
//						for(int i=0;i<c.getWorkTimeCondition().getPlanLstWorkTime().size();i++){
//							for(int j=0;j<lst23.size();j++){
//								if(c.getWorkTimeCondition().getPlanLstWorkTime().get(i).equals(lst23.get(j).worktimeCode)){
//									s24 = lst23.get(j).worktimeCode+","+lst23.get(j).workTimeName;
//									
//								}
//							}
//						}
//						data.put(value24, s24);
//					}
				}
				
				//25
				if(c.getOperatorBetweenPlanActual()==0){
					data.put(value25, "AND");
				}else{
					data.put(value25, "OR");
				}
				//26 27
				 if(c.getWorkTypeCondition().getComparePlanAndActual() == 0 || c.getWorkTypeCondition().getComparePlanAndActual() == 2){
                     if(c.getWorkTypeCondition().isActualFilterAtr()== false){
                            data.put(value26, "-");
                            data.put(value27,"");
                     }else{
                            //listErrorAlarmWorkRecord;
                            data.put(value26, "○");
                            List<String> codeAndNameActualType = new ArrayList<>();
                            List<String> listActualLstWorkTypeCode = c.getWorkTypeCondition().getActualLstWorkType();
                            Collections.sort(listActualLstWorkTypeCode);
                            for (String key : listActualLstWorkTypeCode) {
                            	WorkTypeDto workTypeDto=  mapListWorkTypeDto.get(key);
                            	if(workTypeDto!=null){
                            		codeAndNameActualType.add(workTypeDto.getWorkTypeCode()+workTypeDto.getName());
                            	}
                            }
                            String stringDataColumn27 = String.join(",", codeAndNameActualType);
                            data.put(value27,stringDataColumn27);
                     }
              }
//              
//              //28 29
//              
              if(c.getWorkTimeCondition().getComparePlanAndActual() == 0 || c.getWorkTimeCondition().getComparePlanAndActual() == 2){
              
                     if(c.getWorkTimeCondition().isActualFilterAtr()== false){
                            data.put(value28, "-");
                            data.put(value29,"");
                     }else{
                            data.put(value28, "○");
                            List<String> listActualTimeCode = c.getWorkTimeCondition().getActualLstWorkTime();
                            List<String> codeAndNameActualTime = new ArrayList<>();
                            Collections.sort(listActualTimeCode);
                            for (String key : listActualTimeCode) {
                            	WorkTypeDto workTypeDto=  mapListWorkTypeDto.get(key);
                            	if(workTypeDto!=null){
                            		codeAndNameActualTime.add(workTypeDto.getWorkTypeCode()+workTypeDto.getName());
                            	}
                            }
                            String stringDataColumn29 =String.join(",", codeAndNameActualTime);
                            
                            data.put(value29,stringDataColumn29);
                     }
              }
              //30
              if(c.getOperatorGroup1() ==0){
                     data.put(value30, "AND");
              }else{
                     data.put(value30, "OR");
              }
              // 31 32 33
              List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1 = c.getErAlAtdItemConditionGroup1();
               if(!CollectionUtil.isEmpty(erAlAtdItemConditionGroup1)){
                     for(int i = 0 ; i < erAlAtdItemConditionGroup1.size();i++){
                            ErAlAtdItemConditionDto erro = erAlAtdItemConditionGroup1.get(i);
                            String alarmDescription = "";
                            String targetName = "";
                            String conditonName = "";
                            String startValue = "";
                            String endValue = "";
                            int compare = erro.getCompareOperator();
                            CompareOperatorText compareOpera = convertCompareType(compare);
                            int conditionType = erro.getConditionType();
                            int conditionAtr = erro.getConditionAtr();
                            Map<Integer,AttdItemDto> mapListAttItemDto = new HashMap<>();
                         	/** 回数 
                        	TIMES(0, "Enum_ConditionAtr_Times"),
                        	時間 
                        	TIME_DURATION(1, "Enum_ConditionAtr_TimeDuration"),
                        	 時刻 
                        	TIME_WITH_DAY(2, "Enum_ConditionAtr_TimeWithDay"),
                        	金額 
                            AMOUNT_VALUE(3, "Enum_ConditionAtr_AmountValue"),
                        	日数
                        	DAYS(4, "日数");*/
                            ////////////////////////
                        	/** 固定値
                        	FIXED_VALUE(0, "Enum_ConditionType_FixedValue"),
                        	勤怠項目 
                        	ATTENDANCE_ITEM(1, "Enum_ConditionType_AttendanceItem"),
                        	入力チェック 
                        	INPUT_CHECK(2, "Enum_ConditionType_InputCheck");*/
                            
                            switch (conditionAtr) {
                            case 0:
                            case 1:
                            case 3:
								if(conditionAtr==0){
									 mapListAttItemDto = mapListAttItemDto2;
								}
								if(conditionAtr==1){
									 mapListAttItemDto = mapListAttItemDto5;
								}
								if(conditionAtr==3){
									 mapListAttItemDto = mapListAttItemDto3;
								}
                            	List<Integer> listKeyAdd = erro.getCountableAddAtdItems();
                            	List<Integer> listKeySub = erro.getCountableSubAtdItems();
                                List<String> listStringAttNameAdd = new ArrayList<>();
                                List<String> listStringAttNameSub = new ArrayList<>();
                                //get list att add
                                for (Integer attId : listKeyAdd) {
                                	AttdItemDto attItem = mapListAttItemDto.get(attId);
                                	if(attItem!=null){
                                		listStringAttNameAdd.add(attItem.getAttendanceItemName());
                                	}
                                }
                              //get list att sub
                                for (Integer attId : listKeySub) {
                                	AttdItemDto attItem = mapListAttItemDto.get(attId);
                                	if(attItem!=null){
                                		listStringAttNameSub.add(attItem.getAttendanceItemName());
                                	}
                                }
                                Collections.sort(listStringAttNameAdd);
                                Collections.sort(listStringAttNameSub);
                                targetName = String.join(",", listStringAttNameAdd) + String.join(",", listStringAttNameSub);
								break;
							case 2:
								AttdItemDto attItem = mapListAttItemDto6.get(erro.getUncountableAtdItem());
										if(attItem!=null){
											targetName =attItem.getAttendanceItemName();
										}
								break;
							default:
								break;
							}
                          
                            switch (conditionType) {
                            case 0:
                                   startValue = erro.getCompareStartValue().toString();
                                   if (compare <= 5) {
                                          alarmDescription = targetName+compareOpera.getCompareLeft()+startValue;
                                   } else {
	                                    startValue =conditionAtr==0?timeToString(erro.getCompareStartValue().intValueExact()):erro.getCompareStartValue().toString();
                                          if (compare > 5 && compare <= 7) {
                                                 alarmDescription = startValue + compareOpera.getCompareLeft()+targetName+compareOpera.getCompareright()+endValue;
                                          } else {
                                                 alarmDescription = targetName+compareOpera.getCompareLeft()+startValue+","+endValue+compareOpera.getCompareright()+targetName;
                                          }
                                   }
                                   break;
                            case 1:
                            	 String singleAttName = "";
                            	if(conditionAtr==3){
                            		AttdItemDto at = mapListAttItemDto.get(erro.getSingleAtdItem());
                            		if(at!=null){
                            		 singleAttName= at.getAttendanceItemName();
                            		}
                            	}else {
                            		Map<Integer,AttdItemDto> mapAttDto = mapListAttItemDto3;
                            		AttdItemDto attItem = mapAttDto.get(erro.getSingleAtdItem());
                            		if(attItem!=null){
                            			singleAttName = attItem.getAttendanceItemName();
                            		}
								}
                                   alarmDescription = targetName + compareOpera.getCompareLeft()+singleAttName;
                                   break;
                            case 2:
                                   //Enum : InputCheckCondition
                                   /** 入力されていない */
                                   //INPUT_NOT_DONE(0, "Enum_ConditionType_FixedValue"),
                                   /** 入力されている */
                                   //INPUT_DONE(1, "Enum_ConditionType_AttendanceItem");
                                   conditonName = erro.getInputCheckCondition()==0?TextResource.localize("Enum_ConditionType_FixedValue"):
                                          TextResource.localize("Enum_ConditionType_AttendanceItem");
                                   alarmDescription = targetName+conditonName;
                                   break;
                            default:
                                   break;
                            }
                            if(i==0){
                                   data.put(value31, alarmDescription);
                            }
                            if(i==1){
                                   data.put(value32,alarmDescription);
                            }
                            if(i==2){
                                   data.put(value33,alarmDescription);
                            }
                     }
              }
              List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2 = c.getErAlAtdItemConditionGroup2();
              //34,35,39
              if(c.isGroup2UseAtr() ==false){
                     data.put(value34, "-");
                     data.put(value35, "");
                     data.put(value39, "");
              }else{
                     data.put(value34, "○");
                     if(c.getOperatorGroup2()== 0){
                            data.put(value35, "AND");
                     }else{
                            data.put(value35, "OR");
                     }
                     //36 37 38
                     if(!CollectionUtil.isEmpty(erAlAtdItemConditionGroup2)){
                         for(int i = 0 ; i < erAlAtdItemConditionGroup2.size();i++){
                                ErAlAtdItemConditionDto erro = erAlAtdItemConditionGroup2.get(i);
                                String alarmDescription = "";
                                String targetName = "";
                                String conditonName = "";
                                String startValue = "";
                                String endValue = "";
                                int compare = erro.getCompareOperator();
                                CompareOperatorText compareOpera = convertCompareType(compare);
                                int conditionType = erro.getConditionType();
                                int conditionAtr = erro.getConditionAtr();
                                Map<Integer,AttdItemDto> mapListAttItemDto = new HashMap<>();
                             	/** 回数 
	                        	TIMES(0, "Enum_ConditionAtr_Times"),
	                        	時間 
	                        	TIME_DURATION(1, "Enum_ConditionAtr_TimeDuration"),
	                        	 時刻 
	                        	TIME_WITH_DAY(2, "Enum_ConditionAtr_TimeWithDay"),
	                        	金額 
	                            AMOUNT_VALUE(3, "Enum_ConditionAtr_AmountValue"),
	                        	日数
	                        	DAYS(4, "日数");*/
                                ////////////////////////
                            	/** 固定値
                            	FIXED_VALUE(0, "Enum_ConditionType_FixedValue"),
                            	勤怠項目 
                            	ATTENDANCE_ITEM(1, "Enum_ConditionType_AttendanceItem"),
                            	入力チェック 
                            	INPUT_CHECK(2, "Enum_ConditionType_InputCheck");*/
                                switch (conditionAtr) {
                                case 0:
                                case 1:
                                case 3:
    								if(conditionAtr==0){
    									 mapListAttItemDto = mapListAttItemDto2;
    								}
    								if(conditionAtr==1){
    									 mapListAttItemDto = mapListAttItemDto5;
    								}
    								if(conditionAtr==3){
    									 mapListAttItemDto = mapListAttItemDto3;
    								}
                                	List<Integer> listKeyAdd = erro.getCountableAddAtdItems();
                                	List<Integer> listKeySub = erro.getCountableSubAtdItems();
                                    List<String> listStringAttNameAdd = new ArrayList<>();
                                    List<String> listStringAttNameSub = new ArrayList<>();
                                    //get list att add
                                    for (Integer attId : listKeyAdd) {
                                    	AttdItemDto attItem = mapListAttItemDto.get(attId);
                                    	if(attItem!=null){
                                    		listStringAttNameAdd.add(attItem.getAttendanceItemName());
                                    	}
                                    }
                                  //get list att sub
                                    for (Integer attId : listKeySub) {
                                    	AttdItemDto attItem = mapListAttItemDto.get(attId);
                                    	if(attItem!=null){
                                    		listStringAttNameSub.add(attItem.getAttendanceItemName());
                                    	}
                                    }
                                    Collections.sort(listStringAttNameAdd);
                                    Collections.sort(listStringAttNameSub);
                                    targetName = String.join(",", listStringAttNameAdd) + String.join(",", listStringAttNameSub);
    								break;
    							case 2:
    								AttdItemDto attItem = mapListAttItemDto6.get(erro.getUncountableAtdItem());
    										if(attItem!=null){
    											targetName =attItem.getAttendanceItemName();
    										}
    								break;
    							default:
    								break;
    							}
                              
                                switch (conditionType) {
                                case 0:
                                	startValue =conditionAtr==0?timeToString(erro.getCompareStartValue().intValueExact()):erro.getCompareStartValue().toString();
                                       if (compare <= 5) {
                                              alarmDescription = targetName+compareOpera.getCompareLeft()+startValue;
                                       } else {
                                              if (compare > 5 && compare <= 7) {
                                                     alarmDescription = startValue + compareOpera.getCompareLeft()+targetName+compareOpera.getCompareright()+endValue;
                                              } else {
                                                     alarmDescription = targetName+compareOpera.getCompareLeft()+startValue+","+endValue+compareOpera.getCompareright()+targetName;
                                              }
                                       }
                                       break;
                                case 1:
                                	 String singleAttName = "";
                                	if(conditionAtr==3){
                                		 singleAttName= mapListAttItemDto.get(erro.getSingleAtdItem()).getAttendanceItemName();
                                	}else {
                                		Map<Integer,AttdItemDto> mapAttDto = mapListAttItemDto3;
                                		AttdItemDto attItem = mapAttDto.get(erro.getSingleAtdItem());
                                		if(attItem!=null){
                                			singleAttName = attItem.getAttendanceItemName();
                                		}
    								}
                                       alarmDescription = targetName + compareOpera.getCompareLeft()+singleAttName;
                                       break;
                                case 2:
                                       //Enum : InputCheckCondition
                                       /** 入力されていない */
                                       //INPUT_NOT_DONE(0, "Enum_ConditionType_FixedValue"),
                                       /** 入力されている */
                                       //INPUT_DONE(1, "Enum_ConditionType_AttendanceItem");
                                       conditonName = erro.getInputCheckCondition()==0?TextResource.localize("Enum_ConditionType_FixedValue"):
                                              TextResource.localize("Enum_ConditionType_AttendanceItem");
                                       alarmDescription = targetName+conditonName;
                                       break;
                                default:
                                       break;
                                }
                                if(i==0){
                                       data.put(value36, alarmDescription);
                                }
                                if(i==1){
                                       data.put(value37,alarmDescription);
                                }
                                if(i==2){
                                       data.put(value38,alarmDescription);
                                }
                         }
                     }
                     if(c.getOperatorBetweenGroups() == 0){
                            data.put(value39, "AND");
                     }else{
                            data.put(value39, "OR");
                     }
              }
				
				// 40 
				
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
				masterData.cellAt(value19).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value20).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value21).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value22).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value23).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value24).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value25).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value26).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value27).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value28).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value29).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value30).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value31).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value32).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value33).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value34).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value35).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value36).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value37).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value38).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value39).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
				masterData.cellAt(value40).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
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
   columns.add(new MasterHeaderColumn(value12, "value12", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value13, "value13", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value14, "value14", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value15, "value15", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value16, "value16", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value17, "value17", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value18, "value18", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value19, "value19", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value20, "value20", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value21, "value21", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value22, "value22", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value23, "value23", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value24, "value24", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value25, "value25", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value26, "value26", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value27, "value27", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value28, "value28", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value29, "value29", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value30, "value30", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value31, "value31", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value32, "value32", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value33, "value33", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value34, "value34", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value35, "value35", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value36, "value36", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value37, "value37", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value38, "value38", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value39, "value39", ColumnTextAlign.LEFT,
                "", true));
   columns.add(new MasterHeaderColumn(value40, "value40", ColumnTextAlign.LEFT,
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
	private String timeToString(int value) {
		if (value % 60 < 10) {
			return String.valueOf(value / 60) + ":0" + String.valueOf(value % 60);
		}
		return String.valueOf(value / 60) + ":" + String.valueOf(value % 60);
	}

	private CompareOperatorText convertCompareType(int compareOperator) {
		CompareOperatorText compare = new CompareOperatorText();
		switch (compareOperator) {
		case 0:/* 等しくない（≠） */
			compare.setCompareLeft("≠");
			compare.setCompareright("");
			break;
		case 1:/* 等しい（＝） */
			compare.setCompareLeft("＝");
			compare.setCompareright("");
			break;
		case 2:/* 以下（≦） */
			compare.setCompareLeft("≦");
			compare.setCompareright("");
			break;
		case 3:/* 以上（≧） */
			compare.setCompareLeft("≧");
			compare.setCompareright("");
			break;
		case 4:/* より小さい（＜） */
			compare.setCompareLeft("＜");
			compare.setCompareright("");
			break;
		case 5:/* より大きい（＞） */
			compare.setCompareLeft("＞");
			compare.setCompareright("");
			break;
		case 6:/* 範囲の間（境界値を含まない）（＜＞） */
			compare.setCompareLeft("＜");
			compare.setCompareright("＜");
			break;
		case 7:/* 範囲の間（境界値を含む）（≦≧） */
			compare.setCompareLeft("≦");
			compare.setCompareright("≦");
			break;
		case 8:/* 範囲の外（境界値を含まない）（＞＜） */
			compare.setCompareLeft("＜");
			compare.setCompareright("＜");
			break;

		default:/* 範囲の外（境界値を含む）（≧≦） */
			compare.setCompareLeft("≦");
			compare.setCompareright("≦");
			break;
		}
		return compare;
	}

}