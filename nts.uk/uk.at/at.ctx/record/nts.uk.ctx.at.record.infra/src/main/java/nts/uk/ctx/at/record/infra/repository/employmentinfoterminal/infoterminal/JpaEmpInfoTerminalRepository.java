package nts.uk.ctx.at.record.infra.repository.employmentinfoterminal.infoterminal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.CreateStampInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerMemo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerSerialNo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalName;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MSConversionInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MacAddress;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.ModelEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.MonitorIntervalTime;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.NRConvertInfo;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.OutPlaceConvert;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StampClassifi;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StampDestination;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.StampInfoConversion;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTimeRecorder;
import nts.uk.ctx.at.record.infra.entity.employmentinfoterminal.infoterminal.KrcmtTimeRecorderPK;
import nts.uk.ctx.at.shared.dom.common.WorkplaceId;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.common.timestamp.WorkLocationCD;
import nts.uk.ctx.at.shared.dom.workrule.goingout.GoingOutReason;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.net.Ipv4Address;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class JpaEmpInfoTerminalRepository extends JpaRepository implements EmpInfoTerminalRepository {

	private final static String UPDATE_SERIALNO = "update KRCMT_TIMERECORDER set SERIAL_NO = @serialNo where CD = @cd and CONTRACT_CD = @contractCode ";

	private final static String FIND_WITH_MAC = "select t  from KrcmtTimeRecorder t where t.macAddress = :mac and t.pk.contractCode = :contractCode ";

	private final static String FIND_ALL_CONTRACTCODE = "SELECT a FROM KrcmtTimeRecorder a WHERE a.pk.contractCode = :contractCode ORDER BY a.pk.timeRecordCode ASC";
	
	private final static String FIND_NOT_INCLUDE_CODE = "SELECT a FROM KrcmtTimeRecorder a WHERE a.pk.contractCode = :contractCode AND a.pk.timeRecordCode != :code ORDER BY a.pk.timeRecordCode ASC"; 
	
	private final static String FIND_CONTRACTCODE_TYPE = "SELECT a FROM KrcmtTimeRecorder a WHERE a.pk.contractCode = :contractCode AND a.type = :type ORDER BY a.pk.timeRecordCode ASC";
	
	private final static String FIND_BY_MAC = "select t from KrcmtTimeRecorder t where t.macAddress = :mac";
	
	
	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode) {

		return this.queryProxy()
				.find(new KrcmtTimeRecorderPK(contractCode.v(), empInfoTerCode.v()), KrcmtTimeRecorder.class).map(x -> {
					return toDomain(x);
				});
	}
	
	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode) {
		ContractCode contractCode = new ContractCode(AppContexts.user().contractCode());
		return getEmpInfoTerminal(empInfoTerCode, contractCode);
	}

	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerWithMac(MacAddress macAdd, ContractCode contractCode) {
		return this.queryProxy().query(FIND_WITH_MAC, KrcmtTimeRecorder.class).setParameter("mac", macAdd.v())
				.setParameter("contractCode", contractCode.v()).getList().stream().findFirst().map(x -> toDomain(x));
	}
	
	@Override
	public Optional<EmpInfoTerminal> getEmpInfoTerByMac(MacAddress macAdd) {
		return this.queryProxy().query(FIND_BY_MAC, KrcmtTimeRecorder.class)
					.setParameter("mac", macAdd.v())
					.getList().stream().findFirst().map(x -> toDomain(x));
	}

	@Override
	public void updateSerialNo(EmpInfoTerminalCode empInfoTerCode, ContractCode contractCode,
			EmpInfoTerSerialNo terSerialNo) {
		jdbcProxy().query(UPDATE_SERIALNO).paramString("serialNo", terSerialNo.v()).paramString("cd", empInfoTerCode.v())
				.paramString("contractCode", contractCode.v()).execute();
	}

	private EmpInfoTerminal toDomain(KrcmtTimeRecorder entity) {
		StampInfoConversion stampInfoConver = null;
		if (ModelEmpInfoTer.valueOf(entity.type) != ModelEmpInfoTer.NRL_M) {
			stampInfoConver = new NRConvertInfo(
					new OutPlaceConvert(NotUseAtr.valueOf(entity.replaceGoOut),
							entity.reasonGoOut == null ? Optional.empty()
									: Optional.of(GoingOutReason.valueOf(entity.reasonGoOut))),
					NotUseAtr.valueOf(entity.replaceLeave));
		} else {
			List<MSConversion> lstMSConversion = new ArrayList<>();
			if (entity.msChangeAtrAtd != null) {
				lstMSConversion.add(
						new MSConversion(StampClassifi.ATTENDANCE, StampDestination.valueOf(entity.msChangeAtrAtd)));
			}

			if (entity.msChangeAtrLvw != null) {
				lstMSConversion
						.add(new MSConversion(StampClassifi.LEAVING, StampDestination.valueOf(entity.msChangeAtrLvw)));
			}

			if (entity.msChangeAtrGo != null) {
				lstMSConversion
						.add(new MSConversion(StampClassifi.GO_OUT, StampDestination.valueOf(entity.msChangeAtrGo)));
			}

			if (entity.msChangeAtrBack != null) {
				lstMSConversion
						.add(new MSConversion(StampClassifi.GO_BACK, StampDestination.valueOf(entity.msChangeAtrBack)));
			}
			stampInfoConver = new MSConversionInfo(lstMSConversion);
		}
		CreateStampInfo createStampInfo = new CreateStampInfo(
				stampInfoConver,
				Optional.ofNullable(
						entity.workLocationCode == null ? null : new WorkLocationCD(entity.workLocationCode)),
				Optional.ofNullable(entity.workPlaceId == null ? null : new WorkplaceId(entity.workPlaceId)));
		
		return new EmpInfoTerminal.EmpInfoTerminalBuilder(
				Optional.ofNullable(entity.ipAddress1 == null ? null 
						: Ipv4Address.parse(entity.getIpAddress())),
				new MacAddress(entity.macAddress),
				new EmpInfoTerminalCode(entity.pk.timeRecordCode),
				Optional.ofNullable(entity.serialNo).map(x -> new EmpInfoTerSerialNo(x)),
				new EmpInfoTerminalName(entity.name), new ContractCode(entity.pk.contractCode))
						.createStampInfo(createStampInfo)
						.modelEmpInfoTer(ModelEmpInfoTer.valueOf(entity.type))
						.intervalTime(new MonitorIntervalTime(entity.inverterTime))
						.empInfoTerMemo(Optional.ofNullable(entity.memo).map(x -> new EmpInfoTerMemo(x)))
						.build();
	}
	
	@Override
	public List<EmpInfoTerminal> get(ContractCode contractCode) {
		return this.queryProxy().query(FIND_ALL_CONTRACTCODE, KrcmtTimeRecorder.class).setParameter("contractCode", contractCode.v())
					.getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
	}
	
	@Override
	public List<EmpInfoTerminal> getEmpInfoTerminalNotIncludeCode(ContractCode contractCode, EmpInfoTerminalCode empInfoTerCode) {
		return this.queryProxy().query(FIND_NOT_INCLUDE_CODE, KrcmtTimeRecorder.class).setParameter("contractCode", contractCode.v())
					.setParameter("code", empInfoTerCode.v()).getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
	}
	
	@Override
	public List<EmpInfoTerminal> get(ContractCode contractCode, ModelEmpInfoTer model) {
		return this.queryProxy().query(FIND_CONTRACTCODE_TYPE, KrcmtTimeRecorder.class).setParameter("contractCode", contractCode.v())
					.setParameter("type", model.value).getList().stream().map(e -> toDomain(e)).collect(Collectors.toList());
	}
	
	private KrcmtTimeRecorder toEntity(EmpInfoTerminal domain) {
		
		Integer reasonGoOut = null, replaceGoOut = null, replaceLeave= null;
		Integer msChangeAtrAtd = null, msChangeAtrLvw = null, msChangeAtrGo = null, msChangeAtrBack= null;
		
		if(domain.getCreateStampInfo().getStampInfoConver() instanceof NRConvertInfo) {
			val nrConvertInfo = (NRConvertInfo)domain.getCreateStampInfo().getStampInfoConver();
			reasonGoOut = nrConvertInfo.getOutPlaceConvert().getGoOutReason().map(x -> x.value).orElse(null);
			replaceGoOut = nrConvertInfo.getOutPlaceConvert().getReplace().value;
			replaceLeave = nrConvertInfo.getEntranceExit().value;
			
		}else {
			val nrConvertInfo = (MSConversionInfo) domain.getCreateStampInfo().getStampInfoConver();
			msChangeAtrAtd = createStampMsConnver(StampClassifi.ATTENDANCE, nrConvertInfo.getLstMSConversion());
			msChangeAtrLvw = createStampMsConnver(StampClassifi.LEAVING, nrConvertInfo.getLstMSConversion());
			msChangeAtrGo = createStampMsConnver(StampClassifi.GO_OUT, nrConvertInfo.getLstMSConversion());
			msChangeAtrBack = createStampMsConnver(StampClassifi.GO_BACK, nrConvertInfo.getLstMSConversion());
		}
		
		return new KrcmtTimeRecorder(
				new KrcmtTimeRecorderPK(domain.getContractCode().v(), domain.getEmpInfoTerCode().v()),
				domain.getEmpInfoTerName().v(), domain.getModelEmpInfoTer().value,
				domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getNet1()) : null,
				domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getNet2()) : null,
				domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getHost1()) : null,
				domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getHost2()) : null,
				domain.getMacAddress().v(),
				domain.getTerSerialNo().isPresent() ? domain.getTerSerialNo().get().v() : null,
				domain.getCreateStampInfo().getWorkLocationCd().isPresent()
						? domain.getCreateStampInfo().getWorkLocationCd().get().v()
						: null,
				reasonGoOut, replaceGoOut, replaceLeave, domain.getIntervalTime().v().intValue(),
				domain.getEmpInfoTerMemo().isPresent() ? domain.getEmpInfoTerMemo().get().v() : null,
				domain.getCreateStampInfo().getWorkPlaceId().map(x -> x.v()).orElse(null), msChangeAtrAtd,
				msChangeAtrLvw, msChangeAtrGo, msChangeAtrBack);
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
		entity.setIpAddress1(domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getNet1()) : null);
		entity.setIpAddress2(domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getNet2()) : null);
		entity.setIpAddress3(domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getHost1()) : null);
		entity.setIpAddress4(domain.getIpAddress().isPresent() ? String.valueOf(domain.getIpAddress().get().getHost2()) : null);
		entity.setMacAddress(domain.getMacAddress().v());
		entity.setSerialNo(domain.getTerSerialNo().isPresent() ? domain.getTerSerialNo().get().v() : null);
		entity.setWorkLocationCode(domain.getCreateStampInfo().getWorkLocationCd().isPresent() ? domain.getCreateStampInfo().getWorkLocationCd().get().v() : null);
		
		if(domain.getCreateStampInfo().getStampInfoConver() instanceof NRConvertInfo) {
			val nrConvertInfo = (NRConvertInfo)domain.getCreateStampInfo().getStampInfoConver();
			entity.setReasonGoOut(nrConvertInfo.getOutPlaceConvert().getGoOutReason().map(x -> x.value).orElse(null));
			entity.setReplaceGoOut(nrConvertInfo.getOutPlaceConvert().getReplace().value);
			entity.setReplaceLeave(nrConvertInfo.getEntranceExit().value);
			
		}else {
			val nrConvertInfo = (MSConversionInfo) domain.getCreateStampInfo().getStampInfoConver();
			entity.setMsChangeAtrAtd(createStampMsConnver(StampClassifi.ATTENDANCE, nrConvertInfo.getLstMSConversion()));
			entity.setMsChangeAtrLvw(createStampMsConnver(StampClassifi.LEAVING, nrConvertInfo.getLstMSConversion()));
			entity.setMsChangeAtrGo(createStampMsConnver(StampClassifi.GO_OUT, nrConvertInfo.getLstMSConversion()));
			entity.setMsChangeAtrBack(createStampMsConnver(StampClassifi.GO_BACK, nrConvertInfo.getLstMSConversion()));
		}
		
		entity.setInverterTime(domain.getIntervalTime().v().intValue());
		entity.setMemo(domain.getEmpInfoTerMemo().isPresent() ? domain.getEmpInfoTerMemo().get().v() : null);
		entity.setWorkPlaceId(domain.getCreateStampInfo().getWorkPlaceId().map(x -> x.v()).orElse(null));
		this.commandProxy().update(entity);
	}

	private Integer createStampMsConnver(StampClassifi classifi, List<MSConversion> lstMSConversion) {
		return lstMSConversion.stream().filter(x -> x.getStampClassifi() == classifi)
				.map(x -> x.getStampDestination().value).findFirst().orElse(null);
	}
	

	@Override
	public void delete(EmpInfoTerminal domain) {
		KrcmtTimeRecorderPK pk = new KrcmtTimeRecorderPK(domain.getContractCode().v(), domain.getEmpInfoTerCode().v());
		this.commandProxy().remove(KrcmtTimeRecorder.class, pk);
	}

}
