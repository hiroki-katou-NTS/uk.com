package nts.uk.ctx.sys.portal.infra.entity.flowmenu;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.ctx.sys.portal.dom.flowmenu.ArrowSetting;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *フローメニューレイアウトの添付ファイル設定																			
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "SPTMT_FLOW_LAYOUT_ARROW")
public class SptmtFlowLayoutArrow extends UkJpaEntity implements Serializable,
																 ArrowSetting.MementoGetter,
																 ArrowSetting.MementoSetter {

	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	@EmbeddedId
	private SptmtFlowLayoutArrowPk pk;
	
	/**
	 * 契約コード									
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	/**
	 * 矢印ファイル名																								
	 */
	@Basic(optional = false)
	@Column(name = "ARROW_FILE_NAME")
	private String fileName;
	
	/**
	 * width
	 */
	@Basic(optional = false)
	@Column(name = "WIDTH")
	private int width;
	
	/**
	 * height
	 */
	@Basic(optional = false)
	@Column(name = "HEIGHT")
	private int height;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "FLOW_MENU_CD", referencedColumnName = "FLOW_MENU_CD", insertable = false, updatable = false) })
	private SptmtCreateFlowMenu flowMenu;
	
	@Override
	protected Object getKey() {
		return pk;
	}
	
	@Override
	public void setCid(String cid) {
		if (pk == null)
			pk = new SptmtFlowLayoutArrowPk();
		pk.cid = cid;
	}

	@Override
	public void setFlowMenuCode(String flowMenuCode) {
		if (pk == null)
			pk = new SptmtFlowLayoutArrowPk();
		pk.flowMenuCode = flowMenuCode;
	}

	@Override
	public void setColumn(int column) {
		if (pk == null)
			pk = new SptmtFlowLayoutArrowPk();
		pk.column = column;
	}

	@Override
	public void setRow(int row) {
		if (pk == null)
			pk = new SptmtFlowLayoutArrowPk();
		pk.row = row;
	}

	@Override
	public String getFlowMenuCode() {
		if (pk != null)
			return pk.flowMenuCode;
		return null;
	}

	@Override
	public int getColumn() {
		if (pk != null)
			return pk.column;
		return 0;
	}

	@Override
	public int getRow() {
		if (pk != null)
			return pk.row;
		return 0;
	}
}

