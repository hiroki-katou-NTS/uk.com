package nts.uk.ctx.at.shared.infra.entity.worktype.worktypedisporder;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTypeOrderPk implements Serializable {

	private static final long serialVersionUID = 1L;
	/*会社ID*/
	@Column(name = "CID")
	public String companyId;
	/*勤務種類コード*/
	@Column(name = "WORKTYPE_CD")
	public String workTypeCode;
}
