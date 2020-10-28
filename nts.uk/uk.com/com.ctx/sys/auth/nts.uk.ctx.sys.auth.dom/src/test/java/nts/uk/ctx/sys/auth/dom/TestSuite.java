package nts.uk.ctx.sys.auth.dom;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nts.uk.ctx.sys.auth.dom.adapter.employee.service.EmployeeServiceTest;
import nts.uk.ctx.sys.auth.dom.anniversary.AnniversaryNoticeTest;
import nts.uk.ctx.sys.auth.dom.anniversary.service.IsTodayHaveNewAnniversaryTest;
import nts.uk.ctx.sys.auth.dom.anniversary.service.SetFlagTest;
import nts.uk.ctx.sys.auth.dom.avatar.UserAvatarTest;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContactTest;
import nts.uk.ctx.sys.auth.dom.personal.contact.PersonalContactTest;

@RunWith(Suite.class)
@SuiteClasses({
	EmployeeServiceTest.class,
	IsTodayHaveNewAnniversaryTest.class,
	SetFlagTest.class,
	AnniversaryNoticeTest.class,
	UserAvatarTest.class,
	EmployeeContactTest.class,
	PersonalContactTest.class
})
public class TestSuite {

}
