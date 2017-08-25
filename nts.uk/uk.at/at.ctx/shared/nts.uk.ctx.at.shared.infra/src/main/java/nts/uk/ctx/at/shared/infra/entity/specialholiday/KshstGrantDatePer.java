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
@Table(name = "KSHST_GRANT_DATE_PER")
public class KshstGrantDatePer extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
		/* 主キー */
		@EmbeddedId
		public KshstGrantDateComPK kshstGrantDateComPK;
		
		/* 名称 */
		@Column(name = "GRANT_DATE_NAME")
		public int grantDateName;
		
		/* 一律基準日 */
		@Column(name = "GRANT_DATE")
		public int grantDate;
		
		/* 付与基準日 */
		@Column(name = "GRANT_DATE_ATR")
		public int grantDateAtr;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}