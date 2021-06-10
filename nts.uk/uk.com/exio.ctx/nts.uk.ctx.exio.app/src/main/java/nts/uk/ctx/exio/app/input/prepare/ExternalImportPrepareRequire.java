package nts.uk.ctx.exio.app.input.prepare;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import nts.arc.diagnose.stopwatch.embed.EmbedStopwatch;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;
import nts.uk.ctx.bs.employee.dom.employment.history.EmploymentHistory;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.PrepareImporting;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToDelete;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.importableitem.ImportableItem;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.revise.reviseddata.RevisedDataRecord;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportCode;
import nts.uk.ctx.exio.dom.input.setting.ExternalImportSetting;
import nts.uk.ctx.exio.dom.input.validation.condition.ImportingUserCondition;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class ExternalImportPrepareRequire {

	public Require create() {
		
		return EmbedStopwatch.embed(new RequireImpl());
	}
	
	public static interface Require extends PrepareImporting.Require {
		
		Optional<ExternalImportSetting> getExternalImportSetting(String companyId, ExternalImportCode settingCode);
	}
	
	public class RequireImpl implements Require {

		@Override
		public List<ImportingUserCondition> getImportingUserCondition(String companyId, String settingCode,
				List<Integer> itemNo) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public List<ImportableItem> getDefinition(String companyId, ImportingGroupId groupId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public GroupCanonicalization getGroupCanonicalization(ImportingGroupId groupId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void save(AnyRecordToDelete toDelete) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void save(CanonicalizedDataRecord canonicalizedDataRecord) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getNumberOfRowsRevisedData() {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public void save(AnyRecordToChange recordToChange) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String getEmployeeIdByEmployeeCode(String companyId, String employeeCode) {
			// TODO Auto-generated method stub
			return null;
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
		public EmploymentHistory getEmploymentHistory(String employeeId) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<Task> getTask(String companyId, int taskFrameNo, String taskCode) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<WorkInfoOfDailyPerformance> getWorkInfoOfDailyPerformance(String employeeId, GeneralDate date) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Optional<ExternalImportSetting> getExternalImportSetting(String companyId,
				ExternalImportCode settingCode) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
