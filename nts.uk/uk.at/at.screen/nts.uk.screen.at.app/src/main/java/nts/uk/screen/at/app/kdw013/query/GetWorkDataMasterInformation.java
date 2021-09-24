package nts.uk.screen.at.app.kdw013.query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordItem;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordItemRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.shared.app.query.task.GetTaskListOfSpecifiedWorkFrameNoQuery;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
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
    			mapTask.get(1), 
    			mapTask.get(2), 
    			mapTask.get(3), 
    			mapTask.get(4), 
    			mapTask.get(5), 
    			workLocation, 
    			taskSupInfoChoicesDetails, 
    			manHourRecordItems);
    			
    }
}
