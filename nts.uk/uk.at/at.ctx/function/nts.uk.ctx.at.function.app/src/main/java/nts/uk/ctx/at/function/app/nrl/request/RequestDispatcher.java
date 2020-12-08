package nts.uk.ctx.at.function.app.nrl.request;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import lombok.AllArgsConstructor;
import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.app.nrl.Command;
import nts.uk.ctx.at.function.app.nrl.DefaultValue;
import nts.uk.ctx.at.function.app.nrl.data.MarshalResult;
import nts.uk.ctx.at.function.app.nrl.data.RequestData;
import nts.uk.ctx.at.function.app.nrl.exceptions.ErrorCode;
import nts.uk.ctx.at.function.app.nrl.exceptions.InvalidFrameException;
import nts.uk.ctx.at.function.app.nrl.response.NRLResponse;
import nts.uk.ctx.at.function.app.nrl.xml.DefaultXDocument;
import nts.uk.ctx.at.function.app.nrl.xml.Element;
import nts.uk.ctx.at.function.app.nrl.xml.Frame;
import nts.uk.ctx.at.function.app.nrlremote.SendToNRLRemote;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.FuncEmpInfoTerminalImport;
import nts.uk.ctx.at.function.dom.adapter.employmentinfoterminal.infoterminal.RQEmpInfoTerminalAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.FuncStampCardAdapter;
import nts.uk.ctx.at.function.dom.adapter.stamp.StampCard;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.AuthenticateNRCommunicationQuery;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.ContractCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerComAbPeriod;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalComStatus;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.RecordCommunicationStatus;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerComAbPeriodRepository;
import nts.uk.ctx.at.function.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalComStatusRepository;
import nts.uk.shr.com.system.property.UKServerSystemProperties;
import nts.uk.shr.infra.data.TenantLocatorService;

/**
 * Request dispatcher.
 * 
 * NRLをオブジェクトに変換する
 * 
 * @author manhnd
 */
public class RequestDispatcher {
	
	/**
	 * Request mapper
	 */
	private static final Map<String, Class<? extends NRLRequest<Frame>>> RequestMapper;
	static {
		RequestMapper = new HashMap<>();
		RequestMapper.put(Command.TEST.Request, TestRequest.class);
		RequestMapper.put(Command.POLLING.Request, StatusPollingRequest.class);
		RequestMapper.put(Command.SESSION.Request, SessionRequest.class);
		RequestMapper.put(Command.ALL_IO_TIME.Request, TimeIORequest.class);
		RequestMapper.put(Command.ALL_RESERVATION.Request, AllReservationRequest.class);
		RequestMapper.put(Command.ALL_PETITIONS.Request, AllPetitionsRequest.class);
		RequestMapper.put(Command.PERSONAL_INFO.Request, PersonalInfoRequest.class);
		RequestMapper.put(Command.OVERTIME_INFO.Request, OverTimeInfoRequest.class);
		RequestMapper.put(Command.RESERVATION_INFO.Request, ReservationMenuInfoRequest.class);
		RequestMapper.put(Command.TIMESET_INFO.Request, TimeSettingInfoRequest.class);
		RequestMapper.put(Command.WORKTIME_INFO.Request, WorkTimeInfoRequest.class);
		RequestMapper.put(Command.WORKTYPE_INFO.Request, WorkTypeInfoRequest.class);
		RequestMapper.put(Command.TR_REMOTE.Request, SendToNRLRemote.class);
		RequestMapper.put(Command.APPLICATION_INFO.Request, ApplicationReasonRequest.class);
		RequestMapper.put(Command.UK_SWITCH_MODE.Request, DateTimeSwitchUKModeRequest.class);
	}
	
	/**
	 * Document
	 */
	@Inject
	private DefaultXDocument document;
	
	@Inject
	private RQEmpInfoTerminalAdapter rqEmpInfoTerminalAdapter;
	
	@Inject
	private EmpInfoTerminalComStatusRepository empInfoTerminalComStatusRepo;

	@Inject
	private EmpInfoTerComAbPeriodRepository empInfoTerComAbPeriodRepo;
	
	@Inject
	private FuncStampCardAdapter funcStampCardAdapter;
	
	/**
	 * Ignite.
	 * @param is input stream
	 * @return response
	 */
	protected NRLResponse ignite(InputStream is) {
		
		MarshalResult result = null;
		try {
 			String caller = Thread.currentThread().getStackTrace()[2].getMethodName();
			RequestData data = this.getClass().getDeclaredMethod(caller, InputStream.class)
					.getDeclaredAnnotation(RequestData.class);
			if (data == null) throw new RuntimeException("RequestData is required.");
			Command[] datas = data.value();
			JAXBContext context = JAXBContext.newInstance(Frame.class);
			result = new MarshalResult();
			Frame frame = document.unmarshal(context, is, result);
			
			String soh = frame.pickItem(Element.SOH);
			if (!DefaultValue.SOH.equals(soh)) throw new InvalidFrameException();
			String hdr = (frame.pickItem(Element.HDR)).toUpperCase();

			String nrlNo = frame.pickItem(Element.NRL_NO);
			String macAddr = frame.pickItem(Element.MAC_ADDR);
			String contractCode = frame.pickItem(Element.CONTRACT_CODE);
			Command command = Command.findName(hdr).orElseThrow(InvalidFrameException::new);
			if (command == Command.ACCEPT || command == Command.NOACCEPT) return NRLResponse.mute();
			if (Arrays.asList(datas).stream().noneMatch(d -> d == command)) {
				return NRLResponse.noAccept(nrlNo, macAddr, contractCode).build().addPayload(Frame.class, ErrorCode.PARAM.value);
			}
			
			NRLRequest<Frame> request = CDI.current().select(RequestMapper.get(hdr)).get();
			if (request.intrEntity(frame.isCracked())) {
				frame = document.unmarshal(result.toInputStream());
			}
			
			RequireImpl impl = new RequireImpl(rqEmpInfoTerminalAdapter, empInfoTerminalComStatusRepo,
					empInfoTerComAbPeriodRepo, funcStampCardAdapter);
			//タイムレコーダの通信を認証する
			boolean checkAuthen = AuthenticateNRCommunicationQuery.process(impl, contractCode, macAddr);
			if(!checkAuthen) {
				return NRLResponse.noAccept(nrlNo, macAddr, contractCode).build().addPayload(Frame.class, ErrorCode.PARAM.value);
			}
			
			//就業情報端末を取得する
			val empInfoTerCodeOpt = rqEmpInfoTerminalAdapter.getEmpInfoTerminalCode(contractCode, macAddr);
			if(!empInfoTerCodeOpt.isPresent()) {
				return NRLResponse.noAccept(nrlNo, macAddr, contractCode).build().addPayload(Frame.class, ErrorCode.PARAM.value);
			}

			// DS_通信状況を記録する.通信状況を記録する
			val status = RecordCommunicationStatus.recordStatus(impl, new ContractCode(contractCode),
					new EmpInfoTerminalCode(empInfoTerCodeOpt.get()));
			status.run();

			NRLResponse reponse =  request.responseTo(empInfoTerCodeOpt.get(), frame);
			
			return reponse;
			
		} catch (JAXBException | NoSuchMethodException ex) {
			throw new RuntimeException(ex);
		} finally {
			if (Objects.nonNull(result)) {
				result.dispose();
			}
			if(UKServerSystemProperties.usesTenantLocator()) {
				TenantLocatorService.disconnect();
			}
		}
	}

	@AllArgsConstructor
	public class RequireImpl implements RecordCommunicationStatus.Require, AuthenticateNRCommunicationQuery.Require {

		private final RQEmpInfoTerminalAdapter rQEmpInfoTerminalAdapter;

		private final EmpInfoTerminalComStatusRepository empInfoTerminalComStatusRepo;

		private final EmpInfoTerComAbPeriodRepository empInfoTerComAbPeriodRepo;
		
		private final FuncStampCardAdapter funcStampCardAdapter;

		@Override
		public Optional<FuncEmpInfoTerminalImport> getEmpInfoTerminal(String empInfoTerCode, String contractCode) {
			return rQEmpInfoTerminalAdapter.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<EmpInfoTerminalComStatus> getEmpTerComStatus(ContractCode contractCode,
				EmpInfoTerminalCode empInfoTerCode) {
			return empInfoTerminalComStatusRepo.get(contractCode, empInfoTerCode);
		}

		@Override
		public void insertEmpComAbPeriod(EmpInfoTerComAbPeriod empInfoTerComAbPeriod) {
			empInfoTerComAbPeriodRepo.insert(empInfoTerComAbPeriod);
		}

		@Override
		public void updateEmpTerStatus(EmpInfoTerminalComStatus empInfoTerComStatus) {
			empInfoTerminalComStatusRepo.update(empInfoTerComStatus);
		}

		@Override
		public void deleteEmpTerComAbPast(ContractCode contractCode, EmpInfoTerminalCode code, GeneralDate dateDelete) {
			empInfoTerComAbPeriodRepo.deletePast(contractCode, code, dateDelete);
		}

		@Override
		public void insertEmpTerStatus(EmpInfoTerminalComStatus empInfoTerComStatus) {
			empInfoTerminalComStatusRepo.insert(empInfoTerComStatus);
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String contractCode, String stampNumber) {
			return funcStampCardAdapter.getByCardNoAndContractCode(contractCode, stampNumber);
		}
		
	}
}
