package nts.uk.ctx.bs.employee.infra.repository.employee.contact;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.employeeinfo.contact.EmployeeInfoContact;
import nts.uk.ctx.bs.employee.dom.employeeinfo.contact.EmployeeInfoContactRepository;
import nts.uk.ctx.bs.employee.infra.entity.employee.contact.BsymtEmpInfoContact;
import nts.uk.ctx.bs.person.infra.entity.person.info.BpsmtPersonPk;

@Stateless
public class EmployeeInfoContactRepositoryImp extends JpaRepository implements EmployeeInfoContactRepository {

	@Override
	public Optional<EmployeeInfoContact> findByEmpId(String sId) {
		
		Optional<BsymtEmpInfoContact> empContact = this.queryProxy().find(new BpsmtPersonPk(sId), BsymtEmpInfoContact.class);
		if (empContact.isPresent()) {
			return Optional.of(toDomain(empContact.get()));
		} else {
			return Optional.empty();
		}
	}
	
	private static EmployeeInfoContact toDomain(BsymtEmpInfoContact entity) {
		

		EmployeeInfoContact domain = EmployeeInfoContact.createFromJavaType(
				entity.bsymtEmpInfoContactPK.sid, 
				entity.mailAdress,
				entity.seatDialIn,
				entity.seatExtensionNo, 
				entity.phoneMailAddress, 
				entity.cellPhoneNo);
		return domain;
	}

}
