package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPECIAL_HOLIDAY_EVENT")
// 事象に対する特別休暇
public class KshstSpecialHolidayEvent extends UkJpaEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstSpecialHolidayEventPK pk;

	/* 固定日数を上限とする */
	@Column(name = "LIMIT_FIXED_DAYS_TYPE")
	public int limitFixedDays;

	/* 続柄ごとに上限を設定する */
	@Column(name = "REFER_RELATIONSHIP_TYPE")
	public int refRelationShip;
	
	/* 固定上限日数 */
	@Column(name = "FIXED_DAY_GRANT")
	public Integer fixedDayGrant;
	
	/* 忌引とする */
	@Column(name = "MAKE_INVITATION_ATR")
	public String makeInvitation;
	
	/* 休日を取得日に含める */
	@Column(name = "INCLUDE_HOLIDAYS_ATR")
	public String includeHolidays;
	
	/* 年齢条件 */
	@Column(name = "AGE_LIMIT_ATR")
	public int ageLimit;
	
	/* 性別条件 */
	@Column(name = "GENDER_RESTRICT_ATR")
	public int genderRestrict;
	
	/* 雇用条件 */
	@Column(name = "RESTRICT_EMP_ATR")
	public String restrictEmployment;
	
	/* 分類条件 */
	@Column(name = "RESTRICT_CLS_ATR")
	public String restrictClassification;
	
	/* 性別 */
	@Column(name = "GENDER_ATR")
	public Integer gender;

	/* 年齢範囲 */
	@Column(name = "AGE_RANGE_ATR")
	public String ageRange;
	
	/* 年齢基準 */
	@Column(name = "AGE_STANDARD_ATR")
	public String ageStandard;
	
	/* メモ */
	@Column(name = "MEMO")
	public String memo;

	@Override
	protected Object getKey() {
		return this.pk;
	}

}
