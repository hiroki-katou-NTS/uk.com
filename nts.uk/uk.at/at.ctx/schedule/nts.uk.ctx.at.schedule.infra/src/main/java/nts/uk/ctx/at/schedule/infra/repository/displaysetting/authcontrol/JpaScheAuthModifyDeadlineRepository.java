package nts.uk.ctx.at.schedule.infra.repository.displaysetting.authcontrol;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadline;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadlineRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthModifyDadline;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthModifyDadlinePk;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaScheAuthModifyDeadlineRepository extends JpaRepository implements ScheAuthModifyDeadlineRepository {

	@Override
	public void insert (String companyId, ScheAuthModifyDeadline modifyDeadline) {
		this.commandProxy().insert(KscmtAuthModifyDadline.of(companyId, modifyDeadline));
	}
	
	@Override
	public void update (String companyId, ScheAuthModifyDeadline modifyDeadline) {
		val pk = new KscmtAuthModifyDadlinePk(companyId, modifyDeadline.getRoleId());
		
		KscmtAuthModifyDadline upData = this.queryProxy()
				.find(pk, KscmtAuthModifyDadline.class)
				.get();
		
		upData.useAtr = modifyDeadline.getUseAtr().value;
		upData.deadLine = modifyDeadline.getDeadLine().v();
		
		this.commandProxy().update(upData);
	}
	
	@Override
	public void delete (String companyId, String roleId) {
		val pk = new KscmtAuthModifyDadlinePk(companyId, roleId);		
		this.commandProxy().remove(KscmtAuthModifyDadline.class, pk);
	}
	
	@Override
	public Optional<ScheAuthModifyDeadline> get (String companyId, String roleId) {
		
		String sql = "SELECT * FROM KSCMT_AUTH_MODIFYDEADLINE"
				+ " WHERE CID = @companyId"
				+ " AND ROLE_ID = @roleId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.paramString("roleId", roleId)
				.getSingle(x -> KscmtAuthModifyDadline.MAPPER.toEntity(x).toDomain());
	}
}
