package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.refersToInformation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author chungnt
 *
 */

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrmctFingerVeinInfoPk implements Serializable{

	private static final long serialVersionUID = 1L;

	/**
	 * 社員ID
	 */
	@Basic(optional = false)
	@Column(name = "SID")
	public String employeeId;

	/**
	 * 指種類
	 */
	@Basic(optional = false)
	@Column(name = "FINGER_TYPE")
	public int fingerType;
	
	/**
	 * 静脈NO
	 */
	@Basic(optional = false)
	@Column(name = "VEIN_NO")
	public int veinNo;
	
}
