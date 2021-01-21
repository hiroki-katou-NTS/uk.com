package nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KrcstDisplayAndInputMonthlyPK implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "CID")
	public String companyID;
	
	@Column(name = "AUTHORITY_MON_ID")
	public String authorityMonthlyID;
	
	@Column(name = "ITEM_MONTHLY_ID")
	public int itemMonthlyID;

}
