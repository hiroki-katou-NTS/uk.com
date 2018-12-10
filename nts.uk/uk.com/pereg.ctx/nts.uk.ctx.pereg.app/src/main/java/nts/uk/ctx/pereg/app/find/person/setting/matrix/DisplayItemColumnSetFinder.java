/**
 * 
 */
package nts.uk.ctx.pereg.app.find.person.setting.matrix;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pereg.dom.person.info.item.PerInfoItemDefRepositoty;

/**
 * @author hieult
 *
 */
@Stateless
public class DisplayItemColumnSetFinder {

	@Inject
	private PerInfoItemDefRepositoty perInfoItemDefRepositoty;
	
	public Optional<StatupProcessDto> findSetting(String pInfoCategoryID, int isAbolition , String parentCodeItem) {
		/*
		 * ドメインモデル「個人情報項目定義」を取得する (Lấy domain [PersonInfoItemDefinition])
		 */
		//List<PersonInfoItemDefinition> personInfoItemDefinition = 

		/**
		 * ドメインモデル「個人情報項目並び順」を取得する (Lấy domain [PersonInfoItemOrder])
		 **/
		
		return null;
	}
	
}
