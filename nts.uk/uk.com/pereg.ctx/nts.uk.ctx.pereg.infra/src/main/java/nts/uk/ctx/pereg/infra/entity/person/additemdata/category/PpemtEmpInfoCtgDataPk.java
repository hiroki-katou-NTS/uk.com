package nts.uk.ctx.pereg.infra.entity.person.additemdata.category;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class PpemtEmpInfoCtgDataPk implements Serializable {

	private static final long serialVersionUID = 1L;
	// レコードID
	@Basic(optional = false)
	@Column(name = "RECORD_ID")
	public String recordId;

}
