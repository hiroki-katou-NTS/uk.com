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
@Table(name = "KSHST_GRANT_DAY_SINGLE")
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
		public int fixNumberDays;
		
		/* 忌引とする */
		@Column(name = "MAKE_INVITATION")
		public int makeInvitation;
		
		/* 休日除外区分 */
		@Column(name = "HD_EXCLUSION_ATR")
		public int holidayExcusionAtr;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}