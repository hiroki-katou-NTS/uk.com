package nts.ctx.at.record.infra.entity.divergencetime;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class KmnmtDivergenceTimePK implements Serializable{

	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Basic(optional = false)
	@Column(name = "CID")
	public int companyId;
	/*乖離時間ID*/
	@Basic(optional = false)
	@Column(name = "DIVERGENCETIME_ID")
	public int divTimeId;
}
