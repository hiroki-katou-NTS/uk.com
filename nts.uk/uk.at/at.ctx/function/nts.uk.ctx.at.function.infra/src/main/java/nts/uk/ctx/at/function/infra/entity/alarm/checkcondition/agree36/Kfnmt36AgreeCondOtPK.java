package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Kfnmt36AgreeCondOtPK implements Serializable {
	private static final long serialVersionUID = 1L;
	/** ID **/
	@Column(name = "ID")
	public String id;
	/** NO **/
	@Column(name = "NO")
	public int no;
	
	/** NO **/
	@Column(name = "CD")
	public String code;
	
	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "CATEGORY")
	public int category;
}
