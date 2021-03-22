package nts.uk.ctx.office.dom.status;

import lombok.Getter;
import lombok.Setter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.在席のステータス
 */
@Getter
@Setter
public class ActivityStatus extends AggregateRoot {

	// ステータス分類
	private StatusClassfication activity;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;

	public ActivityStatus() {
	}

	public static ActivityStatus createFromMemento(MementoGetter memento) {
		ActivityStatus domain = new ActivityStatus();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.activity = EnumAdaptor.valueOf(memento.getActivity(), StatusClassfication.class);
		this.date = memento.getDate();
		this.sid = memento.getSid();
	}

	public void setMemento(MementoSetter memento) {
		memento.setActivity(this.activity.value);
		memento.setDate(this.date);
		memento.setSid(this.sid);
	}

	public interface MementoSetter {
		void setActivity(Integer activity);

		void setDate(GeneralDate date);

		void setSid(String sid);
	}

	public interface MementoGetter {
		Integer getActivity();

		GeneralDate getDate();

		String getSid();
	}
}
