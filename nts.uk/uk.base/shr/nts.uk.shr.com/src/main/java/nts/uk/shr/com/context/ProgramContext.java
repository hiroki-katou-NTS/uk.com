package nts.uk.shr.com.context;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;

import lombok.Getter;

/**
 * @author manhnd
 * 
 * Program context
 */
@SessionScoped
public class ProgramContext implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -695603970971474875L;
	
	/**
	 * Program Id.
	 */
	@Getter
	private String programId;
	
	/**
	 * Jumps to program.
	 * 
	 * @param programId programId.
	 */
	public void jumpTo(String programId) {
		this.programId = programId;
	}

}
