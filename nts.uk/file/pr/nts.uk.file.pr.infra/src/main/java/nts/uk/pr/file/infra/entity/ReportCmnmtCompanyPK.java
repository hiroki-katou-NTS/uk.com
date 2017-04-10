package nts.uk.pr.file.infra.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * 
 * @author lanlt
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Embeddable

public class ReportCmnmtCompanyPK implements Serializable {
	/**
	 * serialVersionUID
	 */
	public static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name ="CCD")
	public String companyCd;
	

}
