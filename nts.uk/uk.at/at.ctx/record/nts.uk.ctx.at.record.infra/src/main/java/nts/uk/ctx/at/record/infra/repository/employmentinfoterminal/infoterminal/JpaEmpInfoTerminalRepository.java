package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ConvertEmbossCategory;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerMemo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.IPAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTimeRecorder;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTimeRecorderPK;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.breakouting.GoingOutReason;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaEmpInfoTerminalRepository extends JpaRepository implements EmpInfoTerminalRepository {

	private final static String UPDATE_SERIALNO = "update KRCMT_TIMERECORDER set SERIAL_NO = @serialNo where CD = @cd and CONTRACT_CD = @contractCode ";

	private final static String FIND_WITH_MAC = "select t  from KrcmtTimeRecorder t where t.macAddress = :mac and t.pk.contractCode = :contractCode ";

	private final static String FIND_ALL_CONTRACTCODE = "SELECT a FROM KrcmtTimeRecorder a WHERE a.pk.contractCode = :contractCode";
	
	private final static String FIND_NOT_INCLUDE_CODE = "SELECT a FROM KrcmtTimeRecorder a WHERE a.pk.contractCode = :contractCode AND a.pk.timeRecordCode = :code "; 
	
	
	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		return this.queryProxy()
				.find(new KrcmtTimeRecorderPK(contractCode.v(), empInfoTerCode.v()), KrcmtTimeRecorder.class).map(x -> {
					return toDomain(x);
				});
	}

	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode) {
		return this.queryProxy().query(FIND_WITH_MAC, KrcmtTimeRecorder.class).setParameter("mac", macAdd.v())
				.setParameter("contractCode", contractCode.v()).getList().stream().findFirst().map(x -> toDomain(x));
	}

	@Override
	public void updateSerialNo(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode,
			EmpInfoTerSerialNo terSerialNo) {
		jdbcProxy().query(UPDATE_SERIALNO).paramString("serialNo", terSerialNo.v()).paramInt("cd", empInfoTerCode.v())
				.paramString("contractCode", contractCode.v()).execute();
	}

	private EmpInfoTerminal toDomain(KrcmtTimeRecorder entity) {
		return new EmpInfoTerminal.EmpInfoTerminalBuilder(
				Optional.ofNullable(entity.ipAddress).map(x -> new IPAddress(x)), new MacAddress(entity.macAddress),
				new EmpInfoTerminalCode(entity.pk.timeRecordCode),
				Optional.ofNullable(entity.serialNo).map(x -> new EmpInfoTerSerialNo(x)),
				new EmpInfoTerminalName(entity.name), new ContractCode(entity.pk.contractCode))
						.createStampInfo(new CreateStampInfo(
								new OutPlaceConvert(NotUseAtr.valueOf(entity.replaceGoOut),
										Optional.ofNullable(entity.reasonGoOut == null ? null
												: GoingOutReason.valueOf(entity.reasonGoOut))),
								new ConvertEmbossCategory(NotUseAtr.valueOf(entity.replaceLeave),
										NotUseAtr.valueOf(entity.replaceSupport)),
								Optional.ofNullable(entity.workLocationCode == null ? null
										: new WorkLocationCD(entity.workLocationCode))))
						.modelEmpInfoTer(ModelEmpInfoTer.valueOf(entity.type))
						.intervalTime(new MonitorIntervalTime(entity.inverterTime))
						.empInfoTerMemo(Optional.ofNullable(entity.memo).map(x -> new EmpInfoTerMemo(x))).build();
	}
	
	@Override
	public List<EmpInfoTerminal> get(ContractCode contractCode) {
		return this.queryProxy().query(FIND_ALL_CONTRACTCODE, KrcmtTimeRecorder.class).setParameter("contractCode", contractCode.v())
					.getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
	}
	
	@Override
	public List<EmpInfoTerminal> getEmpInfoTerminalNotIncludeCode(ContractCode contractCode,
			EmpInfoTerminalCode empInfoTerCode) {
		return this.queryProxy().query(FIND_NOT_INCLUDE_CODE, KrcmtTimeRecorder.class).setParameter("contractCode", contractCode.v())
					.setParameter("code", empInfoTerCode.v().intValue()).getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
	}
	
	private KrcmtTimeRecorder toEntity(EmpInfoTerminal domain) {
		return new KrcmtTimeRecorder(
				new KrcmtTimeRecorderPK(domain.getContractCode().v(), domain.getEmpInfoTerCode().v()),
				domain.getEmpInfoTerName().v(), domain.getModelEmpInfoTer().value, domain.getIpAddress().get().v(),
				domain.getMacAddress().v(), domain.getTerSerialNo().get().v(),
				domain.getCreateStampInfo().getWorkLocationCd().get().v(),
				Integer.valueOf(domain.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().value),
				domain.getCreateStampInfo().getOutPlaceConvert().getReplace().value,
				domain.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value,
				domain.getCreateStampInfo().getConvertEmbCate().getOutSupport().value,
				domain.getIntervalTime().v().intValue(), domain.getEmpInfoTerMemo().get().v());
	}
	
	@Override
	public void insert(EmpInfoTerminal domain) {
		this.commandProxy().insert(toEntity(domain));
		
	}

	@Override
	public void update(EmpInfoTerminal domain) {
		KrcmtTimeRecorder entity = this.queryProxy().find(new KrcmtTimeRecorderPK(domain.getContractCode().v(), domain.getEmpInfoTerCode().v()), KrcmtTimeRecorder.class).get();
		entity.setName(domain.getEmpInfoTerName().v());
		entity.setType(domain.getModelEmpInfoTer().value);
		entity.setIpAddress(domain.getIpAddress().get().v());
		entity.setMacAddress(domain.getMacAddress().v());
		entity.setSerialNo(domain.getTerSerialNo().get().v());
		entity.setWorkLocationCode(domain.getCreateStampInfo().getWorkLocationCd().get().v());
		entity.setReasonGoOut(Integer.valueOf(domain.getCreateStampInfo().getOutPlaceConvert().getGoOutReason().get().value));
		entity.setReplaceGoOut(domain.getCreateStampInfo().getOutPlaceConvert().getReplace().value);
		entity.setReplaceLeave(domain.getCreateStampInfo().getConvertEmbCate().getEntranceExit().value);
		entity.setReplaceSupport(domain.getCreateStampInfo().getConvertEmbCate().getOutSupport().value);
		entity.setInverterTime(domain.getIntervalTime().v().intValue());
		entity.setMemo(domain.getEmpInfoTerMemo().get().v());
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(EmpInfoTerminal domain) {
		this.commandProxy().remove(toEntity(domain));
		
	}

	

	

}
