package nts.uk.ctx.exio.app.input.prepare;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.PrepareImporting;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.PrepareImportingRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalizationRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.validation.ImportingUserConditionRepository;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportPrepareRequire {
	@Inject
	PrepareImportingRepository prepareImportiongRepo;

	public Require create(String companyId) {
		
		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}
	
	public static interface Require extends PrepareImporting.Require {
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);
	}
	
	@Inject
	ImportingUserConditionRepository importingUserConditionRepo;
	
	@Inject
	ImportableItemsRepository importableItemsRepo;
	
	@Inject
	GroupCanonicalizationRepository groupCanonicalizationRepo;
	
	@Inject
	EmployeeDataMngInfoRepository employeeDataMngInfoRepo;
	
	@Inject
	EmploymentHistoryRepository employmentHistoryRepo;
	
	@Inject
	TaskingRepository taskingRepo;
	
	@Inject
	WorkInformationRepository workInformationRepo;
	
	public class RequireImpl implements Require {

		private final String companyId ;
		
		public RequireImpl(String companyId) {
			this.companyId = companyId;
		}

		@Override
		public List<ImportingUserCondition> getImportingUserCondition(String settingCode,
				List<Integer> itemNo) {
			return importingUserConditionRepo.get(companyId, settingCode, itemNo);
		}

		@Override
		public List<ImportableItem> getDefinition(ImportingGroupId groupId) {
			return importableItemsRepo.get(companyId, groupId);
		}

		@Override
		public GroupCanonicalization getGroupCanonicalization(ImportingGroupId groupId) {
			return groupCanonicalizationRepo.find(groupId);
		}

		@Override
		public void save(AnyRecordToDelete toDelete) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void save(CanonicalizedDataRecord canonicalizedDataRecord) {
			prepareImportiongRepo.save(canonicalizedDataRecord);
		}

		@Override
		public int getNumberOfRowsRevisedData() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void save(AnyRecordToChange recordToChange) {
			prepareImportiongRepo.save(recordToChange);
		}

		@Override
		public Optional<EmployeeDataMngInfo> getEmployeeIdByEmployeeCode(String employeeCode) {
			return employeeDataMngInfoRepo.findByScdNotDel(employeeCode, companyId);
		}

		@Override
		public List<RevisedDataRecord> getRevisedDataRecordsByEmployeeCode(ExecutionContext context,
				String employeeCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RevisedDataRecord getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllEmployeeCodesOfImportingData(ExecutionContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<EmploymentHistory> getEmploymentHistory(String employeeId) {
			return employmentHistoryRepo.getByEmployeeIdDesc(companyId, employeeId);
		}

		@Override
		public Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode) {
			return taskingRepo.getOptionalTask(companyId, new TaskFrameNo(taskFrameNo), new TaskCode(taskCode));
		}

		@Override
		public Optional<WorkInfoOfDailyPerformance> getWorkInfoOfDailyPerformance(String employeeId, GeneralDate date) {
			return workInformationRepo.find(employeeId, date);
		}

		@Override
		public Optional<ExternalImportSetting> getExternalImportSetting(String companyId,
				ExternalImportCode settingCode) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
