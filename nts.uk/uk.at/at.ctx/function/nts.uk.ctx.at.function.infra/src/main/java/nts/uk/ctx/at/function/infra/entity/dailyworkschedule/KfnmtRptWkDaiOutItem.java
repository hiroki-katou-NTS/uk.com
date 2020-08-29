package nts.uk.ctx.at.function.infra.entity.dailyworkschedule;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * The class KfnmtRptWkDaiOutItem
 * @author LienPTK
 *
 */
@Getter
@Setter
@Entity
@Table(name="KFNMT_RPT_WK_DAI_OUT_ITEM")
@NoArgsConstructor
public class KfnmtRptWkDaiOutItem extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/** The layoutId. */
	@Id
	@Column(name = "LAYOUT_ID")
	private String layoutId;

	/** The itemSelType. */
	@Column(name = "ITEM_SEL_TYPE")
	private int itemSelType;

	/** The cid. */
	@Column(name="CID")
	private String cid;

	/** The sid */
	@Column(name="SID")
	private String sid;

	/** The item code. */
	@Column(name="ITEM_CD")
	private String itemCode;

	/** The item name. */
	@Column(name="ITEM_NAME")
	private String itemName;

	/** The work type name display. */
	@Column(name="WORKTYPE_NAME_DISPLAY")
	private BigDecimal workTypeNameDisplay;

	/** The noteInputNo */
	@Column(name="NOTE_INPUT_NO")
	private BigDecimal noteInputNo;

	/** font size */
	@Column(name="CHAR_SIZE_TYPE")
	private BigDecimal charSizeType;

	@Override
	protected Object getKey() {
		return null;
	}

}
