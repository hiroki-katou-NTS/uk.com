package nts.uk.ctx.at.record.app.command.dailyperformanceformat;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.DailyAttendanceItemAuthority;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.repository.DailyAttdItemAuthRepository;

@Stateless
public class UpdateAttdItemAuthCommandHandler extends CommandHandler<List<UpdateAttdItemAuthCommand>> {
	@Inject
	private DailyAttdItemAuthRepository dailyAttdItemAuthRepository;

	@Override
	protected void handle(CommandHandlerContext<List<UpdateAttdItemAuthCommand>> context) {
		List<UpdateAttdItemAuthCommand> lstUpdateAttdItemAuthCommand = context.getCommand();
		this.dailyAttdItemAuthRepository.updateListDailyAttendanceItemAuthority(
				lstUpdateAttdItemAuthCommand.stream().map(c -> toDomain(c)).collect(Collectors.toList()));
	}

	private DailyAttendanceItemAuthority toDomain(UpdateAttdItemAuthCommand updateAttdItemAuthCommand) {
		return DailyAttendanceItemAuthority.createFromJavaType(updateAttdItemAuthCommand.attendanceItemId,
				updateAttdItemAuthCommand.authorityId, updateAttdItemAuthCommand.youCanChangeIt,
				updateAttdItemAuthCommand.canBeChangedByOthers, updateAttdItemAuthCommand.use, 1);
	}

}
