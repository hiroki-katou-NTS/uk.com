package nts.uk.ctx.office.dom.comment;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員のコメント情報
 */
@Getter
public class EmployeeCommentInformation extends AggregateRoot {

	// コメント
	private DailyContactComment comment;

	// 年月日
	private GeneralDate date;

	// 社員ID
	private String sid;

	private EmployeeCommentInformation() {
	}

	public static EmployeeCommentInformation createFromMemento(MementoGetter memento) {
		EmployeeCommentInformation domain = new EmployeeCommentInformation();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.comment = new DailyContactComment(memento.getComment());
		this.date = memento.getDate();
		this.sid = memento.getSid();
	}

	public void setMemento(MementoSetter memento) {
		memento.setComment(this.comment.v());
		memento.setDate(this.date);
		memento.setSid(this.sid);
	}

	public interface MementoSetter {
		void setComment(String dailyContactComment);

		void setDate(GeneralDate date);

		void setSid(String sid);
	}

	public interface MementoGetter {
		String getComment();

		GeneralDate getDate();

		String getSid();
	}
}
