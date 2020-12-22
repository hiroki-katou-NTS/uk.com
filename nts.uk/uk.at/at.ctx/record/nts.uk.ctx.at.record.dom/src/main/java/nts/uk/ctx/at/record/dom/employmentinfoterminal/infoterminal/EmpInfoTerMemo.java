package nts.uk.ctx.at.record.dom.employmentinfoterminal.infoterminal;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author thanh_nx
 *
 *         就業情報端末のメモ
 */
@StringMaxLength(500)
public class EmpInfoTerMemo extends StringPrimitiveValue<EmpInfoTerMemo> {

	private static final long serialVersionUID = 1L;
	
	public EmpInfoTerMemo(String rawValue) {
		super(rawValue);
	}

}
