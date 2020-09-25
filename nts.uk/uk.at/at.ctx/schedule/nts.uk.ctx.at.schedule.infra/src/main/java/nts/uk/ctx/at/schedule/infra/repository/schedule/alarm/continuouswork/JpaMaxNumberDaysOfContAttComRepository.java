package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.continuouswork;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance.MaxNumberDaysOfContAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance.MaxNumberDaysOfContinuousAttendanceCom;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.KscmtAlchkConsecutiveWorkCmp;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaMaxNumberDaysOfContAttComRepository extends JpaRepository implements MaxNumberDaysOfContAttComRepository {

	@Override
	public void insert(String companyId, MaxNumberDaysOfContinuousAttendanceCom maxContAttCom) {
		this.commandProxy().insert(KscmtAlchkConsecutiveWorkCmp.of(companyId, maxContAttCom));
	}

	@Override
	public void update(String companyId, MaxNumberDaysOfContinuousAttendanceCom maxContAttCom) {
		
		KscmtAlchkConsecutiveWorkCmp updata = this.queryProxy()
				.find(companyId, KscmtAlchkConsecutiveWorkCmp.class)
				.get();
		
		updata.maxConsDays = maxContAttCom.getNumberOfDays().getNumberOfDays().v();
		
		this.commandProxy().update(updata);
	}

	@Override
	public void delete(String companyId) {
		this.commandProxy().remove(KscmtAlchkConsecutiveWorkCmp.class, companyId);
	}

	@Override
	public boolean exists(String companyId) {
		return this.get(companyId).isPresent();
	}

	@Override
	public Optional<MaxNumberDaysOfContinuousAttendanceCom> get(String companyId) {
		
		String sql = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WORK_CMP WHERE CID = @companyId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(x -> KscmtAlchkConsecutiveWorkCmp.MAPPER.toEntity(x).toDomain());
	}

}
