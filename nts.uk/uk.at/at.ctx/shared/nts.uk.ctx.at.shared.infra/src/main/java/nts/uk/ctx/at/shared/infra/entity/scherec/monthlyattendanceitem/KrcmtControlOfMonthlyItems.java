package nts.uk.ctx.at.shared.infra.entity.scherec.monthlyattendanceitem;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.TimeInputUnit;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.primitivevalue.HeaderBackgroundColor;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.ControlOfMonthlyItems;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "KSHMT_MON_ITEM_CONTROL")
public class KrcmtControlOfMonthlyItems   extends ContractUkJpaEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KrcmtControlOfMonthlyItemsPK krcmtControlOfMonthlyItemsPK;
	
	@Column(name = "HEADER_BACKGROUND_COLOR")
	public String headerBgColorOfMonthlyPer;
	
	@Column(name = "TIME_INPUT_UNIT")
	public Integer inputUnitOfTimeItem;
	
	@Override
	protected Object getKey() {
		return krcmtControlOfMonthlyItemsPK;
	}

	public KrcmtControlOfMonthlyItems(KrcmtControlOfMonthlyItemsPK krcmtControlOfMonthlyItemsPK, String headerBgColorOfMonthlyPer, Integer inputUnitOfTimeItem) {
		super();
		this.krcmtControlOfMonthlyItemsPK = krcmtControlOfMonthlyItemsPK;
		this.headerBgColorOfMonthlyPer = headerBgColorOfMonthlyPer;
		this.inputUnitOfTimeItem = inputUnitOfTimeItem;
	}
	
	public static KrcmtControlOfMonthlyItems toEntity(ControlOfMonthlyItems domain) {
		return new KrcmtControlOfMonthlyItems(
				new KrcmtControlOfMonthlyItemsPK(
					domain.getCompanyId(),
					domain.getItemMonthlyId()
						),
				domain.getHeaderBgColorOfMonthlyPer().isPresent()?domain.getHeaderBgColorOfMonthlyPer().get().v():null,
				domain.getInputUnitOfTimeItem().isPresent()?domain.getInputUnitOfTimeItem().get().value:null
				);
	}

	public ControlOfMonthlyItems toDomain() {
		return new  ControlOfMonthlyItems(
				this.krcmtControlOfMonthlyItemsPK.companyID,
				this.krcmtControlOfMonthlyItemsPK.itemMonthlyID,
				this.headerBgColorOfMonthlyPer!=null ? new HeaderBackgroundColor(this.headerBgColorOfMonthlyPer) : null,
				this.inputUnitOfTimeItem!=null ? EnumAdaptor.valueOf(this.inputUnitOfTimeItem, TimeInputUnit.class) :null
				);
	}
	
}
