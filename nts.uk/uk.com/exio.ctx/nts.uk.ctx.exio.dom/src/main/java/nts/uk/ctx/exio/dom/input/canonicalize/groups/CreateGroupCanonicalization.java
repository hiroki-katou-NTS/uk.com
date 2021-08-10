package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import static nts.uk.ctx.exio.dom.input.group.ImportingGroupId.CLASSIFICATION_HISTORY;
import static nts.uk.ctx.exio.dom.input.group.ImportingGroupId.TASK;
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
import nts.uk.ctx.exio.dom.input.canonicalize.groups.generic.IndependentCanonicalization;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 * 受入グループ別の正準化インスタンスを作る
 */
public class CreateGroupCanonicalization {

	public static GroupCanonicalization create(Require require, ImportingGroupId groupId) {

		val groupWorkspace = require.getGroupWorkspace(groupId);
		
		return CREATES.get(groupId).apply(groupWorkspace);
	}

	private static final Map<ImportingGroupId, Function<GroupWorkspace, GroupCanonicalization>> CREATES;
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
		CREATES.put(ImportingGroupId.EMPLOYMENT_HISTORY, w -> EmploymentHistoryCanonicalization.create(w));
		
		//分類履歴
		CREATES.put(CLASSIFICATION_HISTORY,w -> AffClassHistoryCanonicalization.create(w));

		//職位履歴
		CREATES.put(ImportingGroupId.JOBTITLE_HISTORY, w -> AffJobTitleHistoryCanonicalization.create(w));
	}
	
	public static interface Require {
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
