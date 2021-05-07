package nts.uk.ctx.at.aggregation.infra.entity.schedulecounter.criterion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.CriterionAmountNo;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmount;
import nts.uk.ctx.at.aggregation.dom.schedulecounter.criterion.HandlingOfCriterionAmountByNo;
import nts.uk.shr.com.color.ColorCode;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.infra.data.entity.ContractUkJpaEntity;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "KAGMT_CRITERION_MONEY_FRAME")
public class KagmtCriterionMoneyFrame extends ContractUkJpaEntity implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	public KagmtCriterionMoneyFramePk pk;
	
	
	@Column(name = "COLOR", nullable = false)
    public String color;

	@Override
	protected Object getKey() {
		
		return this.pk;
	}
	
	public static HandlingOfCriterionAmount toDomain(List<KagmtCriterionMoneyFrame> entity) {
		
		if (CollectionUtil.isEmpty(entity)) {
			
			return null;
		}
		
		List<HandlingOfCriterionAmountByNo> list = new ArrayList<HandlingOfCriterionAmountByNo>();
		entity.forEach(x -> {
			list.add(new HandlingOfCriterionAmountByNo(new CriterionAmountNo(x.pk.frameNo), new ColorCode(x.color)));
		});
		
		return new HandlingOfCriterionAmount(list);
	}
	
	public static List<KagmtCriterionMoneyFrame> toEntity(HandlingOfCriterionAmount domain) {
		if (CollectionUtil.isEmpty(domain.getList())) {
			
			return Collections.emptyList();
		}
		String cid = AppContexts.user().companyId();
		
		return domain.getList()
					 .stream()
					 .map(x -> new KagmtCriterionMoneyFrame(new KagmtCriterionMoneyFramePk(cid, x.getFrameNo().v()), x.getBackgroundColor().v()))
					 .collect(Collectors.toList());
	}

}
