package nts.uk.ctx.at.shared.infra.entity.calculation.holiday;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;


/**
 * The persistent class for the KSHST_INSUFF_FLEX_MNT database table.
 * @author HoangNDH
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="KSHST_INSUFF_FLEX_MNT")
public class KshstInsuffFlexMnt extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshstInsuffFlexMntPK kshstInsuffFlexMntPK;

	/** 補填可能時間 */
	@Column(name="ATTEND_TIME")
	private double attendTime;

	@Override
	protected Object getKey() {
		return kshstInsuffFlexMntPK;
	}
}