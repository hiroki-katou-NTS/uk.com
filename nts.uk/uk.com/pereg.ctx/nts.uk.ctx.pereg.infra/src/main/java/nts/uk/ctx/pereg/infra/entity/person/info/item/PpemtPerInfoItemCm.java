package nts.uk.ctx.pereg.infra.entity.person.info.item;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_PER_INFO_ITEM_CM")
public class PpemtPerInfoItemCm extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public PpemtPerInfoItemCmPK ppemtPerInfoItemCmPK;

	@Basic(optional = true)
	@Column(name = "ITEM_PARENT_CD")
	public String itemParentCd;

	@Basic(optional = false)
	@NotNull
	@Column(name = "SYSTEM_REQUIRED_ATR")
	public int systemRequiredAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "REQUIRE_CHANGABLED_ATR")
	public int requireChangabledAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "FIXED_ATR")
	public int fixedAtr;

	@Basic(optional = false)
	@NotNull
	@Column(name = "ITEM_TYPE")
	public int itemType;

	@Column(name = "DATA_TYPE")
	public BigDecimal dataType;

	@Column(name = "TIME_ITEM_MIN")
	public BigDecimal timeItemMin;

	@Column(name = "TIME_ITEM_MAX")
	public BigDecimal timeItemMax;

	@Column(name = "TIMEPOINT_ITEM_MIN")
	public BigDecimal timepointItemMin;

	@Column(name = "TIMEPOINT_ITEM_MAX")
	public BigDecimal timepointItemMax;

	@Column(name = "DATE_ITEM_TYPE")
	public BigDecimal dateItemType;

	@Column(name = "STRING_ITEM_TYPE")
	public BigDecimal stringItemType;

	@Column(name = "STRING_ITEM_LENGTH")
	public BigDecimal stringItemLength;

	@Column(name = "STRING_ITEM_DATA_TYPE")
	public BigDecimal stringItemDataType;

	@Column(name = "NUMERIC_ITEM_MIN")
	public BigDecimal numericItemMin;

	@Column(name = "NUMERIC_ITEM_MAX")
	public BigDecimal numericItemMax;

	@Column(name = "NUMERIC_ITEM_AMOUNT_ATR")
	public BigDecimal numericItemAmountAtr;

	@Column(name = "NUMERIC_ITEM_MINUS_ATR")
	public BigDecimal numericItemMinusAtr;

	@Column(name = "NUMERIC_ITEM_DECIMAL_PART")
	public BigDecimal numericItemDecimalPart;

	@Column(name = "NUMERIC_ITEM_INTEGER_PART")
	public BigDecimal numericItemIntegerPart;

	@Column(name = "SELECTION_ITEM_REF_TYPE")
	public BigDecimal selectionItemRefType;

	@Column(name = "SELECTION_ITEM_REF_CODE")
	public String selectionItemRefCode;
	
	@Column(name = "RELATED_CATEGORY_CD")
	public String relatedCategoryCode;

	@Override
	protected Object getKey() {
		return ppemtPerInfoItemCmPK;
	}

}
