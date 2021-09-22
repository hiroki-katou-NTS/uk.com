package nts.uk.ctx.at.record.infra.entity.jobmanagement.tasksupplementaryinforitemsetting;

import java.io.Serializable;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ChoiceCode;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ChoiceName;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.ExternalCode;
import nts.uk.ctx.at.record.dom.jobmanagement.tasksupplementaryinforitemsetting.TaskSupInfoChoicesDetail;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * 
 * @author tutt
 * @name 作業補足情報の選択肢詳細 TaskSupInfoChoicesDetail
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KRCMT_TAST_SUP_INFO_CHOICES_DETAIL")
public class KrcmtTaskSupInfoChoicesDetail extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtTaskSupInfoChoicesDetailPk pk;

	@Column(name = "EXTERNAL_CD")
	public String externalCd;

	@Column(name = "NAME")
	public String name;

	@Column(name = "CID")
	public String cid;

	@Override
	protected Object getKey() {
		return this.pk;
	}

	public TaskSupInfoChoicesDetail toDomain() {
		return new TaskSupInfoChoicesDetail(this.pk.hisId, this.pk.manHrItemId,
				new ChoiceCode(this.pk.code), new ChoiceName(this.name),
				Optional.of(new ExternalCode(this.externalCd)));
	}

}
