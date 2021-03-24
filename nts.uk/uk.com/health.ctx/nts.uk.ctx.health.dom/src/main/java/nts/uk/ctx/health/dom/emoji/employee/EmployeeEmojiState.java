package nts.uk.ctx.health.dom.emoji.employee;

import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.health.dom.emoji.manage.EmojiType;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.NittsuSystem.Common (NtsCommons).ヘルスライフ.感情状態管理.感情状態管理.社員の感情状態
 */
@Getter
public class EmployeeEmojiState extends AggregateRoot {
	//年月日
	private GeneralDate date;

	//感情種類
	private EmojiType emojiType;

	//社員ID
	private String sid;

	private EmployeeEmojiState() {
	}

	public static EmployeeEmojiState createFromMemento(MementoGetter memento) {
		EmployeeEmojiState domain = new EmployeeEmojiState();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.date = memento.getDate();
		this.emojiType = EnumAdaptor.valueOf(memento.getEmojiType(), EmojiType.class);
		this.sid = memento.getSid();
	}

	public void setMemento(MementoSetter memento) {
		memento.setDate(this.date);
		memento.setEmojiType(this.emojiType.value);
		memento.setSid(this.sid);
	}

	public interface MementoSetter {
		void setDate(GeneralDate date);

		void setEmojiType(Integer emojiType);

		void setSid(String sid);
	}

	public interface MementoGetter {
		GeneralDate getDate();

		Integer getEmojiType();

		String getSid();
	}
}
