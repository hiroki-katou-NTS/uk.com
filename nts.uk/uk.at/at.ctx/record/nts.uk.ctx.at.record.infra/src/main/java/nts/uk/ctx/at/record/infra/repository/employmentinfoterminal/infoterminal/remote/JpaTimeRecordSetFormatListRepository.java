package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal.remote;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

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
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormat.TimeRecordSetFormatBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.TimeRecordSetFormatList;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.VariableName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.nrlremote.repo.TimeRecordSetFormatListRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcmtTrRemoteSetting;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.remotel.KrcmtTrRemoteSettingPK;

@Stateless
public class JpaTimeRecordSetFormatListRepository extends JpaRepository implements TimeRecordSetFormatListRepository {

	private static final String REMOVE_WITH_CODE = "delete from KRCMT_TR_REMOTE_SETTING where CONTRACT_CD = @contractCode and TIMERECORDER_CD = @trCode";

	private static final String FIND = "select t from KrcmtTrRemoteSetting t where t.pk.contractCode = :contractCode and t.pk.timeRecordCode = :trCode ";

	//[1]  タイムレコード設定フォーマットリストを削除する
	@Override
	public void removeTRSetFormatList(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {
		this.jdbcProxy().query(REMOVE_WITH_CODE).paramString("contractCode", contractCode.v())
				.paramString("trCode", empInfoTerCode.v()).execute();
	}

	//[2]  タイムレコード設定フォーマットリストをインサートする
	@Override
	public void insert(ContractCode code, TimeRecordSetFormatList trSetFormat) {
		this.commandProxy().insertAll(toEntity(code, trSetFormat));
	}

	//[3]  タイムレコード設定フォーマットリストを取得する
	@Override
	public Optional<TimeRecordSetFormatList> findSetFormat(EmpInfoTerminalCode empInfoTerCode,
			ContractCode contractCode) {
		List<KrcmtTrRemoteSetting> lstEntity = this.queryProxy().query(FIND, KrcmtTrRemoteSetting.class)
				.setParameter("contractCode", contractCode.v()).setParameter("trCode", empInfoTerCode.v()).getList();
		return Optional.ofNullable(toDomain(lstEntity));
	}

	private List<KrcmtTrRemoteSetting> toEntity(ContractCode code, TimeRecordSetFormatList dom) {

		return dom.getLstTRSetFormat().stream().map(x -> {
			return new KrcmtTrRemoteSetting(
					new KrcmtTrRemoteSettingPK(code.v(), dom.getEmpInfoTerCode().v(), dom.getEmpInfoTerName().v()),
					dom.getEmpInfoTerName().v(), dom.getRomVersion().v(), dom.getModelEmpInfoTer().value,
					x.getMajorNo().v(), x.getMajorClassification().v(), x.getSmallNo().v(), x.getSmallClassification().v(), x.getType().value,
					x.getNumberOfDigits().v(), x.getSettingValue().v(), x.getInputRange().v(), x.isRebootFlg() ? 1 : 0,
					x.getCurrentValue().v());
		}).collect(Collectors.toList());
	}

	private TimeRecordSetFormatList toDomain(List<KrcmtTrRemoteSetting> lstEntity) {

		if (lstEntity.isEmpty())
			return null;

		List<TimeRecordSetFormat> lstTRSetFormats = lstEntity.stream()
				.map(x -> new TimeRecordSetFormatBuilder(new MajorNameClassification(x.majorClassification),
						new MajorNameClassification(x.smallClassification), new VariableName(x.pk.variableName),
						NrlRemoteInputType.valueOf(x.type), new NumberOfDigits(x.numberOfDigits))
								.settingValue(new SettingValue(x.settingValue))
								.inputRange(new NrlRemoteInputRange(x.inputRange)).rebootFlg(x.rebootFlg == 1)
								.value(new SettingValue(x.currentValue))
								.majorNo(new MajorNoClassification(x.majorNo))
								.smallNo(new MajorNoClassification(x.smallNo))
								.build())
				.collect(Collectors.toList());
		return new TimeRecordSetFormatList(new EmpInfoTerminalCode(lstEntity.get(0).pk.timeRecordCode),
				new EmpInfoTerminalName(lstEntity.get(0).empInfoTerName), new NRRomVersion(lstEntity.get(0).romVersion),
				ModelEmpInfoTer.valueOf(lstEntity.get(0).modelEmpInfoTer), lstTRSetFormats);
	}

	@Override
	public List<TimeRecordSetFormatList> get(ContractCode contractCode, List<EmpInfoTerminalCode> listEmpInfoTerCode) {
		// TODO Auto-generated method stub
		return null;
	}
}
