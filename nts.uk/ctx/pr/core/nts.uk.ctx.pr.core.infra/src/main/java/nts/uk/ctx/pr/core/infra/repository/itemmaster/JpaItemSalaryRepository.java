package nts.uk.ctx.pr.core.infra.repository.itemmaster;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalary;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.ctx.pr.core.infra.entity.itemmaster.QcamtItemSalary;


@RequestScoped
public class JpaItemSalaryRepository extends JpaRepository implements ItemSalaryRespository {
	private final String SEL = "SELECT c FROM QcamtItemSalary c";
	private final String SEL_1 = SEL
			+ " WHERE c.qcamtItemSalaryPK.ccd = :companyCode AND c.qcamtItemSalaryPK.itemCd = :itemCd";

	@Override
	public Optional<ItemSalary> find(String companyCode, String itemCode) {
		return this.queryProxy().query(SEL_1, QcamtItemSalary.class).setParameter("companyCode", companyCode)
				.setParameter("itemCd", itemCode).getSingle(c -> toDomain(c));
	}

	private ItemSalary toDomain(QcamtItemSalary entity) {

		val domain = ItemSalary.createFromJavaType(entity.qcamtItemSalaryPK.ccd, entity.qcamtItemSalaryPK.itemCd,
				entity.taxAtr, entity.socialInsAtr, entity.laborInsAtr, entity.fixPayAtr, entity.applyForAllEmpFlg,
				entity.applyForMonthlyPayEmp, entity.applyForDaymonthlyPayEmp, entity.applyForDaylyPayEmp,
				entity.applyForHourlyPayEmp, entity.avePayAtr, entity.errRangeLowAtr, entity.errRangeLow,
				entity.errRangeHighAtr, entity.errRangeHigh, entity.alRangeLowAtr, entity.alRangeLow,
				entity.alRangeHighAtr, entity.alRangeHigh, entity.memo, entity.limitMnyAtr, entity.limitMnyRefItemCd,
				entity.limitMny);
		// TODO Auto-generated method stub
		return domain;
	}
}
