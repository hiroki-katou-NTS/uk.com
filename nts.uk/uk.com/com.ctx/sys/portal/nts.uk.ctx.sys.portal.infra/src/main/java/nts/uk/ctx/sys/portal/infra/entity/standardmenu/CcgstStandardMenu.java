package nts.uk.ctx.sys.portal.infra.entity.standardmenu;

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
@Table(name = "CCGMT_MY_PAGE_SET")
public class CcgstStandardMenu extends UkJpaEntity {
	@EmbeddedId
	public CcgstStandardMenuPK ccgmtStandardMenuPK;
	
	/** The Target Items. */
	@Column(name = "TARGET_ITEMS")
	public String targetItems;
	
	/** The Display Name. */
	@Column(name = "DISPLAY_NAME")
	public String displayName;
	
	/** The Display Order. */
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;
	
	/** The menuAtr. */
	@Column(name = "MENU_ATR")
	public int menuAtr;
	
	/** The url. */
	@Column(name = "URL")
	public String url;
	
	/** The system. */
	@Column(name = "SYSTEM")
	public int system;
	
	/** The classification. */
	@Column(name = "CLASSIFICATION")
	public int classification;
	
	/** The webMenuSetting. */
	@Column(name = "WEB_MENU_SETTING")
	public int webMenuSetting;
	
	/** The afterLoginDisplay. */
	@Column(name = "AFTER_LOGIN_DISPLAY")
	public int afterLoginDisplay;
	
	/** The logSettingDisplay. */
	@Column(name = "LOG_SETTING_DISPLAY")
	public int logSettingDisplay;

	@Override
	protected Object getKey() {
		// TODO Auto-generated method stub
		return ccgmtStandardMenuPK;
	}
}
