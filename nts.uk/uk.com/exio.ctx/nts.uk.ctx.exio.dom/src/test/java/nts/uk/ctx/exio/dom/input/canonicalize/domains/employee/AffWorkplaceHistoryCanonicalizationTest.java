package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee;

import static nts.arc.time.GeneralDate.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.Verifications;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.bs.employee.dom.workplace.affiliate.AffWorkplaceHistory;
import nts.uk.ctx.exio.dom.input.DataItem;
import nts.uk.ctx.exio.dom.input.canonicalize.Helper;
import nts.uk.ctx.exio.dom.input.canonicalize.Helper.Dummy;
import nts.uk.ctx.exio.dom.input.canonicalize.ImportingMode;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataId;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization.RequireCanonicalize;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.AffWorkplaceHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.existing.AnyRecordToChange;
import nts.uk.ctx.exio.dom.input.canonicalize.history.HistoryType;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.IntermediateResult;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataType;
import nts.uk.ctx.exio.dom.input.workspace.datatype.DataTypeConfiguration;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;
import nts.uk.ctx.exio.dom.input.workspace.item.WorkspaceItem;
import nts.uk.shr.com.history.DateHistoryItem;
import nts.uk.shr.com.history.History;

public class AffWorkplaceHistoryCanonicalizationTest {

	@Injectable
	DomainCanonicalization.RequireCanonicalize require;
	
	
	static class ItemNo {
		static final int EMP_CD = 1;
		static final int START_DATE = 2;
		static final int END_DATE = 3;
		static final int WKP_CODE = 4;
		static final int NORMAL_WKP_CODE = 5;

		static final int EMP_ID = 6;
		static final int HIST_ID = 7;
		static final int WKP_ID = 8;
		static final int NORMAL_WKP_ID = 9;
	}

	static final String EXIST_HIST_ID = "existingHistoryId";
	static final DatePeriod EXIST_HIST_PERIOD = new DatePeriod(ymd(2000, 1, 1), ymd(9999, 12, 31));

	static final String WORKPLACE_CODE = "workplaceCode";
	static final String WORKPLACE_ID = "worklpaceId";
	static final String NORMAL_WORKPLACE_CODE = "normalWorkplaceCode";
	static final String NORMAL_WORKPLACE_ID = "normalWorkplaceId";

	static final String EMPLOYEE_CODE = "employeeCode";
	static final String EMPLOYEE_ID = "employeeId";
	
	static final String NEW_HIST_ID = "newHistoryId";
	
	static final String PARENT_TABLE_NAME = "BSYMT_AFF_WKP_HIST";

	@Test
	public void canonicalizeHistory() {
		
		val existingHistoryItem = new DateHistoryItem(EXIST_HIST_ID, EXIST_HIST_PERIOD);
		val existingHistoryItems = new ArrayList<>(Arrays.asList(existingHistoryItem));
		
		val id = new DomainDataId(
				PARENT_TABLE_NAME,
				Arrays.asList(new DomainDataId.Key(DomainDataColumn.SID, EMPLOYEE_ID))); 
		History<DateHistoryItem, DatePeriod, GeneralDate> history =
				new AffWorkplaceHistory(Dummy.COMPANY_ID, EMPLOYEE_ID, existingHistoryItems);
		
		val newHistoryPeriod = new DatePeriod(ymd(2021, 4, 1), EXIST_HIST_PERIOD.end());
		IntermediateResult canonicalized = Helper.Interm.of(
				Helper.ItemList.of(
						new DataItem(ItemNo.EMP_ID, EMPLOYEE_ID),
						new DataItem(ItemNo.WKP_ID, WORKPLACE_ID),
						new DataItem(ItemNo.NORMAL_WKP_ID, NORMAL_WORKPLACE_ID)
				),
				Helper.ItemList.of(
						new DataItem(ItemNo.EMP_CD, EMPLOYEE_CODE),
						new DataItem(ItemNo.WKP_CODE, WORKPLACE_CODE),
						new DataItem(ItemNo.NORMAL_WKP_CODE, NORMAL_WORKPLACE_CODE)
				),
				Helper.ItemList.of(
						new DataItem(ItemNo.START_DATE, newHistoryPeriod.start()),
						new DataItem(ItemNo.END_DATE, newHistoryPeriod.end())));
		
		new Expectations() {{
			require.getHistory(id, HistoryType.PERSISTENERESIDENT, null);
			result = (History<DateHistoryItem, DatePeriod, GeneralDate>) history;
		}};
		
		new MockUp<IdentifierUtil>() {
			@Mock public String randomUniqueId() { return NEW_HIST_ID; }
		};

		val empcode = new DataTypeConfiguration(DataType.STRING, 12, 0);
		val code = new DataTypeConfiguration(DataType.STRING, 10, 0);
		val date = new DataTypeConfiguration(DataType.DATE, 0, 0);
		val guid = DataTypeConfiguration.guid();
		val domainWorkspace = new DomainWorkspace(
				ImportingDomainId.AFF_WORKPLACE_HISTORY,
				Arrays.asList(
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 7, "HIST_ID", guid)
						),
				Arrays.asList(
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 1, "社員コード", empcode),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 2, "開始日", date),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 3, "終了日", date),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 4, "職場コード", code),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 5, "通常職場コード", code),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 6, "SID", guid),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 8, "WORKPLACE_ID", guid),
						new WorkspaceItem(ImportingDomainId.AFF_WORKPLACE_HISTORY, 9, "NORMAL_WORKPLACE_ID", guid)
						)
		);
		val target = new AffWorkplaceHistoryCanonicalization(domainWorkspace);
		
		val context = Helper.context(ImportingMode.UPDATE_ONLY);
		
		List<IntermediateResult> actual = NtsAssert.Invoke.privateMethod(
				target, "canonicalizeHistory", require, context, Arrays.asList(canonicalized));
		
		assertThat(actual.size()).isEqualTo(1);
		
		// 正準化結果を検証
		Helper.asserts(actual.get(0))
			.equal(ItemNo.HIST_ID, NEW_HIST_ID);
		
		new Verifications() {{
			
			// 既存レコードの変更を検証
			AnyRecordToChange toChange;
			require.save(context, (AnyRecordToChange) (toChange = withCapture())); times = 1;
			
			assertThat(toChange.getKey(ItemNo.HIST_ID).asString()).isEqualTo(EXIST_HIST_ID);
		}};
	}

}
