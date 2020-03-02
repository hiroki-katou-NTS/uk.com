package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.eventoperation;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
/**
 * 
 * @author yennth
 *
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JcmctMenuOperationPK implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/* 会社ID */
	@Column(name = "CID")
	public String companyId;
	
	/* プログラムID */
	@Column(name = "PROGRAM_ID")
	public String programId;
}
