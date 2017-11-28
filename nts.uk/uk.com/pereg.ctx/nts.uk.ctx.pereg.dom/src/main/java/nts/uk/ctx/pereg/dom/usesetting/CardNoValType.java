package nts.uk.ctx.pereg.dom.usesetting;

import lombok.AllArgsConstructor;
import nts.arc.primitive.constraint.IntegerRange;

@AllArgsConstructor
@IntegerRange(max = 5, min = 1)
public enum CardNoValType {
	//頭文字指定 (InitialDesignation)
	INIT_DESIGNATION(1),
	//空白 (Blank)
	BLANK(2),
	//社員コードと同じ (SameAsEmployeeCode)
	SAME_AS_EMP_CODE(3),
	//最大値 (MaxValue)
	MAXVALUE(4),
	//会社コード＋社員コード (CompanyCodeAndEmployeeCode)
	CPC_AND_EMPC(5);
	public final int value;
}
