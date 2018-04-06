package nts.uk.ctx.at.record.dom.divergence.time.history;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;

@Getter
@Setter
@AllArgsConstructor
/**
 * The Class DetermineReferenceTime.
 */
// 基準時間の判定内容
public class DetermineReferenceTime extends DomainObject {

	/** The reference time. */
	// 判定した基準時間
	ReferenceTime referenceTime;
	// 閾値
	/** The threshold. */
	DivergenceReferenceTime threshold;

	/**
	 * Instantiates a new determine reference time.
	 */
	public DetermineReferenceTime() {
		super();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((referenceTime == null) ? 0 : referenceTime.hashCode());
		result = prime * result + ((threshold == null) ? 0 : threshold.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DetermineReferenceTime other = (DetermineReferenceTime) obj;
		if (referenceTime != other.referenceTime)
			return false;
		if (threshold == null) {
			if (other.threshold != null)
				return false;
		} else if (!threshold.equals(other.threshold))
			return false;
		return true;
	}

}
