package nts.uk.ctx.pereg.app.find.person.setting.checkdata;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.ctx.pereg.dom.person.setting.validatecheck.PerInfoValidChkCtgRepository;


@Stateless
public class PerInfoValidChkCtgFinder {
	@Inject
	private PerInfoValidChkCtgRepository perinforRep;
	/**
	 * get a person infor check valid
	 * @param personInfoCategoryId
	 * @return
	 * @author yennth
	 */
	 public List<PerInfoValidChkCtgDto> getListPerInfoValidByListCtgId(List<String> listCategoryCd, String contracCd){
		 List<PerInfoValidChkCtgDto> perinfoDto = this.perinforRep.getListPerInfoValidByListCtgId(listCategoryCd, contracCd)
				 										.stream()
				 										.map(x -> PerInfoValidChkCtgDto.fromDomain(x))
				 										.collect(Collectors.toList());
		 return perinfoDto;
	 }
	 /**
		 * get a list person infor check valid
		 * @return
		 * @author yennth
		 */
	 public List<PerInfoValidChkCtgDto> getListPerInfoValid(){
		 List<PerInfoValidChkCtgDto> listPerinfoDto = this.perinforRep.getListPerInfoValid().stream()
				 										.map(x-> PerInfoValidChkCtgDto.fromDomain(x)).collect(Collectors.toList());
		 return listPerinfoDto;
	 }
}
