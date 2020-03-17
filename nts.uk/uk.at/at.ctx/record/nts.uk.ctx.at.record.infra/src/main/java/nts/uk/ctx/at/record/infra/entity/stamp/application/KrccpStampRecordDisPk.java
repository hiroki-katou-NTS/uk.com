package nts.uk.ctx.at.record.infra.entity.stamp.application;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@EqualsAndHashCode
public class KrccpStampRecordDisPk  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** 表示項目一覧 */
	@Column(name = "D_ATD_ITEM_ID")
	public int dAtdItemId;

}
								
