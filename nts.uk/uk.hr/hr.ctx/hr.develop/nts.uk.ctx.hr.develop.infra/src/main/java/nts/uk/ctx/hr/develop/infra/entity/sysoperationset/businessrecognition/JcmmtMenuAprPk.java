package nts.uk.ctx.hr.develop.infra.entity.sysoperationset.businessrecognition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class JcmmtMenuAprPk implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name = "CID")
	public String cId;
	
	@NotNull
	@Column(name = "WORK_ID")
	public Integer workId;
	
	@Column(name = "PROGRAM_ID")
	public String programId;

	@Column(name = "SCREEN_ID")
	public String screenId;
	
}
