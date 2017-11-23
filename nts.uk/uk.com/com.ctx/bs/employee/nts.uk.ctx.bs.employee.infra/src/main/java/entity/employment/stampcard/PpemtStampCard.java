package entity.employment.stampcard;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PPEMT_STAMP_CARD")
public class PpemtStampCard extends UkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@EmbeddedId
	public PpemtStampCardPk ppemtStampCardPk;

	/** 打刻カード番号 */
	@Column(name = "CARD_NUMBER")
	public String cardNumber;

	@Override
	protected Object getKey() {
		return ppemtStampCardPk;
	}

}
