package nts.uk.ctx.at.function.infra.entity.alarm.checkcondition.agree36;

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
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Kfnmt36AgreeCondErrPK implements Serializable{
	private static final long serialVersionUID = 1L;
	/** ID **/
	@Column(name = "ID")
	public String id;
}
