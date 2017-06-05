package nts.uk.ctx.sys.portal.infra.entity.webmenu;

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
@Table(name = "CCGST_MENU_BAR")
public class CcgstMenuBar extends UkJpaEntity implements Serializable {
	 
	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public CcgstMenuBarPK ccgstMenuBarPK;
	
	@Column(name = "ACT_CLASSIFCATION")
	public int actClassifcation;
	
	@Column(name = "BACKGROUND_COLOR")
	public String  backgroundColor;
	
	@Column(name = "LETTER_COLOR")
	public String letterColor;
	
	@Column(name = "MENU_BAR_NAME")
	public String  menuBarName;

	@Override
	protected Object getKey() {
		
		return ccgstMenuBarPK;
	}

}
