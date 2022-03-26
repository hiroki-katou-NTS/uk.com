package nts.uk.ctx.at.shared.dom.supportmanagement;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import lombok.val;
/**
 * 応援状況のUTコード
 * @author lan_lt
 *
 */
public class SupportStatusTest {
	
	/**
	 * target: canUpdateWorkSchedule
	 */
	@Test
	public void testCanUpdateWorkSchedule() {
		//Map<対象応援状況, 期待値>
		Map<SupportStatus , Boolean > targets = new HashMap<SupportStatus , Boolean>() {
			private static final long serialVersionUID = 1L;
			
			{
				put(SupportStatus.DO_NOT_GO, true);//応援に行かない
				put(SupportStatus.DO_NOT_COME, false);//応援に来ない
				put(SupportStatus.GO_ALLDAY, true);//応援に行く(終日)
				put(SupportStatus.GO_TIMEZONE, true);//応援に行く(時間帯)
				put(SupportStatus.COME_ALLDAY, true);//応援に来る(終日)
				put(SupportStatus.COME_TIMEZONE, false);//応援に来る(時間帯)
			}
			
		};
		
		
		targets.entrySet().stream().forEach(supportStatus ->{
			
			//Act
			val result = supportStatus.getKey().canUpdateWorkSchedule();
			
			//Assert
			assertThat( result).isEqualTo( supportStatus.getValue() );
		});
		
	}
	
	/**
	 * target: canConfirmWorkSchedule
	 */
	@Test
	public void testCanConfirmWorkSchedule() {
		//Map<対象応援状況, 期待値>
		Map<SupportStatus , Boolean > targets = new HashMap<SupportStatus , Boolean>() {
			private static final long serialVersionUID = 1L;
			
			{
				put(SupportStatus.DO_NOT_GO, true);//応援に行かない
				put(SupportStatus.DO_NOT_COME, false);//応援に来ない
				put(SupportStatus.GO_ALLDAY, false);//応援に行く(終日)
				put(SupportStatus.GO_TIMEZONE, true);//応援に行く(時間帯)
				put(SupportStatus.COME_ALLDAY, true);//応援に来る(終日)
				put(SupportStatus.COME_TIMEZONE, false);//応援に来る(時間帯)
			}
		};
		
		targets.entrySet().stream().forEach(supportStatus ->{
			
			//Act
			val result = supportStatus.getKey().canConfirmWorkSchedule();
			
			//Assert
			assertThat( result).isEqualTo( supportStatus.getValue() );
		});
	}
}
