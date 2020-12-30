package nts.uk.ctx.sys.portal.infra.entity.toppagepart.createflowmenu;

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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * フローメニューレイアウトのラベル設定
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SPTMT_FLOW_LAYOUT_LABEL")
public class SptmtFlowLayoutLabel extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private SptmtFlowLayoutLabelPk pk;
	
	/**
	 * 契約コード									
	 */
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCode;
	
	/**
	 * ラベル内容									
	 */
	@Basic(optional = true)
	@Column(name = "LABEL_CONTENT")
	private String labelContent;
	
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
	 * 文字の色									
	 */
	@Basic(optional = false)
	@Column(name = "TEXT_COLOR")
	private String textColor;
	
	/**
	 * 背景の色									
	 */
	@Basic(optional = false)
	@Column(name = "BG_COLOR")
	private String backgroundColor;
	
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
