package nts.uk.ctx.at.function.infra.entity.alarm;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAlstPtnExcpmtPK implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")	
	public String companyID;
	
	@Column(name = "ALARM_PATTERN_CD")	
	public String alarmPatternCD;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((alarmPatternCD == null) ? 0 : alarmPatternCD.hashCode());
		result = prime * result + ((companyID == null) ? 0 : companyID.hashCode());
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
		KfnmtAlstPtnExcpmtPK other = (KfnmtAlstPtnExcpmtPK) obj;
		if (alarmPatternCD == null) {
			if (other.alarmPatternCD != null)
				return false;
		} else if (!alarmPatternCD.equals(other.alarmPatternCD))
			return false;
		if (companyID == null) {
			if (other.companyID != null)
				return false;
		} else if (!companyID.equals(other.companyID))
			return false;
		return true;
	}
	
}
