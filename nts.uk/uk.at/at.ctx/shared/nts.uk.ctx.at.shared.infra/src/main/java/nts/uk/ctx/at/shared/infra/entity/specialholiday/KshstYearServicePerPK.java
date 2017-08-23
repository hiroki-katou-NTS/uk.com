package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KshstYearServicePerPK implements Serializable {
	private static final long serialVersionUID = 1L;

	/* 会社ID */
	@Column(name = "CID")
	public String companyId;

	/* 勤続年数基ID */
	@Column(name = "YEAR_SERVICE_ID")
	public String yearServiceId;

	/* 勤続年数基コード */
	@Column(name = "YEAR_SERVICE_CD")
	public String yearServiceCode;
}