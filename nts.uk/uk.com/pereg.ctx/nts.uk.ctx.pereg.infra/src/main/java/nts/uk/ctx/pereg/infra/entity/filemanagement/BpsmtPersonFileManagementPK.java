package nts.uk.ctx.pereg.infra.entity.filemanagement;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Embeddable
public class BpsmtPersonFileManagementPK {

	private static final long serialVersionUID = 1L;

	/** ファイルID */
	@Basic(optional = false)
	@Column(name = "FILE_ID")
	public String fileid;

}
