package nts.uk.ctx.exio.infra.repository.input.transfer;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nemunoki.oruta.shr.tabledefinetype.databasetype.PostgresSpec;
import nemunoki.oruta.shr.tabledefinetype.databasetype.SqlServerSpec;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.database.DatabaseProduct;
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
		if(this.database().is(DatabaseProduct.MSSQLSERVER)) {
			return conversionSql.build(new SqlServerSpec());
		}
		else if (this.database().is(DatabaseProduct.POSTGRESQL)) {
			return conversionSql.build(new PostgresSpec());
		}
		throw new RuntimeException("Current database products are not yet supported!");
	}

	@SneakyThrows
	private int executeTransfer(String sql) {
		return this.connection().prepareStatement(sql).executeUpdate();
	}

}
