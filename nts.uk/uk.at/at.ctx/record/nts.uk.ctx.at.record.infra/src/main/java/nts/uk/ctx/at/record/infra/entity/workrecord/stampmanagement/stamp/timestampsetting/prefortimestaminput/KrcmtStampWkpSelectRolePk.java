package nts.uk.ctx.at.record.infra.entity.workrecord.stampmanagement.stamp.timestampsetting.prefortimestaminput;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 職場選択を利用できる権限Pk
 * @author chungnt
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KrcmtStampWkpSelectRolePk implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	/** 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/** ロールID*/
	@Column(name = "ROLE_ID")
	public String roleId;
	
}