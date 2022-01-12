package nts.uk.smile.infra.entity.smilelinked;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class LsmmtSmileLinkOutsetPK implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// column 契約コード
	@NotNull
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// column 会社ID
	@NotNull
	@Column(name = "CID")
	private String cid;
}