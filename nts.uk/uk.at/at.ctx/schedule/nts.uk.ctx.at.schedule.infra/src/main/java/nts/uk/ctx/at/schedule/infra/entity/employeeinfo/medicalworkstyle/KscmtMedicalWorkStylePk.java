package nts.uk.ctx.at.schedule.infra.entity.employeeinfo.medicalworkstyle;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KscmtMedicalWorkStylePk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** 社員ID **/
	@Column(name = "SID")
	private String sid;
	
	/** 履歴ID **/
	@Column(name = "HIST_ID")
	private String histId;

}
