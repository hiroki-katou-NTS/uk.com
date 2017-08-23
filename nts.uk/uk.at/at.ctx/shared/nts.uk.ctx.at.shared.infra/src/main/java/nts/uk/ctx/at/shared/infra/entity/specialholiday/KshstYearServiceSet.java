package nts.uk.ctx.at.shared.infra.entity.specialholiday;

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
@Table(name = "KSHST_YEAR_SERVICE_SET")
public class KshstYearServiceSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstYearServiceSetPK kshstYearServiceSetPK;
		
		/* 勤続年数基月数 */
		@Column(name = "YEAR_SERVICE_MONTHS")
		public String yearServiceMonths;
		
		/* 勤続年数基年数 */
		@Column(name = "YEAR_SERVICE_YEARS")
		public int yearServiceYears;
		
		/* 特別休暇付与日数 */
		@Column(name = "GRANT_DATES")
		public int grantDates;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}