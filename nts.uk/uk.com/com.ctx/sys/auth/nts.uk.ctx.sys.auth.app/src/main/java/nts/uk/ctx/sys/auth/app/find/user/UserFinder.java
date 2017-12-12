package nts.uk.ctx.sys.auth.app.find.user;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.User;
import nts.uk.ctx.sys.auth.dom.user.UserRepository;

@Stateless
public class UserFinder {

	@Inject
	private UserRepository userRepo;
	
	public List<UserDto> searchUser(String userNameID ){
		GeneralDate date = GeneralDate.today();
		if(userNameID == null){
			throw new BusinessException("Msg_438");
		}
		List<UserDto> listUserDto = userRepo.searchUser(userNameID, date).stream().map(c -> UserDto.fromDomain(c)).collect(Collectors.toList());
		//Sort
		listUserDto = listUserDto.stream().sorted(Comparator.comparing(UserDto::getUserID)).collect(Collectors.toList());
		return listUserDto;
	
		
	}
	public List<UserDto> getAllUser(){
		return userRepo.getAllUser().stream().map(c -> UserDto.fromDomain(c)).collect(Collectors.toList());
	}
	
	public List<UserDto> findByKey(UserKeyDto userKeyDto){
		return userRepo.findByKey(userKeyDto.getKey(), userKeyDto.isSpecial(), userKeyDto.isMulti()).stream().map(c -> UserDto.fromDomain(c)).collect(Collectors.toList());		
	}
	
 	
}
