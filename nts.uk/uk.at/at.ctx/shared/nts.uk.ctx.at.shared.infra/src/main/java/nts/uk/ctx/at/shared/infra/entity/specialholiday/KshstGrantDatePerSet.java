package nts.uk.ctx.at.shared.infra.entity.specialholiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateMonth;
import nts.uk.ctx.at.shared.dom.specialholiday.grantdate.GrantDateYear;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_GRANTDATE_PER_SET")
public class KshstGrantDatePerSet extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/* 主キー */
	@EmbeddedId
	public KshstGrantDatePerSetPK kshstGrantDatePerSetPK;
	
	/* 月数 */
	@Column(name = "GRANT_DATE_M")
	public GrantDateMonth grantDateMonth;

	/* 年数 */
	@Column(name = "GRANT_DATE_Y")
	public GrantDateYear grantDateYear;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return kshstGrantDatePerSetPK;
	}
}
