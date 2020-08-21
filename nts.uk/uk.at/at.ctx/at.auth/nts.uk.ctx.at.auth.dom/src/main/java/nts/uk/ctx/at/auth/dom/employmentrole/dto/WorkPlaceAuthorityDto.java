package nts.uk.ctx.at.auth.dom.employmentrole.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;

@Getter
@AllArgsConstructor
public class WorkPlaceAuthorityDto extends AggregateRoot {
	/**
	 * ロールID
	 */
	private String roleId;
	/**
	 * 会社ID
	 */
	private String companyId;
	/**
	 * NO
	 */
	private int functionNo;
	/**
	 * 利用できる
	 */
	private boolean availability;
	
}
