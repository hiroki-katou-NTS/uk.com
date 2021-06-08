package nts.uk.ctx.exio.app.command.input.transfer;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMetaRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalData;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalDataRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class TransferCanonicalDataCommandHandler extends CommandHandler<TransferCanonicalDataCommand> {
	
	@Inject
	CanonicalizedDataMetaRepository metaRepo;
	@Inject
	ConversionTableRepository cnvRepo;
	@Inject
	TransferCanonicalDataRepository repository;

	@Override
	protected void handle(CommandHandlerContext<TransferCanonicalDataCommand> command) {
		val require = new RequireImpl();
		ExecutionContext context = new ExecutionContext(
				command.getCommand().getCompanyId(),
				command.getCommand().getSettingCode(),
				command.getCommand().getGroupId(),
				ImportingMode.valueOf(command.getCommand().getMode())
			);
		AtomTask result = TransferCanonicalData.transferAll(require, context);
		result.run();
	}

	private class RequireImpl implements TransferCanonicalData.Require{

		@Override
		public List<String> getImportiongItem() {
			String cid = AppContexts.user().companyId();
			return metaRepo.get(cid);
		}

		@Override
		public List<ConversionTable> getConversionTable(int groupId, ConversionCodeType cct) {
			val source = cnvRepo.getSource(groupId);
			return cnvRepo.get(groupId, source, cct);
		}

		@Override
		public int execute(List<ConversionSQL> conversionSqls) {
			return repository.execute(conversionSqls);
		}
		
	}
}
