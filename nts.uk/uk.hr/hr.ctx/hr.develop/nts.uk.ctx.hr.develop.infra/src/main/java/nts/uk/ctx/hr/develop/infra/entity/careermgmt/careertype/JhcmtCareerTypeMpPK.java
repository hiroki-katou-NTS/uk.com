package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careertype;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JhcmtCareerTypeMpPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "CAREER_TYPE_ID")
	public String careerTypeId;

	@NotNull
	@Column(name = "HIST_ID")
	public String histId;
	
	@NotNull
	@Column(name = "DISP_NUM")
	public Integer dispNum;
}
