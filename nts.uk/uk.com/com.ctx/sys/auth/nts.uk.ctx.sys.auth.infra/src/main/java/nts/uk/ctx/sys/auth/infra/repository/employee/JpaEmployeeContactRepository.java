package nts.uk.ctx.sys.auth.infra.repository.employee;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContact;
import nts.uk.ctx.sys.auth.dom.employee.contact.EmployeeContactRepository;
import nts.uk.ctx.sys.auth.infra.entity.employee.BsymtContactAddrEmp;
import nts.uk.shr.com.context.AppContexts;

import javax.ejb.Stateless;
import java.util.Optional;

@Stateless
public class JpaEmployeeContactRepository extends JpaRepository implements EmployeeContactRepository {

    //select by employee ID
    private static final String SELECT_BY_EMPLOYEE_ID = "SELECT m FROM BsymtContactAddrEmp m WHERE m.bsymtContactAddrEmpPK.employeeId = :employeeId";

    private static BsymtContactAddrEmp toEntity(EmployeeContact domain) {
        BsymtContactAddrEmp entity = new BsymtContactAddrEmp();
        domain.setMemento(entity);
        return entity;
    }

    @Override
    public void insert(EmployeeContact employeeContact) {
        BsymtContactAddrEmp entity = JpaEmployeeContactRepository.toEntity(employeeContact);
        entity.setCompanyId(AppContexts.user().companyId());
        this.commandProxy().insert(entity);
    }

    @Override
    public void update(EmployeeContact employeeContact) {
        BsymtContactAddrEmp entity = JpaEmployeeContactRepository.toEntity(employeeContact);
        Optional<BsymtContactAddrEmp> oldEntity = this.queryProxy().find(entity.getBsymtContactAddrEmpPK(), BsymtContactAddrEmp.class);
        if (oldEntity.isPresent()) {
            BsymtContactAddrEmp updateEntity = oldEntity.get();
            updateEntity.setMailAddress(entity.getMailAddress());
            updateEntity.setIsMailAddressDisplay(entity.getIsMailAddressDisplay());
            updateEntity.setSeatDialIn(entity.getSeatDialIn());
            updateEntity.setIsSeatDialInDisplay(entity.getIsSeatDialInDisplay());
            updateEntity.setSeatExtensionNumber(entity.getSeatExtensionNumber());
            updateEntity.setIsSeatExtensionNumberDisplay(entity.getIsSeatExtensionNumberDisplay());
            updateEntity.setCellPhoneNumber(entity.getCellPhoneNumber());
            updateEntity.setIsCellPhoneNumberDisplay(entity.getIsCellPhoneNumberDisplay());
            updateEntity.setMobileMailAddress(entity.getMobileMailAddress());
            updateEntity.setIsMobileMailAddressDisplay(entity.getIsMobileMailAddressDisplay());
            this.commandProxy().update(updateEntity);
        }

    }

    @Override
    public Optional<EmployeeContact> getByEmployeeId(String employeeId) {
        return this.queryProxy()
                .query(SELECT_BY_EMPLOYEE_ID, BsymtContactAddrEmp.class)
                .setParameter("employeeId", employeeId)
                .getSingle(EmployeeContact::createFromMemento);
    }
}
