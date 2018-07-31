/**
 * 
 */
package nts.uk.ctx.pereg.app.find.layoutdef;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsDto;
import nts.uk.ctx.pereg.app.find.layoutdef.classification.LayoutPersonInfoClsFinder;
import nts.uk.ctx.pereg.dom.person.layout.INewLayoutReposotory;
import nts.uk.ctx.pereg.dom.person.layout.NewLayout;

/**
 * @author laitv
 *
 */
@Stateless
public class NewLayoutFinder {

	@Inject
	private INewLayoutReposotory repo;

	@Inject
	private LayoutPersonInfoClsFinder clsFinder;
	
	
	

	public Boolean checkLayoutExist() {
		Optional<NewLayout> layout = repo.getLayout();
		return layout.isPresent();
	}

	public Optional<NewLayoutDto> getLayout() {
		return repo.getLayout().map(m -> {
			// Get list Classification Item by layoutID
			List<LayoutPersonInfoClsDto> listItemCls = this.clsFinder.getListClsDto(m.getLayoutID());

			return NewLayoutDto.fromDomain(m, listItemCls);
		});
	}
	public Optional<NewLayoutDto> findLayout() {
		//ドメインモデル「新規レイアウト」を取得する
		//(Lấy Domain Model 「新規レイアウト」)
		Optional<NewLayout> newLayout = repo.getLayout();
		if(!newLayout.isPresent()){
			throw new  BusinessException("Msg_334");
		}else{
			
		}
			
		return null;
		
	}
	
}
