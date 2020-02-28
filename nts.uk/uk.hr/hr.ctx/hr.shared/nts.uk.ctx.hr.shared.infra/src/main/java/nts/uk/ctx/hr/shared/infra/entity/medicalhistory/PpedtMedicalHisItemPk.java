/**
 * 
 */
package nts.uk.ctx.hr.shared.infra.entity.medicalhistory;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author laitv
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpedtMedicalHisItemPk implements Serializable{
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "HIST_ID")
	public String hisId;
}

