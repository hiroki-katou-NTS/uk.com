package nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * スケジュール資格設定
 * 
 * @author trungtran
 *
 */
@Getter
@Setter
@Entity
@Table(name = "KSCST_SCHE_QUALIFY_SET")
public class KscstScheQualifySet extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KscstScheQualifySetPK kscstScheQualifySetPK;



	@Override
	protected Object getKey() {
		return this.kscstScheQualifySetPK;
	}

}
