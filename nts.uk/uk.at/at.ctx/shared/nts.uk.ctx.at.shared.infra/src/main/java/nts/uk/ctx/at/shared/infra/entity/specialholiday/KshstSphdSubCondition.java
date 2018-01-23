package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

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
import nts.uk.ctx.at.shared.dom.specialholiday.SubCondition;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

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
		@Column(name = "LIMIT_AGE_FROM", nullable=true)
		public Integer limitAgeFrom;
		
		/* 年齢下限 */
		@Column(name = "LIMIT_AGE_TO", nullable=true)
		public Integer limitAgeTo;
		
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

	public static KshstSphdSubCondition toEntity(SubCondition domain){
		
		List<KshstSphdEmployment> employments = domain.getEmploymentList().stream().map(x -> {
		KshstSphdEmploymentPK key = new KshstSphdEmploymentPK(domain.getCompanyId(),
				domain.getSpecialHolidayCode().v(), x);
		return new KshstSphdEmployment(key);
	}).collect(Collectors.toList());
	
	List<KshstSphdClassfication> classfications = domain.getClassificationList().stream().map(x -> {
		KshstSphdClassficationPK key = new KshstSphdClassficationPK(domain.getCompanyId(),
				domain.getSpecialHolidayCode().v(), x);
		return new KshstSphdClassfication(key);
	}).collect(Collectors.toList());
	
		return new KshstSphdSubCondition(new KshstSphdSubConditionPK(domain.getCompanyId(), domain.getSpecialHolidayCode().v()),
				domain.getUseGender() == null ? null : domain.getUseGender().value, domain.getUseEmployee() == null ? null : domain.getUseEmployee().value, domain.getUseCls() == null ? null : domain.getUseCls().value, domain.getUseAge() == null ? null : domain.getUseAge().value,
				domain.getGenderAtr() == null ? null : domain.getGenderAtr().value, 
				domain.getLimitAgeFrom() == null ? null : domain.getLimitAgeFrom().v(),
				domain.getLimitAgeTo() == null ? null : domain.getLimitAgeTo().v(),
				domain.getAgeCriteriaAtr() == null ? null : domain.getAgeCriteriaAtr().value,
				domain.getAgeBaseYearAtr() == null ? null : domain.getAgeBaseYearAtr().value,
				domain.getAgeBaseDates() == null ? 0 : domain.getAgeBaseDates().v(), 
				employments,
				classfications);
		
	}


	public KshstSphdSubCondition(KshstSphdSubConditionPK kshstSphdSubConditionPK, int useGender, int useEmployee,
			int useCls, int useAge, int genderAtr, Integer limitAgeFrom, Integer limitAgeTo, int ageCriteriaAtr,
			int ageBaseYearAtr, int ageBaseDates, List<KshstSphdEmployment> sphdEmployments, List<KshstSphdClassfication> sphdClassfications
			) {
		super();
		this.kshstSphdSubConditionPK = kshstSphdSubConditionPK;
		this.useGender = useGender;
		this.useEmployee = useEmployee;
		this.useCls = useCls;
		this.useAge = useAge;
		this.genderAtr = genderAtr;
		this.limitAgeFrom = limitAgeFrom;
		this.limitAgeTo = limitAgeTo;
		this.ageCriteriaAtr = ageCriteriaAtr;
		this.ageBaseYearAtr = ageBaseYearAtr;
		this.ageBaseDates = ageBaseDates;
		this.sphdEmployments = sphdEmployments;
		this.sphdClassfications = sphdClassfications;
		
	}



}
