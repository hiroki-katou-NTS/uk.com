package nts.uk.ctx.bs.person.dom.person.info.currentaddress;

import nts.arc.primitive.StringPrimitiveValue;

//最寄り駅
public class NearestStation extends StringPrimitiveValue<NearestStation>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NearestStation(String nearestStation){
		super(nearestStation);
	}
}
