package nts.uk.ctx.exio.dom.input.canonicalize.groups;

import nts.uk.ctx.exio.dom.input.ExecutionContext;
import nts.uk.ctx.exio.dom.input.canonicalize.CanonicalizedDataRecord;
import nts.uk.ctx.exio.dom.input.canonicalize.methods.CanonicalizationMethod;

/**
 * 受入グループ別の正準化
 */
public interface GroupCanonicalization {

	/**
	 * 正準化する
	 * @param require
	 * @param context
	 */
	void canonicalize(Require require, ExecutionContext context);
	
	public static interface Require extends
		CanonicalizationMethod.Require {
		
		void save(CanonicalizedDataRecord canonicalizedDataRecord);
	}
}
