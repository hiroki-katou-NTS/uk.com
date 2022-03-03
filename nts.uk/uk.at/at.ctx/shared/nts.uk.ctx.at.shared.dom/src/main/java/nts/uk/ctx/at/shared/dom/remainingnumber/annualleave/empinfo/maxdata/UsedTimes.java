package nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.maxdata;

import java.io.Serializable;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 休暇使用回数
 * @author masaaki_jinno
 *
 */
@IntegerRange(min = 0, max = 99)
public class UsedTimes extends IntegerPrimitiveValue<UsedTimes> implements Serializable{

	private static final long serialVersionUID = -6059109049160476458L;

	public UsedTimes(Integer rawValue) {
		super(rawValue);
	}

	@Override
	protected Integer reviseRawValue(Integer rawValue) {
		if (rawValue == null) return super.reviseRawValue(rawValue);
		if (rawValue > 99) rawValue = 99;
		if (rawValue < 0) rawValue = 0;
		return super.reviseRawValue(rawValue);
	}

	public UsedTimes add(UsedTimes target) {
		return new UsedTimes(this.v() + target.v());
	}
	
	//[１]残数超過分を補正する
	public UsedTimes correctTheExcess(RemainingTimes remainingTomes){
		if(remainingTomes.v() < 0.0){
			return new UsedTimes(this.v() + remainingTomes.v());
		}else{
			return new UsedTimes(this.v());
		}
	}

}

