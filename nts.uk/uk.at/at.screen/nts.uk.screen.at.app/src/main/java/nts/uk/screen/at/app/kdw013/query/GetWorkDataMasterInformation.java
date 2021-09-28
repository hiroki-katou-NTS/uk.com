package nts.uk.screen.at.app.kdw013.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.app.find.worklocation.WorkLocationDto;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordItem;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordItemRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethod;
import nts.uk.ctx.at.request.dom.application.overtime.CommonAlgorithm.DivergenceReasonInputMethodI;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.app.query.worktime.worktimeset.WorkTimeSettingQuery;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRootRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TypesMasterRelatedDailyAttendanceItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttendanceItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.service.CompanyDailyItemService;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.screen.at.app.kdw013.a.TaskDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * @author thanhpv
 * @part UKDesign.UniversalK.就業.KDW_日別実績.KDW013_工数入力.共通処理
 */
@Stateless
public class GetWorkDataMasterInformation {
    
    @Inject
    private GetTaskListOfSpecifiedWorkFrameNoQuery getTaskListOfSpecifiedWorkFrameNoQuery; 
    
    @Inject 
    private WorkLocationRepository workLocationRepository;
    
    @Inject
    private TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepo;
    
    @Inject
    private ManHourRecordItemRepository manHourRecordItemRepo;
    
    @Inject
    private CompanyDailyItemService companyDailyItemService;
    
    @Inject
    private DailyAttendanceItemRepository dailyAttendanceItemRepo;
    
    @Inject
    private WorkTypeRepository workTypeRepository;
    
    @Inject
    private WorkTimeSettingQuery workTimeSettingQuery;
    
    @Inject
    private DivergenceTimeRootRepository divergenceTimeRootRepo;
    
    @Inject
    private DivergenceReasonInputMethodI divergenceReasonInputMethodI;
    
    /**
     * @name 作業データマスタ情報を取得する
     * @param referenceDate 基準日
     * @param itemId List<工数実績項目ID>
     */
    public WorkDataMasterInformationDto get(GeneralDate refDate, List<Integer> itemIds){
    	LoginUserContext loginUserContext = AppContexts.user();

    	//1
    	Map<Integer, List<Task>> mapTask = new HashMap<>();
    	//作業枠NOを1～5をループする
    	for (int i = 1 ; i <= 5; i++) {
    		//取得する(ログイン会社ID, 処理中の作業枠NO)
    		mapTask.put(i, getTaskListOfSpecifiedWorkFrameNoQuery.getListTask(loginUserContext.companyId(), i));
		}

    	//2
    	//List<勤務場所>
    	List<WorkLocation> workLocation = new ArrayList<WorkLocation>();

    	//工数実績項目ID == 9　がある
    	boolean itemId9 = itemIds.contains(9);
    	if(itemId9) {
	    	//勤務場所を取得する
	    	workLocation.addAll(workLocationRepository.findAll(loginUserContext.contractCode()));
    	}
    	
    	//3
    	//$対象項目 = {25,26,27,28,29}
    	//$項目リスト = INPUT「List<工数実績項目ID>」：filter $対象項目.($)
    	//$項目リストをループする
    	List<TaskSupInfoChoicesDetail> taskSupInfoChoicesDetails = new ArrayList<TaskSupInfoChoicesDetail>();
    	for (Integer itemId : itemIds.stream().filter(c-> Arrays.asList(25,26,27,28,29).contains(c)).collect(Collectors.toList())) {
    		taskSupInfoChoicesDetails.addAll(taskSupInfoChoicesHistoryRepo.get(loginUserContext.companyId(), itemId, refDate));
		}
    	
    	//4
    	//取得する(工数実績項目ID)
    	List<ManHourRecordItem> manHourRecordItems = manHourRecordItemRepo.get(loginUserContext.companyId(), itemIds);
    	
    	return new WorkDataMasterInformationDto(
    			mapTask.get(1).stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList()), 
    			mapTask.get(2).stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList()), 
    			mapTask.get(3).stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList()), 
    			mapTask.get(4).stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList()), 
    			mapTask.get(5).stream().map(c->TaskDto.toDto(c)).collect(Collectors.toList()), 
    			workLocation.stream().map(c->WorkLocationDto.fromDomain(c)).collect(Collectors.toList()), 
    			taskSupInfoChoicesDetails.stream().map(c-> new TaskSupInfoChoicesDetailDto(c)).collect(Collectors.toList()), 
    			manHourRecordItems.stream().map(c-> new ManHourRecordItemDto(c)).collect(Collectors.toList()));
    			
    }
    
    /**
     * @name 勤怠項目マスタ情報を取得する
     * @param itemId List<工数実績項目ID>
     */
    public AttendanceItemMasterInformationDto getAttendanceItemMasterInformation(List<Integer> itemIds){
    	
    	LoginUserContext loginUser = AppContexts.user();
    	
    	//会社の日次項目を取得する
    	//List<勤怠項目>
    	List<AttItemName> attItemName = companyDailyItemService.getDailyItems(loginUser.companyId(), Optional.ofNullable(loginUser.roles().forAttendance()), itemIds, new ArrayList<>());
    	
    	//日次の勤怠項目を取得する
    	//List<日次の勤怠項目>
    	List<DailyAttendanceItem> dailyAttendanceItem = dailyAttendanceItemRepo.findByADailyAttendanceItems(attItemName.stream().map(c -> c.getAttendanceItemId()).collect(Collectors.toList()), loginUser.companyId());
    	
    	//List<勤務種類>
    	List<WorkType> workTypes = new ArrayList<>();
    	//「日次の勤怠項目.属性 = コード AND 日次の勤怠項目.マスタの種類 = 勤務種類」がある
    	if(dailyAttendanceItem.stream().filter(c-> c.getDailyAttendanceAtr().value == DailyAttendanceAtr.Code.value && c.getMasterType().isPresent() && c.getMasterType().get().value == TypesMasterRelatedDailyAttendanceItem.WORK_TYPE.value).findFirst().isPresent()) {
        	//<<Public>> 勤務種類をすべて取得する
    		workTypes = workTypeRepository.findByCompanyId(loginUser.companyId());
    	}
    	
    	//List<就業時間帯の設定>
    	List<WorkTimeSetting> workTimeSettings = new ArrayList<>();
    	if(dailyAttendanceItem.stream().filter(c-> c.getDailyAttendanceAtr().value == DailyAttendanceAtr.Code.value && c.getMasterType().isPresent() && c.getMasterType().get().value == TypesMasterRelatedDailyAttendanceItem.WORKING_HOURS.value).findFirst().isPresent()) {
    		//全ての就業時間帯の設定を取得する
    		workTimeSettings = workTimeSettingQuery.getWorkTimeSettings();
    	}
    	
    	//List<乖離時間>
    	List<DivergenceTimeRoot> divergenceTimeRoots = new ArrayList<>();
    	
    	//List「乖離理由の入力方法」
    	List<DivergenceReasonInputMethod> divergenceReasonInputMethods = new ArrayList<>();
    	
    	//「日次の勤怠項目.属性 = コード AND 日次の勤怠項目.マスタの種類 = 乖離理由」がある
    	if(dailyAttendanceItem.stream().filter(c-> c.getDailyAttendanceAtr().value == DailyAttendanceAtr.Code.value && c.getMasterType().isPresent() && c.getMasterType().get().value == TypesMasterRelatedDailyAttendanceItem.REASON_DIVERGENCE.value).findFirst().isPresent()) {
    		//frames  List<乖離時間NO>
    		List<Integer> frames = dailyAttendanceItem.stream().map(c -> this.convertAttendanceItemIDToDeviationTimeNO(c.getAttendanceItemId())).collect(Collectors.toList());
    		
    		//乖離時間NOリストの乖離時間を取得する
    		divergenceTimeRoots = divergenceTimeRootRepo.getList(loginUser.companyId(), frames);
    		
    		//乖離時間NOリストに対応する乖離理由の入力方法を取得する
    		divergenceReasonInputMethods = divergenceReasonInputMethodI.getData(loginUser.companyId(), frames);
    	}
    	
    	return new AttendanceItemMasterInformationDto(attItemName, dailyAttendanceItem, workTypes, workTimeSettings, divergenceTimeRoots, divergenceReasonInputMethods);
    }
    
    /**
     	【勤怠項目に対応する乖離時間NO】
		勤怠項目ID　→　乖離時間NO
		・438　　　　→　1
		・443　　　　→　2
		・448　　　　→　3
		・453　　　　→　4
		・458　　　　→　5
		・801　　　　→　6
		・806　　　　→　7
		・811　　　　→　8
		・816　　　　→　9
		・821　　　　→　10
     */
    private Integer convertAttendanceItemIDToDeviationTimeNO(int itemID) {
    	switch (itemID) {
		case 438:
			return 1;
		case 443:
			return 2;
		case 448:
			return 3;
		case 453:
			return 4;
		case 458:
			return 5;
		case 801:
			return 6;
		case 806:
			return 7;
		case 811:
			return 8;
		case 816:
			return 9;
		case 821:
			return 10;
		default:
			return null;
		}
    }
}
