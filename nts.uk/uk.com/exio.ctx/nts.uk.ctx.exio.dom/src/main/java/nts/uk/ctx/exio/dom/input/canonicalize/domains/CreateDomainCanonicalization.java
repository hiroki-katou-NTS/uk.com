package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import static nts.uk.ctx.exio.dom.input.domain.ImportingDomainId.*;
import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.EmployeeBasicCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.ShortWorkTimeCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory.CompensatoryHolidayCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.compensatory.HolidayWorkCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute.SubstituteHolidayCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.occurence.substitute.SubstituteWorkCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special.SpecialHolidayGrantRemainCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.holiday.special.SpecialHolidayGrantSettingCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.domain.ImportingDomainId;
import nts.uk.ctx.exio.dom.input.workspace.domain.DomainWorkspace;

/**
 * 受入グループ別の正準化インスタンスを作る
 */
public class CreateDomainCanonicalization {

	public static DomainCanonicalization create(Require require, ImportingDomainId domainId) {

		val domainWorkspace = require.getDomainWorkspace(domainId);
		
		return CREATES.get(domainId).apply(domainWorkspace);
	}

	private static final Map<ImportingDomainId, Function<DomainWorkspace, DomainCanonicalization>> CREATES;
	static {
		CREATES = new HashMap<>();

		// 作業
		CREATES.put(TASK, w -> new IndependentCanonicalization(w) {
			@Override
			protected String getParentTableName() {
				return "KSRMT_TASK_MASTER";
			}
			
			@Override
			protected List<String> getChildTableNames() {
				return Collections.emptyList();
			}
			
			@Override
			protected List<DomainDataColumn> getDomainDataKeys() {
				return Arrays.asList(
						DomainDataColumn.CID,
						new DomainDataColumn("FRAME_NO", INT),
						new DomainDataColumn("CD", STRING));
			}
		});
		
		// 個人基本情報
		CREATES.put(EMPLOYEE_BASIC, w -> new EmployeeBasicCanonicalization());
		
		// 雇用履歴
		CREATES.put(ImportingDomainId.EMPLOYMENT_HISTORY, w -> EmploymentHistoryCanonicalization.create(w));
		
		//分類履歴
		CREATES.put(CLASSIFICATION_HISTORY,w -> AffClassHistoryCanonicalization.create(w));

		//職位履歴
		CREATES.put(ImportingDomainId.JOBTITLE_HISTORY, w -> AffJobTitleHistoryCanonicalization.create(w));
		
		//所属職場履歴
		CREATES.put(ImportingDomainId.AFF_WORKPLACE_HISTORY, w -> AffWorkplaceHistoryCanonicalization.create(w));
		
		// 短時間勤務履歴
		CREATES.put(SHORT_WORK_TIME, ShortWorkTimeCanonicalization::new);
		
		//社員の年休付与設定
		CREATES.put(ImportingDomainId.EMPLOYEE_ANNUAL_LEAVE_SETTING, w -> EmployeeAnnualLeaveSettingCanonicalization.create(w));

		// 社員の特別休暇付与設定
		CREATES.put(ImportingDomainId.EMPLOYEE_SPECIAL_HOLIDAY_GRANT_SETTING, w -> SpecialHolidayGrantSettingCanonicalization.create(w));
		
		// 特別休暇付与残数データ
		CREATES.put(ImportingDomainId.SPECIAL_HORYDAY_GRANT_REMAIN, w -> SpecialHolidayGrantRemainCanonicalization.create(w));

		// 振休管理データ
		CREATES.put(SUBSTITUTE_HOLIDAY, SubstituteHolidayCanonicalization::new);

		// 振出管理データ
		CREATES.put(SUBSTITUTE_WORK, SubstituteWorkCanonicalization::new);

		// 代休管理データ
		CREATES.put(COMPENSATORY_HOLIDAY, CompensatoryHolidayCanonicalization::new);

		// 休出管理データ
		CREATES.put(HOLIDAY_WORK, HolidayWorkCanonicalization::new);
	}
	
	public static interface Require {
		
		DomainWorkspace getDomainWorkspace(ImportingDomainId domainId);
	}
}
