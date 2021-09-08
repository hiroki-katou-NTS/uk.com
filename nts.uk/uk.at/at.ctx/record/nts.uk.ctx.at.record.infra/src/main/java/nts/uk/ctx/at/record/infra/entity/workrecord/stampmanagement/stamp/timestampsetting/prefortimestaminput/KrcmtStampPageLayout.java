package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonLayoutType;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageComment;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageLayout;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampPageName;
import nts.uk.ctx.at.record.infra.entity.stamp.management.KrcmtStampPerson;
import nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier.KrcmtStampRicoh;
import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 打刻ページレイアウト
 * @author phongtq
 *
 */

@Entity
@NoArgsConstructor
@Table(name="KRCMT_STAMP_PAGE_LAYOUT")
public class KrcmtStampPageLayout extends ContractUkJpaEntity{
	
	@EmbeddedId
    public KrcmtStampPageLayoutPk pk;
	
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
	
	@OneToMany(targetEntity = KrcmtStampLayoutDetail.class, mappedBy = "krcmtStampPageLayout", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinTable(name = "KRCMT_STAMP_LAYOUT_DETAIL")
	public List<KrcmtStampLayoutDetail> lstButtonSet;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "CID", insertable = false, updatable = false),
    	@JoinColumn(name = "STAMP_MEANS", insertable = false, updatable = false)
    })
	public KrcmtStampCommunal krcmtStampCommunal;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "CID", insertable = false, updatable = false),
    	@JoinColumn(name = "STAMP_MEANS", insertable = false, updatable = false)
    })
	public KrcmtStampPerson krcmtStampPerson;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "CID", insertable = false, updatable = false),
    	@JoinColumn(name = "STAMP_MEANS", insertable = false, updatable = false)
    })
	public KrcmtStampSmartPhone krcmtStampSmartPhone;
	
	@ManyToOne
    @JoinColumns({
    	@JoinColumn(name = "CID", insertable = false, updatable = false),
    	@JoinColumn(name = "STAMP_MEANS", insertable = false, updatable = false)
    })
	public KrcmtStampRicoh krcmtStampRicoh;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}
	
	@PreUpdate
    private void setUpdateContractInfo() {
		this.contractCd = AppContexts.user().contractCode();
	}
	
	public StampPageLayout toDomain(){
		return new StampPageLayout(
				new PageNo(pk.pageNo), 
				new StampPageName(this.pageName), 
				new StampPageComment(new PageComment(this.pageComment), new ColorCode(this.commentColor)), 
				EnumAdaptor.valueOf(this.buttonLayoutType, ButtonLayoutType.class), 
				lstButtonSet.stream().map(mapper->mapper.toDomain()).collect(Collectors.toList()));
	}
	
	public KrcmtStampPageLayout(KrcmtStampPageLayoutPk pk, String pageName, int buttonLayoutType, String pageComment,
			String commentColor, List<KrcmtStampLayoutDetail> lstButtonSet) {
		super();
		this.pk = pk;
		this.pageName = pageName;
		this.buttonLayoutType = buttonLayoutType;
		this.pageComment = pageComment;
		this.commentColor = commentColor;
		this.lstButtonSet = lstButtonSet;
	}
	
	public static KrcmtStampPageLayout toEntity(StampPageLayout pageLayout, String companyId, int stampMeans){
		return new KrcmtStampPageLayout(
				new KrcmtStampPageLayoutPk(companyId, stampMeans, pageLayout.getPageNo().v()), 
				pageLayout.getStampPageName().v(), 
				pageLayout.getButtonLayoutType().value, 
				pageLayout.getStampPageComment().getPageComment().v(), 
				pageLayout.getStampPageComment().getCommentColor().v(), 
				pageLayout.getLstButtonSet().stream().map(mapper->KrcmtStampLayoutDetail.toEntity(mapper, companyId, pageLayout.getPageNo().v(),stampMeans)).collect(Collectors.toList()));
	}

}
