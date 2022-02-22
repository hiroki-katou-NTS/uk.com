package nts.uk.ctx.exio.app.input.prepare;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.layer.app.cache.MapCache;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCard;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampCardRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Stamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampDakokuRepository;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ChangeClockAtr;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.repo.taskmaster.TaskingRepository;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfo;
import nts.uk.ctx.bs.employee.dom.employee.mgndata.EmployeeDataMngInfoRepository;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfo;
import nts.uk.ctx.bs.employee.dom.jobtitle.info.JobTitleInfoRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfiguration;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceConfigurationRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformation;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.PrepareImporting;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.ExternalImportExistingRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.history.ExternalImportHistory;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryKeyColumnNames;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.result.CanonicalizedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomain;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportError;
import nts.uk.ctx.exio.dom.input.errors.ExternalImportErrorsRepository;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItemsRepository;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentState;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentStateRepository;
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
import nts.uk.ctx.sys.shared.dom.user.User;
import nts.uk.ctx.sys.shared.dom.user.UserRepository;
import nts.uk.shr.com.company.CompanyId;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportPrepareRequire {

	public Require create(String companyId) {

		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}

	public static interface Require extends
			ExternalImportCurrentState.Require,
			PrepareImporting.Require,
			ExternalImportWorkspaceRepository.Require {

		ExternalImportSetting getExternalImportSetting(ExternalImportCode settingCode);

		ExternalImportCurrentState getExternalImportCurrentState();
	}

	@Inject
	private ExternalImportCurrentStateRepository currentStateRepo;

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

	@Inject
	private WorkplaceConfigurationRepository wkpConfigRepo;

	@Inject
	private WorkplaceInformationRepository wkpInfoRepo;

	@Inject
	private JobTitleInfoRepository jobTitleInfoRepo;

	@Inject
	private UserRepository userRepo;

	@Inject
	private StampCardRepository stampCardRepo;

	@Inject
	private StampDakokuRepository stampDakokuRepo;



	public class RequireImpl implements Require {

		private final String contractCode;

		private final String companyId;

		private final MapCache<Triple<ExternalImportCode, ImportingDomainId, Integer>, ReviseItem> cacheReviseItem;

		private final MapCache<Pair<ImportingDomainId, Integer>, ImportableItem> cacheImportableItem;

		private final MapCache<Pair<String, Integer>, ImportingUserCondition> cacheImportingUserCondition;

		public RequireImpl(String companyId) {
			this.contractCode = CompanyId.getContractCodeOf(companyId);
			this.companyId = companyId;

			this.cacheReviseItem = MapCache.incremental(key -> {
				return reviseItemRepo.get(companyId, key.getLeft(), key.getMiddle(), key.getRight());
			});

			this.cacheImportableItem = MapCache.incremental(key -> {
				return importableItemsRepo.get(key.getLeft(), key.getRight());
			});

			this.cacheImportingUserCondition = MapCache.incremental(key -> {
				return importingUserConditionRepo.get(companyId, key.getLeft(), key.getRight());
			});
		}


		/***** 外部受入関連 *****/

		@Override
		public void add(ExternalImportError error) {
			errorsRepo.add(companyId, error);
		}

		@Override
		public ExternalImportCurrentState getExternalImportCurrentState() {
			return currentStateRepo.find(companyId);
		}

		@Override
		public void update(ExternalImportCurrentState currentState) {
			currentStateRepo.save(currentState);
		}

		@Override
		public ExternalImportSetting getExternalImportSetting(ExternalImportCode settingCode) {
			return settingRepo.get(null, companyId, settingCode)
					.orElseThrow(() -> new RuntimeException("not found: " + companyId + ", " + settingCode));
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
		public Optional<ReviseItem> getReviseItem(ExternalImportCode importCode, ImportingDomainId domainId, int importItemNumber) {
			return cacheReviseItem.get(Triple.of(importCode, domainId, importItemNumber));
		}

		@Override
		public ImportableItem getImportableItem(ImportingDomainId domainId, int itemNo) {
			return cacheImportableItem.get(Pair.of(domainId, itemNo))
					.orElseThrow(() -> new RuntimeException("not found: " + domainId + ", " + itemNo));
		}

		@Override
		public Optional<ImportingUserCondition> getImportingUserCondition(String settingCode, int itemNo) {
			return cacheImportingUserCondition.get(Pair.of(settingCode, itemNo));
		}


		/***** Workspace *****/

		@Override
		public void setupWorkspace() {
			errorsRepo.cleanOldTables(companyId);

			errorsRepo.setup(companyId);
		}

		@Override
		public void setupWorkspaceForEachDomain(ExecutionContext context) {
			workspaceRepo.cleanOldTables(this, context);
			existingRepo.cleanOldTables(context);
			metaRepo.cleanOldTables(context);

			workspaceRepo.setup(this, context);
			existingRepo.setup(context);
			metaRepo.setup(context);
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
		public List<RevisedDataRecord> getAllRevisedDataRecords(ExecutionContext context) {
			return revisedDataRecordRepo.findAll(this, context);
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

		@Override
		public Optional<EmployeeDataMngInfo> getEmployeeDataMngInfoByEmployeeId(String employeeId) {
			return employeeDataMngInfoRepo.findByEmpId(employeeId);
		}

		public Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode) {
			return taskingRepo.getOptionalTask(companyId, new TaskFrameNo(taskFrameNo), new TaskCode(taskCode));
		}

		@Override
		public boolean existsDomainData(DomainDataId id) {
			return domainDataRepo.exists(id);
		}


		@Override
		public ExternalImportHistory getHistory(DomainDataId id, HistoryType historyType, HistoryKeyColumnNames keyColumnNames) {
			return domainDataRepo.getHistory(id, historyType, keyColumnNames);
		}

		@Override
		public Optional<WorkplaceInformation> getWorkplaceByCode(String workplaceCode, GeneralDate startdate) {
			return wkpInfoRepo.getWkpNewByCdDate(companyId, workplaceCode, startdate);
		}


		@Override
		public Optional<User> getUserByPersonId(String personId) {
			return userRepo.getByAssociatedPersonId(personId);
		}

		@Override
		public Optional<User> getUserByLoginId(String loginId) {
			return userRepo.getByLoginId(loginId).stream()
					// 契約コードで絞れば最大１つのはず
					.filter(u -> u.getContractCode().v().equals(contractCode))
					.findFirst();
		}

		@Override
		public Optional<JobTitleInfo> getJobTitleByCode(String jobTitleCode, GeneralDate startdate) {
			return jobTitleInfoRepo.findAll(companyId, startdate).stream()
					.filter(jobTitle -> jobTitle.getJobTitleCode().equals(jobTitleCode))
					.findFirst();
		}

		@Override
		public Optional<WorkplaceConfiguration> getWorkplaceConfiguration(String companyId) {
			return wkpConfigRepo.getWkpConfig(companyId);
		}

		public Optional<StampCard> getStampCardByCardNumber(String cardNumber) {
			return stampCardRepo.getByCardNoAndContractCode(cardNumber, contractCode);
		}

		@Override
		public List<WorkplaceInformation> getAllWorkplaceInformations(String companyId) {
			return wkpInfoRepo.findAll(companyId);
		}

		@Override
		public boolean existsStamp(String cardNumber, GeneralDateTime stampDateTime, int changeClockArt) {
			return stampDakokuRepo.existsStamp(new ContractCode(contractCode), new StampNumber(cardNumber), stampDateTime, ChangeClockAtr.valueOf(changeClockArt));
		}
	}
}
