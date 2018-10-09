package nts.uk.ctx.exio.app.find.exo.menu;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class RoleAuthorityDto {
	/**
	 * 担当ロール（リスト）
	 */
	private List<String> inChargeRole;

	/**
	 * 社員ロール（リスト）
	 */
	private List<String> empRole;
}
