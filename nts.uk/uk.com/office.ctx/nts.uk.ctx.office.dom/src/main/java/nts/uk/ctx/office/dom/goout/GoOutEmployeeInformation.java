package nts.uk.ctx.office.dom.goout;

import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.shr.com.time.AttendanceClock;


/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.社員の外出情報
 */
@Getter
public class GoOutEmployeeInformation {
	// 外出時刻
	private AttendanceClock goOutTime;

	// 外出理由
	private GoOutReason goOutReason;

	// 年月日
	private GeneralDate goOutDate;

	// 戻り時刻
	private AttendanceClock comebackTime;

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
		this.goOutTime = new AttendanceClock(memento.getGoOutTime());
		this.goOutReason = new GoOutReason(memento.getGoOutReason());
		this.goOutDate = memento.getGoOutDate();
		this.comebackTime = new AttendanceClock(memento.getComebackTime());
		this.sid = memento.getSid();
	}

	public void setMemento(MementoSetter memento) {
		memento.setGoOutTime(this.goOutTime.v());
		memento.setGoOutReason(this.goOutReason.v());
		memento.setGoOutDate(this.goOutDate);
		memento.setComebackTime(this.comebackTime.v());
		memento.setSid(this.sid);
	}

	public interface MementoSetter {
		void setGoOutTime(Integer goOutTime);

		void setGoOutReason(String goOutReason);

		void setGoOutDate(GeneralDate gouOutDate);

		void setComebackTime(Integer comebackTime);

		void setSid(String sid);
	}

	public interface MementoGetter {
		Integer getGoOutTime();

		String getGoOutReason();

		GeneralDate getGoOutDate();

		Integer getComebackTime();

		String getSid();
	}

}
