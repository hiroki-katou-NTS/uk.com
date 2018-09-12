package nts.uk.ctx.pr.core.infra.entity.wageprovision.processdatecls;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.pr.core.dom.wageprovision.processdatecls.CurrProcessDate;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

/**
 * 現在処理年月
 */
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "QPBMT_CURR_PROCESS_DATE")
public class QpbmtCurrProcessDate extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * ID
	 */
	@EmbeddedId
	public QpbmtCurrProcessDatePk currProcessDatePk;

	/**
	 * GIVE_CURR_TREAT_YEAR
	 */
	@Basic(optional = false)
	@Column(name = "GIVE_CURR_TREAT_YEAR")
	public int giveCurrTreatYear;

	@Override
	protected Object getKey() {
		return currProcessDatePk;
	}

	public CurrProcessDate toDomain() {
		return new CurrProcessDate(this.currProcessDatePk.cid, this.currProcessDatePk.processCateNo,
				this.giveCurrTreatYear);
	}

	public static QpbmtCurrProcessDate toEntity(CurrProcessDate domain) {
		return new QpbmtCurrProcessDate(new QpbmtCurrProcessDatePk(domain.getCid(), domain.getProcessCateNo()),
				domain.getGiveCurrTreatYear().v());
	}

}
