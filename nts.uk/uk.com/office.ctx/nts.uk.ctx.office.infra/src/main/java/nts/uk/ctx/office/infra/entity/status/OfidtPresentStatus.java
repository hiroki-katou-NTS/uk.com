package nts.uk.ctx.office.infra.entity.status;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.status.ActivityStatus;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.ステータス.OFIDT_PRESENT_STATUS
 * 在席のステータス
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "OFIDT_PRESENT_STATUS")
public class OfidtPresentStatus extends ContractUkJpaEntity
		implements ActivityStatus.MementoGetter, ActivityStatus.MementoSetter, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column　会社ID
	@Column(name = "CID")
	private String cid;

	// Embedded primary key 社員ID
	@EmbeddedId
	private OfidtPresentStatusPK pk;

	// column 年月日
	@NotNull
	@Column(name = "YMD")
	private GeneralDate date;

	// column 社員ID
	@NotNull
	@Column(name = "STATUS_CLS")
	private Integer activity;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setSid(String sid) {
		if (this.pk == null) {
			this.pk = new OfidtPresentStatusPK();
		}
		this.pk.setSid(sid);
	}

	@Override
	public String getSid() {
		return this.pk.getSid();
	}
}
