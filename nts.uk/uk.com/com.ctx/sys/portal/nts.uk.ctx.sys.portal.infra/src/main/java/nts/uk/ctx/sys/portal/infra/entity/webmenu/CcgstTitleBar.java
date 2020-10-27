package nts.uk.ctx.sys.portal.infra.entity.webmenu;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnh
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGST_TITLE_BAR")
public class CcgstTitleBar extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public CcgstTitleMenuPK ccgstTitleMenuPK;
	
	@Column(name = "TITLE_BAR_NAME")
	public String titleMenuName;
	
	@Column(name = "BACKGROUND_COLOR")
	public String  backgroundColor;
	
	@Column(name = "IMAGE_FILE")
	public String imageFile;
	
	@Column(name = "TEXT_COLOR")
	public String textColor;	
	
	@Column(name = "TITLE_MENU_ATR")
	public int titleMenuAtr;
	
	@Column(name = "TITLE_MENU_CD")
	public String titleMenuCD;
	
	@Column(name = "DISPLAY_ORDER")
	public int displayOrder;
	
	@ManyToOne
	@JoinColumns( {
        @JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
        @JoinColumn(name = "WEB_MENU_CD", referencedColumnName = "WEB_MENU_CD", insertable = false, updatable = false),
        @JoinColumn(name = "MENU_BAR_ID", referencedColumnName = "MENU_BAR_ID", insertable = false, updatable = false)
    })
	public CcgstMenuBar menuBar;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="titleMenu", orphanRemoval = true)
	public List<CcgstTreeMenu> treeMenus;
	
	@Override
	protected Object getKey() {
		
		return ccgstTitleMenuPK;
	}

	public CcgstTitleBar(CcgstTitleMenuPK ccgstTitleMenuPK, String titleMenuName, String backgroundColor,
			String imageFile, String textColor, int titleMenuAtr, String titleMenuCD, int displayOrder,
			List<CcgstTreeMenu> treeMenus) {
		super();
		this.ccgstTitleMenuPK = ccgstTitleMenuPK;
		this.titleMenuName = titleMenuName;
		this.backgroundColor = backgroundColor;
		this.imageFile = imageFile;
		this.textColor = textColor;
		this.titleMenuAtr = titleMenuAtr;
		this.titleMenuCD = titleMenuCD;
		this.displayOrder = displayOrder;
		this.treeMenus = treeMenus;
	}

}
