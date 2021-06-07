package nts.uk.ctx.exio.infra.repository.input.transfer;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import lombok.val;
import nemunoki.oruta.shr.tabledefinetype.databasetype.DatabaseType;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalDataRepository;

@Stateless
public class TransferCanonicalDataRepositoryImpl extends JpaRepository implements TransferCanonicalDataRepository {

	@Override
	public int execute(ConversionSQL conversionSql) {
		String sql = buildSql(conversionSql);
		int result = executeTransfer(sql);
		return result;
	}
	
	private String buildSql(ConversionSQL conversionSql) {
		val spec = DatabaseType.parse(this.database()).spec();
		return conversionSql.build(spec);
	}

	@SneakyThrows
	private int executeTransfer(String sql) {
		return this.connection().prepareStatement(sql).executeUpdate();
	}

}
