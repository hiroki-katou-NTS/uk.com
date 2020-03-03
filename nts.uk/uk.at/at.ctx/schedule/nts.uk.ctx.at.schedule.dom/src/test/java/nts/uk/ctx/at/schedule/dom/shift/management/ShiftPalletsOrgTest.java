package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletDisplayInfoHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsOrgHelper;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.shr.com.enumcommon.NotUseAtr;
/**
 * 
 * @author sonnh1
 *
 */
public class ShiftPalletsOrgTest {

	@Test
	public void create_shiftPalletsOrg_0page_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			new ShiftPalletsOrg(
					new TargetOrgIdenInfor(
							TargetOrganizationUnit.WORKPLACE, //dummy
							"e34d86c4-1e32-463e-b86c-68551e0bbf18", //dummy
							"e6fea7af-0365-4332-9943-e2c17f65bea6"), //dummy
					0, 
					new ShiftPallet(
							new ShiftPalletDisplayInfor(
									new ShiftPalletName("shpaName"), //dummy
									NotUseAtr.USE, //dummy
									new ShiftRemarks("shRemar")), //dummy
							Arrays.asList(
									new ShiftPalletCombinations(
											1, //dummy
											new ShiftCombinationName("shComName1"), //dummy
											Arrays.asList(new Combinations(
													1, //dummy
													new ShiftPalletCode("0000001"))))))); //dummy
		});
	}
	
	@Test
	public void create_shiftPalletsOrg_11pages_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			new ShiftPalletsOrg(
					new TargetOrgIdenInfor(
							TargetOrganizationUnit.WORKPLACE, //dummy
							"e34d86c4-1e32-463e-b86c-68551e0bbf18", //dummy
							"e6fea7af-0365-4332-9943-e2c17f65bea6"), //dummy
					11, 
					new ShiftPallet(
							new ShiftPalletDisplayInfor(
									new ShiftPalletName("shpaName"), //dummy
									NotUseAtr.USE, //dummy
									new ShiftRemarks("shRemar")), //dummy
							Arrays.asList(
									new ShiftPalletCombinations(
											1, //dummy
											new ShiftCombinationName("shComName1"), //dummy
											Arrays.asList(new Combinations(
													1, //dummy
													new ShiftPalletCode("0000001"))))))); //dummy
		});
	}
	
	@Test
	public void create_shiftPalletsOrg_sort() {

		ShiftPalletsOrg target = new ShiftPalletsOrg(
					new TargetOrgIdenInfor(
							TargetOrganizationUnit.WORKPLACE, //dummy
							"e34d86c4-1e32-463e-b86c-68551e0bbf18", //dummy
							"e6fea7af-0365-4332-9943-e2c17f65bea6"), //dummy
					1, //dummy
					new ShiftPallet(
							PalletDisplayInfoHelper.DUMMY,
							Arrays.asList(
								new ShiftPalletCombinations(
										3, 
										new ShiftCombinationName("combiNam3"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftPalletCode("0000001")))), // dummy
								new ShiftPalletCombinations(
										5, 
										new ShiftCombinationName("combiNam5"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftPalletCode("0000001")))), // dummy
								new ShiftPalletCombinations(
										1, 
										new ShiftCombinationName("combiNam1"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftPalletCode("0000001"))))))); // dummy
		
		assertThat(target.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(1,"combiNam1") , tuple(3, "combiNam3"), tuple(5,"combiNam5"));
	}
	
	@Test
	public void create_shiftPalletsOrg_1page__success() {

		ShiftPalletsOrg target = new ShiftPalletsOrg(
				new TargetOrgIdenInfor(
						TargetOrganizationUnit.WORKPLACE, 
						"e34d86c4-1e32-463e-b86c-68551e0bbf18", 
						"e6fea7af-0365-4332-9943-e2c17f65bea6"), 
				1, 
				new ShiftPallet(
						new ShiftPalletDisplayInfor(
								new ShiftPalletName("shpaName"), 
								NotUseAtr.USE, 
								new ShiftRemarks("shRemar")), 
						Arrays.asList( 
								new ShiftPalletCombinations(
										2, 
										new ShiftCombinationName("combiName"),
										Arrays.asList(new Combinations(
												1, 
												new ShiftPalletCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getTargeOrg().getUnit().value,
					d->d.getTargeOrg().getWorkplaceId().get(),
					d->d.getTargeOrg().getWorkplaceGroupId().get(),
					d->d.getPage(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					d->d.getShiftPallet().getDisplayInfor().getRemarks().v(),
					d->d.getShiftPallet().getCombinations().get(0).getPositionNumber(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinationName().v(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getShiftCode().v())
			.containsExactly(
					0,
					"e34d86c4-1e32-463e-b86c-68551e0bbf18",
					"e6fea7af-0365-4332-9943-e2c17f65bea6",
					1,
					"shpaName",
					1,
					"shRemar",
					2,
					"combiName",
					1,
					"0000001");
	} 
	
	@Test
	public void create_shiftPalletsOrg_10pages__success() {

		ShiftPalletsOrg target = new ShiftPalletsOrg(
				new TargetOrgIdenInfor(
						TargetOrganizationUnit.WORKPLACE, 
						"e34d86c4-1e32-463e-b86c-68551e0bbf18", 
						"e6fea7af-0365-4332-9943-e2c17f65bea6"), 
				10, 
				new ShiftPallet(
						new ShiftPalletDisplayInfor(
								new ShiftPalletName("shpaName"), 
								NotUseAtr.USE, 
								new ShiftRemarks("shRemar")), 
						Arrays.asList( 
								new ShiftPalletCombinations(
										2, 
										new ShiftCombinationName("combiName"),
										Arrays.asList(new Combinations(
												1, 
												new ShiftPalletCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getTargeOrg().getUnit().value,
					d->d.getTargeOrg().getWorkplaceId().get(),
					d->d.getTargeOrg().getWorkplaceGroupId().get(),
					d->d.getPage(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletName().v(),
					d->d.getShiftPallet().getDisplayInfor().getShiftPalletAtr().value,
					d->d.getShiftPallet().getDisplayInfor().getRemarks().v(),
					d->d.getShiftPallet().getCombinations().get(0).getPositionNumber(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinationName().v(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getOrder(),
					d->d.getShiftPallet().getCombinations().get(0).getCombinations().get(0).getShiftCode().v())
			.containsExactly(
					0,
					"e34d86c4-1e32-463e-b86c-68551e0bbf18",
					"e6fea7af-0365-4332-9943-e2c17f65bea6",
					10,
					"shpaName",
					1,
					"shRemar",
					2,
					"combiName",
					1,
					"0000001");
	}
	
	@Test
	public void modifyShiftPalletsOrg_success() {

		ShiftPalletsOrg shiftPalletsOrg = new ShiftPalletsOrg(
					new TargetOrgIdenInfor(
							TargetOrganizationUnit.WORKPLACE, //dummy
							"e34d86c4-1e32-463e-b86c-68551e0bbf18", //dummy
							"e6fea7af-0365-4332-9943-e2c17f65bea6"), //dummy
					1, //dummy
					new ShiftPallet(
							new ShiftPalletDisplayInfor(
									new ShiftPalletName("shpaName"), //dummy
									NotUseAtr.USE, //dummy
									new ShiftRemarks("shRemar")), //dummy
							Arrays.asList( 
									new ShiftPalletCombinations(
											2, 
											new ShiftCombinationName("combiName"),
											Arrays.asList(new Combinations(
													1, 
													new ShiftPalletCode("0000001")))))));

		ShiftPallet shiftPallet = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), //dummy
						NotUseAtr.USE, //dummy
						new ShiftRemarks("shRemar")),//dummy
				Arrays.asList(
						new ShiftPalletCombinations(
								7, 
								new ShiftCombinationName("name07"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftPalletCode("0000001")))),
						new ShiftPalletCombinations(
								4, 
								new ShiftCombinationName("name04"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftPalletCode("0000001")))),
						new ShiftPalletCombinations(
								5, 
								new ShiftCombinationName("name05"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftPalletCode("0000001"))))));

		shiftPalletsOrg.modifyShiftPallets(shiftPallet);

		assertThat(shiftPalletsOrg.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(4,"name04") , tuple(5,"name05"), tuple(7,"name07"));
	}

	@Test
	public void getters() {
		ShiftPalletsOrg target = ShiftPalletsOrgHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}
}
