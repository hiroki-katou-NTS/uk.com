package nts.uk.ctx.at.shared.infra.entity.calculation.setting;

import java.io.Serializable;
import javax.persistence.*;

//import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import java.math.BigDecimal;

/**
 * The persistent class for the KSHST_DEF_LABOR_OT_CALC database table.
 * @author yennh
 * 変形労働の法定内残業計算
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name="KSHST_DEF_LABOR_OT_CALC")
@NamedQuery(name="KshstDefLaborOtCalc.findAll", query="SELECT k FROM KshstDefLaborOtCalc k")
public class KshstDefLaborOtCalc extends UkJpaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*会社ID*/
	@Id
	@Column(name="CID")
	private String cid;

	/*変形法定内残業を計算する*/
	@Column(name="LEGAL_OT_CALC")
	private NotUseAtr legalOtCalc;

	@Override
	protected Object getKey() {
		return cid;
	}

}