package nts.uk.ctx.basic.dom.system.era;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

public class Era extends AggregateRoot {
	@Getter
	private EraName eraName;
	@Getter
	private EraMark eraMark;
	@Getter
	private GeneralDate startDate;
	@Getter
	@Setter
	private GeneralDate endDate;
	@Getter
	private FixAttribute fixAttribute;

	public Era(EraName eraName, EraMark eraMark, GeneralDate startDate, GeneralDate endDate,
			FixAttribute fixAttribute) {
		super();
		this.eraName = eraName;
		this.eraMark = eraMark;
		this.startDate = startDate;
		this.endDate = endDate;
		this.fixAttribute = fixAttribute;
	}

	// convert data to era type
	public static Era createFromDataType(String eraName, String eraMark, GeneralDate startDate, GeneralDate endDate,
			int fixAttribute) {
		return new Era(new EraName(eraName), new EraMark(eraMark), startDate, endDate,
				EnumAdaptor.valueOf(fixAttribute, FixAttribute.class));

	}

}
