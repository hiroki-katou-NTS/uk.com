package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQDT_APP_EMPLOY_WORKTYPE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtAppEmployWorktype extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqdtAppEmployWorktypePK krqdtAppEmployWorktypePK;

	@Override
	protected Object getKey() {
		return krqdtAppEmployWorktypePK;
	}
}
