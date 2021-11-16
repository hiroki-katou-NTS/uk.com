package nts.uk.ctx.exio.infra.repository.input.transfer;

import java.util.List;
import java.util.stream.Collectors;

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
	public int execute(List<ConversionSQL> conversionSqls) {
		
		// 正準化の既存データ補正のSQLがまだDBに流されてない可能性がある。
		// そうすると、この移送処理はJDBC経由なので、データの不整合が起きてしまう。
		// それを避けるためにここでflushしておく。
		this.getEntityManager().flush();
		
		List<String> sqls = conversionSqls.stream()
				.map(conversionSql -> buildSql(conversionSql))
				.collect(Collectors.toList());
		int result = executeTransfer(String.join(";\r\n\r\n", sqls) );
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
