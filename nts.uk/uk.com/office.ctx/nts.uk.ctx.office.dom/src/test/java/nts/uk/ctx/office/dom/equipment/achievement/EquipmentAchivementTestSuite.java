package nts.uk.ctx.office.dom.equipment.achievement;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nts.uk.ctx.office.dom.equipment.achievement.domain.EquipmentUsageRecordItemSettingTest;
import nts.uk.ctx.office.dom.equipment.achievement.domain.ItemInputControlTest;
import nts.uk.ctx.office.dom.equipment.achievement.domainservice.RegisterEquipmentItemSettingMasterTest;

@RunWith(Suite.class)
@SuiteClasses({
	ItemInputControlTest.class,
	EquipmentUsageRecordItemSettingTest.class,
	RegisterEquipmentItemSettingMasterTest.class,
})
public class EquipmentAchivementTestSuite {

}