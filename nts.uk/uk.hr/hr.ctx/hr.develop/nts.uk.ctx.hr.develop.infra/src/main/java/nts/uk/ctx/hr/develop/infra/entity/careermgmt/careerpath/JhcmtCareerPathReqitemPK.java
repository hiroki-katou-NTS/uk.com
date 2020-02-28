package nts.uk.ctx.hr.develop.infra.entity.careermgmt.careerpath;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class JhcmtCareerPathReqitemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Column(name = "ITEM_ID")
	public String itemId;

}
