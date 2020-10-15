package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal.remote;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.SettingValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdate;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetUpdateList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetUpdateListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteUpdate;

@Stateless
public class JpaTimeRecordSetUpdateListRepository extends JpaRepository implements TimeRecordSetUpdateListRepository {

	private static final String FIND = "select t from KrcdtTrRemoteUpdate t where t.pk.contractCode = :contractCode and t.pk.timeRecordCode = :trCode ";

	// [1] タイムレコード設定更新リストを取得する
	@Override
	public Optional<TimeRecordSetUpdateList> findSettingUpdate(EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {
		List<KrcdtTrRemoteUpdate> lstEntity = this.queryProxy().query(FIND, KrcdtTrRemoteUpdate.class)
				.setParameter("contractCode", contractCode.v()).setParameter("trCode", empInfoTerCode.v()).getList();
		return Optional.ofNullable(toDomain(lstEntity));
	}

	private TimeRecordSetUpdateList toDomain(List<KrcdtTrRemoteUpdate> lstEntity) {

		if (lstEntity.isEmpty())
			return null;
		List<TimeRecordSetUpdate> lstTRecordSetUpdate = lstEntity.stream()
				.map(x -> new TimeRecordSetUpdate(new VariableName(x.pk.variableName), new SettingValue(x.updateValue)))
				.collect(Collectors.toList());
		return new TimeRecordSetUpdateList(new EmpInfoTerminalCode(lstEntity.get(0).pk.timeRecordCode),
				new EmpInfoTerminalName(lstEntity.get(0).empInfoTerName), new NRRomVersion(lstEntity.get(0).romVersion),
				ModelEmpInfoTer.valueOf(lstEntity.get(0).modelEmpInfoTer), lstTRecordSetUpdate);

	}
}
