package nts.uk.ctx.at.function.infra.entity.dailymodification;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KfnmtDayAppCallPK implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Column(name = "CID")
	public String companyId;
	
	@Column(name = "APPLICATION_TYPE")
	public int applicationType;
}
