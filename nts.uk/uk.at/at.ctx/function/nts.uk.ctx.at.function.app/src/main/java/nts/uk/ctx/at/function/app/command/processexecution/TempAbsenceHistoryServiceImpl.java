package nts.uk.ctx.at.function.app.command.processexecution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.RecordWorkInfoFunAdapterDto;
import nts.uk.ctx.at.function.dom.processexecution.TempAbsenceHistoryService;
import nts.uk.ctx.at.function.dom.processexecution.executionlog.ProcessExecutionTask;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleRepository;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.DateHistoryItemImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.SharedTempAbsenceAdapter;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceHisItemImport;
import nts.uk.ctx.at.shared.dom.adapter.temporaryabsence.TempAbsenceImport;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TempAbsenceHistoryServiceImpl implements TempAbsenceHistoryService {
	
	@Inject
	private SharedTempAbsenceAdapter sharedTempAbsenceAdapter;
	
	@Inject
    private RecordWorkInfoFunAdapter recordWorkInfoFunAdapter;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private WorkScheduleRepository workScheduleRepository;
	
	@Override
	public TempAbsenceImport getTempAbsence(String cid, DatePeriod period, List<String> employeeIds) {
		return this.sharedTempAbsenceAdapter.getTempAbsence(cid, period, employeeIds);
	}

	@Override
    public List<DatePeriod> findChangingLeaveHistoryPeriod(String sid, DatePeriod period, TempAbsenceImport tempAbsence, boolean isRecreateLeave,
    		ProcessExecutionTask procExecTask) {
    	List<DatePeriod> periodList = new ArrayList<>();
    	String cid = AppContexts.user().companyId();
    	// INPUT「休職・休業者再作成」をチェックする
    	// INPUT「休職・休業者再作成」　=　FALSE
    	if (!isRecreateLeave) {
    		// INPUT「処理期間」をOUTPUTとして返す
    		return Arrays.asList(period);
    	}
    	// INPUT「休職・休業者再作成」　=　TRUE
    	// 「休職休業履歴差異」を作成する
    	boolean leaveHistoryDifferent = false;
    	
        DatePeriod newPeriod = null;
        Map<GeneralDate, String> workTypeCodeMap = this.getWorkTypeCodeMap(procExecTask, sid, period);
        // INPUT．「期間」をループする
        for (GeneralDate date: period.datesBetween()) {
        	Optional<String> workTypeCode = workTypeCodeMap.entrySet().stream()
        			.filter(e -> e.getKey().equals(date))
        			.map(Entry::getValue)
        			.findFirst();
        	if (workTypeCode.isPresent()) {
        		// ドメインモデル「勤務種類」を取得する
        		Optional<WorkType> optWorkType = this.workTypeRepository.findNoAbolishByPK(cid, workTypeCode.get());
        		if (optWorkType.isPresent()) {
        			WorkType workType = optWorkType.get();
        			// 取得したドメインモデル「勤務種類」とINPUT「休職休業履歴項目」を比較する
            		boolean compareResult = this.compareLeaveHistory(tempAbsence, workType, date);
            		// 比較結果 == TRUE　&&　休職休業履歴差異 = FALSE
            		if (compareResult && !leaveHistoryDifferent) {
            			// 「期間」を作成する
            			newPeriod = new DatePeriod(date, null);
            			// 「休職休業履歴差異」を更新する
            			leaveHistoryDifferent = true;
            		} else if (!compareResult && leaveHistoryDifferent) {
            			// 「期間」の終了日を更新する
            			newPeriod = new DatePeriod(newPeriod.start(), date.addDays(-1));
            			// 「休職休業履歴差異」を更新する
            			leaveHistoryDifferent = false;
            			// OUTPUT「期間（List）」に作成した「期間」を追加する
            			periodList.add(newPeriod);
            		}
        		}
        	}
        }
        // 処理の「期間」の終了日がNULLであるかチェック
        // NULL　の場合
        if (newPeriod != null && newPeriod.end().equals(null)) {
        	// 「期間」の終了日を更新してOUTPUT「期間（List）」に追加する
        	newPeriod = new DatePeriod(newPeriod.start(), period.end());
        	periodList.add(newPeriod);
        }
        return periodList;
    }
    
    /**
     * 取得したドメインモデル「勤務種類」とINPUT「休職休業履歴項目」を比較する
     * @return
     */
    private boolean compareLeaveHistory(TempAbsenceImport tempAbsence, WorkType workType, GeneralDate date) {
    	if (tempAbsence.getHistories().isEmpty()) {
    		return false;
    	}
    	// 【比較条件】
    	// ・処理中の年月日が「休職休業履歴．期間」に含まれている
    	Optional<DateHistoryItemImport> optHist = tempAbsence.getHistories().get(0).getDateHistoryItems().stream()
    			.filter(data -> data.getStartDate().beforeOrEquals(date) && data.getEndDate().afterOrEquals(date))
    			.findAny();
    	if (!optHist.isPresent()) {
    		return false;
    	}
    	DateHistoryItemImport hist = optHist.get();
    	// 休職休業履歴項目
    	// ※履歴IDが「休職休業履歴」同じもの
    	Optional<TempAbsenceHisItemImport> optHistItem = tempAbsence.getHistoryItem().stream()
    			.filter(data -> data.getHistoryId().equals(hist.getHistoryId()))
    			.findAny();
    	if (!optHist.isPresent()) {
    		return false;
    	}
    	TempAbsenceHisItemImport histItem = optHistItem.get();
    	if (workType.getDailyWork().isOneDay()) {
    		// 「勤務種類．１日の勤務．勤務区分」 = 1日　AND　「勤務種類．１日の勤務．1日」= 休職　の場合
    		if (workType.getDailyWork().getOneDay().equals(WorkTypeClassification.LeaveOfAbsence)) {
    			// ・「休職休業履歴項目．NO」 <> 1  → True
        		//　・それ以外  → False
        		return histItem.getTempAbsenceFrNo() != 1;
    		}
    		// 「勤務種類．１日の勤務．勤務区分」 = 1日　AND　「勤務種類．１日の勤務．1日」= 休業　の場合
    		if (workType.getDailyWork().getOneDay().equals(WorkTypeClassification.Closure)) {
    			// ・「休職休業履歴項目．NO」 <> 「勤務種類．勤務種類設定．休業の設定」  → True
    			// ・それ以外  → False
    			boolean result = !workType.getWorkTypeSetList().stream()
    					.anyMatch(data -> data.getCloseAtr().value + 2 == histItem.getTempAbsenceFrNo());
    			return result;
    		}
    	}
    	return false;
    }
    
    private Map<GeneralDate, String> getWorkTypeCodeMap(ProcessExecutionTask processExecutionTask, String sid, DatePeriod period) {
    	switch (processExecutionTask) {
		case SCH_CREATION:
			// 「勤務予定Repository．get*()」を実行する
			return this.workScheduleRepository.getListBySid(sid, period).stream()
					.collect(Collectors.toMap(WorkSchedule::getYmd, data -> data.getWorkInfo().getRecordInfo().getWorkTypeCode().v()));
		case DAILY_CREATION:
		case DAILY_CALCULATION:
			// ドメインモデル「日別実績の勤務情報」を取得する
			return this.recordWorkInfoFunAdapter.findByEmpAndPeriod(sid, period).stream()
					.collect(Collectors.toMap(RecordWorkInfoFunAdapterDto::getWorkingDate, RecordWorkInfoFunAdapterDto::getWorkTypeCode));
		default:
			return Collections.emptyMap();
		}
    }
}
