package nts.uk.ctx.exio.app.command.exo.awdataformat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AWDataFormatCommand {
	 
	/**
	    * 会社ID
	    */
	    private String cid;
	    
	    /**
	    * 休業時出力
	    */
	    private String closedOutput;
	    
	    /**
	    * 休職時出力
	    */
	    private String absenceOutput;
	    
	    /**
	    * 固定値
	    */
	    private int fixedValue;
	    
	    /**
	    * 固定値の値
	    */
	    private String valueOfFixedValue;
	    
	    /**
	    * 在職時出力
	    */
	    private String atWorkOutput;
	    
	    /**
	    * 退職時出力
	    */
	    private String retirementOutput;
}
