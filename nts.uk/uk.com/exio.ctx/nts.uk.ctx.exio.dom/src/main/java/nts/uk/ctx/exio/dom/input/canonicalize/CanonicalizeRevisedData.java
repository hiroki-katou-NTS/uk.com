package nts.uk.ctx.exio.dom.input.canonicalize;

import static nts.uk.ctx.exio.dom.input.group.ImportingGroupId.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.EmploymentHistoryCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.TaskCanonicalization;
import nts.uk.ctx.exio.dom.input.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;
import nts.uk.ctx.exio.dom.input.workspace.group.GroupWorkspace;

/**
 * 編集済みデータを正準化する
 * 正準化プロセスの全体的な方針として、一時テーブルへの永続化はトランザクション制御しない。
 * アーキテクチャ的にうまくトランザクション制御するのが難しいことと、トランザクション制御する利点が無いことによる。
 * したがって、AtomTaskも返さず、随時Require経由で永続化処理が呼ばれることになる。
 */
public class CanonicalizeRevisedData {

	/**
	 * 正準化する
	 * @param require
	 * @param context
	 */
	public static void canonicalize(Require require, ExecutionContext context, ImportingDataMeta meta) {

		val canonicalization = GroupCanonicalizations.get(require, context.getGroupId());
		
		canonicalization.canonicalize(require, context);
		val metaCanonicalized = canonicalization.appendMeta(meta);
		
		require.save(metaCanonicalized);
	}
	
	private static final Map<ImportingGroupId, Function<GroupWorkspace, GroupCanonicalization>> CREATES;
	static {
		CREATES = new HashMap<>();

		// 作業
		CREATES.put(TASK, TaskCanonicalization::new);
		
		// 雇用履歴
		CREATES.put(ImportingGroupId.EMPLOYMENT_HISTORY, EmploymentHistoryCanonicalization::new);
	}
	
	public static interface Require extends
			GroupCanonicalizations.Require,
			GroupCanonicalization.RequireCanonicalize {
		
		void save(ImportingDataMeta meta);
	}
}
