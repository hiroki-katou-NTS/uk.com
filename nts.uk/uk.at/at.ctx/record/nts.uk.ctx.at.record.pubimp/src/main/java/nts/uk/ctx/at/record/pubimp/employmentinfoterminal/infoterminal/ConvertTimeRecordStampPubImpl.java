package nts.uk.ctx.at.record.pubimp.employmentinfoterminal.infoterminal;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.tuple.Pair;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.employeemanage.EmployeeManageRCAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminal;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.EmpInfoTerminalCode;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.TimeRecordReqSetting;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPageAlarmEmpInfoTer;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.log.TopPgAlTrRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.receive.StampReceptionData.StampDataBuilder;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.EmpInfoTerminalRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.repo.TimeRecordReqSettingRepository;
import nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal.service.ConvertTimeRecordStampService;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.ConvertTimeRecordStampPub;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.StampDataReflectResultExport;
import nts.uk.ctx.at.record.pub.employmentinfoterminal.infoterminal.StampReceptionDataExport;

/**
 * @author ThanhNX
 *
 */
@Stateless
public class ConvertTimeRecordStampPubImpl implements ConvertTimeRecordStampPub {

	@Inject
	private EmpInfoTerminalRepository empInfoTerminalRepository;

	@Inject
	private TimeRecordReqSettingRepository timeRecordReqSettingRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepository;

	@Inject
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Inject
	private StampRecordRepository stampRecordRepository;

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private EmployeeManageRCAdapter employeeManageRCAdapter;

	@Inject
	private TopPgAlTrRepository executionLog;

	@Override
	public Pair<Optional<AtomTask>, Optional<StampDataReflectResultExport>> convertData(String empInfoTerCode,
			String contractCode, StampReceptionDataExport stampReceptData) {

		RequireImpl require = new RequireImpl(empInfoTerminalRepository, timeRecordReqSettingRepository,
				stampDakokuRepository, createDailyResultDomainSv, stampRecordRepository, stampCardRepository,
				employeeManageRCAdapter, executionLog);

		Pair<Optional<AtomTask>, Optional<StampDataReflectResult>> convertData = ConvertTimeRecordStampService
				.convertData(require, new EmpInfoTerminalCode(empInfoTerCode), new ContractCode(contractCode),
						new StampReceptionData(
								new StampDataBuilder(stampReceptData.getIdNumber(), stampReceptData.getCardCategory(),
										stampReceptData.getShift(), stampReceptData.getLeavingCategory(),
										stampReceptData.getYmd(), stampReceptData.getSupportCode())
												.overTimeHours(stampReceptData.getOverTimeHours())
												.midnightTime(stampReceptData.getMidnightTime())
												.time(stampReceptData.getTime())));
		return Pair.of(convertData.getLeft(),
				convertData.getRight().isPresent()
						? Optional.of(new StampDataReflectResultExport(convertData.getRight().get().getReflectDate(),
								convertData.getRight().get().getAtomTask()))
						: Optional.empty());
	}

	@AllArgsConstructor
	public static class RequireImpl implements ConvertTimeRecordStampService.Require {

		private final EmpInfoTerminalRepository empInfoTerminalRepository;

		private final TimeRecordReqSettingRepository timeRecordReqSettingRepository;

		private final StampDakokuRepository stampDakokuRepository;

		private final CreateDailyResultDomainService createDailyResultDomainSv;

		private final StampRecordRepository stampRecordRepository;

		private final StampCardRepository stampCardRepository;

		private final EmployeeManageRCAdapter employeeManageRCAdapter;

		private final TopPgAlTrRepository executionLog;

		@Override
		public Optional<EmpInfoTerminal> getEmpInfoTerminal(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {
			return empInfoTerminalRepository.getEmpInfoTerminal(empInfoTerCode, contractCode);
		}

		@Override
		public Optional<StampRecord> getStampRecord(ContractCode contractCode, StampNumber stampNumber,
				GeneralDateTime dateTime) {
			return stampRecordRepository.findByKey(stampNumber, dateTime);
		}

		@Override
		public Optional<TimeRecordReqSetting> getTimeRecordReqSetting(EmpInfoTerminalCode empInfoTerCode,
				ContractCode contractCode) {

			return timeRecordReqSettingRepository.getTimeRecordReqSetting(empInfoTerCode, contractCode);
		}

		@Override
		public void insert(StampRecord stampRecord) {
			stampRecordRepository.insert(stampRecord);

		}

		@Override
		public Optional<StampCard> getByCardNoAndContractCode(ContractCode contractCode, StampNumber stampNumber) {
			return stampCardRepository.getByCardNoAndContractCode(stampNumber.v(), contractCode.v());
		}

		@Override
		public void insert(Stamp stamp) {

			stampDakokuRepository.insert(stamp);
		}

		@Override
		public ProcessState createDailyResult(@SuppressWarnings("rawtypes") AsyncCommandHandlerContext asyncContext,
				List<String> emloyeeIds, DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog) {
			return createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public List<String> getListEmpID(String companyID, GeneralDate referenceDate) {
			return employeeManageRCAdapter.getListEmpID(companyID, referenceDate);
		}

		@Override
		public void insertLogAll(TopPageAlarmEmpInfoTer alEmpInfo) {
			executionLog.insertLogAll(alEmpInfo);

		}

	}
}
