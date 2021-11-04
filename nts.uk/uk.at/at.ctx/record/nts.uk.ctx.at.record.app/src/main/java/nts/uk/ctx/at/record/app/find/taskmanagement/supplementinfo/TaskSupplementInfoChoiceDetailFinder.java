package nts.uk.ctx.at.record.app.find.taskmanagement.supplementinfo;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordAndAttendanceItemLink;
import nts.uk.ctx.at.record.dom.jobmanagement.manhourrecorditem.ManHourRecordAndAttendanceItemLinkRepository;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 作業補足情報の選択肢詳細を取得する
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.作業管理.作業補足情報項目設定.App.作業補足情報の選択肢詳細を取得する
 * @author quytb
 *
 */

@Stateless
public class TaskSupplementInfoChoiceDetailFinder {
	
	@Inject
	ManHourRecordAndAttendanceItemLinkRepository manHourRecordAndAttendanceItemLinkRepository;
	
	@Inject
	TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepository;
	
	public List<TaskSupInfoChoiceDetailDto> GetTaskSupInfoChoiceDetail(int atdId, GeneralDate baseDate) {
		/** 1.Get(ログイン会社ID,勤怠項目ID) */
		String companyId = AppContexts.user().companyId();
	
		Optional<ManHourRecordAndAttendanceItemLink> optional = manHourRecordAndAttendanceItemLinkRepository.get(companyId, atdId);
		
		if(!optional.isPresent()) {
			return new ArrayList<TaskSupInfoChoiceDetailDto>();			
		} 
		List<TaskSupInfoChoicesDetail> list = taskSupInfoChoicesHistoryRepository.get(companyId, optional.get().getItemId(), baseDate);
		return list.stream().map(x ->{
			return new TaskSupInfoChoiceDetailDto(x.getCode().v(), x.getName().v(), x.getItemId());
		}).collect(Collectors.toList());
	}
}
