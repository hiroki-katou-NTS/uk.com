package nts.uk.ctx.exio.infra.repository.input.transfer;

import java.util.List;

import javax.ejb.Stateless;

import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;

@Stateless
public class ConversionTableRepositoryImpl implements ConversionTableRepository {

	@Override
	public ConversionSource getSource(int groupId) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public List<ConversionTable> get(int groupId, ConversionSource source) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

}
