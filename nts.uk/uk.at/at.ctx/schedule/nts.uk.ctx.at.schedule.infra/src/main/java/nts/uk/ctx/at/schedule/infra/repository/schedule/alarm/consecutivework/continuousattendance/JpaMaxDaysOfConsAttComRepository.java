package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.consecutivework.continuousattendance;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsAttComRepository;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance.MaxDaysOfConsecutiveAttendanceCompany;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.continuouswork.continuousattendance.KscmtAlchkConsecutiveWorkCmp;

/**
 * 
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaMaxDaysOfConsAttComRepository extends JpaRepository implements MaxDaysOfConsAttComRepository {

	@Override
	public void insert(String companyId, MaxDaysOfConsecutiveAttendanceCompany maxContAttCom) {
		this.commandProxy().insert(KscmtAlchkConsecutiveWorkCmp.of(companyId, maxContAttCom));
	}

	@Override
	public void update(String companyId, MaxDaysOfConsecutiveAttendanceCompany maxContAttCom) {
		
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
	public Optional<MaxDaysOfConsecutiveAttendanceCompany> get(String companyId) {
		
		String sql = "SELECT * FROM KSCMT_ALCHK_CONSECUTIVE_WORK_CMP WHERE CID = @companyId";
		
		return new NtsStatement(sql, this.jdbcProxy())
				.paramString("companyId", companyId)
				.getSingle(x -> KscmtAlchkConsecutiveWorkCmp.MAPPER.toEntity(x).toDomain());
	}

}
