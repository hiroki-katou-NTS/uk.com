package nts.uk.ctx.at.record.app.command.stamp.card.stampcard.management.personalengraving;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeDataMngInfoImport;
import nts.uk.ctx.at.record.dom.adapter.employee.EmployeeRecordAdapter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.ExecutionTypeDaily;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.EmbossingExecutionFlag;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday.createdailyresults.CreateDailyResults;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.OutputCreateDailyOneDay;
import nts.uk.ctx.at.record.dom.dailyresultcreationprocess.creationprocess.creationclass.dailywork.TemporarilyReflectStampDailyAttd;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.ctx.at.shared.dom.adapter.generalinfo.dtoimport.EmployeeGeneralInfoImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyImport622;
import nts.uk.ctx.at.shared.dom.dailyperformanceprocessing.output.PeriodInMasterList;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.function.algorithm.ChangeDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.OutputTimeReflectForWorkinfo;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.StampReflectRangeOutput;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper.TimeReflectFromWorkinfo;
import nts.uk.ctx.at.shared.dom.workrecord.workperfor.dailymonthlyprocessing.ErrorMessageInfo;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author anhdt 打刻入力(個人)を登録する
 */
@Stateless
public class RegisterStampDataCommandHandler extends CommandHandlerWithResult<RegisterStampDataCommand, RegisterStampDataResult> {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private CreateDailyResultDomainService createDailyResultDomainSv;
	
	@Inject
	private StampCardEditingRepo stampCardEditRepo;
	
	@Inject
	private EmployeeRecordAdapter sysEmpPub;

	@Inject
	private CompanyAdapter companyAdapter;
	
	@Inject
	private CreateDailyResults createDailyResults;
	
	@Inject
	private TimeReflectFromWorkinfo timeReflectFromWorkinfo;
	
	@Inject
	private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

	@Override
	protected RegisterStampDataResult handle(CommandHandlerContext<RegisterStampDataCommand> context) {
		CreateStampDataForEmployeesRequiredImpl required = new CreateStampDataForEmployeesRequiredImpl(
				stampCardRepository, stampDakokuRepo, stampRecordRepo, createDailyResultDomainSv, stampCardEditRepo,
				sysEmpPub, companyAdapter, createDailyResults, timeReflectFromWorkinfo, temporarilyReflectStampDailyAttd);

		RegisterStampDataCommand cmd = context.getCommand();
		String employeeId = AppContexts.user().employeeId();
		TimeStampInputResult result = CreateStampDataForEmployeesService.create(required,
				new ContractCode(AppContexts.user().contractCode()), employeeId, Optional.empty(),
				cmd.retriveDateTime(), cmd.toRelieve(), cmd.toButtonType(),
				cmd.toRefectActualResult(), Optional.ofNullable(new StampLocationInfor(cmd.toGeoCoordinate(),true) ));
		
		Optional<AtomTask> atomOpt = result.getAt();
		
		if (atomOpt.isPresent()) {
			transaction.execute(() -> {
				atomOpt.get().run();
			});
		}
		
		AtomTask ReflectResultAtomOpt = result.getStampDataReflectResult().getAtomTask();
		
		if (ReflectResultAtomOpt != null) {
			transaction.execute(() -> {
				ReflectResultAtomOpt.run();
			});
		}

		return new RegisterStampDataResult(employeeId, result.getStampDataReflectResult().getReflectDate());
	}

	@AllArgsConstructor
	private class CreateStampDataForEmployeesRequiredImpl implements CreateStampDataForEmployeesService.Require {

		@Inject
		private StampCardRepository stampCardRepository;

		@Inject
		private StampDakokuRepository stampDakokuRepo;

		@Inject
		private StampRecordRepository stampRecordRepo;

		@Inject
		private CreateDailyResultDomainService createDailyResultDomainSv;
		
		
		@Inject
		private StampCardEditingRepo stampCardEditRepo;
		
		@Inject
		private EmployeeRecordAdapter sysEmpPub;

		@Inject
		private CompanyAdapter companyAdapter;
		
		private CreateDailyResults createDailyResults;

		private TimeReflectFromWorkinfo timeReflectFromWorkinfo;

		private TemporarilyReflectStampDailyAttd temporarilyReflectStampDailyAttd;

		@Override
		public void insert(StampRecord stampRecord) {
			stampRecordRepo.insert(stampRecord);

		}

		@Override
		public void insert(Stamp stamp) {
			stampDakokuRepo.insert(stamp);
		}

		@Override
		public ProcessState createDailyResult(@SuppressWarnings("rawtypes") AsyncCommandHandlerContext asyncContext,
				List<String> emloyeeIds, DatePeriod periodTime, ExecutionAttr executionAttr, String companyId,
				String empCalAndSumExecLogID, Optional<ExecutionLog> executionLog) {
			return createDailyResultDomainSv.createDailyResult(asyncContext, emloyeeIds, periodTime, executionAttr,
					companyId, empCalAndSumExecLogID, executionLog);
		}

		@Override
		public List<StampCard> getLstStampCardBySidAndContractCd(String sid) {
			return this.stampCardRepository.getLstStampCardBySidAndContractCd(AppContexts.user().contractCode(), sid);
		}

		@Override
		public List<EmployeeDataMngInfoImport> findBySidNotDel(List<String> sids) {
			return this.sysEmpPub.findBySidNotDel(sids);
		}

		@Override
		public Optional<CompanyImport622> getCompanyNotAbolitionByCid(String cid) {
			return this.companyAdapter.getCompanyNotAbolitionByCid(cid);
		}

		@Override
		public void add(StampCard domain) {
			this.stampCardRepository.add(domain);
		}

		@Override
		public Optional<StampCardEditing> get(String companyId) {
			return Optional.ofNullable(this.stampCardEditRepo.get(companyId));

		}
		
		@Override
		public Optional<StampCard> getByCardNoAndContractCode(String stampNumber, String contractCode) {
			return this.stampCardRepository.getByCardNoAndContractCode(stampNumber, contractCode);
		}

		@Override
		public OutputCreateDailyOneDay createDailyResult(String companyId, String employeeId, GeneralDate ymd,
				ExecutionTypeDaily executionType, EmbossingExecutionFlag flag,
				EmployeeGeneralInfoImport employeeGeneralInfoImport, PeriodInMasterList periodInMasterList,
				IntegrationOfDaily integrationOfDaily) {
			return this.createDailyResults.createDailyResult(companyId, employeeId, ymd, executionType, flag, employeeGeneralInfoImport, periodInMasterList, integrationOfDaily);
		}

		@Override
		public OutputTimeReflectForWorkinfo get(String companyId, String employeeId, GeneralDate ymd,
				WorkInfoOfDailyAttendance workInformation) {
			return this.timeReflectFromWorkinfo.get(companyId, employeeId, ymd, workInformation);
		}

		@Override
		public List<ErrorMessageInfo> reflectStamp(Stamp stamp, StampReflectRangeOutput stampReflectRangeOutput,
				IntegrationOfDaily integrationOfDaily, ChangeDailyAttendance changeDailyAtt) {
			return this.temporarilyReflectStampDailyAttd.reflectStamp(stamp, stampReflectRangeOutput, integrationOfDaily, changeDailyAtt);
		}

	}
}
