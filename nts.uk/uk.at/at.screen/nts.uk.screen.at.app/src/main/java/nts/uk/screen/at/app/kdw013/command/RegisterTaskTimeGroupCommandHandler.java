package nts.uk.screen.at.app.kdw013.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroup;
import nts.uk.ctx.at.record.dom.daily.timegroup.TaskTimeGroupRepository;

/**
 * 
 * @author sonnlb
 *
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤務実績.日別時間帯別実績.日別実績の作業時間帯グループ.App.作業時間帯グループを登録する
 */

@Stateless
public class RegisterTaskTimeGroupCommandHandler extends CommandHandler<RegisterTaskTimeGroupCommand> {

	@Inject
	private TaskTimeGroupRepository repo;

	/**
	 * 登録する
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterTaskTimeGroupCommand> context) {
		RegisterTaskTimeGroupCommand cmd = context.getCommand();

		// 1. delete(社員ID,年月日)
		this.repo.delete(cmd.getSId(), cmd.getDate());

		// 2. 時間帯リスト.isPresent: create
		if (!CollectionUtil.isEmpty(cmd.getTimezones())) {
			TaskTimeGroup timeGroup = cmd.toDomain();
			// 3. persist
			this.repo.insert(timeGroup);
		}
	}
}
