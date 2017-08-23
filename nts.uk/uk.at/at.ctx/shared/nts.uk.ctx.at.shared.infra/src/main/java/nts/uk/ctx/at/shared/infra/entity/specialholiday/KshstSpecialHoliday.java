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
		@Column(name = "GRANT_PERIODIC_CLS")
		public int grantPeriodicCls;
		
		/* メモ */
		@Column(name = "MEMO")
		public String memo;
	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
