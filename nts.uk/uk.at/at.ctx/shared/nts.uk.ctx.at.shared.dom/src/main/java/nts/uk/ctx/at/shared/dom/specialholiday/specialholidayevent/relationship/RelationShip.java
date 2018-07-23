package nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.relationship;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipCode;
import nts.uk.ctx.at.shared.dom.relationship.primitives.RelationshipName;

@NoArgsConstructor
@AllArgsConstructor
@Data
// 続柄
public class RelationShip extends AggregateRoot {

	/* 会社ID */
	public String companyId;

	/* 続柄コード */
	public RelationshipCode relationshipCd;
	
	/* 名称 */
	public RelationshipName name;

	/* 3親等以内とする */
	public ThreeParentOrLess threeParentOrLess;
}
