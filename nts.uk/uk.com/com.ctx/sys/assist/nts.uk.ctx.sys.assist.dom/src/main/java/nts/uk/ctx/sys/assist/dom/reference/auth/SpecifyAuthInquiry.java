package nts.uk.ctx.sys.assist.dom.reference.auth;

import java.util.List;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.在席照会で参照できる権限の指定
 */
public class SpecifyAuthInquiry {

	// 会社ID
	private String cid;

	// 就業ロールID
	private String employmentRoleId;

	// 見られる職位ID
	private List<String> positonIdSeen;

	private SpecifyAuthInquiry() {
	}

	public static SpecifyAuthInquiry createFromMemento(MementoGetter memento) {
		SpecifyAuthInquiry domain = new SpecifyAuthInquiry();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.cid = memento.getCid();
		this.employmentRoleId = memento.getEmploymentRoleId();
		this.positonIdSeen = memento.getPositonIdSeen();
	}

	public void setMemento(MementoSetter memento) {
		memento.setCid(this.cid);
		memento.setEmploymentRoleId(this.employmentRoleId);
		memento.setPositonIdSeen(this.positonIdSeen);
	}

	public interface MementoSetter {
		void setCid(String cid);

		void setEmploymentRoleId(String employmentRoleId);

		void setPositonIdSeen(List<String> positonIdSeen);
	}

	public interface MementoGetter {
		String getCid();

		String getEmploymentRoleId();

		List<String> getPositonIdSeen();
	}
}
