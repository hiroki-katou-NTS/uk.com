package nts.uk.ctx.pr.core.infra.entity.salary;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.salary.ProcessInformation;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 処理区分基本情報
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_PROCESS_INFORMATION")
public class QpbmtProcessInformation extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtProcessInformationPk processInformationPk;

	/**
	 * 廃止区分
	 */
	@Basic(optional = false)
	@Column(name = "DEPRECAT_CATE")
	public int deprecatCate;

	/**
	 * 処理区分名称
	 */
	@Basic(optional = false)
	@Column(name = "PROCESS_DIVISION_NAME")
	public String processDivisionName;

	@Override
	protected Object getKey() {
		return processInformationPk;
	}

	public ProcessInformation toDomain() {
		return new ProcessInformation(this.processInformationPk.cid, this.processInformationPk.processCateNo,
				this.deprecatCate, this.processDivisionName);
	}

	public static QpbmtProcessInformation toEntity(ProcessInformation domain) {
		return new QpbmtProcessInformation(new QpbmtProcessInformationPk(domain.getCid(), domain.getProcessCateNo()),
				domain.getDeprecatCate().value, domain.getProcessDivisionName().v());
	}

}
