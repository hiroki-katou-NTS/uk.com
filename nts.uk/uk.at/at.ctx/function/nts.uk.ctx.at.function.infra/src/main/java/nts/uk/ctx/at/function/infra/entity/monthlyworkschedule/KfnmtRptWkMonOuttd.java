package nts.uk.ctx.at.function.infra.entity.monthlyworkschedule;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * Entity 表示する勤怠項目
 */
@Data
@Entity
@Table(name = "KFNMT_RPT_WK_MON_OUTATD")
@EqualsAndHashCode(callSuper = true)
public class KfnmtRptWkMonOuttd extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	// Embedded primary key 出力項目ID + 並び順
	@EmbeddedId
	private KfnmtRptWkMonOuttdPK pk;
	
	// column 排他バージョン
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// column 会社ID
	@Column(name = "CID")
	private String CompanyID;

	// column 表示する項目
	@Column(name = "ATD_DISPLAY")
	private int atdDisplay;

	@Override
	protected Object getKey() {
		return this.pk;
	}
}
