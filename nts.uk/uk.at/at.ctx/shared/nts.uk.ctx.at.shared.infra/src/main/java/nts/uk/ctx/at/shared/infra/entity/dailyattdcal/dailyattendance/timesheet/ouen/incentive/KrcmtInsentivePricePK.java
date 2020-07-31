package nts.uk.ctx.at.shared.infra.entity.dailyattdcal.dailyattendance.timesheet.ouen.incentive;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.layer.infra.data.entity.type.GeneralDateToDBConverter;
import nts.arc.time.GeneralDate;

@Embeddable
@NoArgsConstructor
@EqualsAndHashCode
@AllArgsConstructor
public class KrcmtInsentivePricePK implements Serializable {

	/***/
	private static final long serialVersionUID = 1L;
	

	/** インセンティブ単価ID */
	@Column(name = "INSENTIVE_PRICE_ID")
	public String id;
	
	/** 使用開始日 */
	@Convert(converter = GeneralDateToDBConverter.class)
	@Column(name = "START_DATE")
	public GeneralDate startDate;
	

}
