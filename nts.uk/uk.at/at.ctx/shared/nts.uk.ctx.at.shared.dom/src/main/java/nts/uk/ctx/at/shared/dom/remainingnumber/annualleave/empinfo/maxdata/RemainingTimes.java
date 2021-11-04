package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.io.Serializable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 休暇残回数
 * @author masaaki_jinno
 *
 */
@IntegerRange(min = -99, max = 99)
public class RemainingTimes extends IntegerPrimitiveValue<RemainingTimes> implements Serializable{

	private static final long serialVersionUID = -6059109049160476458L;

	public RemainingTimes(Integer rawValue) {
		super(rawValue);
	}

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99) rawValue = 99;
		if (rawValue < -99) rawValue = -99;
		return super.reviseRawValue(rawValue);
	}
	
    /** クローン */
	public RemainingTimes clone() {
		return new RemainingTimes(this.v());
	}
	
	//[１]残数超過分を補正する
	public RemainingTimes correctTheExcess(){
		if(this.v().doubleValue() < 0.0){
			return new RemainingTimes(0);
		}else{
			return new RemainingTimes(this.v());
		}
	}
}

