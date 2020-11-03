package nts.uk.ctx.at.function.infra.repository.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.aspose.pdf.Collection;

import nts.arc.layer.infra.data.DbConsts;
import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalComStatusRepository;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.KfndtTimeRecorderSignalST;
import nts.uk.ctx.at.function.infra.entity.employmentinfoterminal.infoterminal.KfndtTimeRecorderSignalSTPK;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaEmpInfoTerminalComStatusRepository extends JpaRepository implements EmpInfoTerminalComStatusRepository {
	
	private static final String GET_ONE_BY_CODE = "SELECT m FROM KfndtTimeRecorderSignalST m "
			+ "WHERE m.pk.contractCode = :contractCode "
			+ "AND m.pk.timeRecordCode = :timeRecordCode ";
	
	private static final String GET_LIST_BY_CODE_LIST = "SELECT m FROM KfndtTimeRecorderSignalST m "
			+ "WHERE m.pk.contractCode = :contractCode "
			+ "AND m.pk.timeRecordCode IN :timeRecordCodeList ";

	@Override
	public void insert(EmpInfoTerminalComStatus empInfoTerComStatus) {
		this.commandProxy().insert(KfndtTimeRecorderSignalST.toEntity(empInfoTerComStatus));
	}

	@Override
	public void update(EmpInfoTerminalComStatus empInfoTerComStatus) {
		KfndtTimeRecorderSignalSTPK key = 
				new KfndtTimeRecorderSignalSTPK(empInfoTerComStatus.getContractCode().v(), empInfoTerComStatus.getEmpInfoTerCode().v());
		KfndtTimeRecorderSignalST entity = this.queryProxy().find(key, KfndtTimeRecorderSignalST.class).get();
		entity.pk = key;
		entity.signalLastTime = empInfoTerComStatus.getSignalLastTime();
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(EmpInfoTerminalComStatus empInfoTerComStatus) {
		KfndtTimeRecorderSignalSTPK key = 
				new KfndtTimeRecorderSignalSTPK(empInfoTerComStatus.getContractCode().v(), empInfoTerComStatus.getEmpInfoTerCode().v());
		this.commandProxy().remove(KfndtTimeRecorderSignalST.class, key);
	}

	@Override
	public Optional<EmpInfoTerminalComStatus> get(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode) {
		return this.queryProxy().query(GET_ONE_BY_CODE, KfndtTimeRecorderSignalST.class)
				.setParameter("contractCode", contractCode)
				.setParameter("timeRecordCode", empInfoTerCode)
				.getSingle(m -> m.toDomain());
	}

	@Override
	public List<EmpInfoTerminalComStatus> get(ContractCode contractCode, List<EmpInfoTerminalCode> empInfoTerCodeList) {
		List<EmpInfoTerminalComStatus> list = new ArrayList<>();
		CollectionUtil.split(empInfoTerCodeList, DbConsts.MAX_CONDITIONS_OF_IN_STATEMENT, subCodeList -> {
			list.addAll(this.queryProxy().query(GET_LIST_BY_CODE_LIST, KfndtTimeRecorderSignalST.class)
					.setParameter("contractCode", contractCode)
					.setParameter("timeRecordCodeList", subCodeList)
					.getList(m->m.toDomain()));
		});
		return list;
	}

}
