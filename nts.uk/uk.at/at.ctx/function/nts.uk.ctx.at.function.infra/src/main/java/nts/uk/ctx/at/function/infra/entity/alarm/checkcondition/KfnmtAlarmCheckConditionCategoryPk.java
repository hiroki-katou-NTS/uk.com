package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author HungTT
 *
 */

@Embeddable
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtAlarmCheckConditionCategoryPk implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "CID")
	public String companyId;

	@Basic
	@Column(name = "CATEGORY")
	public int category;

	@Basic
	@Column(name = "CD")
	public String code;

}
