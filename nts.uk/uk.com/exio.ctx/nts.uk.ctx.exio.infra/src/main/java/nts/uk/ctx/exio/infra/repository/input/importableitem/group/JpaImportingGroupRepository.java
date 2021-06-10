package nts.uk.ctx.exio.infra.repository.input.importableitem.group;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupRepository;
import nts.uk.ctx.exio.infra.entity.input.importableitem.group.XimctGroup;

@Stateless
@TransactionAttribute
public class JpaImportingGroupRepository extends JpaRepository implements ImportingGroupRepository {

	@Override
	public ImportingGroup find(int groupId) {
		
		String sql = "select * from XIMCT_GROUP"
				+ " where GROUP_ID = @id";
		
		return this.jdbcProxy().query(sql)
				.paramInt("id", groupId)
				.getSingle(rec -> XimctGroup.MAPPER.toEntity(rec))
				.map(e -> e.toDomain())
				.get();
	}

}
