package nts.uk.ctx.exio.dom.input.canonicalize;

import lombok.val;
import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.domains.DomainCanonicalization;
import nts.uk.ctx.exio.dom.input.meta.ImportingDataMeta;

/**
 * 編集済みデータを正準化する 正準化プロセスの全体的な方針として、一時テーブルへの永続化はトランザクション制御しない。
 * アーキテクチャ的にうまくトランザクション制御するのが難しいことと、トランザクション制御する利点が無いことによる。
 * したがって、AtomTaskも返さず、随時Require経由で永続化処理が呼ばれることになる。
 */
public class CanonicalizeRevisedData {

	/**
	 * 正準化する
	 * 
	 * @param require
	 * @param context
	 */
	public static void canonicalize(Require require, ExecutionContext context, ImportingDataMeta meta) {

		val canonicalization = context.getDomainId().createCanonicalization();

		canonicalization.canonicalize(require, context);
		val metaCanonicalized = canonicalization.appendMeta(meta);

		require.save(context, metaCanonicalized);
	}

	public static interface Require extends
			DomainCanonicalization.RequireCanonicalize {

		void save(ExecutionContext context, ImportingDataMeta meta);
	}
}
