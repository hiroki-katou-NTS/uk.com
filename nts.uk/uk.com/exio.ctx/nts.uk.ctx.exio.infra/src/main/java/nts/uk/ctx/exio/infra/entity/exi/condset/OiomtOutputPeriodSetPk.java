package nts.uk.ctx.exio.infra.entity.exi.condset;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OiomtOutputPeriodSetPk {

	// column 会社ID 
	@NonNull
	@Column(name = "CID")
	public String cId;
	
	// column 条件設定コード
	@NonNull
	@Column(name = "CONDITION_SET_CD")
	public String conditionSetCd;
	
}
