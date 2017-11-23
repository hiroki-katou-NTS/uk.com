package nts.uk.screen.com.ws;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import companyinfor.Cmm001AddCommand;
import companyinfor.Cmm001AddCommandHandler;
import companyinfor.Cmm001DeleteCommand;
import companyinfor.Cmm001DeleteCommandHandler;
import companyinfor.Cmm001UpdateCommand;
import companyinfor.Cmm001UpdateCommandHandler;
import nts.arc.layer.ws.WebService;
/**
 * 
 * @author yennth
 *
 */
@Path("screen/com/cmm001")
@Produces("application/json")
public class Cmm001Ws extends WebService{
	@Inject
	private Cmm001UpdateCommandHandler update;
	
	@Inject
	private Cmm001AddCommandHandler add;
	
	@Inject
	private Cmm001DeleteCommandHandler delete;
	
	/**
	 * update cmm001
	 * @param cm
	 */
	@POST
	@Path("update")
	public void update(Cmm001UpdateCommand cm){
		this.update.handle(cm);
	}
	
	/**
	 * insert cmm001
	 * @param cm
	 */
	@POST
	@Path("add")
	public void add(Cmm001AddCommand cm){
		this.add.handle(cm);
	}
	
	/**
	 * delete cmm001  
	 * @param cm
	 */
	@POST
	@Path("del")
	public void del(Cmm001DeleteCommand cm){
		this.delete.handle(cm);
	}
}
