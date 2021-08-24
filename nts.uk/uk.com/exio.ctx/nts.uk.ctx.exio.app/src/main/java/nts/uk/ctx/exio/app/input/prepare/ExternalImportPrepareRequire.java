package nts.uk.ctx.exio.app.input.prepare;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.PrepareImporting;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.ExternalImportExistingRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomain;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportErrorsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMetaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.assembly.RevisedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItem;
import nts.uk.ctx.exio.dom.input.setting.assembly.revise.ReviseItemRepository;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserCondition;
import nts.uk.ctx.exio.dom.input.validation.user.ImportingUserConditionRepository;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspaceRepository;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportPrepareRequire {
	
	public Require create(String companyId) {
		
		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}
	
	public static interface Require extends
			PrepareImporting.Require,
			ExternalImportWorkspaceRepository.Require {
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);
	}
	
	@Inject
	private ImportingUserConditionRepository importingUserConditionRepo;
	
	@Inject
	private ImportableItemsRepository importableItemsRepo;
	
	@Inject
	private ImportingDomainRepository importingDomainRepo;
	
	@Inject
	private DomainWorkspaceRepository domainWorkspaceRepo;
	
	@Inject
	private ExternalImportWorkspaceRepository workspaceRepo;
	
	@Inject
	private RevisedDataRecordRepository revisedDataRecordRepo;
	
	@Inject
	private CanonicalizedDataRecordRepository canonicalizedDataRecordRepo;
	
	@Inject
	private ExternalImportExistingRepository existingRepo;
	
	@Inject
	private ImportingDataMetaRepository metaRepo;
	
	@Inject
	private DomainDataRepository domainDataRepo;
	
	@Inject
	private EmployeeDataMngInfoRepository employeeDataMngInfoRepo;
	
	@Inject
	private TaskingRepository taskingRepo;
	
	@Inject
	private ExternalImportSettingRepository settingRepo;
	
	@Inject
	private ReviseItemRepository reviseItemRepo;
	
	@Inject
	private ExternalImportErrorsRepository errorsRepo;
	
	public class RequireImpl implements Require {
		
		private final String companyId ;
		
		public RequireImpl(String companyId) {
			this.companyId = companyId;
		}
		
		
		/***** 外部受入関連 *****/
		
		@Override
		public Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode) {
			return settingRepo.get(companyId, settingCode);
		}
		
		@Override
		public ImportingDomain getImportingDomain(ImportingDomainId domainId) {
			return importingDomainRepo.find(domainId);
		}
		
		@Override
		public DomainWorkspace getDomainWorkspace(ImportingDomainId domainId) {
			return domainWorkspaceRepo.get(domainId);
		}
		
		@Override
		public Optional<ReviseItem> getReviseItem(String companyId, ExternalImportCode importCode, int importItemNumber) {
			return reviseItemRepo.get(companyId, importCode, importItemNumber);
		}
		
		@Override
		public ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo) {
			return importableItemsRepo.get(domainId, itemNo)
					.orElseThrow(() -> new RuntimeException("not found: " + domainId + ", " + itemNo));
		}
		
		@Override
		public Optional<ImportingUserCondition> getImportingUserCondition(String settingCode,
				int itemNo) {
			return importingUserConditionRepo.get(companyId, settingCode, itemNo);
		}
		
		
		/***** Workspace *****/
		
		@Override
		public void setupWorkspace(ExecutionContext context) {
			workspaceRepo.setup(this, context);
			existingRepo.setup(context);
			metaRepo.setup(context);
			errorsRepo.setup(context);
		}
		
		@Override
		public void save(ExecutionContext context, AnyRecordToDelete toDelete) {
			existingRepo.save(context, toDelete);
		}
		
		@Override
		public void save(ExecutionContext context, AnyRecordToChange recordToChange) {
			existingRepo.save(context, recordToChange);
		}
		
		@Override
		public void save(ExecutionContext context, RevisedDataRecord revisedDataRecord) {
			revisedDataRecordRepo.save(this, context, revisedDataRecord);
		}
		
		@Override
		public void save(ExecutionContext context, CanonicalizedDataRecord canonicalizedDataRecord) {
			canonicalizedDataRecordRepo.save(this, context, canonicalizedDataRecord);
		}
		
		@Override
		public int getMaxRowNumberOfRevisedData(ExecutionContext context) {
			return revisedDataRecordRepo.getMaxRowNumber(this, context);
		}
		
		@Override
		public List<String> getStringsOfRevisedData(ExecutionContext context, int itemNo) {
			return revisedDataRecordRepo.getStrings(this, context, itemNo);
		}
		
		@Override
		public Optional<RevisedDataRecord> getRevisedDataRecordByRowNo(ExecutionContext context, int rowNo) {
			return revisedDataRecordRepo.findByRowNo(this, context, rowNo);
		}
		
		@Override
		public List<RevisedDataRecord> getRevisedDataRecordWhere(
				ExecutionContext context, int itemNoCondition, String conditionString) {
			return revisedDataRecordRepo.findByCriteria(this, context, itemNoCondition, conditionString);
		}
		
		@Override
		public void save(ExecutionContext context, ImportingDataMeta meta) {
			metaRepo.save(context, meta);
		}
		
		
		/***** domains for canonicalization *****/
		
		@Override
		public Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeCode(String employeeCode) {
			return employeeDataMngInfoRepo.findByScdNotDel(employeeCode, companyId);
		}
		
		public Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode) {
			return taskingRepo.getOptionalTask(companyId, new TaskFrameNo(taskFrameNo), new TaskCode(taskCode));
		}
		
		@Override
		public boolean existsDomainData(DomainDataId id) {
			return domainDataRepo.exists(id);
		}


		@Override
		public History<DateHistoryItem, DatePeriod, GeneralDate> getHistory(DomainDataId id, Class<?> historyClass) {
			return domainDataRepo.getHistory(id, historyClass);
		}

		@Override
		public void add(ExecutionContext context, ExternalImportError error) {
			errorsRepo.add(context, error);
		}

	}
}
