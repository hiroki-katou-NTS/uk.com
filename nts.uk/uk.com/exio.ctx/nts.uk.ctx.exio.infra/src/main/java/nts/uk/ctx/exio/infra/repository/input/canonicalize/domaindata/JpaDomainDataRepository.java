package nts.uk.ctx.exio.infra.repository.input.canonicalize.domaindata;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaDomainDataRepository extends JpaRepository implements DomainDataRepository {

	@Override
	public boolean exists(DomainDataId id) {
		val statement = createStatement(id, "select 1");
		return statement.getSingle(rec -> 1).isPresent();
	}

	@Override
	public void delete(DomainDataId id) {
		val statement = createStatement(id, "delete");
		statement.execute();
	}

	private NtsStatement createStatement(DomainDataId id, String baseCommand) {
		
		StringBuilder sql = new StringBuilder()
				.append(" from ")
				.append(id.getTableName())
				.append(" where ");
		
		for (int i = 0; i < id.getKeys().size(); i++) {
			
			if (i > 0) {
				sql.append(" and ");
			}
			
			val column = id.getKeys().get(i).getColumn().getColumnName();
			sql.append(column).append(" = @p").append(i);
		}
		
		val statement = this.jdbcProxy().query(sql.toString());
		
		for (int i = 0; i < id.getKeys().size(); i++) {
			val key = id.getKeys();
			key.get(i).setParam(statement, "p" + i);
		}
		
		return statement;
	}

}
