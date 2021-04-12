package nts.uk.screen.at.app.cmm040.worklocation;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.sys.auth.app.find.company.CompanyDto;

/**
 * 選択するOutput
 * @author tutk
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAndWorkInfoOutput {
	/**
	 * 会社情報 : Map＜会社ID、会社情報＞
	 */
	private List<CompanyDto> listCompany = new ArrayList<>();
	/**
	 * 職場情報: Map＜会社ID、List＜職場ID、職場コード、職場名称、職場示名、職場総称、職場外部コード、階層コード＞
	 */
	private List<CidAndWorkplaceInfoDto> listCidAndWorkplaceInfo = new ArrayList<>();
}
