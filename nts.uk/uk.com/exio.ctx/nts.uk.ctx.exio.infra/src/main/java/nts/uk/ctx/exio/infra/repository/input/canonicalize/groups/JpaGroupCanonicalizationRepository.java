package nts.uk.ctx.exio.infra.repository.input.canonicalize.groups;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalizationRepository;
import nts.uk.ctx.exio.infra.entity.input.canonicalize.groups.XimctGroupCanonicalization;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaGroupCanonicalizationRepository extends JpaRepository implements GroupCanonicalizationRepository {

	@Override
	public GroupCanonicalization find(int groupId) {
		
		String sql = "select * from XIMCT_GROUP_CANONICALIZATION"
				+ " where GROUP_ID = @id";
		
		val entities = this.jdbcProxy().query(sql)
				.paramInt("id", groupId)
				.getList(rec -> XimctGroupCanonicalization.MAPPER.toEntity(rec));
		
		return new XimctGroupCanonicalization.Group(entities).toDomain();
	}

}
