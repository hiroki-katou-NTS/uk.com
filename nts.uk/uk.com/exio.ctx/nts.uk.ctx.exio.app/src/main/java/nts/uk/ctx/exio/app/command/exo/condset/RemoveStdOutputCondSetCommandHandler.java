package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetRepository;
import nts.uk.ctx.exio.dom.exo.outcnddetail.OutCndDetailRepository;
import nts.uk.ctx.exio.dom.exo.outputitem.StandardOutputItemRepository;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrderRepository;

@Stateless
@Transactional
public class RemoveStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand> {

	@Inject
	private StdOutputCondSetRepository stdOutputCondSetRepository;
	
	@Inject
	private StandardOutputItemRepository standardOutputItemRepository;
	
	@Inject
	private StandardOutputItemOrderRepository standardOutputItemOrderRepository;
	
	@Inject
	private OutCndDetailRepository outCndDetailRepository;

	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		String cid = context.getCommand().getCid();
		String condSetCd = context.getCommand().getConditionSetCd();
		stdOutputCondSetRepository.remove(cid, condSetCd);
		standardOutputItemRepository.remove(cid, condSetCd);
		standardOutputItemOrderRepository.remove(cid,condSetCd);
		outCndDetailRepository.remove(cid, condSetCd);
	}
}
