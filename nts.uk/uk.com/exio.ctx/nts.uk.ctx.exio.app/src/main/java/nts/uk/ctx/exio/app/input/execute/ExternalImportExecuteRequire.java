package nts.uk.ctx.exio.app.input.execute;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.at.record.dom.workinformation.repository.WorkInformationRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistoryRepository;
import nts.uk.ctx.exio.dom.input.ExecuteImporting;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.ExternalImportExistingRepository;
import nts.uk.ctx.exio.dom.input.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMetaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalDataRepository;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspaceRepository;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportExecuteRequire {

	public Require create(String companyId) {
		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}
	
	public static interface Require extends
			ExecuteImporting.Require,
			ExternalImportWorkspaceRepository.Require {
	}
	
	@Inject
	private ExternalImportSettingRepository settingRepo;
	
	@Inject
	private ImportingGroupRepository importingGroupRepo;
	
	@Inject
	private ConversionTableRepository conversionTableRepo;
	
	@Inject
	private ImportableItemsRepository importableItemsRepo;
	
	@Inject
	private TransferCanonicalDataRepository transferCanonicalDataRepo;
	
	@Inject
	private GroupWorkspaceRepository groupWorkspaceRepo;
	
	@Inject
	private CanonicalizedDataRecordRepository canonicalizedDataRecordRepo;
	
	@Inject
	private ExternalImportExistingRepository existingRepo;
	
	@Inject
	private ImportingDataMetaRepository metaRepo;
	
	@Inject
	private TaskingRepository taskingRepo;
	
	@Inject
	private WorkInformationRepository workInformationRepo;
	
	@Inject
	private EmploymentHistoryRepository employmentHistoryRepo;
	
	
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class RequireImpl implements Require {
		
		private final String companyId;

		@Override
		public Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode) {
			return settingRepo.get(companyId, settingCode);
		}

		@Override
		public ImportingGroup getImportingGroup(ImportingGroupId groupId) {
			return importingGroupRepo.find(groupId);
		}
		
		@Override
		public GroupWorkspace getGroupWorkspace(ImportingGroupId groupId) {
			return groupWorkspaceRepo.get(groupId);
		}

		@Override
		public List<String> getAllEmployeeIdsOfCanonicalizedData(ExecutionContext context) {
			return canonicalizedDataRecordRepo.getAllEmployeeIds(this, context);
		}

		@Override
		public List<AnyRecordToChange> getAllAnyRecordToChange(ExecutionContext context) {
			return existingRepo.findAllChanges(context);
		}

		@Override
		public List<AnyRecordToDelete> getAllAnyRecordToDelete(ExecutionContext context) {
			return existingRepo.findAllDeletes(context);
		}

		@Override
		public List<AnyRecordToChange> getAnyRecordToChangeWhere(ExecutionContext context, int keyItemNo, String keyValue) {
			return existingRepo.findAllChangesWhere(context, keyItemNo, keyValue);
		}

		@Override
		public List<AnyRecordToDelete> getAnyRecordToDeleteWhere(ExecutionContext context, int keyItemNo, String keyValue) {
			return existingRepo.findAllDeletesWhere(context, keyItemNo, keyValue);
		}

		@Override
		public void deleteTask(int taskFrameNo, String taskCode) {
			taskingRepo.delete(companyId, new TaskFrameNo(taskFrameNo), new TaskCode(taskCode));			
		}

		@Override
		public void changeEmploymentHistory(String employeeId, DateHistoryItem historyItem) {
			employmentHistoryRepo.update(historyItem);
		}

		@Override
		public void deleteEmploymentHistory(String employeeId, DateHistoryItem historyItem) {
			employmentHistoryRepo.delete(historyItem.identifier());
		}

		@Override
		public void deleteDailyPerformance(String employeeId, GeneralDate date) {
			workInformationRepo.delete(employeeId, date);
		}

		@Override
		public ConversionSource getConversionSource(String groupName) {
			return conversionTableRepo.getSource(groupName);
		}
		
		@Override
		public List<ConversionTable> getConversionTable(ConversionSource source, String groupName, ConversionCodeType cct) {
			return conversionTableRepo.get(groupName, source, cct);
		}

		@Override
		public int execute(List<ConversionSQL> conversionSqls) {
			return transferCanonicalDataRepo.execute(conversionSqls);
		}

		@Override
		public ImportingDataMeta getImportingDataMeta(ExecutionContext context) {
			return metaRepo.find(context);
		}

		@Override
		public ImportableItem getImportableItem(ImportingGroupId groupId, int itemNo) {
			return importableItemsRepo.get(groupId, itemNo).get();
		}
		
	}
}
