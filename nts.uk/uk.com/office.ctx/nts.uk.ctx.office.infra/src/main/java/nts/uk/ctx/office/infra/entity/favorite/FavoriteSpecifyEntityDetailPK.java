package nts.uk.ctx.office.infra.entity.favorite;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;

@Getter
@Setter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteSpecifyEntityDetailPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 作成者ID
	@NotNull
	@Column(name = "SID")
	private String creatorId;

	// column 入力日
	@NotNull
	@Column(name = "INPUT_DATE")
	private GeneralDateTime inputDate;

	// column 対象情報ID
	@NotNull
	@Column(name = "TGT_INFO_ID")
	private String targetSelection;
}
