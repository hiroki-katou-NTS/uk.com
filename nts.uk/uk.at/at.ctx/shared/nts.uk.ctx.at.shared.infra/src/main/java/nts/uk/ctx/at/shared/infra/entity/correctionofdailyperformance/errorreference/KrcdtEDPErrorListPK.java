package nts.uk.ctx.at.shared.infra.entity.correctionofdailyperformance.errorreference;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class KrcdtEDPErrorListPK implements Serializable{
		private static final long serialVersionUID = 1L;
		@Column(name = "ERROR")
		public String error;
		@Column(name = "EMPLOYEE_EMERGENCY")
		public String employeeEmergency;
		@Column(name = "RESOLVE_ERROR_DATE")
		public GeneralDate resolveErrorDate;

}
