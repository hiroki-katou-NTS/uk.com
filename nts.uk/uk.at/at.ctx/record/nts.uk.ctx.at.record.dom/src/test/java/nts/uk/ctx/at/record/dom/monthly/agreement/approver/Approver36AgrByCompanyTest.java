package nts.uk.ctx.at.record.dom.monthly.agreement.approver;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author khai.dh
 *
 *					test01		test02		test03		test04
 *	companyId		"cid"		"cid"		"cid"		"cid"
 *	period			01-30/9		01-30/9		01-30/9		01-30/9
 *	approverIds		size=5		size=0		size=6		size=1
 *	confirmerIds	size=5		size=1		size=1		size=6
 * 	Expected		normal		Msg_1790	Msg_1791	Msg_1792
 */
public class Approver36AgrByCompanyTest {

	@Test
	public void getters() {
		val domain = Helper.createApprover36AgrByCompany();
		NtsAssert.invokeGetters(domain);
	}

	@Test
	public void test01(){
		val approverList = Helper.createApproverList(5);
		val confirmerList = Helper.createConfirmerList(5);
		val domain = Approver36AgrByCompany.create(
				Helper.cid,
				Helper.period,
				approverList,
				confirmerList
		);

		assertThat(domain.getCompanyId()).isEqualTo(Helper.cid);
		assertThat(domain.getPeriod()).isEqualTo(Helper.period);
		assertThat(domain.getApproverList()).isEqualTo(approverList);
		assertThat(domain.getConfirmerList()).isEqualTo(confirmerList);
	}

	@Test
	public void test02(){
		NtsAssert.businessException("Msg_1790", () -> Approver36AgrByCompany.create(
				Helper.cid,
				Helper.period,
				Helper.createApproverList(0),
				Helper.createConfirmerList(1)
		));
	}

	@Test
	public void test03(){
		NtsAssert.businessException("Msg_1791", () -> Approver36AgrByCompany.create(
				Helper.cid,
				Helper.period,
				Helper.createApproverList(6),
				Helper.createConfirmerList(1)
		));
	}

	@Test
	public void test04(){
		NtsAssert.businessException("Msg_1792", () -> Approver36AgrByCompany.create(
				Helper.cid,
				Helper.period,
				Helper.createApproverList(1),
				Helper.createConfirmerList(6)
		));
	}
}
