package nts.uk.ctx.sys.portal.infra.entity.mypage;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGMT_MY_PAGE_SET")
public class CcgptMyPage extends UkJpaEntity {
	
	@EmbeddedId
	public CcgptMyPagePK ccgptMyPagePK;	

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ccgptMyPagePK;
	}
}
