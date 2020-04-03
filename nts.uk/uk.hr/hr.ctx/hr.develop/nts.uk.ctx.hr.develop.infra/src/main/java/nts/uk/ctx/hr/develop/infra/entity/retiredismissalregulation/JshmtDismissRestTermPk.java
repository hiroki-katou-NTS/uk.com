package nts.uk.ctx.hr.develop.infra.entity.retiredismissalregulation;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Embeddable
public class JshmtDismissRestTermPk implements Serializable{

	
private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String histId;
	
	@Basic(optional = false)
	@Column(name = "TERM_CASE")
	public Integer termCase;
	
}
