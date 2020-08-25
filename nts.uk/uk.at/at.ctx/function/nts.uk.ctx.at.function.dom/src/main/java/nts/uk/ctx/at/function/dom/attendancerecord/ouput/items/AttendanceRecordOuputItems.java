package nts.uk.ctx.at.function.dom.attendancerecord.ouput.items;

import java.util.List;

import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;


/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.出勤簿.出勤簿の出力項目自由設定
 * 
 * @author nws-ducnt
 *
 */
public class AttendanceRecordOuputItems extends AggregateRoot {

	/**
	 * 会社ID
	 */
	private String cid;
	
	/**
	 * 出力項目: 出勤簿の出力項目設定 (List)
	 */
	private List<AttendanceRecordExportSetting> attendanceRecordExportSettings;
	
	/**
	 * 社員ID
	 */
	private String employeeId;
	
	/**
	 * 項目選択種類
	 */
	private ItemSelectionType itemSelectionType;
	
	/**
	 * 1. Ẩn constructor để khởi tạo domain qua hàm createFromMemento
	 */
	private AttendanceRecordOuputItems() {
	}
	
	/**
	 * 2. Hàm khởi tạo domain thông qua memento
	 * 
	 * @param memento
	 * @return
	 */
	public static AttendanceRecordOuputItems createFromMemento(MementoGetter memento) {
		AttendanceRecordOuputItems domain = new AttendanceRecordOuputItems();
		domain.getMemento(memento);
		return domain;
	}
	
	/**
	 * 3. Hàm get memento được sử dụng để cài đặt giá trị cho các primitive trong
	 * domain
	 * 
	 * @param memento Ý nghĩa của phương thức này là để thể hiện tính đóng gói (bao
	 *                đóng) của đối tượng. Mọi thuộc tính của đối tượng đều được
	 *                khởi tạo và cài đặt bên trong đối tượng. Hàm được sử dụng khi
	 *                lấy các primitive value từ command hoặc entity
	 */
	public void getMemento(MementoGetter memento) {
		this.cid = memento.getCid();
		this.attendanceRecordExportSettings = memento.getAttendanceRecordExportSettings();
		this.employeeId = memento.getEmployeeId();
		this.itemSelectionType = ItemSelectionType.valueOf(memento.getItemSelectionType());
		
	}
	/**
	 * 4. Hàm set memento được sử dụng để set các giá trị primitive của domain cho
	 * các đối tượng cần lấy dữ liệu như là dto hoặc entity
	 * 
	 * @param memento Ý nghĩa của hàm này cũng như getMemento, mọi lỗi ngoại lệ có
	 *                thể xảy ra trong domain đều được quản lý bởi domain
	 */
	public void setMemento(MementoSetter memento) {
		memento.setCid(cid);
		if (this.attendanceRecordExportSettings != null) {
			memento.setAttendanceRecordExportSettings(this.attendanceRecordExportSettings);
		}
		memento.setEmployeeId(employeeId);
		if (this.itemSelectionType !=null ) {
			memento.setItemSelectionType(this.itemSelectionType.value);
		}
	}
	
	/**
	 * 5. interface này sẽ được implement bởi các đối tượng có quan hệ trực tiếp với
	 * domain Cụ thể trong trường hợp này là DTO và Entity là 2 đối tượng sẽ lấy dữ
	 * liệu từ domain trả ra. Như vậy 2 đối tượng kiểu này sẽ implement interface
	 * này
	 * 
	 * @author nws-ducnt
	 *
	 */
	public static interface MementoSetter {
		void setCid(String cid);
		
		void setAttendanceRecordExportSettings(List<AttendanceRecordExportSetting> attendanceRecordExportSettings);
		
		void setEmployeeId(String employeeId);
		
		void setItemSelectionType(int itemSelectionType);
	}
	
	/**
	 * 6. Interface này sẽ được implement bởi đối tượng sẽ sử dụng để khởi tạo
	 * domain Trong kiến trúc của project này thì có command và entity sẽ implement
	 * interface này.
	 * 
	 * @author nws-ducnt
	 *
	 */
	public static interface MementoGetter {
		String getCid();
		
		List<AttendanceRecordExportSetting> getAttendanceRecordExportSettings();
		
		String getEmployeeId();
		
		int getItemSelectionType();
	}
	
}
