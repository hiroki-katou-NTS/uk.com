package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
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
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteUpdatePK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTimeRecordSetUpdateListRepository extends JpaRepository implements TimeRecordSetUpdateListRepository {

	private static final String FIND = "select t from KrcdtTrRemoteUpdate t where t.pk.contractCode = :contractCode and t.pk.timeRecordCode = :trCode ";
	
	private static final String FIND_CONTRACTCD_LISTCD = "SELECT t FROM KrcdtTrRemoteUpdate t WHERE t.pk.contractCode = :contractCode and t.pk.timeRecordCode IN :listCode"; 

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
	
	private List<TimeRecordSetUpdateList> toListDomain(List<KrcdtTrRemoteUpdate> lstEntity) {	
		List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList = new ArrayList<TimeRecordSetUpdateList>();
		Map<String, List<KrcdtTrRemoteUpdate>> results = lstEntity.stream().collect(Collectors.groupingBy(KrcdtTrRemoteUpdate::getGroupByString));
		results.entrySet().stream().forEach(e -> {
			TimeRecordSetUpdateList timeRecordSetUpdateList = toDomain(e.getValue());
			listTimeRecordSetUpdateList.add(timeRecordSetUpdateList);
		});
		return listTimeRecordSetUpdateList;
	}
	
	private List<KrcdtTrRemoteUpdate> toEntity(TimeRecordSetUpdateList domain) {	
		List<TimeRecordSetUpdate> listTimeRecordSetUpdate = domain.getLstTRecordSetUpdate();
		List<KrcdtTrRemoteUpdate> listEntity = listTimeRecordSetUpdate.stream()
				.map(x -> new KrcdtTrRemoteUpdate(new KrcdtTrRemoteUpdatePK(AppContexts.user().contractCode(), domain.getEmpInfoTerCode().v(), x.getVariableName().v()), 
													domain.getEmpInfoTerName().v(), domain.getRomVersion().v(), domain.getModelEmpInfoTer().value, x.getUpdateValue().v()))
				.collect(Collectors.toList());
		return listEntity;
	}

	@Override
	public List<TimeRecordSetUpdateList> get(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode) {
		List<KrcdtTrRemoteUpdate> listEntity = this.queryProxy().query(FIND_CONTRACTCD_LISTCD, KrcdtTrRemoteUpdate.class)
				.setParameter("contractCode", AppContexts.user().contractCode())
				.setParameter("listCode", listEmpInfoTerCode).getList();
		return toListDomain(listEntity);
	}

	@Override
	public void insert(List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList) {
		listTimeRecordSetUpdateList.stream().forEach(x -> this.commandProxy().insertAll(toEntity(x)));
	}

	@Override
	public void delete(List<TimeRecordSetUpdateList> listTimeRecordSetUpdateList) {
		listTimeRecordSetUpdateList.stream().forEach(x -> this.commandProxy().removeAll(toEntity(x)));
		
	}
}
