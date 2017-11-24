package nts.uk.ctx.bs.employee.infra.entity.employee.history;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BsymtAffCompanyInfoPk implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "HIST_ID")
	public String historyId;
}
