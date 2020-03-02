package nts.uk.ctx.hr.shared.infra.entity.retirementinfo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author anhdt 履歴ID
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class PeedtRetirePk implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 履歴ID
	 */
	@Column(name = "HIST_ID")
	public String historyID;
}
