package nts.uk.ctx.health.infra.api.entity.emoji.employee;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.health.dom.emoji.employee.EmployeeEmojiState;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.ヘルスライフ.感情状態管理.感情状態管理.HHLDT_MOOD_SYA
 * 社員の感情状態
 */
@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(name = "HHLDT_MOOD_SYA")
public class HhldtMoodSya extends UkJpaEntity
		implements EmployeeEmojiState.MementoGetter, EmployeeEmojiState.MementoSetter, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	// column 排他バージョン
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 社員ID and 年月日
	@EmbeddedId
	private HhldtMoodSyaPK pk;
	
	// column 会社ID
	@NotNull
	@Column(name = "CID")
	private String cid;

	// column 社員ID
	@NotNull
	@Column(name = "MOOD_TYPE")
	private Integer emojiType;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setDate(GeneralDate date) {
		if (this.pk == null) {
			this.pk = new HhldtMoodSyaPK();
		}
		this.pk.setDate(date);
	}

	@Override
	public void setSid(String sid) {
		if (this.pk == null) {
			this.pk = new HhldtMoodSyaPK();
		}
		this.pk.setSid(sid);
	}

	@Override
	public GeneralDate getDate() {
		return this.pk.getDate();
	}

	@Override
	public String getSid() {
		return this.pk.getSid();
	}
}
