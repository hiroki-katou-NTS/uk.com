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
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountForCompany;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountList;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountValue;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_CMP")
public class KagmtCriterionMoneyCmp extends ContractUkJpaEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 0：年間目安金額
	public static int ISYEAR = 0;
	
	// 1：月間目安金額
	public static int ISMONTH = 1;

	@EmbeddedId
	public KagmtCriterionMoneyCmpPk pk;
	
	@Column(name = "AMOUNT_OF_MONEY", nullable = false)
    public int amountOfMoney;
	
	
	@Override
	protected Object getKey() {
		return this.pk;
	}

	public static CriterionAmountForCompany toDomain(List<KagmtCriterionMoneyCmp> entities) {
		
		if (CollectionUtil.isEmpty(entities)) {
			
			return null;
		}
		Map<Boolean, List<KagmtCriterionMoneyCmp>> partitioned = 
				entities.stream().collect(
			        Collectors.partitioningBy(x -> BooleanUtils.toBoolean(x.pk.yearMonthAtr)));
		CriterionAmountList yearly = new CriterionAmountList(
				partitioned.get(BooleanUtils.toBoolean(ISYEAR))
				   .stream()
				   .map(x -> new CriterionAmountByNo(new CriterionAmountNo(x.pk.frameNo), new CriterionAmountValue(x.amountOfMoney)))
				   .collect(Collectors.toList()));
		
		CriterionAmountList monthly = new CriterionAmountList(
				partitioned.get(BooleanUtils.toBoolean(ISMONTH))
				   .stream()
				   .map(x -> new CriterionAmountByNo(new CriterionAmountNo(x.pk.frameNo), new CriterionAmountValue(x.amountOfMoney)))
				   .collect(Collectors.toList()));
				
		
		return new CriterionAmountForCompany(new CriterionAmount(yearly, monthly));
	}
	
	public static List<KagmtCriterionMoneyCmp> toEntity(CriterionAmountForCompany domain) {
		
		String companyId = AppContexts.user().companyId();
		List<KagmtCriterionMoneyCmp> entities = new ArrayList<KagmtCriterionMoneyCmp>();
		CriterionAmount criterionAmount = domain.getCriterionAmount();
		List<CriterionAmountByNo> years = criterionAmount.getYearly().getList();
		years.forEach(x -> {
			KagmtCriterionMoneyCmp entity = new KagmtCriterionMoneyCmp(
					new KagmtCriterionMoneyCmpPk(companyId, ISYEAR, x.getFrameNo().v()),
					x.getAmount().v());
			entities.add(entity);
		});
		List<CriterionAmountByNo> months = criterionAmount.getMonthly().getList();
		months.forEach(x -> {
			KagmtCriterionMoneyCmp entity = new KagmtCriterionMoneyCmp(
					new KagmtCriterionMoneyCmpPk(companyId, ISMONTH, x.getFrameNo().v()),
					x.getAmount().v());
			entities.add(entity);
		});
		
		return entities;
	}
}
