package nts.uk.ctx.at.request.infra.entity.reasonappdaily;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrqdtApplicationReasonPK implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 社員ID */
	@Column(name = "SID")
	public String sid;

	/** 年月日 */
	@Column(name = "YMD")
	public GeneralDate ymd;

}
