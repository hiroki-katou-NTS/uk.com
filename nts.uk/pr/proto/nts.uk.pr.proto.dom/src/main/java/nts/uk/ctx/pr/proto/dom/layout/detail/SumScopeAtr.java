package nts.uk.ctx.pr.proto.dom.layout.detail;
import lombok.AllArgsConstructor;

/**
 * 
 * 合計対象区分
 *
 */
@AllArgsConstructor
public enum SumScopeAtr {
	/** 0:対象外*/
	EXCLUDED(0),
	/** 1:対象内*/
	INCLUDED(1);
	
	public final int value;	
}
