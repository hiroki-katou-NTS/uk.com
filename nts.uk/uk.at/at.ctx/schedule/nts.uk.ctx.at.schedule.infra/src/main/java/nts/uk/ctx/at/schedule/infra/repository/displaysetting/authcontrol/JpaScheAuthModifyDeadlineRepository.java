package nts.uk.ctx.at.schedule.infra.repository.displaysetting.authcontrol;

import java.util.Optional;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadline;
import nts.uk.ctx.at.schedule.dom.displaysetting.authcontrol.ScheAuthModifyDeadlineRepository;
import nts.uk.ctx.at.schedule.infra.entity.displaysetting.authcontrol.KscmtAuthModifyDadline;

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
		KscmtAuthModifyDadline entity = KscmtAuthModifyDadline.of(companyId, modifyDeadline);
		
		KscmtAuthModifyDadline upData = this.queryProxy()
				.find(entity.kscmtAuthModifyDadlinePk, KscmtAuthModifyDadline.class)
				.get();
		
		upData.setUseAtr(modifyDeadline.getUseAtr().value);
		upData.setDeadLine(modifyDeadline.getDeadLine().v());
		
		this.commandProxy().update(upData);
	}
	
	@Override
	public void delete (String companyId, String roleId) {
		Optional<ScheAuthModifyDeadline> entity = this.get(companyId, roleId);
		if (entity.isPresent()) {
			this.commandProxy().remove(entity);
		}
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
