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
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.cnv.core.dom.conversionsql.ConversionSQL;
import nts.uk.cnv.core.dom.conversiontable.ConversionCodeType;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.ctx.exio.dom.input.ExecuteImporting;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecordRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.ExternalImportExistingRepository;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomain;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainRepository;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentState;
import nts.uk.ctx.exio.dom.input.manage.ExternalImportCurrentStateRepository;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMetaRepository;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSettingRepository;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalDataRepository;
import nts.uk.ctx.exio.dom.input.workspace.ExternalImportWorkspaceRepository;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspaceRepository;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportExecuteRequire {

	public Require create(String companyId) {
		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}
	
	public static interface Require extends
			ExternalImportCurrentState.Require,
			ExecuteImporting.Require,
			ExternalImportWorkspaceRepository.Require {
		
		ExternalImportCurrentState getExternalImportCurrentState(String companyId);
	}
	
	@Inject
	private ExternalImportCurrentStateRepository currentStateRepo;
	
	@Inject
	private ExternalImportSettingRepository settingRepo;
	
	@Inject
	private ImportingDomainRepository importingDomainRepo;
	
	@Inject
	private ConversionTableRepository conversionTableRepo;
	
	@Inject
	private TransferCanonicalDataRepository transferCanonicalDataRepo;
	
	@Inject
	private DomainWorkspaceRepository domainWorkspaceRepo;
	
	@Inject
	private CanonicalizedDataRecordRepository canonicalizedDataRecordRepo;
	
	@Inject
	private ExternalImportExistingRepository existingRepo;
	
	@Inject
	private ImportingDataMetaRepository metaRepo;
	
	@Inject
	private DomainDataRepository domainDataRepo;
	
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class RequireImpl implements Require {
		
		@SuppressWarnings("unused")
		private final String companyId;

		@Override
		public ExternalImportCurrentState getExternalImportCurrentState(String companyId) {
			return currentStateRepo.find(companyId);
		}

		@Override
		public void update(ExternalImportCurrentState currentState) {
			currentStateRepo.save(currentState);
		}

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
		public ConversionSource getConversionSource(String domainName) {
			return conversionTableRepo.getSource(domainName);
		}
		
		@Override
		public List<ConversionTable> getConversionTable(ConversionSource source, String domainName, ConversionCodeType cct) {
			return conversionTableRepo.get(domainName, source, cct);
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
		public void deleteDomainData(DomainDataId id) {
			domainDataRepo.delete(id);
		}

		@Override
		public void delete(DomainDataId id) {
			domainDataRepo.delete(id);			
		}

		@Override
		public void update(DomainDataId id, DatePeriod period) {
			domainDataRepo.update(id, period);
		}
	}
}
