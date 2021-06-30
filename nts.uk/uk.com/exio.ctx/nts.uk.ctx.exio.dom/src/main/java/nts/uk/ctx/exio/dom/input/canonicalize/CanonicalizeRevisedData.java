package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.groups.GroupCanonicalization;
import nts.uk.ctx.exio.dom.input.importableitem.group.ImportingGroupId;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

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
		
		val canonicalization = require.getGroupCanonicalization(context.getGroupId());
		val metaCanonicalized = canonicalization.canonicalize(require, context, meta);
		
		require.save(metaCanonicalized);
	}
	
	public static interface Require extends GroupCanonicalization.RequireCanonicalize {
		
		GroupCanonicalization getGroupCanonicalization(ImportingGroupId groupId);
		
		void save(ImportingDataMeta meta);
	}
}
