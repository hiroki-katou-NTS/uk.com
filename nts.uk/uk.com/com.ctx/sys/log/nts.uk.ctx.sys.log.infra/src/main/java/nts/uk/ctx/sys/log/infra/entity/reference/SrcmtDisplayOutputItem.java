package nts.uk.ctx.sys.log.infra.entity.reference;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.log.dom.reference.LogSetOutputItem;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * author: hiep.th
 */
@Entity
@Table(name = "SRCMT_DISPLAY_OUTPUT_ITEM")
@NoArgsConstructor
public class SrcmtDisplayOutputItem extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SrcmtDisplayOutputItemPK srcmtDisplayOutputItemPK;
	
	/** 表示順 */
	@Basic(optional=false)
	@Column(name = "DISPLAY_ORDER")
    public int displayOrder;
	
	/** 囲い文字  */
	@Basic(optional=false)
	@Column(name = "IS_USE_FLG")
    public int isUseFlg;

	@ManyToOne
	@JoinColumn(name="LOG_SET_ID", referencedColumnName="LOG_SET_ID", insertable = false, updatable = false)		
	public SrcmtDisplaySetting logDisplaySetCond;
	
	@OneToMany(mappedBy = "logSetOutputItemCond", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<SrcmtDisplaySetDetail> listLogSetItemDetails;
	
	@Override
	protected Object getKey() {
		return srcmtDisplayOutputItemPK;
	}

	public LogSetOutputItem toDomain() {
		boolean isUseFlg = this.isUseFlg == 1;
		return LogSetOutputItem.createFromJavatype(this.srcmtDisplayOutputItemPK.logSetId, this.srcmtDisplayOutputItemPK.itemNo,
				this.displayOrder, isUseFlg, this.listLogSetItemDetails.stream().map(item -> item.toDomain()).collect(Collectors.toList()));
	}

	public static SrcmtDisplayOutputItem toEntity(LogSetOutputItem domain) {
		int isUseFlag = domain.isUseFlag() ? 1 : 0;
		return new SrcmtDisplayOutputItem(new SrcmtDisplayOutputItemPK(domain.getLogSetId(), domain.getItemNo()),
				 domain.getDisplayOrder(), isUseFlag, 
				 domain.getLogSetItemDetails().stream().map(item -> SrcmtDisplaySetDetail.toEntity(item)).collect(Collectors.toList()));
	}

	public SrcmtDisplayOutputItem(SrcmtDisplayOutputItemPK srcmtDisplayOutputItemPK, int displayOrder, 
			int isUseFlg, List<SrcmtDisplaySetDetail> listLogSetItemDetails) {
		super();
		this.srcmtDisplayOutputItemPK = srcmtDisplayOutputItemPK;
		this.displayOrder = displayOrder;
		this.isUseFlg = isUseFlg;
		this.listLogSetItemDetails = listLogSetItemDetails;
	}
}
