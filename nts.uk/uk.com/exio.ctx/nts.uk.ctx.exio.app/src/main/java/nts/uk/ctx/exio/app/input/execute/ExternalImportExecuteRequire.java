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
import nts.uk.ctx.exio.dom.input.ExecuteImporting;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportiongItemRepository;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroup;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.transfer.ConversionTableRepository;
import nts.uk.ctx.exio.dom.input.transfer.TransferCanonicalDataRepository;
import nts.uk.shr.com.history.DateHistoryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportExecuteRequire {

	public Require create(String companyId) {
		return EmbedStopwatch.embed(new RequireImpl(companyId));
	}
	
	public static interface Require extends ExecuteImporting.Require {
		
	}
	
	@Inject
	ImportiongItemRepository importingItemRepo;
	
	@Inject
	ConversionTableRepository conversionTableRepo;
	
	@Inject
	TransferCanonicalDataRepository transferCanonicalDataRepo;
	
	@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
	public class RequireImpl implements Require {
		
		private final String companyId;

		@Override
		public Optional<ExternalImportSetting> getExternalImportSetting(String companyId,
				ExternalImportCode settingCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public ImportingGroup getImportingGroup(ImportingGroupId groupId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getAllEmployeeIdsOfCanonicalizedData(ExecutionContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AnyRecordToChange> getAllAnyRecordToChange(ExecutionContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AnyRecordToDelete> getAllAnyRecordToDelete(ExecutionContext context) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public GroupCanonicalization getGroupCanonicalization(ImportingGroupId groupId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void deleteTask(String companyId, int taskFrameNo, String taskCode) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void changeEmploymentHistory(String companyId, String employeeId, DateHistoryItem historyItem) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteEmploymentHistory(String companyId, String employeeId, DateHistoryItem historyItem) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void deleteDailyPerformance(String employeeId, GeneralDate date) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public List<AnyRecordToChange> getEmployeeAnyRecordToChange(ExecutionContext context, String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<AnyRecordToDelete> getEmployeeAnyRecordToDelete(ExecutionContext context, String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<String> getImportingItem() {
			return importingItemRepo.get(companyId);
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
		
	}
}
