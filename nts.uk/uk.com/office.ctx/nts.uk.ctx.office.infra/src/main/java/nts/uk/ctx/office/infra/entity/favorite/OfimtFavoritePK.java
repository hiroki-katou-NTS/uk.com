package nts.uk.ctx.office.infra.entity.favorite;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class OfimtFavoritePK implements Serializable {
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
}
