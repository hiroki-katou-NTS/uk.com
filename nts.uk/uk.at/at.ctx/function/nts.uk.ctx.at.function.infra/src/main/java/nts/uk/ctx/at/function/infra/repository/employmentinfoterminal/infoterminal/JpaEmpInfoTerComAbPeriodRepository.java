package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerComAbPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerComAbPeriodRepository;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.KfndtTrSignalAbNormal;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.KfndtTrSignalAbNormalPK;

@Stateless
public class JpaEmpInfoTerComAbPeriodRepository extends JpaRepository implements EmpInfoTerComAbPeriodRepository {
	
	private final static String FIND_IN_PERIOD = "SELECT a FROM KfndtTrSignalAbNormal a WHERE a.pk.contractCode = :contractCode AND a.pk.timeRecordCode = :code AND a.pk.preTimeSuccDate <= end AND a.lastestTimeSuccDate >= start ORDER BY a.pk.preTimeSuccDate ASC";

	@Override
	public void insert(EmpInfoTerComAbPeriod empInfoTerComAbPeriod) {
		this.commandProxy().insert(KfndtTrSignalAbNormal.toEntity(empInfoTerComAbPeriod));
	}

	@Override
	public void update(EmpInfoTerComAbPeriod empInfoTerComAbPeriod) {
		KfndtTrSignalAbNormalPK key = new KfndtTrSignalAbNormalPK(empInfoTerComAbPeriod.getContractCode().v(),Integer.parseInt(empInfoTerComAbPeriod.getEmpInfoTerCode().v()), empInfoTerComAbPeriod.getLastComSuccess());
		KfndtTrSignalAbNormal entity = this.queryProxy().find(key, KfndtTrSignalAbNormal.class).get();
		entity.lastestTimeSuccDate = empInfoTerComAbPeriod.getLastestComSuccess();
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(EmpInfoTerComAbPeriod empInfoTerComAbPeriod) {
		KfndtTrSignalAbNormalPK key = new KfndtTrSignalAbNormalPK(empInfoTerComAbPeriod.getContractCode().v(),Integer.parseInt(empInfoTerComAbPeriod.getEmpInfoTerCode().v()), empInfoTerComAbPeriod.getLastComSuccess());
		this.commandProxy().remove(KfndtTrSignalAbNormal.class, key);;
	}

	@Override
	public List<EmpInfoTerComAbPeriod> getInPeriod(ContractCode contractCode, EmpInfoTerminalCode code,
			GeneralDateTime start, GeneralDateTime end) {
		return this.queryProxy().query(FIND_IN_PERIOD, KfndtTrSignalAbNormal.class)
				.setParameter("contractCode", contractCode.v())
				.setParameter("code", Integer.parseInt(code.v()))
				.setParameter("start", start.localDateTime())
				.setParameter("end", end.localDateTime())
				.getList().stream().map(e -> e.toDomain()).collect(Collectors.toList());
	}
}
