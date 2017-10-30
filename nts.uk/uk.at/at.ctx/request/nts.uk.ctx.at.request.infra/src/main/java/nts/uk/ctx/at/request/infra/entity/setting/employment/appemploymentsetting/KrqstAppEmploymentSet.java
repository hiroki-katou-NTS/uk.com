package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;

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
 *
 * @author loivt
 */
@Entity
@Table(name = "KRQST_APP_EMPLOYMENT_SET")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KrqstAppEmploymentSet extends UkJpaEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected KrqstAppEmploymentSetPK krqstAppEmploymentSetPK;

    @Column(name = "HOLIDAY_TYPE_USE_FLG")
    private int holidayTypeUseFlg;
    
    @Column(name = "DISPLAY_FLAG")
    private int displayFlag;

	@Override
	protected Object getKey() {
		return krqstAppEmploymentSetPK;
	}

    
}

