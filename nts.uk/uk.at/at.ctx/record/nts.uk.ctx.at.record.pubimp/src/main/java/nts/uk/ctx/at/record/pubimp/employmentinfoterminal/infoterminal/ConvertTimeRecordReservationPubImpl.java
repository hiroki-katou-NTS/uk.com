package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.auth.dom.adapter.login.IGetInfoForLogin;
import nts.uk.ctx.at.record.dom.adapter.employeemanage.EmployeeManageRCAdapter;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.EmpDataImport;
import nts.uk.ctx.at.record.dom.adapter.employmentinfoterminal.infoterminal.GetMngInfoFromEmpIDListAdapter;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPgAlTrRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.ReservationReceptionData;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.ConvertTimeRecordReservationService;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservation;
import nts.uk.ctx.at.record.dom.reservation.bento.BentoReservationRepository;
import nts.uk.ctx.at.record.dom.reservation.bento.ReservationDate;
import nts.uk.ctx.at.record.dom.reservation.bento.WorkLocationCode;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistory;
import nts.uk.ctx.at.record.dom.reservation.bentomenu.BentoMenuHistRepository;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationRecTimeZone;
import nts.uk.ctx.at.record.dom.reservation.reservationsetting.ReservationSettingRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ConvertTimeRecordReservationPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ReservReceptDataExport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyInfo;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.loginuser.LoginUserContextManager;

@Stateless
public class ConvertTimeRecordReservationPubImpl implements ConvertTimeRecordReservationPub {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private BentoMenuHistRepository bentoMenuRepository;

	@Inject
	private BentoReservationRepository bentoReservationRepository;

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private EmployeeManageRCAdapter employeeManageRCAdapter;

	@Inject
	private TopPgAlTrRepository executionLog;
	
	@Inject
	private GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
	
	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private IGetInfoForLogin iGetInfoForLogin;
	
	@Inject
	private LoginUserContextManager loginUserContextManager;
	
	@Inject
	private ReservationSettingRepository reservationSettingRepository;

	@Override
	public Optional<AtomTask> convertData(String empInfoTerCode, String contractCode,
			ReservReceptDataExport reservReceptData, String companyID) {

		RequireImpl requireImpl = new RequireImpl(empInfoTerminalRepository, timeRecordReqSettingRepository,
				employeeManageRCAdapter, bentoMenuRepository, bentoReservationRepository, stampCardRepository,
				executionLog, getMngInfoFromEmpIDListAdapter, companyAdapter, iGetInfoForLogin,
				loginUserContextManager, reservationSettingRepository);
		return ConvertTimeRecordReservationService.convertData(requireImpl, new EmpInfoTerminalCode(empInfoTerCode),
				new ContractCode(contractCode),
				new ReservationReceptionData(reservReceptData.getIdNumber(), reservReceptData.getMenu(),
						reservReceptData.getYmd(), reservReceptData.getTime(), reservReceptData.getQuantity()),
				companyID);
	}

	@AllArgsConstructor
	public static class RequireImpl implements ConvertTimeRecordReservationService.Require {

		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final EmployeeManageRCAdapter employeeManageRCAdapter;

		private final BentoMenuHistRepository bentoMenuRepository;

		private final BentoReservationRepository bentoReservationRepository;

		private final StampCardRepository stampCardRepository;

		private final TopPgAlTrRepository executionLog;
		
		private final GetMngInfoFromEmpIDListAdapter getMngInfoFromEmpIDListAdapter;
		
		private final CompanyAdapter companyAdapter;
		
		private final IGetInfoForLogin iGetInfoForLogin;
		
		private final LoginUserContextManager loginUserContextManager;
		
		private final ReservationSettingRepository reservationSettingRepository;

		@Override
		public void reserve(BentoReservation bentoReservation) {

			bentoReservationRepository.add(bentoReservation);
		}

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {

			Optional<TimeRecordReqSetting> timeOpt = timeRecordReqSettingRepository
					.getTimeRecordReqSetting(empInfoTerCode, contractCode);
			return timeOpt;
		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber) {
			return stampCardRepository.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return employeeManageRCAdapter.getListEmpID(companyID, referenceDate);
		}

		@Override
		public void insertLogAll(TopPageAlarmEmpInfoTer alEmpInfo) {
			executionLog.insertLogAll(alEmpInfo);

		}

		@Override
		public void removeBentoReserve(String reservationCardNo, GeneralDate date) {
			bentoReservationRepository.removeWithCardNumberDate(reservationCardNo, date);
		}

		@Override
		public List<EmpDataImport> getEmpData(List<String> empIDList) {
			return getMngInfoFromEmpIDListAdapter.getEmpData(empIDList);
		}

		@Override
		public CompanyInfo getCompanyInfoById(String companyId) {
			return companyAdapter.getCompanyInfoById(companyId);
		}

		@Override
		public Optional<String> getUserIdFromLoginId(String perId) {
			return iGetInfoForLogin.getUserIdFromLoginId(perId);
		}

		@Override
		public void loggedInAsEmployee(String userId, String personId, String contractCode, String companyId,
				String companyCode, String employeeId, String employeeCode) {
			loginUserContextManager.loggedInAsEmployee(userId, personId, contractCode, companyId, companyCode, employeeId, employeeCode);
		}
		
		@Override
		public void loggedOut() {
			loginUserContextManager.loggedOut();
		}

		@Override
		public ReservationRecTimeZone getReservationSetByOpDistAndFrameNo(String companyID, int frameNo,
				int operationDistinction) {
			return reservationSettingRepository.getReservationSetByOpDistAndFrameNo(companyID, frameNo, operationDistinction);
		}

		@Override
		public BentoMenuHistory getBentoMenu(ReservationDate reservationDate,
				Optional<WorkLocationCode> workLocationCode) {
			return bentoMenuRepository.getBentoMenu(AppContexts.user().companyId(), reservationDate.getDate(),
					workLocationCode);
		}
	}

}
