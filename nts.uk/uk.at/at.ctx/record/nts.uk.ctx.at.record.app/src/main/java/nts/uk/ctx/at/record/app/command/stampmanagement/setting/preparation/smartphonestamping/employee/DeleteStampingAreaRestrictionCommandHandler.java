package nts.uk.ctx.at.record.app.command.stampmanagement.setting.preparation.smartphonestamping.employee;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.EmployeeStampingAreaRestrictionSetting;
import nts.uk.ctx.at.record.dom.stampmanagement.setting.preparation.smartphonestamping.employee.StampingAreaRepository;

@TransactionAttribute(TransactionAttributeType.SUPPORTS)
@Stateless
public class DeleteStampingAreaRestrictionCommandHandler extends CommandHandler<StampingAreaCmd> {
	@Inject
	private StampingAreaRepository stampingAreaReposiroty;
	
	/*
	 * 選択社員の打刻エリア制限設定を削除する
	 * */
	@Override
	protected void handle(CommandHandlerContext<StampingAreaCmd> context) {
		
		StampingAreaCmd cmd = context.getCommand();
		if (cmd.getEmployeeId() != null && !cmd.getEmployeeId().isEmpty()) {
			Optional<EmployeeStampingAreaRestrictionSetting> result = stampingAreaReposiroty
					.findByEmployeeId(cmd.getEmployeeId());
			if (result.isPresent()) {
				stampingAreaReposiroty.deleteStampSetting(cmd.getEmployeeId());
			}
		}
	}

}
