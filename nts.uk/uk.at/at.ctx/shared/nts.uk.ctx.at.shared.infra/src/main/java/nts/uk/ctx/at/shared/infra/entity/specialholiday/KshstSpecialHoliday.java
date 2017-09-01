package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_SPECIAL_HOLIDAY")
public class KshstSpecialHoliday extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/* 主キー */
	@EmbeddedId
	public KshstSpecialHolidayPK kshstSpecialHolidayPK;

	/* 特別休暇名称 */
	@Column(name = "SPHD_NAME")
	public String specialHolidayName;

	/* 定期付与 */
	@Column(name = "GRANT_METHOD")
	public int grantMethod;

	/* メモ */
	@Column(name = "MEMO")
	public String memo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public List<KshstSphdWorkType> sphdWorkTypes;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstGrantRegular grantRegular;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstGrantPeriodic grantPeriodic;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstSphdLimit sphdLimit;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstSphdSubCondition subCondition;
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy="specialHoliday", orphanRemoval = true)
	public KshstGrantSingle grantSingle;
	
	@Override
	protected Object getKey() {
		return kshstSpecialHolidayPK;
	}
}
