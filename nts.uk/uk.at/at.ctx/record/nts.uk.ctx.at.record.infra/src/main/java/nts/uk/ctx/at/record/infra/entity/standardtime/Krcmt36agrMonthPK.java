package nts.uk.ctx.at.record.infra.entity.standardtime;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class Krcmt36agrMonthPK implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "SID")
	public String employeeId;
	
	@Column(name = "YM_K")
	public BigDecimal yearmonthValue;
}
