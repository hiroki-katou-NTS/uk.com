package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.simultaneousattendance.designation;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsStatement;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation.SimultaneousAttendanceDesignation;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.designation.SimultaneousAttendanceDesignationRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.simultaneousattendance.designation.KscmtAlchkWorkPair;

/**
 * 同時出勤指定Repository
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaSimultaneousAttendanceDesignationRepository extends JpaRepository implements SimultaneousAttendanceDesignationRepository {

	@Override
	public void insert(SimultaneousAttendanceDesignation simulAttDes) {
		this.commandProxy().insertAll(KscmtAlchkWorkPair.toEntityList(simulAttDes));
	}

	@Override
	public void update(SimultaneousAttendanceDesignation simulAttDes) {
		this.commandProxy().updateAll(KscmtAlchkWorkPair.toEntityList(simulAttDes));
	}

	@Override
	public void delete(String employeeId) {
		Optional<SimultaneousAttendanceDesignation> domain = this.get(employeeId);
		
		if (domain.isPresent()) {
			this.commandProxy().removeAll(KscmtAlchkWorkPair.toEntityList(domain.get()));
		}
	}

	@Override
	public List<SimultaneousAttendanceDesignation> getAll(String companyId) {
		String sql = "SELECT * FROM KSCMT_ALCHK_WORK_PAIR"
				+ " WHERE CID = @cid";
		
		List<KscmtAlchkWorkPair> emtityLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("cid", companyId)
				.getList(x -> KscmtAlchkWorkPair.MAPPER.toEntity(x));
		
		if (emtityLst.isEmpty()) {
			return Collections.emptyList();
		}
		
		Map<String, List<KscmtAlchkWorkPair>> grpByEmployeeId = emtityLst.stream()
				.collect(Collectors.groupingBy(x -> x.pk.employeeId, Collectors.toList()));
		
		return grpByEmployeeId.entrySet().stream()
				.map(x -> KscmtAlchkWorkPair.toDomain(x.getKey(), x.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<SimultaneousAttendanceDesignation> get(String employeeId) {
		String sql = "SELECT * FROM KSCMT_ALCHK_WORK_PAIR"
				+ " WHERE SID in :sid";
		
		List<KscmtAlchkWorkPair> emtityLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("sid", employeeId)
				.getList(x -> KscmtAlchkWorkPair.MAPPER.toEntity(x));
		
		if (emtityLst.isEmpty()) {
			return Optional.empty();
		}
		
		return Optional.of(KscmtAlchkWorkPair.toDomain(employeeId, emtityLst));
	}

	@Override
	public List<SimultaneousAttendanceDesignation> getWithEmpIdList(List<String> employeeIdList) {
		String sql = "SELECT * FROM KSCMT_ALCHK_WORK_PAIR"
				+ " WHERE SID in :sid";
		
		List<KscmtAlchkWorkPair> emtityLst = new NtsStatement(sql, this.jdbcProxy())
				.paramString("sid", employeeIdList)
				.getList(x -> KscmtAlchkWorkPair.MAPPER.toEntity(x));
		
		if (emtityLst.isEmpty()) {
			return Collections.emptyList();
		}
		
		Map<String, List<KscmtAlchkWorkPair>> grpByEmployeeId = emtityLst.stream()
				.collect(Collectors.groupingBy(x -> x.pk.employeeId, Collectors.toList()));
		
		return grpByEmployeeId.entrySet().stream()
				.map(x -> KscmtAlchkWorkPair.toDomain(x.getKey(), x.getValue()))
				.collect(Collectors.toList());
	}

	@Override
	public boolean exists(String employeeId) {
		return this.get(employeeId).isPresent();
	}

}
