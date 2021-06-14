package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.apache.commons.lang3.BooleanUtils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountByNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForEmployment;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountList;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountValue;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.EmploymentCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_EMP")
public class KagmtCriterionMoneyEmp extends ContractUkJpaEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	public KagmtCriterionMoneyEmpPk pk;

	@Column(name = "AMOUNT_OF_MONEY")
	public int amountOfMoney;

	@Override
	protected Object getKey() {

		return this.pk;
	}

	public static CriterionAmountForEmployment toDomain(List<KagmtCriterionMoneyEmp> entities) {

		if (CollectionUtil.isEmpty(entities)) {

			return null;
		}
		Map<Boolean, List<KagmtCriterionMoneyEmp>> partitioned = entities.stream()
				.collect(Collectors.partitioningBy(x -> BooleanUtils.toBoolean(x.pk.yearMonthAtr)));
		CriterionAmountList yearly = new CriterionAmountList(partitioned.get(BooleanUtils.toBoolean(KagmtCriterionMoneyCmp.ISYEAR)).stream()
				.map(x -> new CriterionAmountByNo(new CriterionAmountNo(x.pk.frameNo),
						new CriterionAmountValue(x.amountOfMoney)))
				.collect(Collectors.toList()));

		CriterionAmountList monthly = new CriterionAmountList(partitioned.get(BooleanUtils.toBoolean(KagmtCriterionMoneyCmp.ISMONTH)).stream()
				.map(x -> new CriterionAmountByNo(new CriterionAmountNo(x.pk.frameNo),
						new CriterionAmountValue(x.amountOfMoney)))
				.collect(Collectors.toList()));

		return new CriterionAmountForEmployment(new EmploymentCode(entities.get(0).pk.employmentCd), new CriterionAmount(yearly, monthly));
	}
	
	public static List<KagmtCriterionMoneyEmp> toEntity(CriterionAmountForEmployment domain) {
			
			String companyId = AppContexts.user().companyId();
			List<KagmtCriterionMoneyEmp> entities = new ArrayList<KagmtCriterionMoneyEmp>();
			CriterionAmount criterionAmount = domain.getCriterionAmount();
			List<CriterionAmountByNo> years = criterionAmount.getYearly().getList();
			years.forEach(x -> {
				KagmtCriterionMoneyEmp entity = new KagmtCriterionMoneyEmp(
						new KagmtCriterionMoneyEmpPk(companyId, domain.getEmploymentCode().v(), KagmtCriterionMoneyCmp.ISYEAR, x.getFrameNo().v()),
						x.getAmount().v());
				entities.add(entity);
			});
			List<CriterionAmountByNo> months = criterionAmount.getMonthly().getList();
			months.forEach(x -> {
				KagmtCriterionMoneyEmp entity = new KagmtCriterionMoneyEmp(
						new KagmtCriterionMoneyEmpPk(companyId, domain.getEmploymentCode().v(), KagmtCriterionMoneyCmp.ISMONTH, x.getFrameNo().v()),
						x.getAmount().v());
				entities.add(entity);
			});
			
			return entities;
		}

}
