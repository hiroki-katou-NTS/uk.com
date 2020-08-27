package nts.uk.ctx.at.shared.infra.entity.remainingnumber.subhdmana;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class KrcmtLeaveDayOffManaPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//社員ID
	@Column(name = "SID")
	public String sid;

	// 発生日
	@Column(name = "OCCURRENCE_DATE")
	public GeneralDate occDate;

	// 使用日
	@Column(name = "DIGESTION_DATE")
	public GeneralDate digestDate;
}
