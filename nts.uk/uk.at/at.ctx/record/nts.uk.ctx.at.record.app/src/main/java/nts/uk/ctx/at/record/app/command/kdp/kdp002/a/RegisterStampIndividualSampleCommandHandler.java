package nts.uk.ctx.at.record.app.command.kdp.kdp002.a;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.command.AsyncCommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.task.tran.AtomTask;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.output.ExecutionAttr;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainService;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.CreateDailyResultDomainServiceImpl.ProcessState;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecord;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampRecordRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.CreateStampDataForEmployeesService;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.workperfor.dailymonthlyprocessing.ExecutionLog;
import nts.uk.shr.com.context.AppContexts;

/**
 * UKDesign.UniversalK.就業.KDP_打刻.KDP002_打刻入力(個人打刻).A:打刻入力(個人).メニュー別OCD.打刻入力(個人)を登録する
 * @author lamvt
 *
 */
@Stateless
public class RegisterStampIndividualSampleCommandHandler extends CommandHandlerWithResult<RegisterStampIndividualSampleCommand, RegisterStampIndividualSampleResult> {

	@Inject
	private StampCardRepository stampCardRepository;

	@Inject
	private StampDakokuRepository stampDakokuRepo;

	@Inject
	private StampRecordRepository stampRecordRepo;

	@Inject
	private CreateDailyResultDomainService createDailyResultDomainSv;

	@Override
	protected RegisterStampIndividualSampleResult handle(CommandHandlerContext<RegisterStampIndividualSampleCommand> context) {
		
		CreateStampDataForEmployeesRequiredImpl required = new CreateStampDataForEmployeesRequiredImpl(
				stampCardRepository, stampDakokuRepo, stampRecordRepo, createDailyResultDomainSv);

		RegisterStampIndividualSampleCommand cmd = context.getCommand();
		String employeeId = AppContexts.user().employeeId();
		
		/** 
		 * 1. 作成する(@Require, 契約コード, 社員ID, 打刻カード番号, 日時, 打刻する方法, ボタン種類, 実績への反映内容, 打刻場所情報) */
		StampDataReflectResult result = CreateStampDataForEmployeesService.create(required, employeeId, cmd.retriveDateTime(),
				cmd.toRelieve(), cmd.toButtonType(), Optional.empty(), Optional.empty(), Optional.empty());
		
		/**
		 * 2.  */
		AtomTask atom = result.getAtomTask();
		
		transaction.execute(() -> {
			atom.run();
		});
		
		return new RegisterStampIndividualSampleResult(result.getReflectDate());
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
		public List<StampCard> getListStampCard(String sid) {
			return stampCardRepository.getListStampCard(sid);
		}

	}
}