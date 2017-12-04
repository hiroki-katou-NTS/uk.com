package nts.uk.ctx.pereg.app.command.person.setting.selectionitem.selection;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelection;
import nts.uk.ctx.pereg.dom.person.setting.selectionitem.PerInfoHistorySelectionRepository;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * 
 * @author tuannv
 *
 */
@Stateless
public class EditHistoryCommandHandler extends CommandHandler<EditHistoryCommand> {

	@Inject
	private PerInfoHistorySelectionRepository repoHistSel;

	@Override
	protected void handle(CommandHandlerContext<EditHistoryCommand> context) {
		EditHistoryCommand data = context.getCommand();
		// Kim tra loi lich su: アルゴリズム「履歴エラーチェック」を実行する
		//最新の履歴のみ変更可能 (Chỉ có thể cập nhật lịch sử mới nhất)=> #Msg_154
		if(!data.getEndDate().equals("9999/12/31")){
			throw new BusinessException("Msg_154");
		}
		GeneralDate startDate = GeneralDate.fromString(data.getStartDate(), "yyyy/MM/dd");
		GeneralDate startDateNew = GeneralDate.fromString(data.getStartDateNew(), "yyyy/MM/dd");
		GeneralDate endDatePr = startDate.addDays(-1);
		//get history current
		Optional<PerInfoHistorySelection> histCurOpstional = this.repoHistSel.getHistSelByHistId(data.getHistId());
		PerInfoHistorySelection histCur = histCurOpstional.get();
		List<PerInfoHistorySelection> lstHistPr = repoHistSel.getHistSelByEndDate(data.getSelectionItemId(), histCur.getCompanyId(), endDatePr);
		if(!lstHistPr.isEmpty()){
			PerInfoHistorySelection histPr = lstHistPr.get(0);
			//直前の履歴の開始日以前に開始日を変更することはできない。 (Không thể sửa ngày bắt đầu về trước ngày bắt đầu của lịch sử trước đấy)
			//=>#Msg_127
			if(startDateNew.beforeOrEquals(histPr.getPeriod().start())){
				throw new BusinessException("Msg_127");
			}
			GeneralDate endDatePrNew = startDateNew.addDays(-1);
			DatePeriod periodPrNew = new DatePeriod(histPr.getPeriod().start(), endDatePrNew);
			histPr.updateDate(periodPrNew);
			//update lich su truoc do
			this.repoHistSel.update(histPr);
		}
		// Cap nhat domain: ドメインモデル「選択肢履歴」を更新する
		DatePeriod periodNew = new DatePeriod(startDateNew, GeneralDate.fromString("9999/12/31", "yyyy/MM/dd"));
		histCur.updateDate(periodNew);
		//update lich su hien tai
		this.repoHistSel.update(histCur);
	}

}
