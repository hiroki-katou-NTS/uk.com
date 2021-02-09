package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
* The primary key class for the KFNMT_RPT_WK_MON_OUTATD database table.
* 
*/
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class KfnmtRptWkMonOutAtdPK implements Serializable {
	/** The Constant serialVersionUID. */
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	// column 出力項目ID 
	@Column(name = "LAYOUT_ID")
	private String layoutID;
	
	// column 並び順
	@Column(name = "ORDER_NO")
	private int orderNo;
}
