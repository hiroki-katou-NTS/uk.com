package nts.uk.ctx.health.infra.api.entity.emoji.employee;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class HhldtMoodSyaPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 社員ID
	@NotNull
	@Column(name = "SID")
	private String sid;

	// column 年月日
	@NotNull
	@Column(name = "YMD")
	private GeneralDate date;
}
