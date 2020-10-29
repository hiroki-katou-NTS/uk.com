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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *フローメニューレイアウトのメニュー設定																																					
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPTMT_FLOW_LAYOUT_MENU")
public class SptmtFlowLayoutMenu extends UkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;
	
	@EmbeddedId
	private SptmtFlowLayoutMenuPk pk;
	
	/**
	 * 契約コード									
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	/**
	 * メニュー名称																	
	 */
	@Basic(optional = false)
	@Column(name = "DISPLAY_NAME")
	private String menuName;
	
	/**
	 * メニューコード																		
	 */
	@Basic(optional = false)
	@Column(name = "MENU_CD")
	private String menuCode;
	
	/**
	 * メニュー分類
	 */
	@Basic(optional = false)
	@Column(name = "MENU_CLS")
	private int menuClassification;
	
	/**
	 * システム区分
	 */
	@Basic(optional = false)
	@Column(name = "SYSTEM_ATR")
	private int systemType;
	
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
	
	/**
	 * 文字のサイズ									
	 */
	@Basic(optional = false)
	@Column(name = "FONT_SIZE")
	private int fontSize;
	
	/**
	 * 太字
	 */
	@Basic(optional = false)
	@Column(name = "BOLD")
	private int bold;
	
	/**
	 * 横の位置
	 */
	@Basic(optional = false)
	@Column(name = "HORIZONTAL_POSITION")
	private int horizontalPosition;
	
	/**
	 * 縦の位置
	 */
	@Basic(optional = false)
	@Column(name = "VERTICAL_PISITION")
	private int verticalPosition;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
			@JoinColumn(name = "CID", referencedColumnName = "CID", insertable = false, updatable = false),
			@JoinColumn(name = "FLOW_MENU_CD", referencedColumnName = "FLOW_MENU_CD", insertable = false, updatable = false) })
	private SptmtCreateFlowMenu flowMenu;
	
	@Override
	protected Object getKey() {
		return pk;
	}
}
