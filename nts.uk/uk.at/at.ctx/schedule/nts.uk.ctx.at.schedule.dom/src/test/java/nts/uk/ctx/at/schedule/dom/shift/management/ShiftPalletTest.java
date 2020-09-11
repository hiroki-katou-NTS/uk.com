package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletCombinationsHelper.CombinationHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletDisplayInfoHelper;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletTest {

	@Test
	public void create_shiftPallet_size0_fail() {

		NtsAssert.businessException("Msg_1616", () -> {
			ShiftPallet.create(
					new ShiftPalletDisplayInfor(
							new ShiftPalletName("shpaName"), // dummy
							NotUseAtr.USE, // dummy
							new ShiftRemarks("shRemar")), // dummy
					Collections.emptyList()); 
		});}
	
	@Test
	public void create_shiftPallet_size21_fail() {

		NtsAssert.businessException("Msg_1616", () -> {
			ShiftPallet.create(
					new ShiftPalletDisplayInfor(
							new ShiftPalletName("shpaName"), // dummy
							NotUseAtr.USE, // dummy
							new ShiftRemarks("shRemar")), // dummy
					Arrays.asList(
							new ShiftPalletCombinations(1, new ShiftCombinationName("ShiftComNam1"),Arrays.asList(new Combinations(1, new ShiftMasterCode("0000001")))),
							new ShiftPalletCombinations(2, new ShiftCombinationName("ShiftComNam2"),Arrays.asList(new Combinations(2, new ShiftMasterCode("0000002")))),
							new ShiftPalletCombinations(3, new ShiftCombinationName("ShiftComNam3"),Arrays.asList(new Combinations(3, new ShiftMasterCode("0000003")))),
							new ShiftPalletCombinations(4, new ShiftCombinationName("ShiftComNam4"),Arrays.asList(new Combinations(4, new ShiftMasterCode("0000004")))),
							new ShiftPalletCombinations(5, new ShiftCombinationName("ShiftComNam5"),Arrays.asList(new Combinations(5, new ShiftMasterCode("0000005")))),
							new ShiftPalletCombinations(6, new ShiftCombinationName("ShiftComNam6"),Arrays.asList(new Combinations(6, new ShiftMasterCode("0000006")))),
							new ShiftPalletCombinations(7, new ShiftCombinationName("ShiftComNam7"),Arrays.asList(new Combinations(7, new ShiftMasterCode("0000007")))),
							new ShiftPalletCombinations(8, new ShiftCombinationName("ShiftComNam8"),Arrays.asList(new Combinations(8, new ShiftMasterCode("0000008")))),
							new ShiftPalletCombinations(9, new ShiftCombinationName("ShiftComNam9"),Arrays.asList(new Combinations(9, new ShiftMasterCode("0000009")))),
							new ShiftPalletCombinations(10, new ShiftCombinationName("ShiftComNam10"),Arrays.asList(new Combinations(10, new ShiftMasterCode("0000010")))),
							new ShiftPalletCombinations(11, new ShiftCombinationName("ShiftComNam11"),Arrays.asList(new Combinations(11, new ShiftMasterCode("0000011")))),
							new ShiftPalletCombinations(12, new ShiftCombinationName("ShiftComNam12"),Arrays.asList(new Combinations(12, new ShiftMasterCode("0000012")))),
							new ShiftPalletCombinations(13, new ShiftCombinationName("ShiftComNam13"),Arrays.asList(new Combinations(13, new ShiftMasterCode("0000013")))),
							new ShiftPalletCombinations(14, new ShiftCombinationName("ShiftComNam14"),Arrays.asList(new Combinations(14, new ShiftMasterCode("0000014")))),
							new ShiftPalletCombinations(15, new ShiftCombinationName("ShiftComNam15"),Arrays.asList(new Combinations(15, new ShiftMasterCode("0000015")))),
							new ShiftPalletCombinations(16, new ShiftCombinationName("ShiftComNam16"),Arrays.asList(new Combinations(16, new ShiftMasterCode("0000016")))),
							new ShiftPalletCombinations(17, new ShiftCombinationName("ShiftComNam17"),Arrays.asList(new Combinations(17, new ShiftMasterCode("0000017")))),
							new ShiftPalletCombinations(18, new ShiftCombinationName("ShiftComNam18"),Arrays.asList(new Combinations(18, new ShiftMasterCode("0000018")))),
							new ShiftPalletCombinations(19, new ShiftCombinationName("ShiftComNam19"),Arrays.asList(new Combinations(19, new ShiftMasterCode("0000019")))),
							new ShiftPalletCombinations(20, new ShiftCombinationName("ShiftComNam20"),Arrays.asList(new Combinations(20, new ShiftMasterCode("0000020")))),
							new ShiftPalletCombinations(21, new ShiftCombinationName("ShiftComNam21"),Arrays.asList(new Combinations(21, new ShiftMasterCode("0000021"))))));
		});
	}

	@Test
	public void create_shiftPallet_duplicate() {

		NtsAssert.businessException("Msg_1616", () -> {
			ShiftPallet.create(
					new ShiftPalletDisplayInfor(
							new ShiftPalletName("shpaName"), // dummy
							NotUseAtr.USE, // dummy
							new ShiftRemarks("shRemar")),// dummy
					Arrays.asList(
							new ShiftPalletCombinations(
									1, 
									new ShiftCombinationName("shComName1"), // dummy
									Arrays.asList(new Combinations(// dummy
											1, // dummy
											new ShiftMasterCode("0000001")))),// dummy
							new ShiftPalletCombinations(
									1, 
									new ShiftCombinationName("shComName2"), // dummy
									Arrays.asList(new Combinations(
											2, // dummy
											new ShiftMasterCode("0000002")))))); // dummy
		});
	}
	
	@Test
	public void create_shiftPallet_sort() {

		ShiftPallet target = new ShiftPallet(
				PalletDisplayInfoHelper.DUMMY,
				Arrays.asList(
					new ShiftPalletCombinations(
							3, 
							new ShiftCombinationName("combiNam3"), // dummy
							Arrays.asList(CombinationHelper.DUMMY)),
					new ShiftPalletCombinations(
							5, 
							new ShiftCombinationName("combiNam5"), // dummy
							Arrays.asList(CombinationHelper.DUMMY)),
					new ShiftPalletCombinations(
							1, 
							new ShiftCombinationName("combiNam1"), // dummy
							Arrays.asList(CombinationHelper.DUMMY))));
		
		assertThat(target.getCombinations())
			.extracting(d->d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(3,"combiNam3"), tuple(5,"combiNam5"), tuple(1,"combiNam1"));
	}
	
	@Test
	public void create_shiftPallet_size20_success() {

		ShiftPallet target = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar")),
				Arrays.asList(
						new ShiftPalletCombinations(1, new ShiftCombinationName("ShiftComNam1"),Arrays.asList(new Combinations(1, new ShiftMasterCode("0000001")))),
						new ShiftPalletCombinations(2, new ShiftCombinationName("ShiftComNam2"),Arrays.asList(new Combinations(2, new ShiftMasterCode("0000002")))),
						new ShiftPalletCombinations(3, new ShiftCombinationName("ShiftComNam3"),Arrays.asList(new Combinations(3, new ShiftMasterCode("0000003")))),
						new ShiftPalletCombinations(4, new ShiftCombinationName("ShiftComNam4"),Arrays.asList(new Combinations(4, new ShiftMasterCode("0000004")))),
						new ShiftPalletCombinations(5, new ShiftCombinationName("ShiftComNam5"),Arrays.asList(new Combinations(5, new ShiftMasterCode("0000005")))),
						new ShiftPalletCombinations(6, new ShiftCombinationName("ShiftComNam6"),Arrays.asList(new Combinations(6, new ShiftMasterCode("0000006")))),
						new ShiftPalletCombinations(7, new ShiftCombinationName("ShiftComNam7"),Arrays.asList(new Combinations(7, new ShiftMasterCode("0000007")))),
						new ShiftPalletCombinations(8, new ShiftCombinationName("ShiftComNam8"),Arrays.asList(new Combinations(8, new ShiftMasterCode("0000008")))),
						new ShiftPalletCombinations(9, new ShiftCombinationName("ShiftComNam9"),Arrays.asList(new Combinations(9, new ShiftMasterCode("0000009")))),
						new ShiftPalletCombinations(10, new ShiftCombinationName("ShiftComNam10"),Arrays.asList(new Combinations(10, new ShiftMasterCode("0000010")))),
						new ShiftPalletCombinations(11, new ShiftCombinationName("ShiftComNam11"),Arrays.asList(new Combinations(11, new ShiftMasterCode("0000011")))),
						new ShiftPalletCombinations(12, new ShiftCombinationName("ShiftComNam12"),Arrays.asList(new Combinations(12, new ShiftMasterCode("0000012")))),
						new ShiftPalletCombinations(13, new ShiftCombinationName("ShiftComNam13"),Arrays.asList(new Combinations(13, new ShiftMasterCode("0000013")))),
						new ShiftPalletCombinations(14, new ShiftCombinationName("ShiftComNam14"),Arrays.asList(new Combinations(14, new ShiftMasterCode("0000014")))),
						new ShiftPalletCombinations(15, new ShiftCombinationName("ShiftComNam15"),Arrays.asList(new Combinations(15, new ShiftMasterCode("0000015")))),
						new ShiftPalletCombinations(16, new ShiftCombinationName("ShiftComNam16"),Arrays.asList(new Combinations(16, new ShiftMasterCode("0000016")))),
						new ShiftPalletCombinations(17, new ShiftCombinationName("ShiftComNam17"),Arrays.asList(new Combinations(17, new ShiftMasterCode("0000017")))),
						new ShiftPalletCombinations(18, new ShiftCombinationName("ShiftComNam18"),Arrays.asList(new Combinations(18, new ShiftMasterCode("0000018")))),
						new ShiftPalletCombinations(19, new ShiftCombinationName("ShiftComNam19"),Arrays.asList(new Combinations(19, new ShiftMasterCode("0000019")))),
						new ShiftPalletCombinations(20, new ShiftCombinationName("ShiftComNam20"),Arrays.asList(new Combinations(20, new ShiftMasterCode("0000020"))))));
		
		assertThat(target)
			.extracting(
					d->d.getDisplayInfor().getShiftPalletName().v(),
					d->d.getDisplayInfor().getShiftPalletAtr().value,
					d->d.getDisplayInfor().getRemarks().v(),
					d->d.getCombinations().get(0).getPositionNumber(),
					d->d.getCombinations().get(0).getCombinationName().v(),
					d->d.getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getCombinations().get(0).getCombinations().get(0).getShiftCode().v(),
					d->d.getCombinations().size()) // check size
			.containsExactly(
					"shpaName",
					NotUseAtr.USE.value,
					"shRemar",
					1,
					"ShiftComNam1",
					1,
					"0000001",
					20);
	}
	
	@Test
	public void create_shiftPallet_size1_success() {

		ShiftPallet target = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar")),
				Arrays.asList(
						new ShiftPalletCombinations(
								1, 
								new ShiftCombinationName("shComName1"),
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001"))))));
		
		assertThat(target)
			.extracting(
					d->d.getDisplayInfor().getShiftPalletName().v(),
					d->d.getDisplayInfor().getShiftPalletAtr().value,
					d->d.getDisplayInfor().getRemarks().v(),
					d->d.getCombinations().get(0).getPositionNumber(),
					d->d.getCombinations().get(0).getCombinationName().v(),
					d->d.getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getCombinations().get(0).getCombinations().get(0).getShiftCode().v(),
					d->d.getCombinations().size()) // check size
			.containsExactly(
					"shpaName",
					NotUseAtr.USE.value,
					"shRemar",
					1,
					"shComName1",
					1,
					"0000001",
					1);
	}
	
	@Test
	public void testGetListShiftMasterCode() {

		ShiftPallet target = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar")),
				Arrays.asList(
						new ShiftPalletCombinations(
								1, new ShiftCombinationName("ShiftComNam1"),
								Arrays.asList(new Combinations(1, new ShiftMasterCode("0000011")))),
						new ShiftPalletCombinations(
								2, new ShiftCombinationName("ShiftComNam2"),
								Arrays.asList(new Combinations(2, new ShiftMasterCode("0000002")),
											  new Combinations(3, new ShiftMasterCode("0000002")))),
						new ShiftPalletCombinations(
								19, new ShiftCombinationName("ShiftComNam19"),
								Arrays.asList(new Combinations(15, new ShiftMasterCode("0000019")),
										      new Combinations(16, new ShiftMasterCode("0000018")))),
						new ShiftPalletCombinations(
								20, new ShiftCombinationName("ShiftComNam20"),
								Arrays.asList(new Combinations(20, new ShiftMasterCode("0000007"))))));
		List<ShiftMasterCode> listShiftMasterCode = target.getListShiftMasterCode();
		assertThat(listShiftMasterCode)
			.extracting(
					d->d.v())
			.containsExactly(
					"0000011",
					"0000002",
					"0000019",
					"0000018",
					"0000007");
	}
	@Test
	public void testReproduct() {

		ShiftPallet target = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar")),
				Arrays.asList(
						new ShiftPalletCombinations(
								1, new ShiftCombinationName("ShiftComNam1"),
								Arrays.asList(new Combinations(1, new ShiftMasterCode("0000011")))),
						new ShiftPalletCombinations(
								2, new ShiftCombinationName("ShiftComNam2"),
								Arrays.asList(new Combinations(2, new ShiftMasterCode("0000002")),
											  new Combinations(3, new ShiftMasterCode("0000002")))),
						new ShiftPalletCombinations(
								19, new ShiftCombinationName("ShiftComNam19"),
								Arrays.asList(new Combinations(15, new ShiftMasterCode("0000019")),
										      new Combinations(16, new ShiftMasterCode("0000018")))),
						new ShiftPalletCombinations(
								20, new ShiftCombinationName("ShiftComNam20"),
								Arrays.asList(new Combinations(20, new ShiftMasterCode("0000007"))))));
		
		ShiftPalletName shiftPalletName = new ShiftPalletName("ShiftPalletNameNew");
		ShiftPallet targetNew = target.reproduct(shiftPalletName);
		
		assertThat(targetNew.getDisplayInfor().getShiftPalletName().v()).isEqualTo(shiftPalletName.v());
		assertThat(targetNew.getDisplayInfor().getShiftPalletAtr()).isEqualTo(NotUseAtr.USE);
	}

	@Test
	public void getters() {
		ShiftPallet target = PalletHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}
}
