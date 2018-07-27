package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;

@AllArgsConstructor
@Getter
public class StampCardEditing extends AggregateRoot {


	private String companyId;

	private StampCardDigitNumber digitsNumber;

	private StampCardEditMethod method;

	public static StampCardEditing createFromJavaType(String companyId, int digitsNumber, int method) {
		return new StampCardEditing(companyId, new StampCardDigitNumber(digitsNumber), EnumAdaptor.valueOf(method, StampCardEditMethod.class));
	}

}
