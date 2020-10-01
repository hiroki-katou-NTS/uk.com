package nts.uk.file.at.app.export.attendanceitemprepare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.aggregationprocess.daily.dailyaggregationprocess.LogicalOperator;
import nts.uk.ctx.at.function.dom.alarm.alarmlist.monthly.CompareOperatorText;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.AttendanceItemsFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.AttdItemDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.ErrorAlarmWorkRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.erroralarm.condition.ErAlAtdItemConditionDto;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.BusinessType;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.primitivevalue.BusinessTypeCode;
import nts.uk.ctx.at.record.dom.dailyperformanceformat.repository.BusinessTypesRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecord;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.ErrorAlarmWorkRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.enums.ErrorAlarmClassification;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeFinder;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.erroralarm.ErrorAlarmWorkRecordCode;
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
import nts.uk.shr.infra.file.report.masterlist.data.ColumnTextAlign;
import nts.uk.shr.infra.file.report.masterlist.data.MasterCellStyle;
import nts.uk.shr.infra.file.report.masterlist.data.MasterData;
import nts.uk.shr.infra.file.report.masterlist.data.MasterHeaderColumn;
import nts.uk.shr.infra.file.report.masterlist.data.SheetData;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListExportQuery;
import nts.uk.shr.infra.file.report.masterlist.webservice.MasterListMode;
/**
 *
 * @author hoidd
 *
 */
@Stateless
public class ErrorAlarmWorkRecordExportImpl {
       
       @Inject
       private ErrorAlarmWorkRecordRepository repository;
       
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
      
       public List<MasterData> getMasterDatas(MasterListExportQuery query) {
    	   List<String> header = new ArrayList<>();
           String headerColumn = "header";
           for(int i = 0 ; i <= 40; i++){
        	   header.add(headerColumn+i);  
           }
          LoginUserContext loginUserContext = AppContexts.user();
          
          String companyId = loginUserContext.companyId();
          
          List<MasterData> datas = new ArrayList<>();
          List<ErrorAlarmWorkRecord> listErrorAlarmWorkRecord = repository.getListErrorAlarmWorkRecord(companyId, 0)
        		  .stream()
                  .collect(Collectors.toList());// fixedAtr":1

          //7
//        List<Integer> lista = new ArrayList<>(Arrays.asList(833, 834, 835, 836, 837));
         List<AttdItemDto> listDivergenName = attendanceItemsFinder.findAll();
          //16
         GeneralDate lastDate =  GeneralDate.ymd(9999, 12, 31);
         List<JobTitleInfo> listJobs = jobTitleInfoRepository.findAll(companyId, lastDate);
          //18
	     List<BusinessType> listBusinessType = businessTypesRepository.findAll(companyId);
         //22
         List<WorkTypeDto> listWorkTypeDto = find.findByCompanyId() ;
         //12
         List<Employment> listEmp =  employmentRepository.findAll(companyId);
         //14
         List<Classification> listClass = classificationRepository.getAllManagementCategory(companyId);
//         //24
         List<WorkTimeSetting> lstWorktimeSetting = workTimeSettingRepository.findByCompanyId(companyId); 
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
         Map<WorkTimeCode,WorkTimeSetting> mapListWorkTime = lstWorktimeSetting.stream()
                 .collect(Collectors.toMap(WorkTimeSetting::getWorktimeCode, Function.identity()));
         Map<ClassificationCode,Classification> mapListClass = listClass.stream()
                 .collect(Collectors.toMap(Classification::getClassificationCode, Function.identity()));
         Map<EmploymentCode,Employment> mapListEmp = listEmp.stream()
                 .collect(Collectors.toMap(Employment::getEmploymentCode, Function.identity()));
         Map<String,WorkTypeDto> mapListWorkTypeDto = listWorkTypeDto.stream()
                 .collect(Collectors.toMap(WorkTypeDto::getWorkTypeCode, Function.identity()));
	     Map<BusinessTypeCode , BusinessType> maplistBusinessType = listBusinessType.stream()
                 .collect(Collectors.toMap(BusinessType::getBusinessTypeCode, Function.identity()));
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
         Map<Integer,AttdItemDto> mapListDivergenName = listDivergenName.stream()
                 .collect(Collectors.toMap(AttdItemDto::getAttendanceItemId, Function.identity()));
         List<ErrorAlarmWorkRecordDto> lstDto = listErrorAlarmWorkRecord.stream()
                      .map(eral -> ErrorAlarmWorkRecordDto.fromDomain(eral, eral.getErrorAlarmCondition()))
                       .sorted(Comparator.comparing(ErrorAlarmWorkRecordDto::getCode))
                      .collect(Collectors.toList());


         //40
              if(CollectionUtil.isEmpty(lstDto)){
                     return null;
              }else{
                     lstDto.stream().forEach(c->{
                           Map<String, Object> data = new HashMap<>();
                           putEmptyDataOne(data);
                           data.put(header.get(1), c.getCode());
                           data.put(header.get(2), c.getName());
                           if(c.getUseAtr()==1){
                                  data.put(header.get(3), TextResource.localize("KDW006_185"));
                           }else{
//                                  data.put(header.get(3), TextResource.localize("KDW006_186"));
                        	   return;
                           }
                                  data.put(header.get(4), TextResource.localize(EnumAdaptor.valueOf(c.getTypeAtr(), ErrorAlarmClassification.class).nameId));
                           //5,6
                          if (c.getFixedAtr() != 1 && c.getTypeAtr() != 2) {       
	                           if(c.getRemarkCancelErrorInput() == 1){
	                                  data.put(header.get(5), TextResource.localize("KDW007_109"));
	                                  AttdItemDto attItemDto = mapListDivergenName.get(c.getRemarkColumnNo());
	                                  data.put(header.get(6), attItemDto!=null?attItemDto.getAttendanceItemName():"");
	                                  
	                           }else{
	                        	   	data.put(header.get(5), TextResource.localize("KDW007_110"));
	                                  data.put(header.get(6), "");
	                           }
                          }
                           //7
                          if (c.getTypeAtr() != 2){
	                           AttdItemDto attItemDto = mapListDivergenName.get(c.getErrorDisplayItem());
	                           data.put(header.get(7), attItemDto!=null?attItemDto.getAttendanceItemName():"");
	                           String color = c.getMessageColor();
	                           if(color!=null){
	                    	   data.put(header.get(8), color = color.replace("#", ""));
	                           }
	                           data.put(header.get(9), c.getDisplayMessage());
	                           
	                           if(c.getBoldAtr() ==0){
	                                  data.put(header.get(10), "-");
	                           }else{
	                                  data.put(header.get(10), "○");
	                           }
                          }
                           //11
                            if(c.getAlCheckTargetCondition().isFilterByEmployment() == false){
                                  data.put(header.get(11), "-");
                           }else{
                                  data.put(header.get(11), "○");
                                  //12
                                  List<String> lstEmpCode =c.getAlCheckTargetCondition().getLstEmployment();
                                  Collections.sort(lstEmpCode);
                                  List<String> codeAndNameEmp = new ArrayList<>();
                                  for (String key : lstEmpCode) {
                                         EmploymentCode keyEmp = new EmploymentCode(key);
                                         Employment emp = mapListEmp.get(keyEmp);
                                         if(emp!=null){
                                         codeAndNameEmp.add(emp.getEmploymentCode().v()+emp.getEmploymentName().v());
                                         }else {
                                        	 codeAndNameEmp.add(key+TextResource.localize("KDW006_226"));
										}
                                  }
                                  
                                  
                                  String listStringemp = "";
                                  if(CollectionUtil.isEmpty(lstEmpCode)){
                                         data.put(header.get(12), listStringemp);
                                  }else{
                                         listStringemp = String.join(",", codeAndNameEmp);
                                         data.put(header.get(12), listStringemp);
                                  }
                           }
                          
                           //13 filterByClassification
                            if(c.getAlCheckTargetCondition().isFilterByClassification() == false){
                                  data.put(header.get(13), "-");
                           }else{
                                  data.put(header.get(13), "○");
                                  String sValues14 = "";
                                  List<String> lstClassificationCode= c.getAlCheckTargetCondition().getLstClassification();
                                  Collections.sort(lstClassificationCode);
                                  List<String> codeAndNameClassification = new ArrayList<>();
                                  for (String key : lstClassificationCode) {
                                         ClassificationCode keyClass = new ClassificationCode(key);
                                         Classification classification = mapListClass.get(keyClass);
                                         if(classification!=null){
                                        	 codeAndNameClassification.add(classification.getClassificationCode().v()+classification.getClassificationName().v());
                                         }else {
                                        	 codeAndNameClassification.add(key+TextResource.localize("KDW006_226"));
										}
                                                
                                  }
                                   if(!CollectionUtil.isEmpty(codeAndNameClassification)){
                                         sValues14 = String.join(",", codeAndNameClassification);
                                         data.put(header.get(14), sValues14);
                                  }
                           }
                           
                           
                           //15 filterByJobTitle
                           if(c.getAlCheckTargetCondition().isFilterByJobTitle() == false){
                                  data.put(header.get(15), "-");
                           }else{
                                  data.put(header.get(15), "○");
                                  List<String> lst3 = new ArrayList<>();
                                  
                                  for(int i=0;i<c.getAlCheckTargetCondition().getLstJobTitle().size();i++){
                                         lst3.add(c.getAlCheckTargetCondition().getLstJobTitle().get(i));
                                  }
                                  //16
                                  String sValues16 = "";
                                  List<String> lstJobTitleId= c.getAlCheckTargetCondition().getLstJobTitle();
//                                  Collections.sort(lstJobTitleCode);
                                  List<JobTitleItemDto> listJobTitleItemDto = new ArrayList<>();
                                  List<String> lstJobTitleIdDeleted = new ArrayList<>();
                                  if(!CollectionUtil.isEmpty(lstJobTitleId)){
                            	     for (String key : lstJobTitleId) {
                                         JobTitleItemDto jobTitleItemDto= mapListJobs.get(key);
                                         if(jobTitleItemDto!=null){
                                       	  listJobTitleItemDto.add(jobTitleItemDto);
                                         }else {
                                       	  lstJobTitleIdDeleted.add(key);
   										}
                            	     }
                                  }
                                  List<String> codeAndNameJobTitle = new ArrayList<>();
                                  if(!CollectionUtil.isEmpty(listJobTitleItemDto)){
                                	  listJobTitleItemDto.sort(Comparator.comparing(JobTitleItemDto::getCode));
                                      for (JobTitleItemDto jobTitleItemDto : listJobTitleItemDto) {
                                          if(jobTitleItemDto!=null){
                                         	 codeAndNameJobTitle.add(jobTitleItemDto.getCode()+jobTitleItemDto.getName());
                                          }
                                   }
                                  }
                                  
                                  if(!CollectionUtil.isEmpty(codeAndNameJobTitle)){
                                         sValues16 = String.join(",", codeAndNameJobTitle);
                                         for (int i = 0 ; i <lstJobTitleIdDeleted.size();i++) {
                                        	 sValues16+=","+TextResource.localize("KDW006_226");
										}
                                         data.put(header.get(16), sValues16);
                                  }
                           }
                          
                           //17
                            if(c.getAlCheckTargetCondition().isFilterByBusinessType() == false){
                                  data.put(header.get(17), "-");
                           }else{
                                  data.put(header.get(17), "○");
                                //18
                                  String sValues18 = "";
                                  List<String> lstBusinessTypeCode= c.getAlCheckTargetCondition().getLstBusinessType();
                                  Collections.sort(lstBusinessTypeCode);
                                  List<String> codeAndBusinessType = new ArrayList<>();
                                  for (String key : lstBusinessTypeCode) {
                                         BusinessTypeCode keyBz = new BusinessTypeCode(key);
                                         BusinessType businessType = maplistBusinessType.get(keyBz);
                                         if(businessType!=null){
                                                codeAndBusinessType.add(businessType.getBusinessTypeCode().v()+businessType.getBusinessTypeName());
                                         }else {
                                        	 codeAndBusinessType.add(key+TextResource.localize("KDW006_226"));
										}
                                  }
                                  if(!CollectionUtil.isEmpty(codeAndBusinessType)){
                                         sValues18 = String.join(",", codeAndBusinessType);
                                         data.put(header.get(18), sValues18);
                                  }
                           }
                           
                           // 19
                           if(c.getWorkTypeCondition().getComparePlanAndActual() == 1){
                                  data.put(header.get(19), TextResource.localize("Enum_FilterByCompare_Extract_Same"));
                           }else{
                        	   if(c.getWorkTypeCondition().getComparePlanAndActual() == 0){
                                   data.put(header.get(19), TextResource.localize("Enum_FilterByCompare_NotCompare"));
                        	   }else {
                        		   data.put(header.get(19), TextResource.localize("Enum_FilterByCompare_Extract_Different"));
                        	   } 
                        	 //26,27
                               if(c.getWorkTypeCondition().isActualFilterAtr()== false){
                                   data.put(header.get(26), "-");
                                   data.put(header.get(27),"");
                               }else{
                                   //listErrorAlarmWorkRecord;
                                   data.put(header.get(26), "○");
                                   List<String> codeAndNameActualType = new ArrayList<>();
                                   List<String> listActualLstWorkTypeCode = c.getWorkTypeCondition().getActualLstWorkType();
                                   Collections.sort(listActualLstWorkTypeCode);
                                   for (String key : listActualLstWorkTypeCode) {
                                         WorkTypeDto workTypeDto=  mapListWorkTypeDto.get(key);
                                         if(workTypeDto!=null){
                                                codeAndNameActualType.add(workTypeDto.getWorkTypeCode()+workTypeDto.getName());
                                         }else {
                                        	 codeAndNameActualType.add(key+TextResource.localize("KDW006_226"));
										}
                                   }
                                   String stringDataColumn27 = String.join(",", codeAndNameActualType);
                                   data.put(header.get(27),stringDataColumn27);
                               }
                           }
                           //20
                            if(c.getWorkTimeCondition().getComparePlanAndActual() ==1){
                                  data.put(header.get(20), TextResource.localize("Enum_FilterByCompare_Extract_Same"));
                           }else{
                        	   if(c.getWorkTimeCondition().getComparePlanAndActual() ==0){
                                   data.put(header.get(20), TextResource.localize("Enum_FilterByCompare_NotCompare"));
                        	   }else{
                                  data.put(header.get(20), TextResource.localize("Enum_FilterByCompare_Extract_Different"));
                        	   }
                        	   //28,29
                               if(c.getWorkTimeCondition().isActualFilterAtr()== false){
                                   data.put(header.get(28), "-");
                                   data.put(header.get(29),"");
                               }else{
                                   data.put(header.get(28), "○");
                                   List<String> listActualTimeCode = c.getWorkTimeCondition().getActualLstWorkTime();
                                   List<String> codeAndNameActualTime = new ArrayList<>();
                                   Collections.sort(listActualTimeCode);
                                   for (String key : listActualTimeCode) {
                                	   WorkTimeCode keyWorkTime = new WorkTimeCode(key);
                                       WorkTimeSetting workTypeDto = mapListWorkTime.get(keyWorkTime);
                                       if(workTypeDto!=null){
                                    	   codeAndNameActualTime.add(workTypeDto.getWorktimeCode().v()+workTypeDto.getWorkTimeDisplayName().getWorkTimeName().v());
                                       }else {
                                    	   codeAndNameActualTime.add(key+TextResource.localize("KDW006_226"));
									}
                                   }
                                   String stringDataColumn29 =String.join(",", codeAndNameActualTime);
                                  
                                   data.put(header.get(29),stringDataColumn29);
                               }
                           }
                           //21 22
                           if(c.getWorkTypeCondition().isPlanFilterAtr() ==false){
                                  data.put(header.get(21), "-");
                                  data.put(header.get(22), "");
                           }else{
                                  data.put(header.get(21), "○");
                                  List<String> listWorkTypeCode = c.getWorkTypeCondition().getPlanLstWorkType();
                                  String sValues22 = "";
                                  Collections.sort(listWorkTypeCode);
                                  List<String> codeAndNameWorkType = new ArrayList<>();
                                  for (String key : listWorkTypeCode) {
                                         WorkTypeDto workTypeDto = mapListWorkTypeDto.get(key);
                                         if(workTypeDto!=null){
                                                codeAndNameWorkType.add(workTypeDto.getWorkTypeCode()+workTypeDto.getName());
                                         }else {
                                        	 codeAndNameWorkType.add(key+TextResource.localize("KDW006_226"));
										}
                                  }
                                  if(!CollectionUtil.isEmpty(codeAndNameWorkType)){
                                         sValues22 = String.join(",", codeAndNameWorkType);
                                         data.put(header.get(22), sValues22);
                                  }
                           }
                           // 23 24
                           if(c.getWorkTimeCondition().isPlanFilterAtr() ==false){
                                  data.put(header.get(23), "-");
                                  data.put(header.get(24), "");
                           }else{
                                  data.put(header.get(23), "○");
                                  List<String> listWorkTimeCode = c.getWorkTimeCondition().getPlanLstWorkTime();
                                  String sValues24 = "";
                                  Collections.sort(listWorkTimeCode);
                                  List<String> codeAndNameWorkTime = new ArrayList<>();
                                  for (String key : listWorkTimeCode) {
                                         WorkTimeCode keyWorkTime = new WorkTimeCode(key);
                                         WorkTimeSetting workTypeDto = mapListWorkTime.get(keyWorkTime);
                                         if(workTypeDto!=null){
                                                codeAndNameWorkTime.add(workTypeDto.getWorktimeCode().v()+workTypeDto.getWorkTimeDisplayName().getWorkTimeName().v());
                                         }else{
                                        	 codeAndNameWorkTime.add(key+TextResource.localize("KDW006_226"));
                                         }
                                  }
                                  if(!CollectionUtil.isEmpty(codeAndNameWorkTime)){
                                         sValues24 = String.join(",", codeAndNameWorkTime);
                                         data.put(header.get(24), sValues24);
                                  }
                           }
                           
				// 25
				data.put(header.get(25), TextResource
						.localize(EnumAdaptor.valueOf(c.getOperatorBetweenPlanActual(), LogicalOperator.class).nameId));
				// 30
				data.put(header.get(30), TextResource
						.localize(EnumAdaptor.valueOf(c.getOperatorGroup1(), LogicalOperator.class).nameId));
				//31 32 33
				List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup1 = c.getErAlAtdItemConditionGroup1();
				if (!CollectionUtil.isEmpty(erAlAtdItemConditionGroup1)) {
					for (int i = 0; i < erAlAtdItemConditionGroup1.size(); i++) {
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
						Map<Integer, AttdItemDto> mapListAttItemDto = new HashMap<>();
						/**
						 * 回数 TIMES(0, "Enum_ConditionAtr_Times"), 時間
						 * TIME_DURATION(1, "Enum_ConditionAtr_TimeDuration"),
						 * 時刻 TIME_WITH_DAY(2, "Enum_ConditionAtr_TimeWithDay"),
						 * 金額 AMOUNT_VALUE(3, "Enum_ConditionAtr_AmountValue"),
						 * 日数 DAYS(4, "日数");
						 */
						////////////////////////
						/**
						 * 固定値 FIXED_VALUE(0, "Enum_ConditionType_FixedValue"),
						 * 勤怠項目 ATTENDANCE_ITEM(1,
						 * "Enum_ConditionType_AttendanceItem"), 入力チェック
						 * INPUT_CHECK(2, "Enum_ConditionType_InputCheck");
						 */

						switch (conditionAtr) {
						case 0:
						case 1:
						case 3:
							if (conditionAtr == 0) {
								mapListAttItemDto = mapListAttItemDto2;
							}
							if (conditionAtr == 1) {
								mapListAttItemDto = mapListAttItemDto5;
							}
							if (conditionAtr == 3) {
								mapListAttItemDto = mapListAttItemDto3;
							}
							if(conditionType!=2){
							List<Integer> listKeyAdd = erro.getCountableAddAtdItems();
							List<Integer> listKeySub = erro.getCountableSubAtdItems();
							List<String> listStringAttNameAdd = new ArrayList<>();
							List<String> listStringAttNameSub = new ArrayList<>();
							// get list att add
							if(!CollectionUtil.isEmpty(listKeyAdd)){
								for (Integer attId : listKeyAdd) {
									AttdItemDto attItem = mapListAttItemDto.get(attId);
									if (attItem != null) {
										listStringAttNameAdd.add(attItem.getAttendanceItemName());
									}
								}
							}
							// get list att sub
							if(!CollectionUtil.isEmpty(listKeySub)){
								for (Integer attId : listKeySub) {
									AttdItemDto attItem = mapListAttItemDto.get(attId);
									if (attItem != null) {
										listStringAttNameSub.add(attItem.getAttendanceItemName());
									}
								}
							}
//							Collections.sort(listStringAttNameAdd);
//							Collections.sort(listStringAttNameSub);
							String subString = String.join("-", listStringAttNameSub);
							boolean checkEmptyString = subString.isEmpty() && subString != null;
							targetName = checkEmptyString ? String.join("+", listStringAttNameAdd)
									: String.join("+", listStringAttNameAdd) + "-" + subString;
							}else {
								AttdItemDto attItem = mapListAttItemDto.get(erro.getUncountableAtdItem());
								if (attItem != null) {
									targetName = attItem.getAttendanceItemName();
								}
							}
							break;
						case 2:
							AttdItemDto attItem = mapListAttItemDto6.get(erro.getUncountableAtdItem());
							if (attItem != null) {
								targetName = attItem.getAttendanceItemName();
							}
							break;
						default:
							break;
						}
						if (targetName != null && !targetName.isEmpty()) {
							switch (conditionType) {
							case 0:
								startValue = convertValueTimeAndString(erro.getCompareStartValue().intValueExact(),conditionAtr);
								if (compare <= 5) {
									alarmDescription = targetName + compareOpera.getCompareLeft() + startValue;
								} else {
									endValue = convertValueTimeAndString(erro.getCompareEndValue().intValueExact(),conditionAtr);
									if (compare > 5 && compare <= 7) {
										alarmDescription = startValue + compareOpera.getCompareLeft() + targetName
												+ compareOpera.getCompareright() + endValue;
									} else {
										alarmDescription = targetName + compareOpera.getCompareLeft() + startValue + ","
												+ endValue + compareOpera.getCompareright() + targetName;
									}
								}
								break;
							case 1:
								String singleAttName = "";
								if (conditionAtr == 2) {
									AttdItemDto at = mapListAttItemDto6.get(erro.getSingleAtdItem());
									if (at != null) {
										singleAttName = at.getAttendanceItemName();
									}
								} else {
									AttdItemDto attItem = mapListAttItemDto.get(erro.getSingleAtdItem());
									if (attItem != null) {
										singleAttName = attItem.getAttendanceItemName();
									}
								}
								alarmDescription = targetName + compareOpera.getCompareLeft() + singleAttName;
								break;
							case 2:
								// Enum : InputCheckCondition
								/** 入力されていない */
								// INPUT_NOT_DONE(0,
								// "Enum_ConditionType_FixedValue"),
								/** 入力されている */
								// INPUT_DONE(1,
								// "Enum_ConditionType_AttendanceItem");
								/**
								 * fix tam theo man hinh : KDW007_107 = 入力されている
								 * 							KDW007_108 = 入力されていない	
								 */
								conditonName = erro.getInputCheckCondition() == 0
										? TextResource.localize("KDW007_108")
										: TextResource.localize("KDW007_107");
								alarmDescription = targetName +" "+ conditonName;
								break;
							default:
								break;
							}
						}
                            if(i==0){
                                   data.put(header.get(31), alarmDescription);
                            }
                            if(i==1){
                                   data.put(header.get(32),alarmDescription);
                            }
                            if(i==2){
                                   data.put(header.get(33),alarmDescription);
                            }
                     }
              }
              List<ErAlAtdItemConditionDto> erAlAtdItemConditionGroup2 = c.getErAlAtdItemConditionGroup2();
              //34,35,39
              if(c.isGroup2UseAtr() ==false){
                     data.put(header.get(34), "-");
              }else{
                     data.put(header.get(34), "○");
                     data.put(header.get(35), TextResource
            						.localize(EnumAdaptor.valueOf(c.getOperatorGroup2(), LogicalOperator.class).nameId)); 
                     //36 37 38
					if (!CollectionUtil.isEmpty(erAlAtdItemConditionGroup2)) {
						for (int i = 0; i < erAlAtdItemConditionGroup2.size(); i++) {
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
							Map<Integer, AttdItemDto> mapListAttItemDto = new HashMap<>();
							/**
							 * 回数 TIMES(0, "Enum_ConditionAtr_Times"), 時間
							 * TIME_DURATION(1,
							 * "Enum_ConditionAtr_TimeDuration"), 時刻
							 * TIME_WITH_DAY(2,
							 * "Enum_ConditionAtr_TimeWithDay"), 金額
							 * AMOUNT_VALUE(3, "Enum_ConditionAtr_AmountValue"),
							 * 日数 DAYS(4, "日数");
							 */
							////////////////////////
							/**
							 * 固定値 FIXED_VALUE(0,
							 * "Enum_ConditionType_FixedValue"), 勤怠項目
							 * ATTENDANCE_ITEM(1,
							 * "Enum_ConditionType_AttendanceItem"), 入力チェック
							 * INPUT_CHECK(2, "Enum_ConditionType_InputCheck");
							 */
							switch (conditionAtr) {
							case 0:
							case 1:
							case 3:
								if (conditionAtr == 0) {
									mapListAttItemDto = mapListAttItemDto2;
								}
								if (conditionAtr == 1) {
									mapListAttItemDto = mapListAttItemDto5;
								}
								if (conditionAtr == 3) {
									mapListAttItemDto = mapListAttItemDto3;
								}
								if(conditionType!=2){
								List<Integer> listKeyAdd = erro.getCountableAddAtdItems();
								List<Integer> listKeySub = erro.getCountableSubAtdItems();
								List<String> listStringAttNameAdd = new ArrayList<>();
								List<String> listStringAttNameSub = new ArrayList<>();
								// get list att add
								if(!CollectionUtil.isEmpty(listKeyAdd)){
									for (Integer attId : listKeyAdd) {
										AttdItemDto attItem = mapListAttItemDto.get(attId);
										if (attItem != null) {
											listStringAttNameAdd.add(attItem.getAttendanceItemName());
										}
									}
								}
								// get list att sub
								if(!CollectionUtil.isEmpty(listKeySub)){
									for (Integer attId : listKeySub) {
										AttdItemDto attItem = mapListAttItemDto.get(attId);
										if (attItem != null) {
											listStringAttNameSub.add(attItem.getAttendanceItemName());
										}
									}
								}
//								Collections.sort(listStringAttNameAdd);
//								Collections.sort(listStringAttNameSub);
								String subString = String.join("-", listStringAttNameSub);
								boolean checkEmptyString = subString.isEmpty() && subString != null;
								targetName = checkEmptyString ? String.join("+", listStringAttNameAdd)
										: String.join("+", listStringAttNameAdd) + "-" + subString;
								}
								else {
									AttdItemDto attItem = mapListAttItemDto.get(erro.getUncountableAtdItem());
									if (attItem != null) {
										targetName = attItem.getAttendanceItemName();
									}
								}
								break;
							case 2:
								AttdItemDto attItem = mapListAttItemDto6.get(erro.getUncountableAtdItem());
								if (attItem != null) {
									targetName = attItem.getAttendanceItemName();
								}
								break;
							default:
								break;
							}
							if (targetName != null && !targetName.isEmpty()) {
								switch (conditionType) {
								case 0:
									startValue = convertValueTimeAndString(erro.getCompareStartValue().intValueExact(),conditionAtr);
									if (compare <= 5) {
										alarmDescription = targetName + compareOpera.getCompareLeft() + startValue;
									} else {
										endValue = convertValueTimeAndString(erro.getCompareEndValue().intValueExact(),conditionAtr);
										if (compare > 5 && compare <= 7) {
											alarmDescription = startValue + compareOpera.getCompareLeft() + targetName
													+ compareOpera.getCompareright() + endValue;
										} else {
											alarmDescription = targetName + compareOpera.getCompareLeft() + startValue + ","
													+ endValue + compareOpera.getCompareright() + targetName;
										}
									}
									break;
								case 1:
									String singleAttName = "";
									if (conditionAtr == 2) {
										AttdItemDto at = mapListAttItemDto6.get(erro.getSingleAtdItem());
										if (at != null) {
											singleAttName = at.getAttendanceItemName();
										}
									} else {
										AttdItemDto attItem = mapListAttItemDto.get(erro.getSingleAtdItem());
										if (attItem != null) {
											singleAttName = attItem.getAttendanceItemName();
										}
									}
									alarmDescription = targetName + compareOpera.getCompareLeft() + singleAttName;
									break;
								case 2:
									// Enum : InputCheckCondition
									/** 入力されていない */
									// INPUT_NOT_DONE(0,
									// "Enum_ConditionType_FixedValue"),
									/** 入力されている */
									// INPUT_DONE(1,
									// "Enum_ConditionType_AttendanceItem");
									conditonName = erro.getInputCheckCondition() == 0
											? TextResource.localize("KDW007_108")
											: TextResource.localize("KDW007_107");
									alarmDescription = targetName +" "+ conditonName;
									break;
								default:
									break;
								}
							}
							if (i == 0) {
								data.put(header.get(36), alarmDescription);
							}
							if (i == 1) {
								data.put(header.get(37), alarmDescription);
							}
							if (i == 2) {
								data.put(header.get(38), alarmDescription);
							}
						}
					}
						data.put(header.get(39), TextResource
        						.localize(EnumAdaptor.valueOf(c.getOperatorBetweenGroups(), LogicalOperator.class).nameId)); 
				}
                           
                           // 40
                           List<Integer> lstApplicationTypeCode = c.getLstApplicationTypeCode();
                           List<String> listStrApp = new ArrayList<>();
                           if(!CollectionUtil.isEmpty(lstApplicationTypeCode)){
                        	   Collections.sort(lstApplicationTypeCode);
                                  for (Integer key : lstApplicationTypeCode) {
                                         String strAppType = EnumAdaptor.valueOf(key, ApplicationTypeExport.class).nameId;
                                         if(strAppType!=null){
                                                listStrApp.add(strAppType);
                                         }
                                  }
                           }
                           if(!CollectionUtil.isEmpty(listStrApp)){
                                  data.put(header.get(40), String.join(",", listStrApp));
                           }
                           
                           MasterData masterData = new MasterData(data, null, "");
                           for (int i=1;i< header.size();i++) {
//                        	   String tempTest = header.get(i);
                        	   if(i==8&& c.getTypeAtr() != 2){
                        		   masterData.cellAt(header.get(i)).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT).backgroundColor(c.getMessageColor())); 
                        	   }else {
                        		  masterData.cellAt(header.get(i)).setStyle(MasterCellStyle.build().horizontalAlign(ColumnTextAlign.LEFT));
							}
                        	   
                           }
                           datas.add(masterData);
                     });
              }
              return datas;
       }
       private void putEmptyDataOne(Map<String, Object> data) {
              String header = "header";
              for(int i = 1 ; i <= 40; i++){
            	  data.put(header+i, "");
              }
       }
       public List<MasterHeaderColumn> getHeaderColumns(MasterListExportQuery query) {
              List<MasterHeaderColumn> columns = new ArrayList<>();
              List<String> header =  Arrays.asList("column0",TextResource.localize("KDW006_106"),																				
      	   			TextResource.localize("KDW006_90"),																				
    	   			TextResource.localize("KDW006_155"),																				
    	   			TextResource.localize("KDW006_156"),																				
    	   			TextResource.localize("KDW006_157"),																				
    	   			TextResource.localize("KDW006_158"),																				
    	   			TextResource.localize("KDW006_159"),																				
    	   			TextResource.localize("KDW006_160"),																				
    	   			TextResource.localize("KDW006_161"),																				
    	   			TextResource.localize("KDW006_162"),																				
    	   			TextResource.localize("KDW006_163"),																				
    	   			TextResource.localize("KDW006_164"),																				
    	   			TextResource.localize("KDW006_165"),																				
    	   			TextResource.localize("KDW006_166"),																				
    	   			TextResource.localize("KDW006_167"),																				
    	   			TextResource.localize("KDW006_168"),																				
    	   			TextResource.localize("KDW006_169"),																				
    	   			TextResource.localize("KDW006_170"),																				
    	   			TextResource.localize("KDW006_171"),																				
    	   			TextResource.localize("KDW006_172"),																				
    	   			TextResource.localize("KDW006_173"),																				
    	   			TextResource.localize("KDW006_173"),																				
    	   			TextResource.localize("KDW006_174"),																				
    	   			TextResource.localize("KDW006_174"),																				
    	   			TextResource.localize("KDW006_175"),																				
    	   			TextResource.localize("KDW006_176"),																				
    	   			TextResource.localize("KDW006_176"),																				
    	   			TextResource.localize("KDW006_177"),																				
    	   			TextResource.localize("KDW006_177"),																				
    	   			TextResource.localize("KDW006_178"),																				
    	   			TextResource.localize("KDW006_179"),																				
    	   			TextResource.localize("KDW006_179"),																				
    	   			TextResource.localize("KDW006_179"),																				
    	   			TextResource.localize("KDW006_180"),																				
    	   			TextResource.localize("KDW006_181"),																				
    	   			TextResource.localize("KDW006_182"),																				
    	   			TextResource.localize("KDW006_182"),																				
    	   			TextResource.localize("KDW006_182"),																				
    	   			TextResource.localize("KDW006_183"),																				
    	   			TextResource.localize("KDW006_184"));
              String headerColumn = "header";
              for(int i = 1 ; i <= 40; i++){
            	  columns.add(new MasterHeaderColumn(headerColumn+i,header.get(i), ColumnTextAlign.LEFT,
  		                "", true));  
              }
              return columns;
       }
       
       public List<SheetData> extraSheets(MasterListExportQuery query){
              List<SheetData> listSheetData = new ArrayList<>();
              SheetData sheet1 = new SheetData(getMasterDatas(query), getHeaderColumns(query), null, null, TextResource.localize("KDW006_140"), MasterListMode.NONE);
              listSheetData.add(sheet1);
              SheetData sheetDataTwo = new SheetData(getMasterDataTwo(query), getHeaderColumnTwos(query), null, null, TextResource.localize("KDW006_141"), MasterListMode.NONE);
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
              return null;
           }else{
              listErrorAlarmWorkRecord.stream().forEach(c->{
                     List<String> lstApp = new ArrayList<>();
                     Map<String, Object> data = new HashMap<>();
                           putEmptyDataTwo(data);
                           
                           data.put("コード", c.getCode().v().substring(1));
                           data.put("名称", c.getName());
                           if(c.getUseAtr()){
                                  data.put("使用区分", "使用する");
                           }else{
//                                  data.put("使用区分", "使用しない");
                        	   return;
                           }
                           List<Integer> listApplication  = c.getLstApplication();
                           Collections.sort(listApplication);
                           if(CollectionUtil.isEmpty(listApplication)){
                                  data.put("申請", "");
                           }else{
                        	  
                        	   for (Integer key : listApplication) {
                        		   String appName = EnumAdaptor.valueOf(key, ApplicationTypeExport.class).nameId;
                        		   if(appName!=null){
                        			   lstApp.add(appName);
                        		   }
							}
                        	   if(!CollectionUtil.isEmpty(lstApp)){
                                  data.put("申請", String.join(",", lstApp));
                        	   }
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
       private String convertValueTimeAndString(int intValueExact, int conditionAtr) {
    	   if(conditionAtr==1||conditionAtr==2){
    		   return timeToString(intValueExact);
    	   }
    	   return String.valueOf(intValueExact);
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