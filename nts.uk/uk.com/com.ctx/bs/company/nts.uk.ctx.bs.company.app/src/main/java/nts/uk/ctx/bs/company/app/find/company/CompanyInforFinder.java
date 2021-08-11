package nts.uk.ctx.bs.company.app.find.company;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
/**
 * find company information
 * @author yennth
 *
 */
import javax.inject.Inject;

import nts.uk.ctx.bs.company.dom.company.AddInfor;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class CompanyInforFinder {
	@Inject 
	private CompanyRepository comRep;
	
	/**
	 * convert from address domain to dto
	 * @param add
	 * @return
	 * author Hoang Yen
	 */
	private AddInforDto fromDomainAdd(AddInfor add){
		AddInforDto adddto = new AddInforDto();
		if(add == null){
			adddto = null;
			return adddto;
		}
		adddto.setCompanyId(add.getCompanyId());
		adddto.setFaxNum(add.getFaxNum().v());
		adddto.setAdd_1(add.getAdd_1().v());
		adddto.setAdd_2(add.getAdd_2().v());
		adddto.setAddKana_1(add.getAddKana_1().v());
		adddto.setAddKana_2(add.getAddKana_2().v());
		adddto.setPostCd(add.getPostCd().v());
		adddto.setPhoneNum(add.getPhoneNum().v());
		return adddto;
	}
	
	/**
	 * find all company infor
	 * @return
	 * author: Hoang Yen
	 */
	public List<CompanyInforDto> findAll(){
		String contractCd = AppContexts.user().contractCode();
		return this.comRep.findAll(contractCd)
							.stream()
							.map(x -> {
								return new CompanyInforDto( x.getCompanyCode().v(), x.getCompanyName().v(),
															x.getCompanyId(), x.getStartMonth().value,
															x.getIsAbolition().value, x.getRepname() != null ? x.getRepname().v() : null,
															x.getRepjob() != null ? x.getRepjob().v() : null, x.getComNameKana().v(),
															x.getShortComName().v(), contractCd, 
															x.getTaxNo() != null ? x.getTaxNo().v() : null, fromDomainAdd(x.getAddInfor()));
							}).collect(Collectors.toList());
	}
	
	/**
	 * Find company information by company id
	 * @param companyId company id
	 * @return company information
	 */
	public CompanyInforDto find(String companyId){
		return this.comRep.find(companyId).map(x -> {
			return new CompanyInforDto( x.getCompanyCode().v(), x.getCompanyName().v(),
					x.getCompanyId(), x.getStartMonth().value,
					x.getIsAbolition().value, x.getRepname() != null ? x.getRepname().v() : null,
					x.getRepjob() != null ? x.getRepjob().v() : null, 
					x.getComNameKana() != null? x.getComNameKana().v():null,
					x.getShortComName() != null ? x.getShortComName().v() : null, 
					x.getContractCd().v(), 
					x.getTaxNo() != null ? x.getTaxNo().v() : null, 
					fromDomainAdd(x.getAddInfor()));
		}).orElse(null);
	}
}
