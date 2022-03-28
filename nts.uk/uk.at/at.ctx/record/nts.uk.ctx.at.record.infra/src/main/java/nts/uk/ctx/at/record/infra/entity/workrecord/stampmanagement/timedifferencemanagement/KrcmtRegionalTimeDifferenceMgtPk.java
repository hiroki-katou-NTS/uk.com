package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.timedifferencemanagement;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcmtRegionalTimeDifferenceMgtPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * コード
	 */
	@Basic(optional = false)
	@Column(name = "CODE")
	public int code;
	
}
