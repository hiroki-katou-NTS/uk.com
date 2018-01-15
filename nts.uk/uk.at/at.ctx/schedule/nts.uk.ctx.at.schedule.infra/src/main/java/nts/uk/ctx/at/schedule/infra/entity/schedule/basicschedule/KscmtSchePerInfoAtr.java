package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
/**
 * 
 * @author trungtran
 *スケジュール個人情報区分
 */
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@Getter
@Setter
@Entity
@Table(name = "KSCMT_SCHE_PER_INFO_ATR")
public class KscmtSchePerInfoAtr extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public KscmtSchePerInfoAtrPk kscmtSchePerInfoAtrPk;

	@Override
	protected Object getKey() {
		return kscmtSchePerInfoAtrPk;
	}

}
