package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.fourweekfourdayoff;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class KfnmtAlarmCheck4W4DPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "AL_CHECK_4W4D_ID")
	public String alCheck4w4dID;

}
