package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearservicecom;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstYearServiceComPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*コード*/
	@Column(name = "SPHD_CD")
	public String specialHolidayCode;
}
