package nts.uk.ctx.at.schedule.dom.shift.management;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.Assert.assertSame;

import java.util.Arrays;
import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsComHelper.PalletHelper.PalletDisplayInfoHelper;
import nts.uk.ctx.at.schedule.dom.shift.management.ShiftPalletsHelper.ShiftPalletsOrgHelper;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterCode;
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
			ShiftPalletsOrg.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
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
													new ShiftMasterCode("0000001"))))))); //dummy
		});
	}
	
	@Test
	public void create_shiftPalletsOrg_11pages_fail() {

		NtsAssert.businessException("Msg_1615", () -> {
			ShiftPalletsOrg.create(
					TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
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
													new ShiftMasterCode("0000001"))))))); //dummy
		});
	}
	
	@Test
	public void create_shiftPalletsOrg_sort() {

		ShiftPalletsOrg target = ShiftPalletsOrg.create(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
					1, //dummy
					new ShiftPallet(
							PalletDisplayInfoHelper.DUMMY,
							Arrays.asList(
								new ShiftPalletCombinations(
										3, 
										new ShiftCombinationName("combiNa30"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000010")))), // dummy
								new ShiftPalletCombinations(
										5, 
										new ShiftCombinationName("combiNa05"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000032")))), // dummy
								new ShiftPalletCombinations(
										1, 
										new ShiftCombinationName("combiNa10"), // dummy
										Arrays.asList(new Combinations(
												1, // dummy
												new ShiftMasterCode("0000011"))))))); // dummy
		
		assertThat(target.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(1,"combiNa10") , tuple(3, "combiNa30"), tuple(5,"combiNa05"));
	}
	
	@Test
	public void create_shiftPalletsOrg_1page__success() {

		ShiftPalletsOrg target = new ShiftPalletsOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
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
												new ShiftMasterCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getTargeOrg().getUnit().value,
					d->d.getTargeOrg().getWorkplaceId().get(),
					d->d.getTargeOrg().getWorkplaceGroupId(),
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
					Optional.empty(),
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
	public void create_shiftPalletsOrg_10pages__success() {

		ShiftPalletsOrg target = new ShiftPalletsOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
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
												new ShiftMasterCode("0000001")))))));
		
		assertThat(target)
			.extracting(
					d->d.getTargeOrg().getUnit().value,
					d->d.getTargeOrg().getWorkplaceId().get(),
					d->d.getTargeOrg().getWorkplaceGroupId(),
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
					Optional.empty(),
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
	public void modifyShiftPalletsOrg_success() {

		ShiftPalletsOrg shiftPalletsOrg = new ShiftPalletsOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("e34d86c4-1e32-463e-b86c-68551e0bbf18"),
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
													new ShiftMasterCode("0000001")))))));

		ShiftPallet shiftPallet = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), //dummy
						NotUseAtr.USE, //dummy
						new ShiftRemarks("shRemar")),//dummy
				Arrays.asList(
						new ShiftPalletCombinations(
								7, 
								new ShiftCombinationName("name71"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPalletCombinations(
								4, 
								new ShiftCombinationName("name40"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPalletCombinations(
								5, 
								new ShiftCombinationName("name05"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001"))))));

		shiftPalletsOrg.modifyShiftPallets(shiftPallet);

		assertThat(shiftPalletsOrg.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(4,"name40") , tuple(5,"name05"), tuple(7,"name71"));
	}
	
	@Test
	public void modifyShiftPalletsOrg_success_useworkplacegroup() {

		ShiftPalletsOrg shiftPalletsOrg = new ShiftPalletsOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("h35d96c4-1e32-8756-b86c-68551e0bbf18"),
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
													new ShiftMasterCode("0000001")))))));

		ShiftPallet shiftPallet = new ShiftPallet(
				new ShiftPalletDisplayInfor(
						new ShiftPalletName("shpaName"), //dummy
						NotUseAtr.USE, //dummy
						new ShiftRemarks("shRemar")),//dummy
				Arrays.asList(
						new ShiftPalletCombinations(
								7, 
								new ShiftCombinationName("name71"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPalletCombinations(
								4, 
								new ShiftCombinationName("name40"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001")))),
						new ShiftPalletCombinations(
								5, 
								new ShiftCombinationName("name05"), 
								Arrays.asList(new Combinations(
										1, 
										new ShiftMasterCode("0000001"))))));

		shiftPalletsOrg.modifyShiftPallets(shiftPallet);

		assertThat(shiftPalletsOrg.getShiftPallet().getCombinations())
			.extracting(d -> d.getPositionNumber(), d->d.getCombinationName().v())
			.containsExactly(tuple(4,"name40") , tuple(5,"name05"), tuple(7,"name71"));
	}
	
	@Test
	public void testReproductUseWorkplaceGroup() {

		ShiftPalletsOrg shiftPalletsOrg = new ShiftPalletsOrg(
				new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE_GROUP,Optional.empty(), Optional.of("e34d86c4-1e32-463e-b86c-68551e0bbf18")),
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
												new ShiftMasterCode("0000001")))))));
		
		ShiftPalletName shiftPalletName = new ShiftPalletName("shiftPalletNameNew");
		ShiftPalletsOrg shiftPalletsOrgNew = shiftPalletsOrg.reproduct(2, shiftPalletName);
		
		assertThat(shiftPalletsOrgNew.getShiftPallet().getDisplayInfor().getShiftPalletName().v()).isEqualTo(shiftPalletName.v());
		assertThat(shiftPalletsOrgNew.getPage()).isEqualTo(2);
		assertThat(shiftPalletsOrgNew.getTargeOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE_GROUP);
		assertThat(shiftPalletsOrgNew.getTargeOrg().getWorkplaceGroupId().get()).isEqualTo("e34d86c4-1e32-463e-b86c-68551e0bbf18");	
		assertThat(shiftPalletsOrgNew.getTargeOrg().getWorkplaceId().isPresent()).isFalse();	
	}
	
	@Test
	public void testReproductUseWorkplace() {

		ShiftPalletsOrg shiftPalletsOrg = new ShiftPalletsOrg(
				new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,Optional.of("e34d86c4-1e32-463e-b86c-68551e0bbf18"),Optional.empty()),
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
												new ShiftMasterCode("0000001")))))));
		
		ShiftPalletName shiftPalletName = new ShiftPalletName("shiftPalletNameNew");
		ShiftPalletsOrg shiftPalletsOrgNew = shiftPalletsOrg.reproduct(2, shiftPalletName);
		
		assertThat(shiftPalletsOrgNew.getShiftPallet().getDisplayInfor().getShiftPalletName().v()).isEqualTo(shiftPalletName.v());
		assertThat(shiftPalletsOrgNew.getPage()).isEqualTo(2);
		assertThat(shiftPalletsOrgNew.getTargeOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(shiftPalletsOrgNew.getTargeOrg().getWorkplaceId().get()).isEqualTo("e34d86c4-1e32-463e-b86c-68551e0bbf18");	
		assertThat(shiftPalletsOrgNew.getTargeOrg().getWorkplaceGroupId().isPresent()).isFalse();	
	}

	@Test
	public void getters() {
		ShiftPalletsOrg target = ShiftPalletsOrgHelper.DUMMY;
		NtsAssert.invokeGetters(target);
	}
}
