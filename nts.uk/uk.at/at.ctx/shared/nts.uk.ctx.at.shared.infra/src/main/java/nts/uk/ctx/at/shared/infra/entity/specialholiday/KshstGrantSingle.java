package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.grantrelationship.KshstGrantRelationshipItem;
import nts.uk.ctx.at.shared.infra.entity.specialholiday.yearserviceper.KshstYearServicePer;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANT_SINGLE")
public class KshstGrantSingle extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstGrantSinglePK kshstGrantSinglePK;
		
		/* 種類 */
		@Column(name = "GRANT_DAY_SINGLE_TYPE")
		public int grantDaySingleType;
		
		/* 固定付与日数 */
		@Column(name = "FIX_NUMBER_DAYS")
		public Integer fixNumberDays;
		
		/* 忌引とする */
		@Column(name = "MAKE_INVITATION")
		public int makeInvitation;
		
		/* 休日除外区分 */
		@Column(name = "HD_EXCLUSION_ATR")
		public int holidayExcusionAtr;
		
		@OneToOne(optional = false)
		@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false),
			@JoinColumn(name = "SPHD_CD", referencedColumnName="SPHD_CD", insertable = false, updatable = false)
		})
		
		public KshstSpecialHoliday specialHoliday;
		
		@OneToOne(cascade = CascadeType.ALL, mappedBy="grantSingle", orphanRemoval = true)
		public KshstGrantRelationshipItem relationshipItem;

	@Override
	protected Object getKey() {
		return kshstGrantSinglePK;
	}
}