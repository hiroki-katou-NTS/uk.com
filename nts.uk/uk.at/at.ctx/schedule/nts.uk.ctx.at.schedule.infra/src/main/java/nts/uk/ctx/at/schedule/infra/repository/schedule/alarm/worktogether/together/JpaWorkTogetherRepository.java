package nts.uk.ctx.at.schedule.infra.repository.schedule.alarm.worktogether.together;

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
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogether;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.together.WorkTogetherRepository;
import nts.uk.ctx.at.schedule.infra.entity.schedule.alarm.worktogether.together.KscmtAlchkWorkPair;

/**
 * 同時出勤指定Repository
 * @author hiroko_miura
 *
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaWorkTogetherRepository extends JpaRepository implements WorkTogetherRepository {

	@Override
	public void insert(WorkTogether simulAttDes) {
		this.commandProxy().insertAll(KscmtAlchkWorkPair.toEntityList(simulAttDes));
	}

	@Override
	public void update(WorkTogether simulAttDes) {
		this.commandProxy().updateAll(KscmtAlchkWorkPair.toEntityList(simulAttDes));
	}

	@Override
	public void delete(String employeeId) {
		Optional<WorkTogether> domain = this.get(employeeId);
		
		if (domain.isPresent()) {
			this.commandProxy().removeAll(KscmtAlchkWorkPair.toEntityList(domain.get()));
		}
	}

	@Override
	public List<WorkTogether> getAll(String companyId) {
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
	public Optional<WorkTogether> get(String employeeId) {
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
	public List<WorkTogether> getWithEmpIdList(List<String> employeeIdList) {
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
