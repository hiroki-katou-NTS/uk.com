package nts.uk.ctx.exio.infra.repository.input.meta;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMetaRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaImportingDataMetaRepository extends JpaRepository implements ImportingDataMetaRepository {

	@Override
	public void setup(ExecutionContext context) {

		String sql = "create table " + tableName(context.getCompanyId()) + " ("
				+ "ITEM_NAME varchar(100) not null"
				+ ");";
		
		this.jdbcProxy().query(sql).execute();
	}

	@Override
	public void save(ImportingDataMeta meta) {
		
		String sql = "insert into " + tableName(meta.getCompanyId()) + " values (@name);";
		
		for (String name : meta.getItemNames()) {
			this.jdbcProxy().query(sql)
				.paramString("name", name)
				.execute();
		}
	}

	@Override
	public ImportingDataMeta find(ExecutionContext context) {
		
		String sql = "select * from " + tableName(context.getCompanyId());
		List<String> itemNames = this.jdbcProxy().query(sql).getList(rec -> rec.getString("ITEM_NAME"));
		
		return new ImportingDataMeta(context.getCompanyId(), itemNames);
	}

	private static String tableName(String companyId) {
		return "XIMTT_META_ITEMNAMES_" + companyId.replace("-", "");
	}
}
