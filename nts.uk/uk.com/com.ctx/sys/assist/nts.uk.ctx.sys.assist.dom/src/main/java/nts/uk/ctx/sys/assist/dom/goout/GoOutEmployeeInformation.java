package nts.uk.ctx.sys.assist.dom.goout;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員の外出情報
 */
public class GoOutEmployeeInformation {
	// TODO 勤怠時刻 la gi?

	// 外出時刻
	private GeneralDateTime goOutTime;

	// 外出理由
	private GoOutReason goOutReason;

	// 年月日
	private GeneralDate gouOutDate;

	// 戻り時刻
	private GeneralDateTime comebackTime;

	// 社員ID
	private String sid;

	private GoOutEmployeeInformation() {
	}

	public static GoOutEmployeeInformation createFromMemento(MementoGetter memento) {
		GoOutEmployeeInformation domain = new GoOutEmployeeInformation();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.goOutTime = memento.getGoOutTime();
		this.goOutReason = new GoOutReason(memento.getGoOutReason());
		this.gouOutDate = memento.getGouOutDate();
		this.comebackTime = memento.getComebackTime();
		this.sid = memento.getSid();
	}

	public void setMemento(MementoSetter memento) {
		memento.setGoOutTime(this.goOutTime);
		memento.setGoOutReason(this.goOutReason.v());
		memento.setGouOutDate(this.gouOutDate);
		memento.setComebackTime(this.comebackTime);
		memento.setSid(this.sid);
	}

	public interface MementoSetter {
		void setGoOutTime(GeneralDateTime goOutTime);

		void setGoOutReason(String goOutReason);

		void setGouOutDate(GeneralDate gouOutDate);

		void setComebackTime(GeneralDateTime comebackTime);

		void setSid(String sid);
	}

	public interface MementoGetter {
		GeneralDateTime getGoOutTime();

		String getGoOutReason();

		GeneralDate getGouOutDate();

		GeneralDateTime getComebackTime();

		String getSid();
	}

}
