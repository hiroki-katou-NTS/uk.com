package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPHD_SUB_CONDITION")
public class KshstSphdSubCondition extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstSphdSubConditionPK kshstSphdSubConditionPK;
		
		/* 性別制限 */
		@Column(name = "USE_GENDER")
		public int useGender;
		
		/* 雇用制限 */
		@Column(name = "USE_EMP")
		public int useEmployee;
		
		/* 分類制限 */
		@Column(name = "USE_CLS")
		public int useCls;
		
		/* 年齢制限 */
		@Column(name = "USE_AGE")
		public int useAge;
		
		/* 性別区分 */
		@Column(name = "GENDER_ATR")
		public int genderAtr;
		
		/* 年齢上限 */
		@Column(name = "LIMIT_AGE_FROM")
		public int limitAgeFrom;
		
		/* 年齢下限 */
		@Column(name = "LIMIT_AGE_TO")
		public int limitAgeTo;
		
		/* 年齢基準区分 */
		@Column(name = "AGE_CRITERIA_ATR")
		public int ageCriteriaAtr;
		
		/* 年齢基準年区分 */
		@Column(name = "AGE_BASE_YEAR_ATR")
		public int ageBaseYearAtr;
		
		/* 年齢基準日 */
		@Column(name = "AGE_BASE_DATES")
		public int ageBaseDates;

		@OneToOne(optional = false)
		@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
			@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
		})
		public KshstSpecialHoliday specialHoliday;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy="subCondition", orphanRemoval = true)
		public List<KshstSphdClassfication> sphdClassfications;
		
		@OneToMany(cascade = CascadeType.ALL, mappedBy="subCondition", orphanRemoval = true)
		public List<KshstSphdEmployment> sphdEmployments;

	@Override
	protected Object getKey() {
		return kshstSphdSubConditionPK;
	}
}
