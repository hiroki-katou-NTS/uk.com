package nts.uk.ctx.at.request.infra.entity.setting.employment.appemploymentsetting;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
//import nts.uk.ctx.at.request.infra.entity.application.stamp.KrqdtAppStamp;
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

	@ManyToOne
	@PrimaryKeyJoinColumns({
		@PrimaryKeyJoinColumn(name="CID",referencedColumnName="CID"), 
		@PrimaryKeyJoinColumn(name="EMPLOYMENT_CODE",referencedColumnName="EMPLOYMENT_CODE"),
		@PrimaryKeyJoinColumn(name="APP_TYPE",referencedColumnName="APP_TYPE"),
		@PrimaryKeyJoinColumn(name="HOLIDAY_OR_PAUSE_TYPE",referencedColumnName="HOLIDAY_OR_PAUSE_TYPE")
	})
	private KrqstAppEmploymentSet krqstAppEmploymentSet;
	@Override
	protected Object getKey() {
		return krqdtAppEmployWorktypePK;
	}
}
