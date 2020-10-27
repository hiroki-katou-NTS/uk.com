package nts.uk.ctx.at.record.infra.entity.workrecord.erroralarm.monthlycheckcondition;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixConWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.FixedConditionWorkRecordName;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.FixedExtraItemMon;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.SysFixedMonPerEral;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@Entity      
@Table(name = "KRCCT_ALST_FXITM_MON")
public class KrcmtFixedExtraItemMon extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "FIX_EXTRA_ITEM_MON_NO")
	public int fixedExtraItemMonNo;
	
	@Column(name = "FIX_EXTRA_ITEM_MON_NAME")
	public String fixedExtraItemMonName;
	
	@Column(name = "INITIAL_MESSAGE")
	public String message;

	@Override
	protected Object getKey() {
		return fixedExtraItemMonNo;
	}

	public KrcmtFixedExtraItemMon(int fixedExtraItemMonNo, String fixedExtraItemMonName, String message) {
		super();
		this.fixedExtraItemMonNo = fixedExtraItemMonNo;
		this.fixedExtraItemMonName = fixedExtraItemMonName;
		this.message = message;
	}
	
	public static KrcmtFixedExtraItemMon toEntity(FixedExtraItemMon domain) {
		return new KrcmtFixedExtraItemMon(
				domain.getFixedExtraItemMonNo().value,
				domain.getFixedExtraItemMonName().v(),
				domain.getMessage().v()
				);
		
	}
	
	public FixedExtraItemMon toDomain() {
		return new FixedExtraItemMon(
				EnumAdaptor.valueOf(this.fixedExtraItemMonNo, SysFixedMonPerEral.class),
				new FixConWorkRecordName(this.fixedExtraItemMonName),
				new FixedConditionWorkRecordName(this.message)
				);
		
	}

}
