package nts.uk.ctx.exio.dom.input.canonicalize.domains;

import static nts.uk.ctx.exio.dom.input.domain.ImportingDomainId.*;
import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.INT;
import static nts.uk.ctx.exio.dom.input.workspace.datatype.DataType.STRING;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import lombok.val;
import nts.uk.ctx.exio.dom.input.canonicalize.domaindata.DomainDataColumn;
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
		
		// 雇用履歴
		CREATES.put(ImportingDomainId.EMPLOYMENT_HISTORY, w -> EmploymentHistoryCanonicalization.create(w));
		
		//分類履歴
		CREATES.put(CLASSIFICATION_HISTORY,w -> AffClassHistoryCanonicalization.create(w));

		//職位履歴
		CREATES.put(ImportingDomainId.JOBTITLE_HISTORY, w -> AffJobTitleHistoryCanonicalization.create(w));
	}
	
	public static interface Require {
		
		DomainWorkspace getDomainWorkspace(ImportingDomainId domainId);
	}
}
