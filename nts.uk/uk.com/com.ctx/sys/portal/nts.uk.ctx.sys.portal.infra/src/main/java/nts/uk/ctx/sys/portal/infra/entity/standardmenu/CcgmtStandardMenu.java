package nts.uk.ctx.sys.portal.infra.entity.standardmenu;

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
public class CcgmtStandardMenu extends UkJpaEntity {
	@EmbeddedId
	private CcgmtStandardMenuPK ccgmtStandardMenuPK;
	
	/** The Url. */
	public String url;
	
	/** The Web Menu Setting Display Indicator. */
	public int webMenuSettingDisplayIndicator;
	
	/** The menu code. */
	public String code;
	
	/** The system. */
	public String system;
	
	/** The classification. */
	public String classification;
	
	/** The After Login Display Indicator. */
	public int afterLoginDisplayIndicator;
	
	/** The Log Setting Display Indicator. */
	public int logSettingDisplayIndicator;

	/** The Target Items. */
	public String targetItems;
	
	/** The Display Name. */
	public String displayName;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return null;
	}
}
