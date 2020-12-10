package nts.uk.screen.at.app.ksu003.start.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.DisplayInfoOrganization;
/**
 * 組織の表示情報 Dto
 * @author phongtq
 *
 */
@Value
public class DisplayInfoOrganizationDto {
	/** 呼称 **/
	private String designation;	
	/** コード **/
	private String code;

	/** 名称 **/
	private String name;
	/** 表示名 **/
	private String displayName;
	/** 呼称 **/
	private String genericTerm;
	
	public static DisplayInfoOrganizationDto convert(DisplayInfoOrganization infoOrganization) {
		 return new DisplayInfoOrganizationDto(infoOrganization.getDesignation(), infoOrganization.getCode(), 
					infoOrganization.getName(), infoOrganization.getDisplayName(), infoOrganization.getGenericTerm());
	}
}
