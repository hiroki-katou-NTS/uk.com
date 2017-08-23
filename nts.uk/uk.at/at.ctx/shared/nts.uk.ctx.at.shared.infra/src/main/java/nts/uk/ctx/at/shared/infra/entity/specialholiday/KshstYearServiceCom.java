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
@Table(name = "KSHST_YEAR_SERVICE_COM")
public class KshstYearServiceCom extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
		/* 主キー */
		@EmbeddedId
		public KshstYearServiceComPK kshstYearServiceComPK;
		
		/* 付与日数 */
		@Column(name = "SERVICE_GRANT_DAY")
		public int grantDateAtr;
		
		/* 勤続年数 */
		@Column(name = "LENGTH_SERVICE_YEAR_ATR")
		public int grantDate;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
