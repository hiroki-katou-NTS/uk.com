package nts.uk.ctx.at.shared.infra.entity.specialholiday.grantcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 特別休暇利用条件
 * 
 * @author tanlv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHMT_HDSP_CONDITION")
public class KshstSpecialLeaveRestriction extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSpecialLeaveRestrictionPK pk;

	/* 分類条件 */
	@Column(name = "RESTRICTION_CLS")
	public int restrictionCls;
	
	/* 年齢条件 */
	@Column(name = "AGE_LIMIT")
	public int ageLimit;
	
	/* 性別条件 */
	@Column(name = "GENDER_REST")
	public int genderRest;
	
	/* 雇用条件 */
	@Column(name = "REST_EMP")
	public int restEmp;
	
	/* 年齢基準年区分 */
	@Column(name = "AGE_CRITERIA_CLS")
	public Integer ageCriteriaCls;
	
	/* 年齢基準日 */
	@Column(name = "AGE_BASE_DATE")
	public Integer ageBaseDate;
	
	/* 年齢下限 */
	@Column(name = "AGE_LOWER_LIMIT")
	public Integer ageLowerLimit;
	
	/* 年齢上限 */
	@Column(name = "AGE_HIGHER_LIMIT")
	public Integer ageHigherLimit;
	
	/* 性別 */
	@Column(name = "GENDER")
	public Integer gender;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
