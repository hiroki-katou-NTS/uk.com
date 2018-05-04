package nts.uk.ctx.at.function.infra.entity.alarm.extractionrange.monthly;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtExtractPeriodMonthPK implements Serializable{
	private static final long serialVersionUID = 1L;

	@Column(name = "EXTRACTION_ID")
	public String extractionId;
	
	@Column(name = "EXTRACTION_RANGE")
	public int extractionRange;
	
	@Column(name = "UNIT")
	public int unit;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extractionId == null) ? 0 : extractionId.hashCode());
		result = prime * result + extractionRange;
		result = prime * result + unit;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KfnmtExtractPeriodMonthPK other = (KfnmtExtractPeriodMonthPK) obj;
		if (extractionId == null) {
			if (other.extractionId != null)
				return false;
		} else if (!extractionId.equals(other.extractionId))
			return false;
		if (extractionRange != other.extractionRange)
			return false;
		if (unit != other.unit)
			return false;
		return true;
	}
}
