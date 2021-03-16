package nts.uk.ctx.at.shared.infra.entity.worktime.language;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author sonnh1
 * 
 * 就業時間帯の他言語表示名
 *
 */
@Entity
@Table(name = "KSHMT_WT_LANGUAGE")
@AllArgsConstructor
@NoArgsConstructor
public class KshmtWorkTimeLanguage extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KshmtWorkTimeLanguagePK kshmtWorkTimeLanguagePK;
	/* 名称 */
	@Column(name = "NAME")
	public String name;
	/* 略名 */
	@Column(name = "ABNAME")
	public String abname;

	@Override
	protected Object getKey() {
		return this.kshmtWorkTimeLanguagePK;
	}
}
