package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_ADD_SET_MAN_WKHOUR")
public class KshstAddSetManWKHour extends ContractUkJpaEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/** 主キー */
	@EmbeddedId
	public KshstAddSetManWKHourPK kshstAddSetManWKHourPK;
	
	/** 時間外超過の加算設定 */
	@Column(name = "ADD_SET_OT")
	public int addSetOT;

	@OneToOne(optional = false)
	@JoinColumn(name = "CID", referencedColumnName="CID", insertable = false, updatable = false)
	public KshstHolidayAdditionSet holidayAddtimeSet;
	@Override
	protected Object getKey() {
		return kshstAddSetManWKHourPK;
	}
}