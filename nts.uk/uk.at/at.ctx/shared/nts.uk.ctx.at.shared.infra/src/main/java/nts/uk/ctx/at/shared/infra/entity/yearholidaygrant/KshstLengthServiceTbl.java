package nts.uk.ctx.at.shared.infra.entity.yearholidaygrant;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;
/**
 * 
 * @author yennth
 *
 */
@Entity
@Table(name="KSHST_LENG_SERVICE_TBL")
@AllArgsConstructor
@NoArgsConstructor
public class KshstLengthServiceTbl extends ContractUkJpaEntity {
	@EmbeddedId
    public KshstLengthServiceTblPK kshstLengthServiceTblPK;
	
	/* 一斉付与する */
	@Column(name = "ALLOW_STATUS")
	public int allowStatus;
	
	/* 付与基準日 */
	@Column(name = "STAND_GRANT_DAY")
	public int standGrantDay;
	
	/* 年数 */
	@Column(name = "YEAR")
	public int year;
	
	/* 月数 */
	@Column(name = "MONTH")
	public int month;

	@Override
	protected Object getKey() {
		return kshstLengthServiceTblPK;
	}
}
