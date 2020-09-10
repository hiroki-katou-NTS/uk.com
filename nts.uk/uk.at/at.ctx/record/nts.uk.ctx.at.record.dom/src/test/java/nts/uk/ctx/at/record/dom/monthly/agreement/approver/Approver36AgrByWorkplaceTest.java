package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *					test01		test02		test03		test04		test05
 *	companyId		"cid"		"cid"		"cid"		"cid"		null
 *	workplaceId		"wid"		"wid"		"wid"		"wid"		null
 *	period			01-30/9		01-30/9		01-30/9		01-30/9		null
 *	approverIds		size=5		size=0		size=6		size=1		size=1
 *	confirmerIds	size=5		size=1		size=1		size=6		size=1
 * 	Expected		normal		Msg_1790	Msg_1791	Msg_1792	normal
 */
public class Approver36AgrByWorkplaceTest {

	@Test
	public void getters() {
		val domain = Helper.createApprover36AgrByWorkplace();
		NtsAssert.invokeGetters(domain);
	}

	@Test
	public void test01(){
		assertThat(new Approver36AgrByWorkplace(
				Helper.cid,
				Helper.workplaceId,
				Helper.period,
				Helper.createApproverList(1),
				Helper.createConfirmerList(1)
		));
	}

	@Test
	public void test02(){
		NtsAssert.businessException("Msg_1790", () -> new Approver36AgrByWorkplace(
				Helper.cid,
				Helper.workplaceId,
				Helper.period,
				Helper.createApproverList(0),
				Helper.createConfirmerList(1)
		));
	}

	@Test
	public void test03(){
		NtsAssert.businessException("Msg_1791", () -> new Approver36AgrByWorkplace(
				Helper.cid,
				Helper.workplaceId,
				Helper.period,
				Helper.createApproverList(6),
				Helper.createConfirmerList(1)
		));
	}

	@Test
	public void test04(){
		NtsAssert.businessException("Msg_1792", () -> new Approver36AgrByWorkplace(
				Helper.cid,
				Helper.workplaceId,
				Helper.period,
				Helper.createApproverList(1),
				Helper.createConfirmerList(6)
		));
	}

	@Test
	public void test05(){
		assertThat(new Approver36AgrByWorkplace(
				null,
				null,
				null,
				Helper.createApproverList(1),
				Helper.createConfirmerList(1)
		));
	}
}
