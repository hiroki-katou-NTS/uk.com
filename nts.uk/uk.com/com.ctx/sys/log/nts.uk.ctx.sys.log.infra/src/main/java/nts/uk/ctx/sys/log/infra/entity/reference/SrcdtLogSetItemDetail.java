package nts.uk.ctx.sys.log.infra.entity.reference;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.log.dom.reference.LogSetItemDetail;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * author: hiep.th
 */
@Entity
@Table(name = "SRCDT_LOG_SET_ITEM_DETAIL")
@NoArgsConstructor
@AllArgsConstructor
public class SrcdtLogSetItemDetail extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public SrcdtLogSetItemDetailPK srcdtLogSetItemDetailPK;
	
	/** 使用区分 */
	@Basic(optional=false)
	@Column(name = "IS_USE_COND_FLG")
    public int isUseCondFlg;
	
	/** 条件  */
	@Basic(optional=true)
	@Column(name = "CONDITION")
    public String condition;

	/** 記号 */
	@Basic(optional = true)
	@Column(name = "SYMBOL")
	private Integer symbol;

	@ManyToOne
	@JoinColumns(value={
			@JoinColumn(name="LOG_SET_ID", referencedColumnName="LOG_SET_ID", insertable = false, updatable = false),
			@JoinColumn(name="ITEM_NO", referencedColumnName="ITEM_NO", insertable = false, updatable = false)
		})
	public SrcdtLogSetOutputItem logSetOutputItemCond;

	@Override
	protected Object getKey() {
		return srcdtLogSetItemDetailPK;
	}

	public LogSetItemDetail toDomain() {
		boolean isUseCondFlg = this.isUseCondFlg == 1;
		return LogSetItemDetail.createFromJavatype(this.srcdtLogSetItemDetailPK.logSetId,
				this.srcdtLogSetItemDetailPK.itemNo, this.srcdtLogSetItemDetailPK.frame,
				isUseCondFlg, this.condition, this.symbol);
	}

	public static SrcdtLogSetItemDetail toEntity(LogSetItemDetail domain) {
		int isUseCondFlg = domain.isUseCondFlg() ? 1 : 0;
		String condition = domain.getCondition().isPresent() ? domain.getCondition().get().v() : null;
		Integer sybol = domain.getSymbol().isPresent() ? domain.getSymbol().get().code : null;
		return new SrcdtLogSetItemDetail(new SrcdtLogSetItemDetailPK(domain.getLogSetId(), domain.getItemNo(), domain.getFrame()),
				isUseCondFlg, condition, sybol);
	}

	public SrcdtLogSetItemDetail(SrcdtLogSetItemDetailPK srcdtLogSetItemDetailPK, int isUseCondFlg, String condition,
			int symbol) {
		super();
		this.srcdtLogSetItemDetailPK = srcdtLogSetItemDetailPK;
		this.isUseCondFlg = isUseCondFlg;
		this.condition = condition;
		this.symbol = symbol;
	}
}
