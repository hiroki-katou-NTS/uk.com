package nts.uk.ctx.at.record.app.command.knr.knr002.c;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ReqComStatusMonitoring;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class RegisterTimeRecordSetUpdateListCommandHandler extends CommandHandler<List<TimeRecordSetUpdateList>> {

	@Inject
	private TimeRecordSetUpdateListRepository timeRecordSetUpdateListRepository;
	
	@Override
	protected void handle(CommandHandlerContext<List<TimeRecordSetUpdateList>> context) {
		
		List<TimeRecordSetUpdateList> listReqComStatusMonitoring = context.getCommand();
		
		// 1:create(契約コード、就業情報端末コード、タイムレコード設定更新リスト)
		timeRecordSetUpdateListRepository.insert(listReqComStatusMonitoring);
	}	
}
