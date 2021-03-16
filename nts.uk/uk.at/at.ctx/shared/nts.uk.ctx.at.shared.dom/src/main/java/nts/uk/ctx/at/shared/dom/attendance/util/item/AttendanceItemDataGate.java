package nts.uk.ctx.at.shared.dom.attendance.util.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;

public interface AttendanceItemDataGate {
	
	public default Optional<ItemValue> valueOf(String path) { return Optional.empty(); }
	
	public default AttendanceItemDataGate newInstanceOf(String path) { return null; }

	public default Optional<AttendanceItemDataGate> get(String path) { return Optional.empty(); }
	
	public default int size(String path) { return 0; }
	
	public default PropType typeOf(String path) { return PropType.OBJECT; }
	
	public default <T extends AttendanceItemDataGate> List<T> gets(String path) {
		return new ArrayList<>();
	}
	
	public default void set(String path, ItemValue value) { }
	
	public default void set(String path, AttendanceItemDataGate value) {}
	
	public default <T extends AttendanceItemDataGate> void set(String path, List<T> value) {}
	
	public default boolean isNo(int idx) { return idx == getNo(); }
	
	public default boolean isEnum(String enumText) { return enumText().equals(enumText); }
	
	public default void setNo(int idx) { }
	
	public default void setEnum(String enumText) { }

	public default int getNo() { return -1; }
	
	public default String enumText() { return ""; }
	
	public default void exsistData() {}
	
	public default boolean isRoot() { return false; }
	
	public default String rootName() { return ""; }
	
	public default boolean isContainer() { return false; }
	
	public static enum PropType {
		OBJECT, VALUE, IDX_LIST, ENUM_LIST, IDX_ENUM_LIST, ENUM_HAVE_IDX, IDX_IN_ENUM, IDX_IN_IDX; 
	}
}
