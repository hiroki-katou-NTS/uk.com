package nts.uk.ctx.at.shared.infra.entity.specialholidaynew;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstSpecialHolidayPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	
	/*特別休暇コード*/
	@Column(name = "SPHD_CD")
	public int specialHolidayCode;
}