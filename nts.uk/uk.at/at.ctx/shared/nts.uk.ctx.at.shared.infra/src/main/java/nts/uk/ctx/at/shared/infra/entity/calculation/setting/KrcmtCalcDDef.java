package nts.uk.ctx.at.shared.infra.entity.calculation.setting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

/**
 * The persistent class for the KRCMT_CALC_D_DEF database table.
 * @author yennh
 * 変形労働の法定内残業計算
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KRCMT_CALC_D_DEF")
@NamedQuery(name="KrcmtCalcDDef.findAll", query="SELECT k FROM KrcmtCalcDDef k")
public class KrcmtCalcDDef extends ContractUkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*会社ID*/
	@Id
	@Column(name="CID")
	private String cid;

	/*変形法定内残業を計算する*/
	@Column(name="LEGAL_OT_CALC")
	private int legalOtCalc;

	@Override
	protected Object getKey() {
		return cid;
	}

}