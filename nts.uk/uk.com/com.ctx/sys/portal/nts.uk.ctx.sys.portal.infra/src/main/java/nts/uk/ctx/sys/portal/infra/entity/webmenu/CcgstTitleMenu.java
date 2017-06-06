package nts.uk.ctx.sys.portal.infra.entity.webmenu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 
 * @author sonnh
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CCGST_TITLE_MENU")
public class CcgstTitleMenu extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public CcgstTitleMenuPK ccgstTitleMenuPK;
	
	@Column(name = "BACKGROUND_COLOR")
	public String  backgroundColor;
	
	@Column(name = "LETTER_COLOR")
	public String letterColor;
	
	@Column(name = "IMAGE_FILE")
	public String imageFile;
	
	@Column(name = "TITLE_MENU_CD")
	public String titleMenuCD;
	
	@Column(name = "TITLE_MENU_INDICATOR")
	public int titleMenuIndicator;

	@Override
	protected Object getKey() {
		
		return ccgstTitleMenuPK;
	}

}
