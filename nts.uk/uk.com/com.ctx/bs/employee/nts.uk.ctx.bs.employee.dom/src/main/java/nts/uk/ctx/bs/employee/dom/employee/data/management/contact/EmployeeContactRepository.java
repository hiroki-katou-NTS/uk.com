package nts.uk.ctx.bs.employee.dom.employee.data.management.contact;

import java.util.Optional;

/**
 * Repository 社員連絡先
 */
public interface EmployeeContactRepository {
    /**
     * Add new EmployeeContact
     *
     * @param employeeContact
     */
    void insert(EmployeeContact employeeContact);

    /**
     * Update EmployeeContact
     *
     * @param employeeContact
     */
    void update(EmployeeContact employeeContact);

    /**
     * Find EmployeeContact by employeeId
     *
     * @param employeeId
     */
    Optional<EmployeeContact> getByEmployeeId(String employeeId);
}
