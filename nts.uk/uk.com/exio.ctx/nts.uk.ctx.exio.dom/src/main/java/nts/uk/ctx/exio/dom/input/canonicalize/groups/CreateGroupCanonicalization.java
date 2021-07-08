package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import static nts.uk.ctx.exio.dom.input.group.ImportingGroupId.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.val;
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
		CREATES.put(TASK, TaskCanonicalization::new);
		
		// 雇用履歴
		CREATES.put(ImportingGroupId.EMPLOYMENT_HISTORY, EmploymentHistoryCanonicalization::new);
	}
	
	public static interface Require {
		
		GroupWorkspace getGroupWorkspace(ImportingGroupId groupId);
	}
}
