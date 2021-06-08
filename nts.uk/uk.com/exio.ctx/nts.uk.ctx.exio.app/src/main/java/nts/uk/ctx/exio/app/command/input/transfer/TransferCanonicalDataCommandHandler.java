package nts.uk.ctx.exio.app.command.input.transfer;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMetaRepository;
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
	protected void handle(CommandHandlerContext<TransferCanonicalDataCommand> context) {
		val require = new RequireImpl();
		TransferCanonicalData.transfer(require, null);
	}

	private class RequireImpl implements TransferCanonicalData.Require{

		@Override
		public CanonicalizedDataMeta getMetaData() {
			String cid = AppContexts.user().companyId();
			return metaRepo.get(cid);
		}

		@Override
		public List<ConversionTable> getConversionTable(int groupId) {
			val source = cnvRepo.getSource(groupId);
			return cnvRepo.get(groupId, source);
		}

		@Override
		public int execute(ConversionSQL conversionSql) {
			return repository.execute(conversionSql);
		}
		
	}
}
