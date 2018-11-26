package nts.uk.ctx.at.shared.app.attendanceitem;

//import static org.hamcrest.CoreMatchers.is;

//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil;
//import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
//import nts.uk.ctx.at.shared.dom.attendance.util.item.ValueType;

public class AttendanceItemUtilTest {
	
//	@Test
//    public void test_toAttendanceItemDto() {
//		List<ItemValue> items = new ArrayList<>();
//		items.add(new ItemValue(ValueType.STRING, "A_A", 1));
//		items.add(new ItemValue(ValueType.STRING, "B", 0));
//		items.add(new ItemValue(ValueType.STRING, "B0_A_A", 1));
//		items.add(new ItemValue(ValueType.STRING, "B0_B", 0));
//		items.add(new ItemValue(ValueType.STRING, "B0_C", 2));
//		items.add(new ItemValue(ValueType.STRING, "B1_A_B", 3));
//		items.add(new ItemValue(ValueType.STRING, "B2_B", 0));
//		items.add(new ItemValue(ValueType.STRING, "C", 2));
//		SampleConvertibleAttendanceItem result = AttendanceItemUtil.toConvertibleAttendanceItem(SampleConvertibleAttendanceItem.class, items);
//		Assert.assertEquals(result.getAttendanceItem().getAttendanceItem(), items.get(0).value());
//    }
//	
//	@Test
//    public void test_toAttendanceItemDto_2() {
//		List<ItemValue> items = new ArrayList<>();
//		items.add(new ItemValue("1", ValueType.STRING, "A_A", 1));
//		items.add(new ItemValue("2", ValueType.STRING, "B0_A_A", 1));
//		items.add(new ItemValue("3", ValueType.STRING, "B0_B0", 0));
//		items.add(new ItemValue("4", ValueType.STRING, "B0_C", 2));
////		items.add(new ItemValue(ValueType.STRING, "B1_A_B", 3));
//		items.add(new ItemValue("5", ValueType.STRING, "B2_A", 0));
//		items.add(new ItemValue("6", ValueType.STRING, "C", 2));
//		SampleConvertibleAttendanceItem input = new SampleConvertibleAttendanceItem();
//		input.setAttendanceItem(new SampleObject("What", "Where"));
//		input.setAttendanceItemB("Why");
//		input.setAttendanceItems(Arrays.asList(input));
//		SampleConvertibleAttendanceItem result = AttendanceItemUtil.toConvertibleAttendanceItem(input, items);
//		Assert.assertEquals(result.getAttendanceItem().getAttendanceItem(), items.get(0).value());
//    }
//	
//	@Test
//    public void test_toItemValue() {
//		SampleConvertibleAttendanceItem input = new SampleConvertibleAttendanceItem();
//		input.setAttendanceItem(new SampleObject("What", "Where"));
//		input.setAttendanceItemB("Why");
//		SampleConvertibleAttendanceItem input2 = new SampleConvertibleAttendanceItem();
//		input2.setAttendanceItem(new SampleObject("What 3", "Where 3"));
//		input2.setAttendanceItemB("Why 2");
//		input.setAttendanceItems(Arrays.asList(input2, input2, input2));
//		List<ItemValue> result = AttendanceItemUtil.toItemValues(input);
//		Assert.assertEquals(result.size(), 12);
//    }
}
