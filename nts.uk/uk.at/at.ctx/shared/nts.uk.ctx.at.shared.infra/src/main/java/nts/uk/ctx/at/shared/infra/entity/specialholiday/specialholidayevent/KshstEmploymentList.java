package nts.uk.ctx.at.shared.infra.entity.specialholiday.specialholidayevent;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KSHST_EMPLOYMENT_LIST")
// 雇用一覧
public class KshstEmploymentList extends UkJpaEntity implements Serializable {
	
	@EmbeddedId
	public KshstEmploymentListPK pk;
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

}
