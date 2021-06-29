package nts.uk.screen.at.app.query.kdp.kdp002.b;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiStateRepository;

/**
 * 感情状態を登録する
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).B:打刻結果確認.メニュー別OCD.打刻入力でニコニコマークを指定する
 * 
 * @author chungnt
 *
 */

@Stateless
public class RegisterEmotionalStateCommandhandler extends CommandHandler<RegisterEmotionalStateCommand> {

	@Inject
	private EmployeeEmojiStateRepository employeeEmojiStateRepo;

	@Override
	protected void handle(CommandHandlerContext<RegisterEmotionalStateCommand> context) {
		RegisterEmotionalStateCommand command = context.getCommand();

		Optional<EmployeeEmojiState> employeeEmojiState = this.employeeEmojiStateRepo.getBySidAndDate(command.getSid(),
				command.getDate());
		EmployeeEmojiState.MementoGetter memento = new EmployeeEmojiState.MementoGetter() {
			@Override
			public GeneralDate getDate() {
				return command.getDate();
			}

			@Override
			public Integer getEmojiType() {
				return command.getEmoji();
			}

			@Override
			public String getSid() {
				return command.getSid();
			}
		};
		

		EmployeeEmojiState domain = employeeEmojiState.map(dm -> {
			dm.getMemento(memento);
			return dm;
		}).orElse(EmployeeEmojiState.createFromMemento(memento));
		
		// update or insert
		
		if (employeeEmojiState.isPresent()) {
			employeeEmojiStateRepo.update(domain);
		} else {
			employeeEmojiStateRepo.insert(domain);
		}
		
	}
}
