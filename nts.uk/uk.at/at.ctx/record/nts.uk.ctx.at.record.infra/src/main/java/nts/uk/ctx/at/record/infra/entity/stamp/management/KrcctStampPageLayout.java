package nts.uk.ctx.at.record.infra.entity.stamp.management;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 打刻ページレイアウト
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCCT_STAMP_PAGE_LAYOUT")
public class KrcctStampPageLayout extends ContractUkJpaEntity{
	
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
	
	public KrcctStampPageLayout(KrcctStampPageLayoutPk pk, String pageName, int buttonLayoutType, String pageComment,
			String commentColor, List<KrcctStampLayoutDetail> lstButtonSet) {
		super();
		this.pk = pk;
		this.pageName = pageName;
		this.buttonLayoutType = buttonLayoutType;
		this.pageComment = pageComment;
		this.commentColor = commentColor;
		this.lstButtonSet = lstButtonSet;
	}
	
	@OneToMany(targetEntity = KrcctStampLayoutDetail.class, mappedBy = "krcctStampPageLayout", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "KRCCT_STAMP_LAYOUT_DETAIL")
	public List<KrcctStampLayoutDetail> lstButtonSet;
	
	public StampPageLayout toDomain(){
		return new StampPageLayout(
				new PageNo(pk.pageNo), 
				new StampPageName(this.pageName), 
				new StampPageComment(new PageComment(this.pageComment), new ColorCode(this.commentColor)), 
				EnumAdaptor.valueOf(this.buttonLayoutType, ButtonLayoutType.class), 
				lstButtonSet.stream().map(mapper->mapper.toDomain()).collect(Collectors.toList()));
	}
	
	public static KrcctStampPageLayout toEntity(StampPageLayout pageLayout, String companyId){
		return new KrcctStampPageLayout(
				new KrcctStampPageLayoutPk(companyId, 1, pageLayout.getPageNo().v()), 
				pageLayout.getStampPageName().v(), 
				pageLayout.getButtonLayoutType().value, 
				pageLayout.getStampPageComment().getPageComment().v(), 
				pageLayout.getStampPageComment().getCommentColor().v(), 
				pageLayout.getLstButtonSet().stream().map(mapper->KrcctStampLayoutDetail.toEntity(mapper, companyId, pageLayout.getPageNo().v())).collect(Collectors.toList()));
	}

}
