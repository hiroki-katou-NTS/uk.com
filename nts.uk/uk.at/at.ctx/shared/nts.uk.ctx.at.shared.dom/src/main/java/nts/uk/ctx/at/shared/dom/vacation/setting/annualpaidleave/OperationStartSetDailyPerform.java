/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.gul.serialize.binary.SerializableWithOptional;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * The Class OperationStartSetDailyPerform.
 */
// 日別実績の運用開始設定
@Getter
@AllArgsConstructor
public class OperationStartSetDailyPerform extends AggregateRoot implements SerializableWithOptional{
	
	/**
	 * Serializable
	 */
	private static final long serialVersionUID = 1L;

	/** The company id. */
	// 会社ID
	private CompanyId companyId;
	
	/** The operate start date daily perform. */
	// 日別実績の運用開始日
	private transient Optional<GeneralDate> operateStartDateDailyPerform;
	
	/**
	 * Instantiates a new operation start set daily perform.
	 *
	 * @param memento the memento
	 */
	public OperationStartSetDailyPerform(OperationStartSetDailyPerformGetMemento memento) {
		this.companyId = memento.getCompanyId();
		this.operateStartDateDailyPerform = memento.getOperateStartDateDailyPerform();
	}
	
	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(OperationStartSetDailyPerformSetMemento memento) {
		memento.setCompanyId(this.companyId);
		memento.setOperateStartDateDailyPerform(this.operateStartDateDailyPerform);
	}
	
	private void writeObject(ObjectOutputStream stream){	
		writeObjectWithOptional(stream);
	}	
	private void readObject(ObjectInputStream stream){	
		readObjectWithOptional(stream);
	}	

}

