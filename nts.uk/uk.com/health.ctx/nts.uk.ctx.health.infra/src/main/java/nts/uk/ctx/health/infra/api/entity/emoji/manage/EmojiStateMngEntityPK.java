package nts.uk.ctx.health.infra.api.entity.emoji.manage;

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
public class EmojiStateMngEntityPK implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 会社ID
	@NotNull
	@Column(name = "CID")
	private String cid;

}
