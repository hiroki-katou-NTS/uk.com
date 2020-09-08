package nts.uk.ctx.sys.portal.dom.logsettings;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * ログ設定
 * 
 * @author admin
 *
 */
@Getter
public class LogSetting extends AggregateRoot {
	/**
	 * システム
	 */
	private int system;

	/** プログラムID **/
	private String programId;

	/**
	 * メニュー分類
	 */
	private MenuClassification menuClassification;

	/**
	 * ログイン履歴記録
	 */
	private NotUseAtr loginHistoryRecord;

	/**
	 * 会社ID
	 */
	private String companyId;

	/**
	 * 修正履歴（データ）記録
	 */
	private NotUseAtr editHistoryRecord;

	/**
	 * 起動履歴記録
	 */
	private NotUseAtr bootHistoryRecord;

	/**
	 * Hàm khởi tạo domain thông qua memento
	 * 
	 * @param logSettingMementoGetter
	 * @return
	 */
	public static LogSetting createFromMemento(MementoGetter mementoGetter) {
		LogSetting domain = new LogSetting();
		domain.getMemento(mementoGetter);
		return domain;
	}

	/**
	 * Hàm get memento được sử dụng để cài đặt giá trị cho các primitive trong
	 * domain
	 * 
	 * @param logSettingMementoGetter Ý nghĩa của phương thức này là để thể hiện
	 *                                tính đóng gói (bao đóng) của đối tượng. Mọi
	 *                                thuộc tính của đối tượng đều được khởi tạo và
	 *                                cài đặt bên trong đối tượng. Hàm được sử dụng
	 *                                khi lấy các primitive value từ command hoặc
	 *                                entity
	 */
	public void getMemento(MementoGetter mementoGetter) {
		this.system = mementoGetter.getSystem();
		this.programId = mementoGetter.getProgramId();
		this.menuClassification = MenuClassification.valueOf(mementoGetter.getMenuClassification());
		this.loginHistoryRecord = NotUseAtr.valueOf(mementoGetter.getLoginHistoryRecord());
		this.companyId = mementoGetter.getCompanyId();
		this.editHistoryRecord = NotUseAtr.valueOf(mementoGetter.getEditHistoryRecord());
		this.bootHistoryRecord = NotUseAtr.valueOf(mementoGetter.getBootHistoryRecord());
	}

	/**
	 * Hàm set memento được sử dụng để set các giá trị primitive của domain cho các
	 * đối tượng cần lấy dữ liệu như là dto hoặc entity
	 * 
	 * @param logSettingMementoSetter Ý nghĩa của hàm này cũng như getMemento, mọi
	 *                                lỗi ngoại lệ có thể xảy ra trong domain đều
	 *                                được quản lý bởi domain
	 */
	public void setMemento(MementoSetter mementoSetter) {
		mementoSetter.setSystem(this.system);
		mementoSetter.setProgramId(this.programId);
		mementoSetter.setMenuClassification(this.menuClassification.value);
		mementoSetter.setLoginHistoryRecord(this.loginHistoryRecord.value);
		mementoSetter.setCompanyId(this.companyId);
		mementoSetter.setEditHistoryRecord(this.editHistoryRecord.value);
		mementoSetter.setBootHistoryRecord(this.bootHistoryRecord.value);
	}

	/**
	 * interface này sẽ được implement bởi các đối tượng có quan hệ trực tiếp với
	 * domain Cụ thể trong trường hợp này là DTO và Entity là 2 đối tượng sẽ lấy dữ
	 * liệu từ domain trả ra. Như vậy 2 đối tượng kiểu này sẽ implement interface
	 * này
	 * 
	 * @author vuongnv
	 *
	 */
	public static interface MementoSetter {
		void setSystem(int system);

		void setProgramId(String programId);

		void setMenuClassification(Integer menuClassification);

		void setLoginHistoryRecord(Integer loginHistoryRecord);

		void setCompanyId(String companyId);

		void setEditHistoryRecord(Integer editHistoryRecord);

		void setBootHistoryRecord(Integer bootHistoryRecord);
	}

	/**
	 * Interface này sẽ được implement bởi đối tượng sẽ sử dụng để khởi tạo domain
	 * Trong kiến trúc của project này thì có command và entity sẽ implement
	 * interface này.
	 * 
	 * @author vuongnv
	 *
	 */
	public static interface MementoGetter {
		int getSystem();

		String getProgramId();

		Integer getMenuClassification();

		Integer getLoginHistoryRecord();

		String getCompanyId();

		Integer getEditHistoryRecord();

		Integer getBootHistoryRecord();
	}
}
