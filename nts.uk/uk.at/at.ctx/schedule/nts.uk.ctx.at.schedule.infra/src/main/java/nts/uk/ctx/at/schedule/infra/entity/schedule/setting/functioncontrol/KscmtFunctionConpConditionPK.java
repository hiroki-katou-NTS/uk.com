package nts.uk.ctx.at.schedule.infra.entity.schedule.setting.functioncontrol;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author TanLV
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtFunctionConpConditionPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** 条件NO */
	@Column(name = "CONDITION_NO")
	public int conditionNo;
}