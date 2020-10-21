package nts.uk.ctx.sys.portal.infra.entity.flowmenu;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.NonNull;

@Embeddable
public class SptmtFlowLayoutMenuPk implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 会社ID									
	 */
	@NonNull
	@Column(name = "CID")
	public String cid;
	
	/**
	 * フローメニューコード									
	 */
	@NonNull
	@Column(name = "FLOW_MENU_CD")
	public String flowMenuCode;
	
	/**
	 * column																
	 */
	@Column(name = "POS_COL")
	public int column;
	
	/**
	 * row									
	 */
	@Column(name = "POS_ROW")
	public int row;
}
