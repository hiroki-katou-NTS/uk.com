package nts.uk.ctx.at.schedule.app.command.schedule.basicschedule;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
/**
 * 決定ボタン押下時処理
 * 
 * @author sonnh1
 *
 */
@Stateless
public class BasicScheduleUpdateCommandHandler extends CommandHandler<BasicScheduleUpdateCommand> {

	@Inject
	private BasicScheduleRepository basicScheduleRepository;

	@Override
	protected void handle(CommandHandlerContext<BasicScheduleUpdateCommand> context) {
		BasicScheduleUpdateCommand command = context.getCommand();
		List<BasicSchedule> listBasicScheduleToUpdate = new ArrayList<>();
		List<String> employeeIds = command.getEmployeeIds();
		List<GeneralDate> dates = command.getDates();
		int confirmedAtrValue = command.getConfirmedAtr();
		// boolean checkedHandler = command.isCheckedHandler();
		
		// エラーチェック(error check)
		if(employeeIds.isEmpty()){
			throw new BusinessException("Msg_499");
		} 
		if(dates.isEmpty()){
			throw new BusinessException("Msg_500");
		}
		
		GeneralDate maxDate = dates.stream().max(Comparator.comparing(GeneralDate::dayOfYear)).get();
		GeneralDate minDate = dates.stream().min(Comparator.comparing(GeneralDate::dayOfYear)).get();
		DatePeriod datePeriod = new DatePeriod(minDate, maxDate);
		List<BasicSchedule> listBasicSchedule = basicScheduleRepository.findSomePropertyWithJDBC(employeeIds, datePeriod);
		
		// 対象者、対象日付でループして処理(loop xử lý theo đối tượng nhân viên , ngày đã chọn)
		employeeIds.stream().forEach((employeeId) -> {
			dates.stream().forEach(date -> {
				// アルゴリズム「確定/解除処理」(thực hiện xử lý 「確定/解除処理」)
				// 対象者、対象日付のスケジュールが存在するかチェック(kiểm tra vào ngày đang xử lý cảu nhân viên đang xử lý có dữ liệu dự định (schedule) hay không)
				// TODO
				
				Optional<BasicSchedule> optional = listBasicSchedule.stream().filter(x -> (x.getEmployeeId().equals(employeeId) && x.getDate().compareTo(date) == 0)).findFirst();
				if (optional.isPresent()) {
					BasicSchedule basicSchedule = optional.get();
					if(basicSchedule.getConfirmedAtr().value != confirmedAtrValue) {
						basicSchedule.setConfirmedAtr(ConfirmedAtr.valueOf(Integer.valueOf(confirmedAtrValue)));
						listBasicScheduleToUpdate.add(basicSchedule);
					}
				}
			});
		});
		// ドメインモデル「勤務予定基本情報」を更新する(update domain 「勤務予定基本情報」)
		basicScheduleRepository.updateConfirmAtr(listBasicScheduleToUpdate);
		// 手修正解除するかどうかチェック(kiểm tra có xóa sửa bằng tay hay không)
		// TODO
	}

}
