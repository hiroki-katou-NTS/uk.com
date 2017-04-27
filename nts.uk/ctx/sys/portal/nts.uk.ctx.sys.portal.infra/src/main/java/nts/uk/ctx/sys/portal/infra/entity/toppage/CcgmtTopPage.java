package nts.uk.ctx.sys.portal.infra.entity.toppage;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_TOP_PAGE")
public class CcgmtTopPage extends UkJpaEntity {

	@EmbeddedId
	public CcgmtTopPagePK ccgmtTopPagePK;

	@Column(name = "TOP_PAGE_NAME")
	public int topPageName;

	@Column(name = "LANG_NO")
	public int width;

	@Column(name = "LAYOUT_ID")
	public int height;

	@Override
	protected Object getKey() {
		return ccgmtTopPagePK;
	}

}
