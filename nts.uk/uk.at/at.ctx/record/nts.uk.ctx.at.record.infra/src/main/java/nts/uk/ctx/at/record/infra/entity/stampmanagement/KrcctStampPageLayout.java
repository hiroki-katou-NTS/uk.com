package nts.uk.ctx.at.record.infra.entity.stampmanagement;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 打刻ページレイアウト
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_PAGE_LAYOUT")
public class KrcctStampPageLayout extends UkJpaEntity implements Serializable{
private static final long serialVersionUID = 1L;
	
	@EmbeddedId
    public KrcctStampPageLayoutPk pk;
	
	/** ページ名 */
	@Column(name ="PAGE_NAME")
	public String pageName;
	
	/** ボタン配置タイプ(0: 大2小4, 1: 小8) */
	@Column(name ="BUTTON_LAYOUT_TYPE")
	public int buttonLayoutType;
	
	/** 打刻ページコメント */
	@Column(name ="PAGE_COMMENT")
	public String pageComment;
	
	/** コメント色 */
	@Column(name ="COMMENT_COLOR")
	public String commentColor;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
}
