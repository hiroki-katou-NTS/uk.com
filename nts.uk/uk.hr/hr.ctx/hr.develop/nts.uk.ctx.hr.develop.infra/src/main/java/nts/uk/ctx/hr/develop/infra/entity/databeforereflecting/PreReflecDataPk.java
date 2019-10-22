package nts.uk.ctx.hr.develop.infra.entity.databeforereflecting;

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
public class PreReflecDataPk implements Serializable{

	
private static final long serialVersionUID = 1L;
	
	/** The historyId. */
	@Basic(optional = false)
	@Column(name="HIST_ID")
	public String historyId;
	
	
}
