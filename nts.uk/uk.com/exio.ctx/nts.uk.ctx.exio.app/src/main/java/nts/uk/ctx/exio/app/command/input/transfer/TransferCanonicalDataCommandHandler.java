package nts.uk.ctx.exio.app.command.input.transfer;

import java.util.List;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataMeta;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalData;

@Stateless
public class TransferCanonicalDataCommandHandler extends CommandHandler<TransferCanonicalDataCommand> {

	@Override
	protected void handle(CommandHandlerContext<TransferCanonicalDataCommand> context) {
		val require = new RequireImpl();
		TransferCanonicalData.transfer(require);
	}

	private class RequireImpl implements TransferCanonicalData.Require{

		@Override
		public CanonicalizedDataMeta getMetaData() {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public List<ConversionTable> getConversionTable(int groupId) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public List<String> getEnabledColumnList(String tableName) {
			// TODO 自動生成されたメソッド・スタブ
			return null;
		}

		@Override
		public int execute(ConversionSQL conversionSql) {
			// TODO 自動生成されたメソッド・スタブ
			return 0;
		}
		
	}
}
