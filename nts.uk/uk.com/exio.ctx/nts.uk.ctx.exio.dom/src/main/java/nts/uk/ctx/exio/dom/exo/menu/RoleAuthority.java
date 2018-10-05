package nts.uk.ctx.exio.dom.exo.menu;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class RoleAuthority {
	/**
	 * 担当ロール（リスト）
	 */
	private List<String> inChargeRole;

	/**
	 * 社員ロール（リスト）
	 */
	private List<String> empRole;
}
