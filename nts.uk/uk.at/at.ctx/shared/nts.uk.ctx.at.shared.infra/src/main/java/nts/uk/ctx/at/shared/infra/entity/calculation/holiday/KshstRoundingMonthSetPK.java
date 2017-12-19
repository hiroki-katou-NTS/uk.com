package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class KshstRoundingMonthSetPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
}