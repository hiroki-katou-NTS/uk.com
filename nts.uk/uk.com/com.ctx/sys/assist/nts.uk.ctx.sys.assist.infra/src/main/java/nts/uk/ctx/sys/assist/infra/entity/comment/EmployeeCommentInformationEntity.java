package nts.uk.ctx.sys.assist.infra.entity.comment;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.assist.dom.comment.EmployeeCommentInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/*
 * UKDesign.データベース.ER図.オフィス支援.在席照会.コメント.OFIDT_COMMENT_SYA
 * 在席照会のコメント
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "OFIDT_COMMENT_SYA")
public class EmployeeCommentInformationEntity extends UkJpaEntity
		implements EmployeeCommentInformation.MementoGetter, EmployeeCommentInformation.MementoSetter, Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	// column 排他バージョン
	@Version
	@Column(name = "EXCLUS_VER")
	private long version;

	// column 契約コード
	@Basic(optional = false)
	@Column(name = "CONTRACT_CD")
	private String contractCd;

	// Embedded primary key 社員ID and 年月日
	@EmbeddedId
	private EmployeeCommentInformationEntityPK pk;

	// column コメント
	@NotNull
	@Column(name = "COMMENT")
	private String comment;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	@Override
	public void setComment(String dailyContactComment) {
		this.comment = dailyContactComment;
	}

	@Override
	public void setDate(GeneralDate date) {
		if (this.pk == null) {
			this.pk = new EmployeeCommentInformationEntityPK();
		}
		this.pk.setDate(date);
	}

	@Override
	public void setSid(String sid) {
		if (this.pk == null) {
			this.pk = new EmployeeCommentInformationEntityPK();
		}
		this.pk.setSid(sid);
	}

	@Override
	public String getComment() {
		return this.comment;
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
