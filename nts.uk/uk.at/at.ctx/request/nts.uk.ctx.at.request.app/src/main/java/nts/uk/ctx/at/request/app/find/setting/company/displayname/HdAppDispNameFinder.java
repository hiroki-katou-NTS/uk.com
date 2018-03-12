package nts.uk.ctx.at.request.app.find.setting.company.displayname;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;

@Stateless
public class HdAppDispNameFinder {
	@Inject
	private HdAppDispNameRepository hdAppRep;
	/**
	 * get hd app disp name by company id
	 * @return
	 * @author yennth
	 */
	public List<HdAppDispNameDto> findByCid(){
		return hdAppRep.getAllHdApp().stream()
										.map(c -> HdAppDispNameDto.convertToDto(c))
										.collect(Collectors.toList());
	}
	/**
	 * get hd app disp name by company id and hd app type
	 * @param hdType
	 * @return
	 * @author yennth
	 */
	public HdAppDispNameDto findItem(int hdType){
		Optional<HdAppDispName> hdApp = hdAppRep.getHdApp(hdType);
		if(hdApp.isPresent()){
			return HdAppDispNameDto.convertToDto(hdApp.get());
		}
		return null;
	}
}
