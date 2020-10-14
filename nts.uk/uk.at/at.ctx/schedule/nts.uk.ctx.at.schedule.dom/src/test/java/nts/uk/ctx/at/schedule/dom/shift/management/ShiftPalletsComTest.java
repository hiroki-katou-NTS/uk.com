package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.Combinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftCombinationName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPalette;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCombinations;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteDisplayInfor;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteName;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftPaletteCom;
import nts.uk.ctx.at.schedule.dom.shift.management.shiftPalette.ShiftRemarks;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsComTest {
	
	@Test
	public void create_shiftPalletsCom_0page_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			ShiftPaletteCom.create(
					"000000000000-0001", // dummy
					0, 
					new ShiftPalette(
							new ShiftPaletteDisplayInfor(
									new ShiftPaletteName("shpaName"), // dummy
									NotUseAtr.USE, // dummy
									new ShiftRemarks("shRemar")), // dummy
							Arrays.asList( 
									new ShiftPaletteCombinations(
											2, // dummy
											new ShiftCombinationName("combiName"),// dummy
											Arrays.asList(new Combinations(
													1, // dummy
													new ShiftMasterCode("0000001"))))))); // dummy
		});
	}

	@Test
	public void create_shiftPalletsCom_11pages_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			ShiftPaletteCom.create(
					"000000000000-0001", // dummy
					11, 
					new ShiftPalette(
							new ShiftPaletteDisplayInfor(
									new ShiftPaletteName("shpaName"), // dummy
									NotUseAtr.USE, // dummy
									new ShiftRemarks("shRemar")), // dummy
							Arrays.asList( 
									new ShiftPaletteCombinations(
											2, // dummy
											new ShiftCombinationName("combiName"),// dummy
											Arrays.asList(new Combinations(
													1, // dummy
													new ShiftMasterCode("0000001"))))))); // dummy
		});
	}
	
	@Test
	public void create_shiftPalletsCom_sort() {

		ShiftPaletteCom target = ShiftPaletteCom.create(
					"000000000000-0001", // dummy
					1, // dummy
					new ShiftPalette(
							new ShiftPaletteDisplayInfor(
									new ShiftPaletteName("shpaName"), // dummy
									NotUseAtr.USE, // dummy
									new ShiftRemarks("shRemar")), // dummy
							Arrays.asList(
								new ShiftPaletteCombinations(
										2, 
										new ShiftCombinationName("combiName2"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000001")))), // dummy
								new ShiftPaletteCombinations(
										3, 
										new ShiftCombinationName("combiName3"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000001")))), // dummy
								new ShiftPaletteCombinations(
										1, 
										new ShiftCombinationName("combiName1"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000001"))))))); // dummy
		
		assertThat(target.getShiftPallet().getCombinations())
			.extracting(d->d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(1, "combiName1"),tuple(2, "combiName2"),tuple(3, "combiName3"));
	}

	@Test
	public void create_shiftPalletsCom_1page_success() {

		ShiftPaletteCom target = new ShiftPaletteCom(
				"000000000000-0001",
				1, 
				new ShiftPalette(
						new ShiftPaletteDisplayInfor(
								new ShiftPaletteName("shpaName"), 
								NotUseAtr.USE, 
								new ShiftRemarks("shRemar")), 
						Arrays.asList( 
								new ShiftPaletteCombinations(
										2, 
										new ShiftCombinationName("combiName"),
										Arrays.asList(new Combinations(
												1, 
												new ShiftMasterCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getCompanyId(),
					d->d.getPage(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					d->d.getShiftPallet().getDisplayInfor().getRemarks().v(),
					d->d.getShiftPallet().getCombinations().get(0).getPositionNumber(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinationName().v(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getShiftCode().v())
			.containsExactly(
					"000000000000-0001",
					1,
					"shpaName",
					NotUseAtr.USE.value,
					"shRemar",
					2,
					"combiName",
					1,
					"0000001");
	} 
	
	@Test
	public void create_shiftPalletsCom_10pages_success() {

		ShiftPaletteCom target = new ShiftPaletteCom(
				"000000000000-0001",
				10, 
				new ShiftPalette(
						new ShiftPaletteDisplayInfor(
								new ShiftPaletteName("shpaName"), 
								NotUseAtr.USE, 
								new ShiftRemarks("shRemar")), 
						Arrays.asList( 
								new ShiftPaletteCombinations(
										2, 
										new ShiftCombinationName("combiName"),
										Arrays.asList(new Combinations(
												1, 
												new ShiftMasterCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getCompanyId(),
					d->d.getPage(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					d->d.getShiftPallet().getDisplayInfor().getRemarks().v(),
					d->d.getShiftPallet().getCombinations().get(0).getPositionNumber(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinationName().v(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getShiftCode().v())
			.containsExactly(
					"000000000000-0001",
					10,
					"shpaName",
					NotUseAtr.USE.value,
					"shRemar",
					2,
					"combiName",
					1,
					"0000001");
	} 
	
	@Test
	public void modifyShiftPalletsCom_success() {

		ShiftPaletteCom shiftPalletsCom = new ShiftPaletteCom(
					"000000000000-0001", 
					1, 
					new ShiftPalette(
							new ShiftPaletteDisplayInfor(
									new ShiftPaletteName("shpaName"), 
									NotUseAtr.USE, 
									new ShiftRemarks("shRemar")),
							Arrays.asList( 
									new ShiftPaletteCombinations(
											2, 
											new ShiftCombinationName("combiName"),
											Arrays.asList(new Combinations(
													1, 
													new ShiftMasterCode("0000001")))))));

		ShiftPalette shiftPallet = new ShiftPalette(
				new ShiftPaletteDisplayInfor(
						new ShiftPaletteName("shpaName"), 
						NotUseAtr.USE, 
						new ShiftRemarks("shRemar")),
				Arrays.asList(
						new ShiftPaletteCombinations(
								7, 
								new ShiftCombinationName("name07"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPaletteCombinations(
								4, 
								new ShiftCombinationName("name04"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPaletteCombinations(
								5, 
								new ShiftCombinationName("name05"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001"))))));
		
		shiftPalletsCom.modifyShiftPallets(shiftPallet);
		
		assertThat(shiftPalletsCom.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(4,"name04") , tuple(5,"name05"), tuple(7,"name07"));
	}
	
	@Test
	public void testReproduct() {

		ShiftPaletteCom shiftPalletsCom = new ShiftPaletteCom(
					"000000000000-0001", 
					1, 
					new ShiftPalette(
							new ShiftPaletteDisplayInfor(
									new ShiftPaletteName("shpaName"), 
									NotUseAtr.USE, 
									new ShiftRemarks("shRemar")),
							Arrays.asList( 
									new ShiftPaletteCombinations(
											2, 
											new ShiftCombinationName("combiName"),
											Arrays.asList(new Combinations(
													1, 
													new ShiftMasterCode("0000001")))))));
		ShiftPaletteName shiftPalletName = new ShiftPaletteName("shiftPalletNameNew");
		ShiftPaletteCom shiftPalletsComNew = shiftPalletsCom.reproduct(2, shiftPalletName);
		
		assertThat(shiftPalletsComNew.getShiftPallet().getDisplayInfor().getShiftPalletName().v()).isEqualTo(shiftPalletName.v());
		assertThat(shiftPalletsComNew.getPage()).isEqualTo(2);
		assertThat(shiftPalletsComNew.getCompanyId()).isEqualTo("000000000000-0001");
		
	}



	@Test
	public void getters() {
		ShiftPaletteCom target = ShiftPalletsComHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}

}
