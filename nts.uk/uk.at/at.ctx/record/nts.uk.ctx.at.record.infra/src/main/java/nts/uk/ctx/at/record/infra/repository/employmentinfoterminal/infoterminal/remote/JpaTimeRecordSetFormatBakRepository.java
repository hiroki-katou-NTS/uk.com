package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteBackup;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteBackupPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTimeRecordSetFormatBakRepository extends JpaRepository implements TimeRecordSetFormatBakRepository {

	@Override
	public void insert(TimeRecordSetFormatBak timeRecordSetFormatBak) {
		this.commandProxy().insertAll(toEntity(timeRecordSetFormatBak));
	}

	@Override
	public void update(TimeRecordSetFormatBak timeRecordSetFormatBak) {
		List<KrcdtTrRemoteBackup> listEntity = new ArrayList<KrcdtTrRemoteBackup>();
		timeRecordSetFormatBak.getListTimeRecordSetFormat().stream()
			.forEach(e -> {
				Optional<KrcdtTrRemoteBackup> entity = this.queryProxy().find(new KrcdtTrRemoteBackupPK(AppContexts.user().companyCode(), Integer.parseInt(timeRecordSetFormatBak.getEmpInfoTerCode().v()), e.getVariableName().v()) , KrcdtTrRemoteBackup.class);
				if (entity.isPresent()) {
					entity.get().setModelName(timeRecordSetFormatBak.getEmpInfoTerName().v());
					entity.get().setRomVersion(timeRecordSetFormatBak.getRomVersion().v());
					entity.get().setModelType(timeRecordSetFormatBak.getModelEmpInfoTer().value);
					entity.get().setBackupDate(timeRecordSetFormatBak.getBackupDate());
					entity.get().setMajorNo(e.getMajorNo().v());
					entity.get().setMajorName(e.getMajorClassification().v());
					entity.get().setSmallNo(e.getSmallNo().v());
					entity.get().setSmallName(e.getSmallClassification().v());
					entity.get().setInputType(e.getType().value);
					entity.get().setNumberDigist(e.getNumberOfDigits().v());
					entity.get().setSettingValue(e.getSettingValue().v());
					entity.get().setInputRange(e.getInputRange().v());
					entity.get().setReboot(e.isRebootFlg() ? 1 : 0);
					entity.get().setCurrentValue(e.getCurrentValue().v());
					listEntity.add(entity.get());
				}
			});
		this.commandProxy().updateAll(listEntity);
	}

	@Override
	public void delete(TimeRecordSetFormatBak timeRecordSetFormatBak) {
		this.commandProxy().removeAll(toEntity(timeRecordSetFormatBak));
		
	}

	@Override
	public List<TimeRecordSetFormatBak> get(ContractCode contractCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeRecordSetFormat> getTimeRecordSetFormat(ContractCode contractCode, EmpInfoTerminalCode code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<TimeRecordSetFormatBak> get(ContractCode contractCode, EmpInfoTerminalCode code) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private List<KrcdtTrRemoteBackup> toEntity(TimeRecordSetFormatBak domain) {
		List<TimeRecordSetFormat> listTimeRecordSetFormat = domain.getListTimeRecordSetFormat();
		return listTimeRecordSetFormat.stream()
					.map(e -> new KrcdtTrRemoteBackup(
							new KrcdtTrRemoteBackupPK(AppContexts.user().companyCode(), Integer.parseInt(domain.getEmpInfoTerCode().v()), e.getVariableName().v()),
							domain.getEmpInfoTerName().v(), domain.getRomVersion().v(), domain.getModelEmpInfoTer().value, domain.getBackupDate(),
							e.getMajorNo().v(), e.getMajorClassification().v(), e.getSmallNo().v(), e.getSmallClassification().v(), e.getType().value,
							e.getNumberOfDigits().v(), e.getSettingValue().v(), e.getInputRange().v(), e.isRebootFlg() ? 1 : 0, e.getCurrentValue().v()))
					.collect(Collectors.toList());
	}
	
	private TimeRecordSetFormatBak toDomain(List<KrcdtTrRemoteBackup> entities) {
		return null;
	}
	
	private TimeRecordSetFormatBak toDomain() {
		return null;
	}

}
