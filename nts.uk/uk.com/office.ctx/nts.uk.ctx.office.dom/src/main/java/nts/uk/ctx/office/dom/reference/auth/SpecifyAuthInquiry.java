package nts.uk.ctx.office.dom.reference.auth;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

/*
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.オフィス支援.在席照会.在席照会.在席照会で参照できる権限の指定
 */
@Builder
@Getter
public class SpecifyAuthInquiry extends AggregateRoot {

	// 会社ID
	private String cid;

	// 就業ロールID
	private String employmentRoleId;

	// 見られる職位ID
	private List<String> positionIdSeen;
}
