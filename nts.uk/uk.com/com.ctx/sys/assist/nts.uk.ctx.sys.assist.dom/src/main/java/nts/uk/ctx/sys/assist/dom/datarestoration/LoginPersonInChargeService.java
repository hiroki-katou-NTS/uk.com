package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.role.RoleImportAdapter;
import nts.uk.ctx.sys.assist.dom.storage.SystemType;
import nts.uk.shr.com.context.loginuser.role.LoginUserRoles;

@Stateless
public class LoginPersonInChargeService {
	@Inject 
	private RoleImportAdapter roleAdapter;
	
	public LoginPersonInCharge getPic(LoginUserRoles roles) {
		return roleAdapter.getInChargeInfo();
	}
	
	public List<SystemType> getSystemTypes(LoginPersonInCharge pic) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<SystemType> systemTypes = new ArrayList<SystemType>();
		for(SystemType type: SystemType.values()) {
			Optional<Method> op = getMethodLike(trimName(type.toString()));
			if (op.isPresent() && ((boolean) op.get().invoke(pic))) {
				systemTypes.add(type);
			}
		}
		return systemTypes;
	}
	
	private String trimName(String name) {
		try {
			return name.substring(0, name.indexOf("_SYSTEM")).replace("_", "");
		} catch (StringIndexOutOfBoundsException e) {
			return name.replace("_", "");
		}
	}
	
	private Optional<Method> getMethodLike(String name) {
		return Arrays.stream(LoginPersonInCharge.class.getDeclaredMethods())
													.filter(m -> m.getName().toLowerCase().indexOf(name.toLowerCase()) >= 0
																 && m.getParameterCount() == 0)
													.findFirst();
	}
}
