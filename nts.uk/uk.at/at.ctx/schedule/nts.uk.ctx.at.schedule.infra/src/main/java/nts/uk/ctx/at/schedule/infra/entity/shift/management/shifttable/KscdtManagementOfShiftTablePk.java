package nts.uk.ctx.at.schedule.infra.entity.shift.management.shifttable;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscdtManagementOfShiftTablePk  implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;

	/**
	 * 対象組織の単位 0:職場 1:職場グループ
	 */
	@Column(name = "TARGET_UNIT")
	public int targetUnit;

	@Column(name = "TARGET_ID")
	public String targetID;

}
