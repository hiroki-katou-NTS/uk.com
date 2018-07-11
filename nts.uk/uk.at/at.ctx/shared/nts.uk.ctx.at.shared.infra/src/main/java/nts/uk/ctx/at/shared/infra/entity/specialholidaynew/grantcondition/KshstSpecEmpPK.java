package nts.uk.ctx.at.shared.infra.entity.specialholidaynew.grantcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstSpecEmpPK implements Serializable {
private static final long serialVersionUID = 1L;
	
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* 特別休暇コード */
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;
	
	/* コード */
	@Column(name = "SPEC_LEAVE_CD")
	public String specLeaveCd;
	
	/* 雇用コード */
	@Column(name = "EMP_CD")
	public String empCode;
}
