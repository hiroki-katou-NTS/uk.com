package nts.uk.ctx.at.shared.dom.monthlyattditem.aggregate;

import java.math.BigDecimal;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.monthlyattditem.MonthlyAttItemId;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.勤怠項目.月次の勤怠項目.集計可能な月次の勤怠項目
 * 
 * @author LienPTK
 */
@Getter
public class MonthlyAttItemCanAggregate extends AggregateRoot {

	/** 会社ID */
	private CompanyId cid;

	/** 勤怠項目ID */
	private MonthlyAttItemId attItemId;

	/** 集計可能区分 */
	private boolean editable;

	private MonthlyAttItemCanAggregate() {}

	/**
	 * Creates the from memento.
	 *
	 * @param memento the memento
	 * @return the monthly att item can aggregate
	 */
	public static MonthlyAttItemCanAggregate createFromMemento(MementoGetter memento) {
		MonthlyAttItemCanAggregate domain = new MonthlyAttItemCanAggregate();
		domain.getMemento(memento);
		return domain;
	}

	/**
	 * Gets the memento.
	 *
	 * @param memento the memento
	 * @return the memento
	 */
	public void getMemento(MementoGetter memento) {
		this.cid = new CompanyId(memento.getCid());
		this.attItemId = new MonthlyAttItemId(memento.getAttItemId());
		this.editable = memento.isEditable();
	}

	/**
	 * Sets the memento.
	 *
	 * @param memento the new memento
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCid(this.cid.v());
		memento.setAttItemId(this.attItemId.v());
		memento.setEditable(this.editable);
	}

	public static interface MementoSetter {
		void setCid(String cid);

		void setAttItemId(BigDecimal attItemId);

		void setEditable(boolean editable);
	}

	public static interface MementoGetter {
		String getCid();

		BigDecimal getAttItemId();

		boolean isEditable();
	}

}
