package nts.uk.ctx.sys.auth.dom.adapter.employee.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.adapter.person.EmployeeBasicInforAuthImport;
import nts.uk.ctx.sys.auth.dom.adapter.person.PersonAdapter;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffWorkplaceHistImport;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.AffiliationWorkplace;
import nts.uk.ctx.sys.auth.dom.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.sys.auth.dom.algorithm.EmpReferenceRangeService;
import nts.uk.ctx.sys.auth.dom.role.EmployeeReferenceRange;
import nts.uk.ctx.sys.auth.dom.role.Role;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManager;
import nts.uk.ctx.sys.auth.dom.wkpmanager.WorkplaceManagerRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class EmployeeService {
	

}
