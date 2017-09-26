package nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceper;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshstYearServicePerPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*コード*/
	@Column(name = "SPHD_CD")
	public String specialHolidayCode;
	/*コード*/
	@Column(name = "YEAR_SERVICE_CD")
	public String yearServiceCode;
}
