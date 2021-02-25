package nts.uk.ctx.at.shared.infra.entity.monthlyattditem.aggregrate;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The Class KrcctMonEdiAttenItemPK.
 *
 * @author LienPTK
 */
@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KrcctMonEdiAttenItemPK implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	private String cid;

	/** 月次の勤怠項目ID */
	@Column(name = "M_ATD_ITEM_ID")
	private BigDecimal mAtItemId;

}
