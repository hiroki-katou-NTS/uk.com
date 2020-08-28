package nts.uk.ctx.at.record.infra.entity.workrecord.workrecord;

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
public class KrcdtWorkFixedPk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 会社ID
	 */
	@Basic(optional = false)
	@Column(name = "CID")
	public String companyId;
	
	/**
	 * 職場ID
	 */
	@Basic(optional = false)
	@Column(name = "WKPID")
	public String workplaceId;
	
	/**
	 * 締めID
	 */
	@Basic(optional = false)
	@Column(name = "CLOSURE_ID")
	public int closureId;
	
	/**
	 * 処理年月
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_YM")
	public int processYM;
}
