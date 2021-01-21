/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;

/**
 * The Class ReferWorkRecord.
 */
// 勤務実績を参照する
@Getter
public class ReferWorkRecord extends DomainObject implements SerializableWithOptional{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	// 所定時間参照先
	private Optional<ReferenceDestinationAbsenceWorkingHours> timeReferenceDestination;
	// 会社一律の加算時間
	private Optional<BreakdownTimeDay> additionTimeCompany;

	private void writeObject(ObjectOutputStream stream){
		writeObjectWithOptional(stream);
	}
	
	private void readObject(ObjectInputStream stream){
		readObjectWithOptional(stream);
	}
	
	/**
	 * @param timeReferenceDestination
	 * @param additionTimeCompany
	 */
	public ReferWorkRecord(ReferenceDestinationAbsenceWorkingHours timeReferenceDestination,
			BreakdownTimeDay additionTimeCompany) {
		super();
		this.timeReferenceDestination = Optional.of(timeReferenceDestination);
		this.additionTimeCompany = Optional.of(additionTimeCompany);
	}
}

