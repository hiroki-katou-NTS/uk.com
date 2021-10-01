package nts.uk.screen.at.app.kdw006.k;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ChoiceName;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ExternalCode;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistory;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesHistoryRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.ChoiceCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.history.DateHistoryItem;

/**
 * ScreenQuery: 作業補足情報の選択肢履歴を取得する
 * UKDesign.UniversalK.就業.KDW_日別実績.KDW006_前準備.K：任意作業コード設定.メニュー別OCD.作業補足情報の選択肢履歴を取得する
 * 
 * @author chungnt
 *
 */

@Stateless
public class AcquireSelectionHistoryOfWork {

	@Inject
	private TaskSupInfoChoicesHistoryRepository taskSupInfoChoicesHistoryRepo;

	public List<AcquireSelectionHistoryOfWorkDto> get() {
		
		List<AcquireSelectionHistoryOfWorkDto> result = new ArrayList<>();
		String cid = AppContexts.user().companyId();	
		
		List<TaskSupInfoChoicesHistory> doamins = taskSupInfoChoicesHistoryRepo.getAll(cid);
		
		doamins.forEach(f -> {
			List<DateHistoryItemDto> dateHis = f.getDateHistoryItems().stream().map(m -> {
				return new DateHistoryItemDto("", m.start(), m.end());
			}).collect(Collectors.toList());
			
			result.add(new AcquireSelectionHistoryOfWorkDto(f.getItemId(), dateHis));
		});
		
		for (int i = 25 ; i < 30; i++) {
			List<DateHistoryItem> a = new ArrayList<>();
			a.add(new DateHistoryItem(i+"", new DatePeriod(GeneralDate.today(), GeneralDate.max())));
			a.add(new DateHistoryItem(i+"", new DatePeriod(GeneralDate.min(), GeneralDate.max())));
			taskSupInfoChoicesHistoryRepo.insert(new TaskSupInfoChoicesHistory(i, a),
					new TaskSupInfoChoicesDetail(i+"", i, new ChoiceCode(i+""), new ChoiceName("History"+i), Optional.of(new ExternalCode(i+"")))
					);
		}
		
		return result;
	}

}
