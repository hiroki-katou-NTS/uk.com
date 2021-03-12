package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal.remote;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.MajorNameClassification;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.MajorNoClassification;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NRRomVersion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NrlRemoteInputRange;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NrlRemoteInputType;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.NumberOfDigits;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.SettingValue;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatBak;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatBakRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteBackup;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcdtTrRemoteBackupPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaTimeRecordSetFormatBakRepository extends JpaRepository implements TimeRecordSetFormatBakRepository {
	
	private final static String FIND_CONTRACTCD = "SELECT m FROM KrcdtTrRemoteBackup m WHERE m.pk.contractCode = :contractCode ORDER BY m.pk.timeRecordCode ASC";
	
	private final static String FIND_CONTRACTCD_CODE = "SELECT m FROM KrcdtTrRemoteBackup m WHERE m.pk.contractCode = :contractCode AND m.pk.timeRecordCode = :code";

	@Override
	public void insert(TimeRecordSetFormatBak timeRecordSetFormatBak) {
		this.commandProxy().insertAll(toEntity(timeRecordSetFormatBak));
	}

	@Override
	public void update(TimeRecordSetFormatBak timeRecordSetFormatBak) {
		List<KrcdtTrRemoteBackup> listEntity = new ArrayList<KrcdtTrRemoteBackup>();
		timeRecordSetFormatBak.getListTimeRecordSetFormat().stream()
			.forEach(e -> {
				Optional<KrcdtTrRemoteBackup> entity = this.queryProxy().find(new KrcdtTrRemoteBackupPK(AppContexts.user().companyCode(), timeRecordSetFormatBak.getEmpInfoTerCode().v(), e.getVariableName().v()) , KrcdtTrRemoteBackup.class);
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
		List<KrcdtTrRemoteBackup> listEntity = toEntity(timeRecordSetFormatBak);
		this.commandProxy().removeAll(KrcdtTrRemoteBackup.class, listEntity.stream().map(e -> e.pk).collect(Collectors.toList()));
		this.getEntityManager().flush();
	}

	
	@Override
	public List<TimeRecordSetFormatBak> get(ContractCode contractCode) {
		List<KrcdtTrRemoteBackup> listEntity = this.queryProxy().query(FIND_CONTRACTCD, KrcdtTrRemoteBackup.class)
				.setParameter("contractCode", contractCode.v()).getList();
		return toListDomain(listEntity);
	}

	@Override
	public List<TimeRecordSetFormat> getTimeRecordSetFormat(ContractCode contractCode, EmpInfoTerminalCode code) {
		List<KrcdtTrRemoteBackup> listEntity = this.queryProxy().query(FIND_CONTRACTCD_CODE, KrcdtTrRemoteBackup.class)
				.setParameter("contractCode", contractCode.v())
				.setParameter("code", code.v())
				.getList();
		TimeRecordSetFormatBak timeRecordSetFormatBak = toDomain(listEntity);
		if (timeRecordSetFormatBak == null) {
			return Collections.emptyList();
		}
		return timeRecordSetFormatBak.getListTimeRecordSetFormat();
	}

	@Override
	public Optional<TimeRecordSetFormatBak> get(ContractCode contractCode, EmpInfoTerminalCode code) {
		List<KrcdtTrRemoteBackup> listEntity = this.queryProxy().query(FIND_CONTRACTCD_CODE, KrcdtTrRemoteBackup.class)
				.setParameter("contractCode", contractCode.v())
				.setParameter("code", code.v())
				.getList();
		return Optional.ofNullable(toDomain(listEntity));
	}
	
	private List<KrcdtTrRemoteBackup> toEntity(TimeRecordSetFormatBak domain) {
		List<TimeRecordSetFormat> listTimeRecordSetFormat = domain.getListTimeRecordSetFormat();
		return listTimeRecordSetFormat.stream()
					.map(e -> new KrcdtTrRemoteBackup(
							new KrcdtTrRemoteBackupPK(AppContexts.user().contractCode(), domain.getEmpInfoTerCode().v(), e.getVariableName().v()),
							domain.getEmpInfoTerName().v(), domain.getRomVersion().v(), domain.getModelEmpInfoTer().value, domain.getBackupDate(),
							e.getMajorNo().v(), e.getMajorClassification().v(), e.getSmallNo().v(), e.getSmallClassification().v(), e.getType().value,
							e.getNumberOfDigits().v(), e.getSettingValue().v(), e.getInputRange().v(), e.isRebootFlg() ? 1 : 0, e.getCurrentValue().v()))
					.collect(Collectors.toList());
	}
	
	private List<TimeRecordSetFormatBak> toListDomain(List<KrcdtTrRemoteBackup> listEntity) {
		List<TimeRecordSetFormatBak> listTimeRecordSetFormatBak = new ArrayList<TimeRecordSetFormatBak>();
		Map<String, List<KrcdtTrRemoteBackup>> results= listEntity.stream()
				.collect(Collectors.groupingBy(KrcdtTrRemoteBackup::groupByString));
		results.entrySet().stream().forEach(e -> {
			TimeRecordSetFormatBak timeRecordSetFormatBak = toDomain(e.getValue());
			listTimeRecordSetFormatBak.add(timeRecordSetFormatBak);
		});
		
		Collections.sort(listTimeRecordSetFormatBak, (TimeRecordSetFormatBak o1, TimeRecordSetFormatBak o2) -> 
			Integer.parseInt(o1.getEmpInfoTerCode().v()) - Integer.parseInt(o2.getEmpInfoTerCode().v())
        );
		return listTimeRecordSetFormatBak;
	}
	
	private TimeRecordSetFormatBak toDomain(List<KrcdtTrRemoteBackup> listEntity) {
		if (listEntity.isEmpty()) {
			return null;
		}
		List<TimeRecordSetFormat> listTimeRecordSetFormat = listEntity.stream()
				.map(x -> new TimeRecordSetFormat
						.TimeRecordSetFormatBuilder(
								new MajorNameClassification(x.majorName),
								new MajorNameClassification(x.smallName),
								new VariableName(x.pk.variableName),
								EnumAdaptor.valueOf(x.inputType, NrlRemoteInputType.class),
								new NumberOfDigits(x.numberDigist))
						.settingValue(new SettingValue(x.settingValue))
						.inputRange(new NrlRemoteInputRange(x.inputRange))
						.rebootFlg(x.reboot == 1 ? true : false)
						.majorNo(new MajorNoClassification(x.majorNo))
						.smallNo(new MajorNoClassification(x.smallNo))
						.value(new SettingValue(x.currentValue))
						.build())
				.collect(Collectors.toList());
		return new TimeRecordSetFormatBak(
				new EmpInfoTerminalCode(String.valueOf(listEntity.get(0).pk.timeRecordCode)),
				new EmpInfoTerminalName(listEntity.get(0).modelName),
				new NRRomVersion(listEntity.get(0).romVersion),
				EnumAdaptor.valueOf(listEntity.get(0).modelType, ModelEmpInfoTer.class),
				listTimeRecordSetFormat, listEntity.get(0).backupDate);
	}
}
